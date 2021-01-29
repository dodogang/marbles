package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.world.gen.feature.YellowBambooFeature;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.decorator.CountNoiseBiasedDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.MegaPineFoliagePlacer;
import net.minecraft.world.gen.foliage.PineFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class MarblesConfiguredFeatures {
    public static final ConfiguredFeature<TreeFeatureConfig, ?> ASPEN = register(
        MarblesBlocks.ASPEN.getId(),
        Feature.TREE.configure(
            new TreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(States.ASPEN_LOG),
                new SimpleBlockStateProvider(States.ASPEN_LEAVES),
                new PineFoliagePlacer(
                    UniformIntDistribution.of(2, 1),
                    UniformIntDistribution.of(0, 2),
                    UniformIntDistribution.of(4, 2)
                ),
                new StraightTrunkPlacer(5, 2, 1),
                new TwoLayersFeatureSize(6, 0, 6)
            )
                .ignoreVines()
                .build()
        )
    );

    public static final ConfiguredFeature<TreeFeatureConfig, ?> HOOPSI_SPRUCE = register(
        MarblesBlocks.HOOPSI_SPRUCE.getId(),
        Feature.TREE.configure(
            new TreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(States.HOOPSI_SPRUCE_LOG),
                new SimpleBlockStateProvider(States.HOOPSI_SPRUCE_LEAVES),
                new SpruceFoliagePlacer(
                    UniformIntDistribution.of(2, 1),
                    UniformIntDistribution.of(0, 2),
                    UniformIntDistribution.of(1, 1)
                ),
                new StraightTrunkPlacer(5, 2, 1),
                new TwoLayersFeatureSize(2, 0, 2)
            )
            .ignoreVines()
            .build()
        )
    );
    public static final ConfiguredFeature<TreeFeatureConfig, ?> MEGA_HOOPSI_SPRUCE = register(
        "mega_" + MarblesBlocks.HOOPSI_SPRUCE.getId(),
        Feature.TREE.configure(
            new TreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(States.HOOPSI_SPRUCE_LOG),
                new SimpleBlockStateProvider(States.HOOPSI_SPRUCE_LEAVES),
                new MegaPineFoliagePlacer(
                    UniformIntDistribution.of(0),
                    UniformIntDistribution.of(0),
                    UniformIntDistribution.of(13, 4)
                ),
                new GiantTrunkPlacer(13, 2, 14),
                new TwoLayersFeatureSize(1, 1, 2)
            )
            .build()
        )
    );

    public static final ConfiguredFeature<?, ?> YELLOW_BAMBOO = register(
        YellowBambooFeature.id,
        MarblesFeatures.YELLOW_BAMBOO.configure(new ProbabilityConfig(0.2F))
            .decorate(ConfiguredFeatures.Decorators.HEIGHTMAP_WORLD_SURFACE)
            .spreadHorizontally()
            .decorate(Decorator.COUNT_NOISE_BIASED.configure(new CountNoiseBiasedDecoratorConfig(160, 80.0D, 0.3D)))
    );
    public static final ConfiguredFeature<?, ?> YELLOW_BAMBOO_LIGHT = register(
        YellowBambooFeature.id + "_light",
        MarblesFeatures.YELLOW_BAMBOO.configure(new ProbabilityConfig(0.0F))
            .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
            .repeat(16)
    );

    public MarblesConfiguredFeatures() {}

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Marbles.MOD_ID, id), configuredFeature);
    }

    private static class States {
        private static final BlockState ASPEN_LOG = MarblesBlocks.ASPEN.LOG.getDefaultState();
        private static final BlockState ASPEN_LEAVES = MarblesBlocks.ASPEN.LEAVES.getDefaultState();
        private static final BlockState HOOPSI_SPRUCE_LOG = MarblesBlocks.HOOPSI_SPRUCE.LOG.getDefaultState();
        private static final BlockState HOOPSI_SPRUCE_LEAVES = MarblesBlocks.HOOPSI_SPRUCE.LEAVES.getDefaultState();
    }
}
