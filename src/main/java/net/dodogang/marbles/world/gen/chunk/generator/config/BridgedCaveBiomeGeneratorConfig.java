package net.dodogang.marbles.world.gen.chunk.generator.config;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public abstract class BridgedCaveBiomeGeneratorConfig {
    /**
     * Cave field noise size, determines where more and less caves generate.
     */
    public double getFieldSize() {
        return 228.0d;
    }

    /**
     * Cave level (height) noise size.
     */
    public double getLevelSize() {
        return 104.46d;
    }

    /**
     * Size of random cave noise.
     */
    public double getNoiseSize() {
        return 40.0d;
    }

    /**
     * Multiplier of random cave noise.
     */
    public double getNoiseScale() {
        return 3.0d;
    }

    /**
     * Size of vertical offset noise.
     */
    public double getOffsetSize() {
        return 48.0d;
    }

    /**
     * Multiplier of vertical offset scale.
     */
    public double getOffsetScale() {
        return 6.0d;
    }

    /**
     * Horizontal size of noise field that generates bridges and pillars.
     */
    public double getBridgeSizeH() {
        return 15.0d;
    }

    /**
     * Vertical size of noise field that generates bridges and pillars.
     */
    public double getBridgeSizeV() {
        return 7.5d;
    }

    /**
     * Multiplier of bridge noise.
     */
    public double getBridgeScale() {
        return 12.0d;
    }

    /**
     * Addend of bridge noise.
     */
    public double getBridgeAdd() {
        return 6.0d;
    }

    /**
     * Overall rarity of caves.
     */
    public double getCaveRarity() {
        return 32.0d;
    }

    /**
     * Overall scaling of caves.
     */
    public double getCaveSize() {
        return 2.8d;
    }

    /**
     * Minimum horizontal cave radius (gets scaled by {@link BridgedCaveBiomeGeneratorConfig#getCaveSize}).
     */
    public double getMinRadius() {
        return 10.0d;
    }

    /**
     * Maximum horizontal cave radius (gets scaled by {@link BridgedCaveBiomeGeneratorConfig#getCaveSize}).
     */
    public double getMaxRadius() {
        return 17.0d;
    }

    /**
     * Minimum layer height (gets scaled by {@link BridgedCaveBiomeGeneratorConfig#getCaveSize}).
     */
    public double getMinLayerSize() {
        return 6.0d;
    }

    /**
     * Maximum layer height (gets scaled by {@link BridgedCaveBiomeGeneratorConfig#getCaveSize}).
     */
    public double getMaxLayerSize() {
        return 8.0d;
    }

    public abstract @NotNull BlockState getBodyBlockState(BlockPos pos, Random random);
    public abstract @NotNull BlockState getFillerBlockState(BlockPos pos, Random random);
    public abstract @NotNull List<ConfiguredFeature<?, ?>> getFeatures();
    public abstract @NotNull RegistryKey<Biome> getBiome();
}
