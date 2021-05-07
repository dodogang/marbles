package net.dodogang.marbles.block;

import me.andante.chord.block.vanilla.CScaffoldingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class YellowScaffoldingBlock extends CScaffoldingBlock {
    public static final String id = "yellow_scaffolding";

    private static final VoxelShape NORMAL_OUTLINE_SHAPE = VoxelShapes.union(
        Block.createCuboidShape(0.0D, 13.0D, 0.0D, 16.0D, 15.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 3.0D),
        Block.createCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D),
        Block.createCuboidShape(0.0D, 0.0D, 13.0D, 3.0D, 16.0D, 16.0D),
        Block.createCuboidShape(13.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D)
    );
    private static final VoxelShape NORMAL_COLLISION_SHAPE = VoxelShapes.union(
        Block.createCuboidShape(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 3.0D),
        Block.createCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D),
        Block.createCuboidShape(0.0D, 0.0D, 13.0D, 3.0D, 16.0D, 16.0D),
        Block.createCuboidShape(13.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D)
    );
    private static final VoxelShape BOTTOM_OUTLINE_SHAPE = VoxelShapes.union(
        NORMAL_OUTLINE_SHAPE,
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
        Block.createCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 2.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 3.0D),
        Block.createCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 2.0D, 16.0D)
    );

    private static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    private static final VoxelShape OUTLINE_SHAPE = VoxelShapes.fullCube().offset(0.0D, -1.0D, 0.0D);

    public YellowScaffoldingBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (!context.isHolding(state.getBlock().asItem())) {
            return state.get(BOTTOM) ? BOTTOM_OUTLINE_SHAPE : NORMAL_OUTLINE_SHAPE;
        } else {
            return VoxelShapes.fullCube();
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context.isAbove(VoxelShapes.fullCube(), pos, true) && !context.isDescending()) {
            return NORMAL_COLLISION_SHAPE;
        } else {
            return state.get(DISTANCE) != 0 && state.get(BOTTOM) && context.isAbove(OUTLINE_SHAPE, pos, true) ? COLLISION_SHAPE : VoxelShapes.empty();
        }
    }
}
