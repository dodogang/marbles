package net.dodogang.marbles.block;

import net.dodogang.marbles.block.enums.RopePart;
import net.dodogang.marbles.tag.MarblesBlockTags;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class RopeBlock extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final EnumProperty<RopePart> PART = EnumProperty.of("rope_part", RopePart.class);

    public static final VoxelShape SHAPE = Block.createCuboidShape(6.0d, 0.0d, 6.0d, 10.0d, 16.0d, 10.0d);

    public RopeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(PART, RopePart.MIDDLE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED).add(PART);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (direction == Direction.UP && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        } else {
            return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom).with(PART, this.getRopePart((World) world, pos));
        }
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState superState = super.getPlacementState(ctx);
        return superState == null ? null : superState.with(PART, this.getRopePart(ctx.getWorld(), ctx.getBlockPos()));
    }

    public RopePart getRopePart(World world, BlockPos pos) {
        return world.getBlockState(pos.down()).isOf(this)
            ? world.getBlockState(pos.up()).isOf(this)
                ? RopePart.MIDDLE
                : RopePart.START
            : RopePart.END;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (Block.sideCoversSmallSquare(world, pos.up(), Direction.DOWN)) {
            return true;
        } else {
            Block support = world.getBlockState(pos.up()).getBlock();
            return support instanceof RopeBlock || MarblesBlockTags.SPECIAL_ROPE_SUPPORTS.contains(support);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack != null) {
            Item item = stack.getItem();
            if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof RopeBlock) {
                BlockPos.Mutable mpos = pos.down().mutableCopy();
                while (world.getBlockState(mpos).getBlock() instanceof RopeBlock) {
                    mpos.move(Direction.DOWN);
                }

                if (mpos.getY() < 0) {
                    return super.onUse(state, world, pos, player, hand, hit);
                }

                if (world.getBlockState(mpos).getMaterial().isReplaceable() && this.canPlaceAt(this.getDefaultState(), world, mpos)) {
                    world.setBlockState(mpos, this.getDefaultState().with(WATERLOGGED, world.getFluidState(mpos).isIn(FluidTags.WATER)));

                    if (!player.isCreative()) {
                        stack.decrement(1);
                    }

                    return ActionResult.SUCCESS;
                }
            }
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }
}
