package net.dodogang.marbles.block;

import net.minecraft.block.*;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class PollenGracedBedBlock extends BedBlock {
    public static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0.0D, 4.0D, 0.0D, 16.0D, 10.0D, 16.0D);
    public static final VoxelShape LEG_1_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 4.0D, 4.0D, 4.0D);
    public static final VoxelShape LEG_2_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 12.0D, 4.0D, 4.0D, 16.0D);
    public static final VoxelShape LEG_3_SHAPE = Block.createCuboidShape(12.0D, 0.0D, 0.0D, 16.0D, 4.0D, 4.0D);
    public static final VoxelShape LEG_4_SHAPE = Block.createCuboidShape(12.0D, 0.0D, 12.0D, 16.0D, 4.0D, 16.0D);
    public static final VoxelShape NORTH_SHAPE = VoxelShapes.union(TOP_SHAPE, LEG_1_SHAPE, LEG_3_SHAPE);
    public static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(TOP_SHAPE, LEG_2_SHAPE, LEG_4_SHAPE);
    public static final VoxelShape WEST_SHAPE = VoxelShapes.union(TOP_SHAPE, LEG_1_SHAPE, LEG_2_SHAPE);
    public static final VoxelShape EAST_SHAPE = VoxelShapes.union(TOP_SHAPE, LEG_3_SHAPE, LEG_4_SHAPE);

    public PollenGracedBedBlock(Settings settings) {
        super(DyeColor.YELLOW, settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = getOppositePartDirection(state).getOpposite();
        switch(direction) {
            case NORTH:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
                return EAST_SHAPE;
        }
    }
}
