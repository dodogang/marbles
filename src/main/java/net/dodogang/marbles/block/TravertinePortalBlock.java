package net.dodogang.marbles.block;

import net.dodogang.marbles.util.TravertinePortalHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

@SuppressWarnings("deprecation")
public class TravertinePortalBlock extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = Properties.HORIZONTAL_AXIS;
    protected static final VoxelShape X_SHAPE = Block.createCuboidShape(0, 0, 6, 16, 16, 10);
    protected static final VoxelShape Z_SHAPE = Block.createCuboidShape(6, 0, 0, 10, 16, 16);

    public TravertinePortalBlock(AbstractBlock.Settings settings) {
        super(settings);
        setDefaultState(stateManager.getDefaultState().with(AXIS, Direction.Axis.X));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(AXIS)) {
            case Z:
                return Z_SHAPE;
            case X:
            default:
                return X_SHAPE;
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getDimension().isNatural() && world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING) && random.nextInt(2000) < world.getDifficulty().getId()) {
            while (world.getBlockState(pos).isOf(this)) {
                pos = pos.down();
            }

            if (world.getBlockState(pos).allowsSpawning(world, pos, EntityType.ZOMBIFIED_PIGLIN)) {
                Entity entity = EntityType.ZOMBIFIED_PIGLIN.spawn(world, null, null, null, pos.up(), SpawnReason.STRUCTURE, false, false);
                if (entity != null) {
                    entity.resetNetherPortalCooldown();
                }
            }
        }

    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction dir, BlockState adjState, WorldAccess world, BlockPos pos, BlockPos adjPos) {
        Direction.Axis eventAxis = dir.getAxis();
        Direction.Axis myAxis = state.get(AXIS);
        boolean ignore = myAxis != eventAxis && eventAxis.isHorizontal();
        return !ignore && !adjState.isOf(this) && !new TravertinePortalHelper(world, pos, myAxis).wasAlreadyValid()
               ? Blocks.AIR.getDefaultState()
               : super.getStateForNeighborUpdate(state, dir, adjState, world, pos, adjPos);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals()) {
            entity.setInNetherPortal(pos);
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(100) == 0) {
            world.playSound(
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                SoundEvents.BLOCK_PORTAL_AMBIENT,
                SoundCategory.BLOCKS,
                0.5f, random.nextFloat() * 0.4f + 0.8f,
                false
            );
        }

        for (int i = 0; i < 4; ++i) {
            double particleX = pos.getX() + random.nextDouble();
            double particleY = pos.getY() + random.nextDouble();
            double particleZ = pos.getZ() + random.nextDouble();
            double motionX = (random.nextDouble() - 0.5) * 0.5;
            double motionY = (random.nextDouble() - 0.5) * 0.5;
            double motionZ = (random.nextDouble() - 0.5) * 0.5;

            int axisModifier = random.nextInt(2) * 2 - 1;
            if (!world.getBlockState(pos.west()).isOf(this) && !world.getBlockState(pos.east()).isOf(this)) {
                particleX = pos.getX() + 0.5 + 0.25 * axisModifier;
                motionX = random.nextFloat() * 2 * axisModifier;
            } else {
                particleZ = pos.getZ() + 0.5 + 0.25 * axisModifier;
                motionZ = random.nextFloat() * 2 * axisModifier;
            }

            world.addParticle(ParticleTypes.PORTAL, particleX, particleY, particleZ, motionX, motionY, motionZ);
        }

    }

    @Override
    @Environment(EnvType.CLIENT)
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        switch (rotation) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch (state.get(AXIS)) {
                    case Z:
                        return state.with(AXIS, Direction.Axis.X);
                    case X:
                        return state.with(AXIS, Direction.Axis.Z);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    public float getPortalTravelSpeedAdditional() {
        return 2.0f;
    }
}
