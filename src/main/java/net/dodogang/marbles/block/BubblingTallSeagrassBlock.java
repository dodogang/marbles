package net.dodogang.marbles.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallSeagrassBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class BubblingTallSeagrassBlock extends TallSeagrassBlock implements Bubbling {
    private final Supplier<Block> shortVariant;

    public BubblingTallSeagrassBlock(Supplier<Block> shortVariant, Settings settings) {
        super(settings);
        this.shortVariant = shortVariant;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(shortVariant.get());
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        this.spawnBubbles(world, pos, random, state.getModelOffset(world, pos));
    }
}
