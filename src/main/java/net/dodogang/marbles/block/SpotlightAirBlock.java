package net.dodogang.marbles.block;

import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.state.property.MarblesProperties;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

@SuppressWarnings("deprecation")
public class SpotlightAirBlock extends AirBlock {
    public static final DirectionProperty FACING = Properties.FACING;
    public static final IntProperty DISTANCE = MarblesProperties.DISTANCE_0_31;

    public SpotlightAirBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, DISTANCE);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState source, WorldAccess world, BlockPos pos, BlockPos srcPos) {
        Direction facing = state.get(FACING);
        if (direction == facing.getOpposite()) {
            Block srcBlock = source.getBlock();
            if (srcBlock instanceof SpotlightAirBlock) {
                int dist = source.get(DISTANCE) + 1;
                if (dist <= 31) {
                    return state.with(DISTANCE, dist);
                } else {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                    return Blocks.AIR.getDefaultState();
                }
            } else if (srcBlock instanceof SpotlightBlock) {
                return state.with(DISTANCE, 0);
            } else {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                return Blocks.AIR.getDefaultState();
            }
        } else if (direction == facing) {
            addMoreAir(world, srcPos, facing);
        }
        return super.getStateForNeighborUpdate(state, direction, source, world, pos, srcPos);
    }

    public static void addMoreAir(WorldAccess world, BlockPos pos, Direction facing) {
        BlockState state = world.getBlockState(pos);
        if (state.isAir() && !(state.getBlock() instanceof SpotlightAirBlock) && !World.isOutOfBuildLimitVertically(pos)) {
            BlockPos srcPos = pos.offset(facing, -1);
            BlockState source = world.getBlockState(srcPos);
            Block srcBlock = source.getBlock();

            if (srcBlock instanceof SpotlightAirBlock) {
                int dist = source.get(DISTANCE) + 1;
                if (dist <= 31) {
                    world.setBlockState(pos, MarblesBlocks.SPOTLIGHT_AIR.getDefaultState().with(DISTANCE, dist).with(FACING, facing), 2 | 16);
                } else {
                    return;
                }
            } else if (srcBlock instanceof SpotlightBlock) {
                world.setBlockState(pos, MarblesBlocks.SPOTLIGHT_AIR.getDefaultState().with(DISTANCE, 0).with(FACING, facing), 2 | 16);
            }

            addMoreAir(world, pos.offset(facing), facing);
        }
    }
}
