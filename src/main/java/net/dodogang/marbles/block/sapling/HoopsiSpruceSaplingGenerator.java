package net.dodogang.marbles.block.sapling;

import net.dodogang.marbles.init.MarblesConfiguredFeatures;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;

public class HoopsiSpruceSaplingGenerator extends LargeTreeSaplingGenerator {
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
        return MarblesConfiguredFeatures.HOOPSI_SPRUCE;
    }

    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createLargeTreeFeature(Random random) {
        return MarblesConfiguredFeatures.MEGA_HOOPSI_SPRUCE;
    }
}
