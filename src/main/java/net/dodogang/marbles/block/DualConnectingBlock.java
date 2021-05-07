package net.dodogang.marbles.block;

import net.dodogang.marbles.block.enums.OptionalDirection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class DualConnectingBlock extends Block {
    public static final EnumProperty<OptionalDirection> CONNECTION = EnumProperty.of("connection", OptionalDirection.class);

    public DualConnectingBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(CONNECTION, OptionalDirection.NONE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CONNECTION);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        if (ctx.shouldCancelInteraction()) {
            return this.getDefaultState();
        }

        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();

        for (Direction direction : ctx.getPlacementDirections()) {
            BlockPos near = pos.offset(direction);
            BlockState nearState = world.getBlockState(near);

            if (nearState.isOf(this) && nearState.get(CONNECTION) == OptionalDirection.NONE) {
                return this.getDefaultState().with(CONNECTION, OptionalDirection.fromDirection(direction));
            }
        }

        return this.getDefaultState();
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState adjState, WorldAccess world, BlockPos pos, BlockPos adjPos) {
        if (state.get(CONNECTION) == OptionalDirection.NONE && adjState.isOf(this)) {
            if (adjState.get(CONNECTION).getDirection() == direction.getOpposite()) {
                return state.with(CONNECTION, OptionalDirection.fromDirection(direction));
            }
        } else if (state.get(CONNECTION).getDirection() == direction) {
            if (!adjState.isOf(this) || adjState.get(CONNECTION).getDirection() != direction.getOpposite()) {
                return state.with(CONNECTION, OptionalDirection.NONE);
            }
        }
        return super.getStateForNeighborUpdate(state, direction, adjState, world, pos, adjPos);
    }
}
