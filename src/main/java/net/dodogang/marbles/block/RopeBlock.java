package net.dodogang.marbles.block;

import net.dodogang.marbles.block.enums.OptionalHorizontalDirection;
import net.dodogang.marbles.block.enums.RopePart;
import net.dodogang.marbles.mixin.block.AbstractBlockStateAccessor;
import net.dodogang.marbles.state.property.MarblesProperties;
import net.dodogang.marbles.tag.MarblesBlockTags;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
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
    public static final EnumProperty<RopePart> PART = MarblesProperties.ROPE_PART;
    public static final EnumProperty<OptionalHorizontalDirection> CONNECTION = MarblesProperties.HORIZONTAL_CONNECTION;

    public static final VoxelShape SHAPE = Block.createCuboidShape(5.0d, 0.0d, 5.0d, 11.0d, 16.0d, 11.0d);

    public RopeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(CONNECTION, OptionalHorizontalDirection.NONE).with(WATERLOGGED, false).with(PART, RopePart.MIDDLE));
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction side) {
        AbstractBlockState.ShapeCache shapeCache = ((AbstractBlockStateAccessor) stateFrom).getShapeCache();
        return (side.getAxis().isVertical() && stateFrom.isOf(this)) || (shapeCache != null && shapeCache.isFullCube);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED, PART, CONNECTION);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }
    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(CONNECTION).isPresent() ? VoxelShapes.fullCube() : SHAPE;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        Direction connectionDirection = state.get(CONNECTION).getDirection();
        if ((connectionDirection == direction || connectionDirection == null) && !this.doesStateSupport(world, pos, connectionDirection)) {
            return Blocks.AIR.getDefaultState();
        } else {
            return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom)
                        .with(PART, this.getRopePart(world, pos));
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getPlacementState(ctx.getWorld(), ctx.getBlockPos());
    }
    protected BlockState getPlacementState(World world, BlockPos pos) {
        OptionalHorizontalDirection connection = this.getConnection(world, pos);
        if (connection == null) {
            return null;
        }

        return this.getDefaultState().with(PART, this.getRopePart(world, pos))
                                     .with(CONNECTION, connection)
                                     .with(Properties.WATERLOGGED, world.getFluidState(pos).isIn(FluidTags.WATER));
    }

    public RopePart getRopePart(WorldView world, BlockPos pos) {
        return world.getBlockState(pos.up()).isOf(this)
            ? world.getBlockState(pos.down()).isOf(this)
                ? RopePart.MIDDLE
                : RopePart.END
            : RopePart.START;
    }
    @Nullable
    public OptionalHorizontalDirection getConnection(WorldView world, BlockPos pos) {
        for (OptionalHorizontalDirection ioptdir : OptionalHorizontalDirection.values()) {
            if (this.doesStateSupport(world, pos, ioptdir.isPresent() ? ioptdir.getDirection() : Direction.UP)) {
                return ioptdir;
            }
        }

        return null;
    }

    protected boolean doesStateSupport(WorldView world, BlockPos pos, @Nullable Direction direction) {
        direction = direction == null ? Direction.UP : direction;

        BlockPos posof = pos.offset(direction);
        BlockState state = world.getBlockState(posof);
        return (direction == Direction.UP && state.isOf(this)) || Block.isShapeFullCube(state.getCollisionShape(world, pos)) || MarblesBlockTags.SPECIAL_ROPE_SUPPORTS.contains(state.getBlock());
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);

        if (!world.isClient && player.getMainHandStack().isIn(FabricToolTags.SHEARS) && !player.isSneaking()) {
            BlockPos.Mutable mpos = pos.up().mutableCopy();
            while (world.getBlockState(mpos).getBlock() instanceof RopeBlock) {
                world.breakBlock(mpos, true, player);
                mpos.move(Direction.UP);
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (hit.getSide().getAxis().isHorizontal()) {
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

                    BlockState istate = world.getBlockState(mpos);
                    if (istate.getMaterial().isReplaceable() && this.canPlaceAt(this.getDefaultState(), world, mpos)) {
                        world.setBlockState(mpos, this.getPlacementState(world, mpos));

                        if (!player.isCreative()) {
                            stack.decrement(1);
                        }
                        if (!world.isClient) {
                            SoundEvent placeSound = this.soundGroup.getPlaceSound();
                            float volume = this.soundGroup.getVolume();
                            float pitch = this.soundGroup.getPitch();

                            world.playSoundFromEntity(null, player, placeSound, SoundCategory.PLAYERS, volume, pitch);
                            world.playSound(null, mpos, placeSound, SoundCategory.BLOCKS, volume, pitch);
                        }
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }
}
