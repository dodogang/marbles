package net.dodogang.marbles.world.gen.feature;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import static net.dodogang.marbles.init.MarblesConfiguredFeatures.*;

public class MarblesConfiguredFeatureLists {
    public static final ImmutableList<ConfiguredFeature<?, ?>> PINK_SALT_CAVE = ImmutableList.of(
        PINK_SALT_STUMP_CLUSTER,
        PINK_SALT_SPIRE,
        PINK_SALT_CAVE_GRANITE_DISK,
        PINK_SALT_CAVE_CRUMBLED_SALT_DISK,
        PINK_SALT_SPIKE_PATCH,

        ORE_COAL_PINK_SALT,
        ORE_IRON_PINK_SALT,
        ORE_GOLD_PINK_SALT,
        ORE_REDSTONE_PINK_SALT,
        ORE_DIAMOND_PINK_SALT,
        ORE_LAPIS_PINK_SALT,
        // ORE_EMERALD_PINK_SALT,
        ORE_COPPER_PINK_SALT,
        ORE_UMBRAL_LAZULI_PINK_SALT
    );
    public static final ImmutableList<ConfiguredFeature<?, ?>> ICE_CAVE = ImmutableList.of(
        ICE_CAVE_FLOESTONE_DISK
    );
    public static final ImmutableList<ConfiguredFeature<?, ?>> SCALED_ICE_CAVE = ImmutableList.copyOf(ICE_CAVE);
    public static final ImmutableList<ConfiguredFeature<?, ?>> MINTED_ICE_CAVE = ImmutableList.copyOf(ICE_CAVE);
}
