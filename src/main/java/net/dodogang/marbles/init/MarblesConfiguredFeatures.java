package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class MarblesConfiguredFeatures {
    public static final ConfiguredFeature<TreeFeatureConfig, ?> ASPEN = register(MarblesBlocks.ASPEN.getId(), Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.ASPEN_LOG), new SimpleBlockStateProvider(States.ASPEN_LEAVES), new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()));
    public static final ConfiguredFeature<TreeFeatureConfig, ?> HOOPSI_SPRUCE = register(MarblesBlocks.HOOPSI_SPRUCE.getId(), Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.HOOPSI_LOG), new SimpleBlockStateProvider(States.HOOPSI_LEAVES), new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()));

    public MarblesConfiguredFeatures() {}

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Marbles.MOD_ID, id), configuredFeature);
    }

    private static class States {
        private static final BlockState ASPEN_LOG = MarblesBlocks.ASPEN.LOG.getDefaultState();
        private static final BlockState ASPEN_LEAVES = MarblesBlocks.ASPEN.LEAVES.getDefaultState();
        private static final BlockState HOOPSI_LOG = MarblesBlocks.HOOPSI_SPRUCE.LOG.getDefaultState();
        private static final BlockState HOOPSI_LEAVES = MarblesBlocks.HOOPSI_SPRUCE.LEAVES.getDefaultState();
    }
}
