package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.world.gen.feature.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.DiskFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class MarblesFeatures {
    public static final Feature<ProbabilityConfig> YELLOW_BAMBOO = register("yellow_bamboo", new YellowBambooFeature(ProbabilityConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> PINK_SALT_STUMP_CLUSTER = register("pink_salt_stump_cluster", new PinkSaltStumpClusterFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> PINK_SALT_SPIRE = register("pink_salt_spire", new PinkSaltSpireFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DiskFeatureConfig> DISK = register("disk", new MarblesDiskFeature(DiskFeatureConfig.CODEC));
    public static final Feature<StateProvidedChanceDiskFeatureConfig> STATE_PROVIDED_DISK = register("state_provided_disk", new StateProvidedChanceDiskFeature(StateProvidedChanceDiskFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> PINK_SALT_SPIKES = register("pink_salt_spikes", new PinkSaltSpikesFeature(DefaultFeatureConfig.CODEC));
    public static Feature<PermafrostIceSpikeFeatureConfig> PERMAFROST_ICE_SPIKE = register("permafrost_ice_spike", new PermafrostIceSpikeFeature(PermafrostIceSpikeFeatureConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String id, F feature) {
        return Registry.register(Registry.FEATURE, new Identifier(Marbles.MOD_ID, id), feature);
    }
}
