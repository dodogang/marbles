package net.dodogang.marbles.world.gen.feature;

import net.dodogang.marbles.init.MarblesConfiguredFeatures;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;

public class MarblesDefaultBiomeFeatures {
    public static void addAspenTrees(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, MarblesConfiguredFeatures.TREES_ASPEN);
    }
    public static void addHoopsiSpruceTrees(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, MarblesConfiguredFeatures.TREES_HOOPSI_SPRUCE);
    }

    public static void addYellowBamboo(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, MarblesConfiguredFeatures.YELLOW_BAMBOO_LIGHT);
    }
    public static void addYellowBambooJungleTrees(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, MarblesConfiguredFeatures.YELLOW_BAMBOO);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.BAMBOO_VEGETATION);
    }
}
