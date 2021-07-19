package net.dodogang.marbles.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.dodogang.marbles.block.enums.QuadBlockPart;
import net.dodogang.marbles.state.property.MarblesProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class QuadAttachingBlock extends AbstractAttachingBlock {
    public static final EnumProperty<QuadBlockPart> PART = MarblesProperties.QUAD_BLOCK_PART;

    public static final ImmutableList<ImmutableMap<Direction, VoxelShape>> SHAPES;
    static {
        ImmutableList.Builder<ImmutableMap<Direction, VoxelShape>> builder = new ImmutableList.Builder<>();

        Direction[] facingValues = AbstractAttachingBlock.FACING.getValues().toArray(new Direction[]{});
        int partCount = QuadBlockPart.values().length;
        double sizePerPart = 16d / partCount;

        for (int ipart = 0; ipart < partCount; ipart++) {
            ImmutableMap.Builder<Direction, VoxelShape> partShapes = new ImmutableMap.Builder<>();
            double partSize = (ipart + 1) * sizePerPart;

            for (Direction direction : facingValues) {
                partShapes.put(
                    direction,
                    switch (direction) {
                        default -> Block.createCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, partSize, 16.0d);
                        case DOWN -> Block.createCuboidShape(0.0d, 16.0d - partSize, 0.0d, 16.0d, 16.0d, 16.0d);
                        case NORTH -> Block.createCuboidShape(0.0d, 0.0d, 16.0d - partSize, 16.0d, 16.0d, 16.0d);
                        case SOUTH -> Block.createCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 16.0d, partSize);
                        case EAST -> Block.createCuboidShape(0.0d, 0.0d, 0.0d, partSize, 16.0d, 16.0d);
                        case WEST -> Block.createCuboidShape(16.0d - partSize, 0.0d, 0.0d, 16.0d, 16.0d, 16.0d);
                    }
                );
            }

            builder.add(partShapes.build());
        }

        SHAPES = builder.build();
    }

    public QuadAttachingBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(PART, QuadBlockPart.FIRST));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return SHAPES.get(state.get(PART).ordinal()).get(state.get(AbstractAttachingBlock.FACING));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(PART);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext ctx) {
        return !ctx.shouldCancelInteraction() && ctx.getSide() == state.get(AbstractAttachingBlock.FACING) && ctx.getStack().isOf(this.asItem()) && state.get(PART).isLowerThan(QuadBlockPart.FOURTH) || super.canReplace(state, ctx);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (state.isOf(this)) {
            QuadBlockPart part = state.get(PART);
            return state.with(PART, part != QuadBlockPart.MOST ? part.next() : part);
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
            boolean fluidIsWater = fluidState.getFluid() == Fluids.WATER;
            BlockState placementState = super.getPlacementState(ctx);

            return (placementState == null ? this.getDefaultState() : placementState).with(WATERLOGGED, fluidIsWater);
        }
    }
}
