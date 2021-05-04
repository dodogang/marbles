package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.world.gen.feature.SaltSpikesFeature;
import net.dodogang.marbles.world.gen.feature.SaltSpireFeature;
import net.dodogang.marbles.world.gen.feature.SaltStumpFeature;
import net.dodogang.marbles.world.gen.feature.YellowBambooFeature;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.*;

public class MarblesFeatures {
    public static final Feature<ProbabilityConfig> YELLOW_BAMBOO = register(YellowBambooFeature.id, new YellowBambooFeature(ProbabilityConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> SALT_STUMP = register(SaltStumpFeature.id, new SaltStumpFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> SALT_SPIRE = register(SaltSpireFeature.id, new SaltSpireFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DiskFeatureConfig> DISK = register("disk", new DiskFeature(DiskFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> SALT_SPIKES = register(SaltSpikesFeature.id, new SaltSpikesFeature(DefaultFeatureConfig.CODEC));

    public MarblesFeatures() {
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String id, F feature) {
        return Registry.register(Registry.FEATURE, new Identifier(Marbles.MOD_ID, id), feature);
    }
}
