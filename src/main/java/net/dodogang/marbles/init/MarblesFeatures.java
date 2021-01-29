package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.world.gen.feature.YellowBambooFeature;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class MarblesFeatures {
    public static final Feature<ProbabilityConfig> YELLOW_BAMBOO = register(YellowBambooFeature.id, new YellowBambooFeature(ProbabilityConfig.CODEC));

    public MarblesFeatures() {}

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String id, F feature) {
        return Registry.register(Registry.FEATURE, new Identifier(Marbles.MOD_ID, id), feature);
    }
}
