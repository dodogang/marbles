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
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Objects;
import java.util.Random;

@SuppressWarnings("deprecation")
public class AbstractLightRetainingBlock extends Block implements Waterloggable {
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
            ? itemStack.getItem() instanceof BlockItem && new ItemPlacementContext(player, hand, itemStack, hit).canPlace()
                ? ActionResult.PASS
                : ActionResult.CONSUME
            : ActionResult.SUCCESS;

        if (world.isClient && retainedLight != 0) {
            if (actionResult == ActionResult.SUCCESS) spawnParticles(world, pos);
        } else {
            this.light(state, world, pos, retainedLight);
        }

        return actionResult;
    }

    protected void light(BlockState state, World world, BlockPos pos, int light) {
        if (light != 0) AbstractLightRetainingBlock.spawnParticles(world, pos);
        world.setBlockState(pos, state.with(RETAINED_LIGHT, light), 3);
    }

    private static void spawnParticles(World world, BlockPos pos) {
        Random random = world.random;
        Direction[] directions = Direction.values();

        for (Direction direction : directions) {
            BlockPos blockPos = pos.offset(direction);
            if (!world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) {
                Direction.Axis axis = direction.getAxis();
                double x = axis == Direction.Axis.X ? 0.5D + 0.12D * (double) direction.getOffsetX() : (double) random.nextFloat();
                double y = axis == Direction.Axis.Y ? 0.5D + 0.12D * (double) direction.getOffsetY() : (double) random.nextFloat();
                double z = axis == Direction.Axis.Z ? 0.5D + 0.12D * (double) direction.getOffsetZ() : (double) random.nextFloat();
                world.addParticle(MarblesParticles.PINK_SALT, (double) pos.getX() + x, (double) pos.getY() + y, (double) pos.getZ() + z, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
