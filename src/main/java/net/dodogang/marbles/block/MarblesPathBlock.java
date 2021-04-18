package net.dodogang.marbles.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassPathBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class MarblesPathBlock extends GrassPathBlock {
    protected final Block hostBlock;

    public MarblesPathBlock(Block hostBlock, Settings settings) {
        super(settings);
        this.hostBlock = hostBlock;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, pushEntitiesUpBeforeBlockChange(state, hostBlock.getDefaultState(), world, pos));
    }
}
