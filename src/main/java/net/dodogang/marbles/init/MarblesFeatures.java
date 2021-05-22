package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.world.gen.feature.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.*;

public class MarblesFeatures {
    public static final Feature<ProbabilityConfig> YELLOW_BAMBOO = register(YellowBambooFeature.id, new YellowBambooFeature(ProbabilityConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> PINK_SALT_STUMP_CLUSTER = register(PinkSaltStumpClusterFeature.id, new PinkSaltStumpClusterFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> PINK_SALT_SPIRE = register(PinkSaltSpireFeature.id, new PinkSaltSpireFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DiskFeatureConfig> DISK = register("disk", new DiskFeature(DiskFeatureConfig.CODEC));
    public static final Feature<StateProvidedChanceDiskFeatureConfig> STATE_PROVIDED_DISK = register("state_provided_disk", new StateProvidedChanceDiskFeature(StateProvidedChanceDiskFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> PINK_SALT_SPIKES = register(PinkSaltSpikesFeature.id, new PinkSaltSpikesFeature(DefaultFeatureConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String id, F feature) {
        return Registry.register(Registry.FEATURE, new Identifier(Marbles.MOD_ID, id), feature);
    }
}
