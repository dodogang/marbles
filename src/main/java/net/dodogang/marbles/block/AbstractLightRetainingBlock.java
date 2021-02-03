package net.dodogang.marbles.block;

import net.dodogang.marbles.init.MarblesParticles;
import net.dodogang.marbles.state.property.MarblesProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Objects;
import java.util.Random;

@SuppressWarnings("deprecation")
public abstract class AbstractLightRetainingBlock extends Block implements Waterloggable {
    protected static final IntProperty RETAINED_LIGHT = MarblesProperties.RETAINED_LIGHT;
    protected static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public AbstractLightRetainingBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(RETAINED_LIGHT, 0).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(RETAINED_LIGHT, WATERLOGGED);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int oldRetainedLight = state.get(RETAINED_LIGHT);
        int retainedLight = Math.max(0, Math.max(world.getLightLevel(LightType.BLOCK, pos), oldRetainedLight) - 1);

        ItemStack itemStack = player.getStackInHand(hand);
        ActionResult actionResult = oldRetainedLight == retainedLight
            ? (itemStack.getItem() instanceof BlockItem && new ItemPlacementContext(player, hand, itemStack, hit).canPlace())
                ? ActionResult.PASS
                : ActionResult.CONSUME
            : ActionResult.SUCCESS;

        if (world.isClient && oldRetainedLight != 0) {
            this.spawnParticles(world, pos);
            return ActionResult.SUCCESS;
        } else {
            this.light(state, world, pos, retainedLight);
        }

        return actionResult;
    }

    protected void light(BlockState state, World world, BlockPos pos, int light) {
        if (light != 0) this.spawnParticles(world, pos);
        world.setBlockState(pos, state.with(RETAINED_LIGHT, light), 3);
    }

    protected final void spawnParticles(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        int retainedLight = state.get(RETAINED_LIGHT);
        for (int i = 0; i < ((retainedLight == 0 ? 15 : retainedLight) * getBonusParticleMultiplier()); i++) {
            Random random = world.random;
            double horizontalOffsetRange = this.getHorizontalParticleOffsetRange();
            double randX = random.nextFloat() * horizontalOffsetRange;
            double randZ = random.nextFloat() * horizontalOffsetRange;

            Vec3d offsetPos = Vec3d.ofBottomCenter(pos).add(state.getModelOffset(world, pos));
            double x = offsetPos.getX() + (random.nextBoolean() ? randX : -randX);
            double y = offsetPos.getY() + this.getVerticalParticleOffset() + (double)random.nextFloat() * 0.3D;
            double z = offsetPos.getZ() + (random.nextBoolean() ? randZ : -randZ);

            world.addParticle(MarblesParticles.PINK_SALT, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }

    protected abstract double getHorizontalParticleOffsetRange();
    protected abstract double getVerticalParticleOffset();
    protected double getBonusParticleMultiplier() {
        return 1.2D;
    }
}
