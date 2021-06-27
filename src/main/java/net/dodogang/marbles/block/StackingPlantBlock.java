package net.dodogang.marbles.block;

import me.andante.chord.block.enums.TripleBlockPart;
import me.andante.chord.state.property.CProperties;
import net.minecraft.block.*;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class StackingPlantBlock extends PlantBlock implements Fertilizable {
    public static final EnumProperty<TripleBlockPart> PART = CProperties.TRIPLE_BLOCK_PART;

    public StackingPlantBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(PART, TripleBlockPart.UPPER));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(PART);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        BlockPos.Mutable mpos = pos.mutableCopy().move(Direction.UP);
        while (true) {
            int y = mpos.getY();
            if (y >= world.getHeight()) {
                return false;
            }

            BlockState mstate = world.getBlockState(mpos);
            if (mstate.isAir()) {
                break;
            } else if (!mstate.isOf(this)) {
                return false;
            }

            mpos.move(Direction.UP);
        }

        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int count = random.nextInt(2) + 1;
        for (int i = 0; i < count; i++) {
            BlockPos.Mutable mpos = pos.mutableCopy().move(Direction.UP);
            while (true) {
                int y = mpos.getY();
                if (y >= world.getHeight()) {
                    return;
                }

                BlockState mstate = world.getBlockState(mpos);
                if (mstate.isAir()) {
                    break;
                } else if (!mstate.isOf(this)) {
                    return;
                }

                mpos.move(Direction.UP);
            }

            world.setBlockState(mpos, this.getPlacementState(new AutomaticItemPlacementContext(world, mpos, Direction.UP, ItemStack.EMPTY, Direction.DOWN)));
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return !state.canPlaceAt(world, pos)
            ? Blocks.AIR.getDefaultState()
            : direction == Direction.UP
                ? state.with(
                    PART,
                    neighborState.isOf(this)
                        ? world.getBlockState(pos.down()).isOf(this)
                            ? TripleBlockPart.MIDDLE
                            : TripleBlockPart.LOWER
                        : TripleBlockPart.UPPER
                )
                : state;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos posDown = pos.down();
        BlockState stateDown = world.getBlockState(posDown);
        return stateDown.isOf(this) || this.canPlantOnTop(stateDown, world, posDown);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = this.getDefaultState();

        return state.with(
            PART,
            !world.getBlockState(pos.up()).isOf(this)
                ? TripleBlockPart.UPPER
                : world.getBlockState(pos.down()).isOf(this)
                    ? TripleBlockPart.MIDDLE
                    : TripleBlockPart.LOWER
        );
    }
}
