package net.dodogang.marbles.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.world.gen.feature.PinkSaltSpireFeature;
import net.dodogang.marbles.world.gen.feature.PinkSaltStumpClusterFeature;
import net.dodogang.marbles.world.gen.feature.StateProvidedChanceDiskFeatureConfig;
import net.dodogang.marbles.world.gen.feature.YellowBambooFeature;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.MegaPineFoliagePlacer;
import net.minecraft.world.gen.foliage.PineFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
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
                    UniformIntDistribution.of(2),
                    UniformIntDistribution.of(1),
                    UniformIntDistribution.of(4, 1)
                ),
                new StraightTrunkPlacer(5, 2, 1),
                new TwoLayersFeatureSize(6, 0, 6)
            ).ignoreVines().build()
        )
    );
    public static final ConfiguredFeature<?, ?> TREES_ASPEN = register(
        "trees_" + MarblesBlocks.ASPEN.getId(),
        Feature.RANDOM_SELECTOR.configure(
            new RandomFeatureConfig(
                ImmutableList.of(ASPEN.withChance(0.0F)),
                ASPEN
            )
        )
        .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
        .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(20, 0.1F, 1)))
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
            ).ignoreVines().build()
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
            ).build()
        )
    );
    public static final ConfiguredFeature<?, ?> TREES_HOOPSI_SPRUCE = register(
        "trees_" + MarblesBlocks.HOOPSI_SPRUCE.getId(),
        Feature.RANDOM_SELECTOR.configure(
            new RandomFeatureConfig(
                ImmutableList.of(MEGA_HOOPSI_SPRUCE.withChance(0.1F)),
                HOOPSI_SPRUCE
            )
        )
        .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
        .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(10, 0.1F, 1)))
    );

    public static final ConfiguredFeature<TreeFeatureConfig, ?> RED_BIRCH = register(
        MarblesBlocks.RED_BIRCH.getId(),
        Feature.TREE.configure(
            new TreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(States.RED_BIRCH_LOG),
                new SimpleBlockStateProvider(States.RED_BIRCH_LEAVES),
                new BlobFoliagePlacer(
                    UniformIntDistribution.of(2),
                    UniformIntDistribution.of(0),
                    3
                ),
                new StraightTrunkPlacer(5, 2, 0),
                new TwoLayersFeatureSize(1, 0, 1)
            ).ignoreVines().build()
        )
    );
    public static final ConfiguredFeature<?, ?> TREES_RED_BIRCH = register(
        "trees_" + MarblesBlocks.RED_BIRCH.getId(),
        Feature.RANDOM_SELECTOR.configure(
            new RandomFeatureConfig(
                ImmutableList.of(RED_BIRCH.withChance(1.0f)),
                RED_BIRCH
            )
        )
        .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
        .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(10, 0.1F, 1)))
    );

    /*
     * YELLOW BAMBOO
     */

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

    /*
     * PINK SALT CAVES
     */

    public static final ConfiguredFeature<?, ?> PINK_SALT_STUMP_CLUSTER = register(
        PinkSaltStumpClusterFeature.id,
        MarblesFeatures.PINK_SALT_STUMP_CLUSTER.configure(FeatureConfig.DEFAULT)
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, 0, 96)))
            .spreadHorizontally()
            .decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(2)))
            .decorate(Decorator.COUNT.configure(new CountConfig(3)))
    );

    public static final ConfiguredFeature<?, ?> PINK_SALT_SPIRE = register(
        PinkSaltSpireFeature.id,
        MarblesFeatures.PINK_SALT_SPIRE.configure(FeatureConfig.DEFAULT)
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, 0, 96)))
            .spreadHorizontally()
            .decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(3)))
            .decorate(Decorator.COUNT.configure(new CountConfig(4)))
    );

    public static final ConfiguredFeature<?, ?> PINK_SALT_CAVE_GRANITE_DISK = register(
        "pink_salt_cave_granite_disk",
        MarblesFeatures.DISK
            .configure(new DiskFeatureConfig(
                States.GRANITE,
                UniformIntDistribution.of(2, 4),
                3,
                Lists.newArrayList(States.PINK_SALT)
            ))
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, 0, 96)))
            .spreadHorizontally()
            .decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(2)))
            .decorate(Decorator.COUNT.configure(new CountConfig(4)))
    );

    public static final ConfiguredFeature<?, ?> PINK_SALT_CAVE_CRUMBLED_SALT_DISK = register(
        "pink_salt_cave_crumbled_salt_disk",
        MarblesFeatures.DISK
            .configure(new DiskFeatureConfig(
                States.CRUMBLED_PINK_SALT,
                UniformIntDistribution.of(2, 4),
                3,
                Lists.newArrayList(States.PINK_SALT)
            ))
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, 0, 96)))
            .spreadHorizontally()
            .decorate(Decorator.COUNT.configure(new CountConfig(6)))
    );

    public static final ConfiguredFeature<?, ?> PINK_SALT_SPIKE_PATCH = register(
        "pink_salt_spike_patch",
        MarblesFeatures.PINK_SALT_SPIKES
            .configure(FeatureConfig.DEFAULT)
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, 0, 96)))
            .spreadHorizontally()
            .decorate(Decorator.COUNT.configure(new CountConfig(6)))
    );

    /*
     * ICE CAVES
     */

    public static final ConfiguredFeature<?, ?> ICE_CAVE_FLOESTONE_DISK = register(
        "ice_cave_floestone_disk",
        MarblesFeatures.STATE_PROVIDED_DISK
            .configure(
                new StateProvidedChanceDiskFeatureConfig(
                    new WeightedBlockStateProvider().addState(States.FLOESTONE, 1).addState(States.RILLED_FLOESTONE, 3),
                    UniformIntDistribution.of(6, 8),
                    4, 0.3f,
                    Lists.newArrayList(States.ICE, States.PACKED_ICE)
                )
            )
            .rangeOf(19)
            .spreadHorizontally()
    );

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Marbles.MOD_ID, id), configuredFeature);
    }

    private static class States {
        private static final BlockState ASPEN_LOG = MarblesBlocks.ASPEN.LOG.getDefaultState();
        private static final BlockState ASPEN_LEAVES = MarblesBlocks.ASPEN.LEAVES.getDefaultState();
        private static final BlockState HOOPSI_SPRUCE_LOG = MarblesBlocks.HOOPSI_SPRUCE.LOG.getDefaultState();
        private static final BlockState HOOPSI_SPRUCE_LEAVES = MarblesBlocks.HOOPSI_SPRUCE.LEAVES.getDefaultState();
        private static final BlockState RED_BIRCH_LOG = MarblesBlocks.RED_BIRCH.LOG.getDefaultState();
        private static final BlockState RED_BIRCH_LEAVES = MarblesBlocks.RED_BIRCH.LEAVES.getDefaultState();
        private static final BlockState PINK_SALT = MarblesBlocks.PINK_SALT.getDefaultState();
        private static final BlockState CRUMBLED_PINK_SALT = MarblesBlocks.CRUMBLED_PINK_SALT.getDefaultState();
        private static final BlockState GRANITE = Blocks.GRANITE.getDefaultState();
        private static final BlockState FLOESTONE = MarblesBlocks.FLOESTONE.getDefaultState();
        private static final BlockState RILLED_FLOESTONE = MarblesBlocks.RILLED_FLOESTONE.getDefaultState();
        private static final BlockState ICE = Blocks.ICE.getDefaultState();
        private static final BlockState PACKED_ICE = Blocks.PACKED_ICE.getDefaultState();
    }
}
