package net.dodogang.marbles.block;

import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.init.MarblesParticles;
import net.dodogang.marbles.state.property.MarblesProperties;
import net.dodogang.marbles.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Random;

@SuppressWarnings("deprecation")
public class PinkSaltSpireBlockOld extends FallingBlock implements Waterloggable {
    public static final String id = "pink_salt_spire";

    public static final DirectionProperty VERTICAL_DIRECTION = MarblesProperties.VERTICAL_DIRECTION;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final VoxelShape SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public PinkSaltSpireBlockOld(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(VERTICAL_DIRECTION, Direction.UP).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(VERTICAL_DIRECTION, WATERLOGGED);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlaceAtWithDirection(world, pos, state.get(VERTICAL_DIRECTION)) || canPlaceAtWithDirection(world, pos, state.get(VERTICAL_DIRECTION).getOpposite());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            return state;
        }

        if (direction != Direction.UP && direction != Direction.DOWN) {
            return state;
        } else if (world.getBlockTickScheduler().isScheduled(pos, this)) {
            return state;
        } else {
            Direction verticalDirection = state.get(VERTICAL_DIRECTION);
            if (direction == verticalDirection.getOpposite() && !canPlaceAtWithDirection(world, pos, verticalDirection)) {
                if (verticalDirection == Direction.DOWN) {
                    this.scheduleFall(state, world, pos);
                } else {
                    world.getBlockTickScheduler().schedule(pos, this, this.getFallDelay());
                }
                state = state.with(VERTICAL_DIRECTION, Direction.DOWN);
                return state;
            } else {
                return state;
            }
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextDouble() <= 0.3D) {
            double clamp = 0.3D;
            double x = pos.getX() + (random.nextBoolean() ? 0.2D : -0.2D) + Math.min(1 - clamp, Math.max(clamp, random.nextDouble()));
            double y = pos.getY() - 0.2D;
            double z = pos.getZ() + (random.nextBoolean() ? 0.2D : -0.2D) + Math.min(1 - clamp, Math.max(clamp, random.nextDouble()));

            world.addParticle(MarblesParticles.PINK_SALT, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }

    private static BlockState getFluidBlockState(BlockState blockState) {
        return blockState.get(WATERLOGGED) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (projectile.getVelocity().length() > 0.6D && projectile instanceof PersistentProjectileEntity) {
            BlockPos pos = hit.getBlockPos();

            if (projectile instanceof TridentEntity) {
                world.breakBlock(pos, true);
            } else {
                this.scheduleFall(state, world, pos);
            }

            Random random = world.random;
            for (int i = 0; i < 40; i++) {
                double clamp = 0.3D;
                double x = pos.getX() + (random.nextBoolean() ? 0.2D : -0.2D) + Math.min(1 - clamp, Math.max(clamp, random.nextDouble()));
                double y = pos.getY() + (random.nextBoolean() ? 0.2D : -0.2D) + Math.min(1 - clamp, Math.max(clamp, random.nextDouble()));
                double z = pos.getZ() + (random.nextBoolean() ? 0.2D : -0.2D) + Math.min(1 - clamp, Math.max(clamp, random.nextDouble()));

                world.addParticle(MarblesParticles.PINK_SALT, x, y, z, 0.0D, -0.1D, 0.0D);
            }
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        spawnFallingBlock(state, world, pos);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        Direction oppositeLookDirection = Util.getLookDirectionForAxis(ctx.getPlayer(), Direction.Axis.Y);
        Direction directionToPlaceAt = getDirectionToPlaceAt(world, pos, oppositeLookDirection);
        if (directionToPlaceAt != Direction.DOWN && directionToPlaceAt != Direction.UP) {
            return null;
        } else {
            return this.getDefaultState()
                       .with(VERTICAL_DIRECTION, directionToPlaceAt)
                       .with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER);
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        return SHAPE.offset(vec3d.x, 0.0D, vec3d.z);
    }

    @Override
    public AbstractBlock.OffsetType getOffsetType() {
        return AbstractBlock.OffsetType.XZ;
    }

    private void scheduleFall(BlockState state, WorldAccess world, BlockPos pos) {
        BlockPos tipPos = getTip(state, world, pos, Integer.MAX_VALUE);
        if (tipPos != null) {
            BlockPos.Mutable mutable = tipPos.mutableCopy();

            while (isPointingDown(world.getBlockState(mutable))) {
                world.getBlockTickScheduler().schedule(mutable, this, 2);
                mutable.move(Direction.UP);
            }
        }
    }

    private static void spawnFallingBlock(BlockState state, ServerWorld world, BlockPos pos) {
        Vec3d vec3d = Vec3d.ofBottomCenter(pos).add(state.getModelOffset(world, pos));
        FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, vec3d.x, vec3d.y, vec3d.z, state.with(VERTICAL_DIRECTION, state.get(VERTICAL_DIRECTION).getOpposite()));

        world.spawnEntity(fallingBlockEntity);
    }

    private static BlockPos getTip(BlockState blockState, WorldAccess world, BlockPos pos, int i) {
        if (isTip(blockState, world, pos)) {
            return pos;
        } else {
            Direction direction = blockState.get(VERTICAL_DIRECTION);
            for (int j = 0; j < i; j++) {
                BlockPos off = pos.offset(direction, j);
                if (isTip(world.getBlockState(off), world, off)) {
                    return off;
                }
            }
            return null;
        }
    }

    private static Direction getDirectionToPlaceAt(WorldView world, BlockPos pos, Direction direction) {
        Direction placeDirection;
        if (canPlaceAtWithDirection(world, pos, direction)) {
            placeDirection = direction;
        } else {
            if (!canPlaceAtWithDirection(world, pos, direction.getOpposite())) {
                return null;
            }

            placeDirection = direction.getOpposite();
        }

        return placeDirection;
    }

    private static boolean canPlaceAtWithDirection(WorldView world, BlockPos pos, Direction direction) {
        BlockPos offsetPos = pos.offset(direction.getOpposite());
        BlockState state = world.getBlockState(offsetPos);
        return state.isSideSolidFullSquare(world, offsetPos, direction) || isFacingDirection(state, direction);
    }

    private static boolean isTip(BlockState state, BlockView world, BlockPos pos) {
        if (!state.isOf(MarblesBlocks.PINK_SALT_SPIRE)) {
            return false;
        } else {
            Direction vdir = state.get(VERTICAL_DIRECTION);
            BlockPos off = pos.offset(vdir, 1);
            BlockState neighbor = world.getBlockState(off);
            return !neighbor.isOf(MarblesBlocks.PINK_SALT_SPIRE) || neighbor.get(VERTICAL_DIRECTION) != vdir;
        }
    }

    private static boolean isPointingDown(BlockState state) {
        return isFacingDirection(state, Direction.DOWN);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    private static boolean isFacingDirection(BlockState state, Direction direction) {
        return state.isOf(MarblesBlocks.PINK_SALT_SPIRE) && state.get(VERTICAL_DIRECTION) == direction;
    }
}
