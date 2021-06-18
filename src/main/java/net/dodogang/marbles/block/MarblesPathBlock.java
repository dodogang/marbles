package net.dodogang.marbles.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirtPathBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;
import java.util.function.Supplier;

public class MarblesPathBlock extends DirtPathBlock {
    protected final Supplier<Block> hostBlockSupplier;
    protected Block hostBlock = null;

    public MarblesPathBlock(Supplier<Block> hostBlock, Settings settings) {
        super(settings);
        this.hostBlockSupplier = hostBlock;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, pushEntitiesUpBeforeBlockChange(state, this.getHostBlock().getDefaultState(), world, pos));
    }

    protected Block getHostBlock() {
        return hostBlock == null ? hostBlockSupplier.get() : hostBlock;
    }
}
