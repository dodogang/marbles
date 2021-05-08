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
}
