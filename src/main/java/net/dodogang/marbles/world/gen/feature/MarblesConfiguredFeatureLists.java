package net.dodogang.marbles.world.gen.feature;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.List;

import static net.dodogang.marbles.init.MarblesConfiguredFeatures.*;

public class MarblesConfiguredFeatureLists {
    public static final List<ConfiguredFeature<?, ?>> PINK_SALT_CAVE = ImmutableList.of(
        PINK_SALT_STUMP_CLUSTER,
        PINK_SALT_SPIRE,
        PINK_SALT_CAVE_GRANITE_DISK,
        PINK_SALT_CAVE_CRUMBLED_SALT_DISK,
        PINK_SALT_SPIKE_PATCH
    );
    public static final List<ConfiguredFeature<?, ?>> ICE_CAVE = ImmutableList.of(
        ICE_CAVE_FLOESTONE_DISK
    );
    public static final List<ConfiguredFeature<?, ?>> SCALED_ICE_CAVE = ICE_CAVE;
    public static final List<ConfiguredFeature<?, ?>> MINTED_ICE_CAVE = ICE_CAVE;
}
