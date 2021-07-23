package net.dodogang.marbles.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SandBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class SnowySandBlock extends SandBlock {
    public static final BooleanProperty SNOWY = Properties.SNOWY;

    public SnowySandBlock(int color, Settings settings) {
        super(color, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(SNOWY, false));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.UP
            ? state.with(SNOWY, this.isSnow(neighborState))
            : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
        return this.getDefaultState().with(SNOWY, this.isSnow(state));
    }

    public boolean isSnow(BlockState state) {
        return state.isIn(BlockTags.SNOW);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SNOWY);
    }
}
