package net.dodogang.marbles.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.Chunk;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SpotlightBlock extends Block {
    public static final DirectionProperty FACING = Properties.FACING;

    public SpotlightBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public static Stream<BlockPos> findSpotlightLightSources(Chunk chunk) {
        return StreamSupport.stream(BlockPos.iterate(chunk.getPos().getStartX(), 0, chunk.getPos().getStartZ(), chunk.getPos().getEndX(), 255, chunk.getPos().getEndZ()).spliterator(), false)
                            .filter(pos -> chunk.getBlockState(pos).getBlock() instanceof SpotlightBlock)
                            .flatMap(pos -> IntStream.range(0, 10).mapToObj(pos::up));
    }

//    @Override
//    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
//        super.onPlaced(world, pos, state, placer, itemStack);
//    }




    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState adjState, WorldAccess world, BlockPos pos, BlockPos adjPos) {
        Direction facing = state.get(FACING);
        if (direction == facing) {
            SpotlightAirBlock.addMoreAir(world, adjPos, facing);
        }
        return super.getStateForNeighborUpdate(state, direction, adjState, world, pos, adjPos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);

        Direction facing = state.get(FACING);
        SpotlightAirBlock.addMoreAir(world, pos.offset(facing), facing);
    }

//    @Override
//    @SuppressWarnings("deprecation")
//    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
//        super.onStateReplaced(state, world, pos, newState, moved);
//
//        for (int i = 0; i < 10; i ++) {
//            BlockEntity be = world.getBlockEntity(pos.up(i));
//            if (be instanceof SpotlightBlockEntity) {
//                world.removeBlockEntity(pos.up(i));
//            }
//            world.getLightingProvider().checkBlock(pos.up(i));
//        }
//    }
}
