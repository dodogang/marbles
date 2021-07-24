package net.dodogang.marbles.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

@SuppressWarnings("deprecation")
public class RingedFloestoneBlock extends AbstractAttachingBlock {
    protected static final VoxelShape EAST_SHAPE = VoxelShapes.union(
        Block.createCuboidShape(0, 12, 4, 4, 16, 16),
        Block.createCuboidShape(0, 4, 0, 4, 16, 4),
        Block.createCuboidShape(0, 0, 0, 4, 4, 12),
        Block.createCuboidShape(0, 0, 12, 4, 12, 16)
    );
    protected static final VoxelShape WEST_SHAPE = VoxelShapes.union(
        Block.createCuboidShape(12, 12, 4, 16, 16, 16),
        Block.createCuboidShape(12, 4, 0, 16, 16, 4),
        Block.createCuboidShape(12, 0, 0, 16, 4, 12),
        Block.createCuboidShape(12, 0, 12, 16, 12, 16)
    );
    protected static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
        Block.createCuboidShape(0, 12, 0, 12, 16, 4),
        Block.createCuboidShape(12, 4, 0, 16, 16, 4),
        Block.createCuboidShape(4, 0, 0, 16, 4, 4),
        Block.createCuboidShape(0, 0, 0, 4, 12, 4)
    );
    protected static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
        Block.createCuboidShape(0, 12, 12, 12, 16, 16),
        Block.createCuboidShape(12, 4, 12, 16, 16, 16),
        Block.createCuboidShape(4, 0, 12, 16, 4, 16),
        Block.createCuboidShape(0, 0, 12, 4, 12, 16)
    );
    protected static final VoxelShape UP_SHAPE = VoxelShapes.union(
        Block.createCuboidShape(0, 0, 0, 12, 4, 4),
        Block.createCuboidShape(12, 0, 0, 16, 4, 12),
        Block.createCuboidShape(4, 0, 12, 16, 4, 16),
        Block.createCuboidShape(0, 0, 4, 4, 4, 16)
    );
    protected static final VoxelShape DOWN_SHAPE = VoxelShapes.union(
        Block.createCuboidShape(0, 12, 0, 12, 16, 4),
        Block.createCuboidShape(12, 12, 0, 16, 16, 12),
        Block.createCuboidShape(4, 12, 12, 16, 16, 16),
        Block.createCuboidShape(0, 12, 4, 4, 16, 16)
    );

    protected static final VoxelShape EAST_OUTLINE_SHAPE = Block.createCuboidShape(0, 0, 0, 4, 16, 16);
    protected static final VoxelShape WEST_OUTLINE_SHAPE = Block.createCuboidShape(12, 0, 0, 16, 16, 16);
    protected static final VoxelShape SOUTH_OUTLINE_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 4);
    protected static final VoxelShape NORTH_OUTLINE_SHAPE = Block.createCuboidShape(0, 0, 12, 16, 16, 16);
    protected static final VoxelShape UP_OUTLINE_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 4, 16);
    protected static final VoxelShape DOWN_OUTLINE_SHAPE = Block.createCuboidShape(0, 12, 0, 16, 16, 16);

    public RingedFloestoneBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            default -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            case UP -> UP_SHAPE;
            case DOWN -> DOWN_SHAPE;
        };
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            default -> NORTH_OUTLINE_SHAPE;
            case SOUTH -> SOUTH_OUTLINE_SHAPE;
            case WEST -> WEST_OUTLINE_SHAPE;
            case EAST -> EAST_OUTLINE_SHAPE;
            case UP -> UP_OUTLINE_SHAPE;
            case DOWN -> DOWN_OUTLINE_SHAPE;
        };
    }
}
