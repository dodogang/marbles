package net.dodogang.marbles.block.sapling;

import net.dodogang.marbles.init.MarblesConfiguredFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;

public class AspenSaplingGenerator extends SaplingGenerator {
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
        return MarblesConfiguredFeatures.ASPEN;
    }
}
