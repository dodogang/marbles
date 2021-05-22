package net.dodogang.marbles.world.gen.chunk.generator.config;

import net.dodogang.marbles.init.MarblesBiomes;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.world.gen.feature.MarblesConfiguredFeatureLists;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class PinkSaltCaveGeneratorConfig extends BridgedCaveBiomeGeneratorConfig {
    @Override
    public @NotNull BlockState getBodyBlockState(BlockPos pos, Random random) {
        return States.PINK_SALT;
    }
    @Override
    public @NotNull BlockState getFillerBlockState(BlockPos pos, Random random) {
        if (pos.getY() <= 12) {
            return States.WATER;
        }

        return States.PINK_SALT_CAVE_AIR;
    }
    @Override
    public @NotNull List<ConfiguredFeature<?, ?>> getFeatures() {
        return MarblesConfiguredFeatureLists.PINK_SALT_CAVE;
    }
    @Override
    public @NotNull RegistryKey<Biome> getBiome() {
        return MarblesBiomes.PINK_SALT_CAVE;
    }

    private static class States {
        private static final BlockState PINK_SALT = MarblesBlocks.PINK_SALT.getDefaultState();
        private static final BlockState PINK_SALT_CAVE_AIR = MarblesBlocks.PINK_SALT_CAVE_AIR.getDefaultState();
        private static final BlockState WATER = Blocks.WATER.getDefaultState();
    }
}
