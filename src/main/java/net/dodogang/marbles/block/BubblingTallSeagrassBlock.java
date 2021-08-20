package net.dodogang.marbles.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.TallSeagrassBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BubblingTallSeagrassBlock extends TallSeagrassBlock implements Bubbling {
    public BubblingTallSeagrassBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        this.spawnBubbles(world, pos, random);
    }
}
