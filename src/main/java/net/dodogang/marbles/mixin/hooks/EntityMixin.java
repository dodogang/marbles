package net.dodogang.marbles.mixin.hooks;

import net.dodogang.marbles.MarblesClient;
import net.dodogang.marbles.block.TravertinePortalBlock;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.mixin.TravertinePortalingEntity;
import net.dodogang.marbles.util.TravertinePortalForcer;
import net.dodogang.marbles.util.TravertinePortalHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.PortalUtil;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@SuppressWarnings({"ConstantConditions","unused"})
@Mixin(Entity.class)
public abstract class EntityMixin implements TravertinePortalingEntity {
    private static final Logger LOGGER = LogManager.getLogger();

    @Shadow public World world;

    @Shadow
    public abstract double getX();

    @Shadow
    public abstract double getY();

    @Shadow
    public abstract double getZ();

    @Shadow
    protected BlockPos lastNetherPortalPosition;

    @Shadow
    public abstract EntityDimensions getDimensions(EntityPose pose);

    @Shadow
    public abstract EntityPose getPose();

    @Shadow
    public abstract Vec3d getVelocity();

    @Shadow public float yaw;

    @Shadow public float pitch;

    @Shadow
    public abstract Vec3d getPos();

    @Shadow protected int netherPortalTime;

    @Inject(
        method = "tickNetherPortal",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/world/ServerWorld;getServer()Lnet/minecraft/server/MinecraftServer;"
        )
    )
    private void onTickNetherPortal(CallbackInfo cb) {
        Block block = this.world.getBlockState(lastNetherPortalPosition).getBlock();
        if (block instanceof TravertinePortalBlock) {
            this.netherPortalTime += ((TravertinePortalBlock) block).getPortalTravelSpeedAdditional(); // Speed up
        }
    }

    @Environment(EnvType.CLIENT)
    @Inject(method = "setInNetherPortal", at = @At("HEAD"))
    private void catchNetherPortalState(BlockPos pos, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null && client.world != null) {
            MarblesClient.lastNetherPortalState = client.world.getBlockState(pos);
        }
    }

    @Inject(
        method = "getTeleportTarget",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;method_30330(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Z)Ljava/util/Optional;"
        ),
        cancellable = true
    )
    private void onGetTeleportTarget(ServerWorld destination, CallbackInfoReturnable<TeleportTarget> cb) {
        BlockState portal = world.getBlockState(lastNetherPortalPosition);
        if (portal.isOf(MarblesBlocks.TRAVERTINE_PORTAL)) {
            Optional<TeleportTarget> target = findTravertineTeleportTarget(destination);
            cb.setReturnValue(target.orElse(null));
            cb.cancel();
        }
    }

    private Optional<TeleportTarget> findTravertineTeleportTarget(ServerWorld destination) {

        boolean isNether = destination.getRegistryKey() == World.NETHER;

        WorldBorder worldBorder = destination.getWorldBorder();
        double westBorder = Math.max(-30000000, worldBorder.getBoundWest() + 16);
        double northBorder = Math.max(-30000000, worldBorder.getBoundNorth() + 16);
        double eastBorder = Math.min(30000000, worldBorder.getBoundEast() - 16);
        double southBorder = Math.min(30000000, worldBorder.getBoundSouth() - 16);
        double coordScale = DimensionType.method_31109(world.getDimension(), destination.getDimension());

        BlockPos destPos = new BlockPos(
            MathHelper.clamp(getX() * coordScale, westBorder, eastBorder),
            getY(),
            MathHelper.clamp(getZ() * coordScale, northBorder, southBorder)
        );

        Optional<PortalUtil.Rectangle> optPortal = TravertinePortalForcer.findPortal(destination, destPos, isNether);

        // If we're a player, try to create a portal if it's not present
        if (ServerPlayerEntity.class.isInstance(this) && !optPortal.isPresent()) {
            Direction.Axis axis = world.getBlockState(lastNetherPortalPosition).method_28500(NetherPortalBlock.AXIS).orElse(Direction.Axis.X);
            Optional<PortalUtil.Rectangle> createdPortal = TravertinePortalForcer.createNewPortal(destination, destPos, axis);
            if (!createdPortal.isPresent()) {
                LOGGER.error("Unable to create a portal, likely target out of worldborder");
            }

            optPortal = createdPortal;
        }

        return optPortal.map(arg -> {
            BlockState portalState = world.getBlockState(lastNetherPortalPosition);
            Direction.Axis portalAxis;
            Vec3d interpolator;

            if (portalState.contains(Properties.HORIZONTAL_AXIS)) {
                portalAxis = portalState.get(Properties.HORIZONTAL_AXIS);
                // Looks up the size of the portal in the world (this class holds a position and a size)
                PortalUtil.Rectangle portal = PortalUtil.getLargestRectangle(
                    lastNetherPortalPosition,
                    portalAxis,
                    TravertinePortalHelper.MAX_WIDTH,
                    Direction.Axis.Y,
                    TravertinePortalHelper.MAX_HEIGHT,
                    pos -> world.getBlockState(pos) == portalState
                );
                interpolator = computeInterpolator(portalAxis, portal);
            } else {
                portalAxis = Direction.Axis.X;
                interpolator = new Vec3d(0.5, 0, 0);
            }

            return TravertinePortalHelper.computeTeleportTarget(destination, arg, portalAxis, interpolator, getDimensions(getPose()), getVelocity(), yaw, pitch);
        });
    }

    private Vec3d computeInterpolator(Direction.Axis axis, PortalUtil.Rectangle portal) {
        Vec3d ipl = TravertinePortalHelper.computeInterpolator(portal, axis, getPos(), getDimensions(getPose()));
        if (LivingEntity.class.isInstance(this)) {
            ipl = LivingEntity.method_31079(ipl);
        }
        return ipl;
    }
}
