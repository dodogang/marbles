package net.dodogang.marbles.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import net.dodogang.marbles.init.MarblesBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class PermafrostSurfaceBuilder extends DefaultSurfaceBuilder {
    public PermafrostSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int minY, long seed, TernarySurfaceConfig config) {
        super.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, minY, seed, config);

        int relX = x & 15;
        int relZ = z & 15;
        BlockPos.Mutable mpos = new BlockPos.Mutable(relX, 0, relZ);
        for (int y = chunk.getBottomY(); y < chunk.getTopY(); y++) {
            mpos.setY(y);
            if (chunk.getBlockState(mpos).equals(defaultBlock)) {
                chunk.setBlockState(mpos, MarblesBlocks.FLOESTONE.getDefaultState(), false);
            }
        }

        /*for (int y = chunk.getBottomY(); y < seaLevel; y++) {
            mpos.setY(y);

            BlockState state = chunk.getBlockState(mpos);
            if (state.isIn(BlockTags.ICE)) {
                Block block = MarblesBlocks.SLUSH;

                if (state.isOf(Blocks.BLUE_ICE)) {
                    block = MarblesBlocks.BLUE_SLUSH;
                } else if (state.isOf(MarblesBlocks.SCALED_ICE)) {
                    block = MarblesBlocks.SCALED_SLUSH;
                } else if (state.isOf(MarblesBlocks.MINTED_ICE)) {
                    block = MarblesBlocks.MINTED_SLUSH;
                }

                chunk.setBlockState(mpos, block.getDefaultState(), false);
            }
        }*/
    }
}
