package net.dodogang.marbles.world.gen.chunk.generator.config;

import net.dodogang.marbles.init.MarblesBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class IceCaveGeneratorConfig extends BridgedCaveBiomeGeneratorConfig {
    private final RegistryKey<Biome> biome;
    private final List<ConfiguredFeature<?,?>> features;
    private final BlockState bodyBlockState;

    public IceCaveGeneratorConfig(RegistryKey<Biome> biome, List<ConfiguredFeature<?, ?>> features, Block block) {
        this.biome = biome;
        this.features = features;
        this.bodyBlockState = block.getDefaultState();
    }

    @Override
    public @NotNull BlockState getBodyBlockState(BlockPos pos, Random random) {
        return pos.getY() <= 24 && random.nextInt((int) Math.max((pos.getY() - 6) * 1.8d, 1)) <= 3 ? States.PACKED_ICE : this.bodyBlockState;
    }

    @Override
    public @NotNull BlockState getFillerBlockState(BlockPos pos, Random random) {
        if (pos.getY() <= 20) {
            return States.WATER;
        }

        return States.ICE_CAVE_AIR;
    }

    @Override
    public @NotNull List<ConfiguredFeature<?, ?>> getFeatures() {
        return this.features;
    }

    @Override
    public @NotNull RegistryKey<Biome> getBiome() {
        return this.biome;
    }

    private static class States {
        private static final BlockState PACKED_ICE = Blocks.PACKED_ICE.getDefaultState();
        private static final BlockState ICE_CAVE_AIR = MarblesBlocks.ICE_CAVE_AIR.getDefaultState();
        private static final BlockState WATER = Blocks.WATER.getDefaultState();
    }
}
