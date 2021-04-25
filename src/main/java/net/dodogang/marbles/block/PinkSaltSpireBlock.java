package net.dodogang.marbles.block;

import net.dodogang.marbles.init.MarblesParticles;
import net.dodogang.marbles.state.property.MarblesProperties;
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

import java.util.Arrays;
import java.util.Random;

@SuppressWarnings("deprecation")
public class PinkSaltSpireBlock extends FallingBlock implements Waterloggable {
    public static final String id = "pink_salt_spire";

    public static final DirectionProperty VERTICAL_DIRECTION = MarblesProperties.VERTICAL_DIRECTION;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final VoxelShape SHAPE = createCuboidShape(4, 0, 4, 12, 16, 12);

    public PinkSaltSpireBlock(Settings settings) {
        super(settings);
        setDefaultState(
            stateManager.getDefaultState()
                        .with(VERTICAL_DIRECTION, Direction.UP)
                        .with(WATERLOGGED, false)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(
            VERTICAL_DIRECTION,
            WATERLOGGED
        );
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos up = pos.up();
        BlockPos down = pos.down();
        BlockState above = world.getBlockState(up);
        BlockState below = world.getBlockState(down);

        // Check if above or below is feasible surface
        return isValidSurface(below, world, down, Direction.UP) || isSelfFacing(below, Direction.UP) || isValidSurface(above, world, up, Direction.DOWN) || isSelfFacing(above, Direction.DOWN);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighbor, WorldAccess world, BlockPos pos, BlockPos adj) {
        Direction pvdir = state.get(VERTICAL_DIRECTION);
        Direction nvdir = pvdir.getOpposite();

        // Tick fluid because..mojang logic
        if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        // The block that supports me changed, decide what to do
        if (direction == nvdir) {
            if (neighbor.isOf(this)) {
                // We're being supported by another salt spire
                Direction adjdir = neighbor.get(VERTICAL_DIRECTION);
                if (adjdir == nvdir) {
                    // If my supporter changed direction somehow, let's just change myself too
                    return state.with(VERTICAL_DIRECTION, nvdir);
                } else {
                    return state;
                }
            }

            if (!isValidSurface(neighbor, world, adj, direction.getOpposite())) {
                // I'm not supported anymore, trying to connect to another spire by changing direction

                BlockPos off = pos.offset(pvdir);
                BlockState supportable = world.getBlockState(off);
                if (isValidSurface(supportable, world, off, nvdir) || isSelfFacing(supportable, nvdir)) {
                    return state.with(VERTICAL_DIRECTION, nvdir);
                } else {
                    // If I failed connecting to something else: fall
                    scheduleFall(state, world, pos);
                }
            }
        }
        return state;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = getDefaultState();

        BlockPos up = pos.up();
        BlockPos down = pos.down();
        BlockState above = world.getBlockState(up);
        BlockState below = world.getBlockState(down);

        boolean canPlaceUp = isValidSurface(above, world, up, Direction.DOWN) || isSelfFacing(above, Direction.DOWN);
        boolean canPlaceDown = isValidSurface(below, world, down, Direction.UP) || isSelfFacing(below, Direction.UP);

        Direction vdir;
        if (canPlaceUp && canPlaceDown) {
            // Both directions work, determine based on player
            vdir = ctx.getSide(); // Targeted side

            if (vdir.getAxis().isHorizontal()) {
                // If targeted side is horizontal, select the closed look direction of the player that is vertical
                Direction[] placementDirs = ctx.getPlacementDirections();
                vdir = Arrays.stream(placementDirs)
                             .filter(dir -> dir.getAxis().isVertical())
                             .findFirst().orElseThrow(() -> new AssertionError("Universe is messed up"));
            }
        } else if (canPlaceDown) { // Since we place down, the facing is up
            vdir = Direction.UP;
        } else if (canPlaceUp) {   // ... and vice versa
            vdir = Direction.DOWN;
        } else {
            return null; // Can't place, cancel placement by returning null
        }

        boolean waterlogged = world.getFluidState(pos).getFluid() == Fluids.WATER;
        return state.with(VERTICAL_DIRECTION, vdir).with(WATERLOGGED, waterlogged);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {

    }

    private boolean isValidSurface(BlockState state, BlockView world, BlockPos pos, Direction facing) {
        return state.isSideSolidFullSquare(world, pos, facing);
    }

    private boolean isSelfFacing(BlockState state, Direction facing) {
        return state.isOf(this) && state.get(VERTICAL_DIRECTION) == facing;
    }

    private static void generateParticle(World world, BlockPos pos, Random rng, Vec3d offset) {
        double x = pos.getX() + (rng.nextBoolean() ? 0.26 : -0.26) + rng.nextDouble() * 0.05 + 0.5 + offset.x;
        double y = pos.getY() + rng.nextDouble();
        double z = pos.getZ() + (rng.nextBoolean() ? 0.26 : -0.26) + rng.nextDouble() * 0.05 + 0.5 + offset.z;

        world.addParticle(MarblesParticles.PINK_SALT, x, y, z, 0, 0, 0);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random rng) {
        if (rng.nextDouble() <= 0.3) {
            generateParticle(world, pos, rng, state.getModelOffset(world, pos));
        }
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (projectile.getVelocity().length() > 0.6D && projectile instanceof PersistentProjectileEntity) {
            BlockPos pos = hit.getBlockPos();

            if (projectile instanceof TridentEntity) {
                world.breakBlock(pos, true);
            } else {
                scheduleFall(state, world, pos);
            }

            Random random = world.random;
            for (int i = 0; i < 40; i++) {
                generateParticle(world, pos, random, state.getModelOffset(world, pos));
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
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    private void scheduleFall(BlockState state, WorldAccess world, BlockPos pos) {
        Direction vdir = state.get(VERTICAL_DIRECTION);

        BlockPos.Mutable mpos = pos.mutableCopy();
        while (isSelfFacing(world.getBlockState(mpos), vdir)) {
            mpos.move(vdir);
        }

        BlockState support = world.getBlockState(mpos);
        boolean isSupportable = isValidSurface(support, world, mpos, vdir.getOpposite());

        if (vdir == Direction.DOWN) {
            mpos.move(vdir, -1);
            while (isSelfFacing(world.getBlockState(mpos), vdir)) {
                if (isSupportable) {
                    world.setBlockState(mpos, state.with(VERTICAL_DIRECTION, vdir.getOpposite()), 3);
                } else {
                    world.getBlockTickScheduler().schedule(mpos.toImmutable(), this, 0);
                }
                mpos.move(vdir, -1);
            }
        } else {
            mpos.set(pos);
            while (isSelfFacing(world.getBlockState(mpos), vdir)) {
                if (isSupportable) {
                    world.setBlockState(mpos, state.with(VERTICAL_DIRECTION, vdir.getOpposite()), 3);
                } else {
                    world.getBlockTickScheduler().schedule(mpos.toImmutable(), this, 0);
                }
                mpos.move(vdir);
            }
        }
    }

    private static void spawnFallingBlock(BlockState state, ServerWorld world, BlockPos pos) {
        Vec3d vec3d = Vec3d.ofBottomCenter(pos);
        FallingBlockEntity fallingBlock = new FallingBlockEntity(world, vec3d.x, vec3d.y, vec3d.z, state.with(VERTICAL_DIRECTION, Direction.UP));

        world.spawnEntity(fallingBlock);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}
