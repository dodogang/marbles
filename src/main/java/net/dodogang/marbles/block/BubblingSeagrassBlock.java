package net.dodogang.marbles.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.Supplier;

public class BubblingSeagrassBlock extends SeagrassBlock implements Bubbling {
    @Nullable private final Supplier<Block> tallVariant;

    public BubblingSeagrassBlock(@Nullable Supplier<Block> tallVariant, Settings settings) {
        super(settings);
        this.tallVariant = tallVariant;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return super.canGrow(world, random, pos, state) && tallVariant != null;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (this.tallVariant != null) {
            BlockState tallState = this.tallVariant.get().getDefaultState();
            BlockState upperTallState = tallState.with(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
            BlockPos posUp = pos.up();
            if (world.getBlockState(posUp).isOf(Blocks.WATER)) {
                world.setBlockState(pos, tallState, Block.NOTIFY_LISTENERS);
                world.setBlockState(posUp, upperTallState, Block.NOTIFY_LISTENERS);
            }
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        this.spawnBubbles(world, pos, random);
    }
}
