package net.dodogang.marbles.block;

import net.dodogang.marbles.block.enums.SpirePart;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.init.MarblesParticles;
import net.dodogang.marbles.state.property.MarblesProperties;
import net.dodogang.marbles.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.util.sat4j.core.Vec;
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
import net.minecraft.state.property.EnumProperty;
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

import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

@SuppressWarnings("deprecation")
public class PinkSaltSpireBlock extends FallingBlock implements Waterloggable {
    public static final String id = "pink_salt_spire";

    public static final DirectionProperty VERTICAL_DIRECTION = MarblesProperties.VERTICAL_DIRECTION;
    public static final EnumProperty<SpirePart> THICKNESS = MarblesProperties.SPIRE_PART;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final VoxelShape SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public PinkSaltSpireBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(VERTICAL_DIRECTION, Direction.UP).with(THICKNESS, SpirePart.TIP).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(VERTICAL_DIRECTION, THICKNESS, WATERLOGGED);
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
                boolean isTipMerge = state.get(THICKNESS) == SpirePart.TIP_MERGE;
                SpirePart spirePart = getSpirePart(world, pos, verticalDirection, isTipMerge);
                return spirePart == null ? getFluidBlockState(state) : state.with(THICKNESS, spirePart);
            }
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {}

    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (isTip(state) ? random.nextDouble() <= 0.6D : random.nextDouble() <= 0.3D) {
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
        Direction oppositeLookDirection = Utils.getLookDirectionForAxis(ctx.getPlayer(), Direction.Axis.Y);
        Direction directionToPlaceAt = getDirectionToPlaceAt(world, pos, oppositeLookDirection);
        if (directionToPlaceAt != Direction.DOWN && directionToPlaceAt != Direction.UP) {
            return null;
        } else {
            boolean tryMerge = !ctx.shouldCancelInteraction();
            SpirePart spirePart = PinkSaltSpireBlock.getSpirePart(world, pos, directionToPlaceAt, tryMerge);
            return spirePart == null ? null : this.getDefaultState().with(VERTICAL_DIRECTION, directionToPlaceAt).with(THICKNESS, spirePart).with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER);
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
        if (isTip(state)) {
            fallingBlockEntity.setHurtEntities(true);
            fallingBlockEntity.dropItem = false;
        }

        world.spawnEntity(fallingBlockEntity);
    }

    private static BlockPos getTip(BlockState blockState, WorldAccess world, BlockPos pos, int i) {
        if (isTip(blockState)) {
            return pos;
        } else {
            Direction direction = blockState.get(VERTICAL_DIRECTION);
            Predicate<BlockState> predicate = (blockStatex) -> blockStatex.isOf(MarblesBlocks.PINK_SALT_SPIRE) && blockStatex.get(VERTICAL_DIRECTION) == direction;
            return loopUntilPredicate(world, pos, direction.getDirection(), predicate, PinkSaltSpireBlock::isTip, i).orElse(null);
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

    private static SpirePart getSpirePart(WorldView world, BlockPos pos, Direction direction, boolean tryMerge) {
        Direction oppositeDirection = direction.getOpposite();
        BlockState state = world.getBlockState(pos.offset(direction));
        if (isPointedDripstoneFacingDirection(state, oppositeDirection)) {
            return !tryMerge && state.get(THICKNESS) != SpirePart.TIP_MERGE ? SpirePart.TIP : SpirePart.TIP_MERGE;
        } else if (!isPointedDripstoneFacingDirection(state, direction)) {
            return SpirePart.TIP;
        } else {
            SpirePart spirePart = state.get(THICKNESS);
            if (spirePart != SpirePart.TIP && spirePart != SpirePart.TIP_MERGE) {
                BlockState blockState2 = world.getBlockState(pos.offset(oppositeDirection));
                return !isPointedDripstoneFacingDirection(blockState2, direction) ? SpirePart.BASE : SpirePart.MIDDLE;
            } else {
                return SpirePart.FRUSTUM;
            }
        }
    }

    private static boolean canPlaceAtWithDirection(WorldView world, BlockPos pos, Direction direction) {
        BlockPos offsetPos = pos.offset(direction.getOpposite());
        BlockState state = world.getBlockState(offsetPos);
        return state.isSideSolidFullSquare(world, offsetPos, direction) || isPointedDripstoneFacingDirection(state, direction);
    }

    private static boolean isTip(BlockState state) {
        if (!state.isOf(MarblesBlocks.PINK_SALT_SPIRE)) {
            return false;
        } else {
            SpirePart spirePart = state.get(THICKNESS);
            return spirePart == SpirePart.TIP || spirePart == SpirePart.TIP_MERGE;
        }
    }

    private static boolean isPointingDown(BlockState state) {
        return isPointedDripstoneFacingDirection(state, Direction.DOWN);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    private static boolean isPointedDripstoneFacingDirection(BlockState state, Direction direction) {
        return state.isOf(MarblesBlocks.PINK_SALT_SPIRE) && state.get(VERTICAL_DIRECTION) == direction;
    }

    private static Optional<BlockPos> loopUntilPredicate(WorldAccess worldAccess, BlockPos blockPos, Direction.AxisDirection axisDirection, Predicate<BlockState> never, Predicate<BlockState> always, int height) {
        Direction direction = Direction.get(axisDirection, Direction.Axis.Y);
        BlockPos.Mutable mutable = blockPos.mutableCopy();

        for (int i = 0; i < height; i++) {
            mutable.move(direction);
            BlockState blockState = worldAccess.getBlockState(mutable);
            if (always.test(blockState)) {
                return Optional.of(mutable);
            }

            if (mutable.getY() > worldAccess.getHeight()|| !never.test(blockState)) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }
}
