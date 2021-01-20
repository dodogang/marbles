package net.dodogang.marbles.block.sapling;

import java.util.Random;

import net.dodogang.marbles.init.MarblesConfiguredFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class HoopsiSpruceSaplingGenerator extends SaplingGenerator {
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
        return MarblesConfiguredFeatures.HOOPSI_SPRUCE;
    }
}
