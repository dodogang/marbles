package net.dodogang.marbles.block;

import me.andante.chord.block.enums.TripleBlockPart;
import me.andante.chord.state.property.CProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

@SuppressWarnings("deprecation")
public class SupportingPoleBlock extends Block {
    public static final VoxelShape SHAPE = Block.createCuboidShape(6.0d, 0.0d, 6.0d, 10.0d, 16.0d, 10.0d);
    public static final EnumProperty<TripleBlockPart> PART = CProperties.TRIPLE_BLOCK_PART;

    public final Predicate<BlockState> holds;

    public SupportingPoleBlock(Predicate<BlockState> holds, Settings settings) {
        super(settings);
        this.holds = holds;

        this.setDefaultState(this.stateManager.getDefaultState().with(PART, TripleBlockPart.LOWER));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(PART);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return !state.canPlaceAt(world, pos)
                ? Blocks.AIR.getDefaultState()
                : direction == Direction.UP
                    ? state.with(
                        PART,
                        world.getBlockState(pos.down()).isOf(this)
                            ? holds.test(world.getBlockState(pos.up()))
                                ? TripleBlockPart.UPPER
                                : TripleBlockPart.MIDDLE
                            : TripleBlockPart.LOWER
                    )
                    : state;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        TripleBlockPart part = state.get(PART);
        BlockPos posDown = pos.down();
        BlockState stateDown = world.getBlockState(posDown);
        return (part == TripleBlockPart.LOWER && sideCoversSmallSquare(world, posDown, Direction.UP)) || stateDown.isOf(this);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = this.getDefaultState();

        return state.with(
            PART,
            world.getBlockState(pos.down()).isOf(this)
                ? holds.test(world.getBlockState(pos.up()))
                    ? TripleBlockPart.UPPER
                    : TripleBlockPart.MIDDLE
                : TripleBlockPart.LOWER
        );
    }
}
