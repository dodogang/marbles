package net.dodogang.marbles.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.world.gen.feature.PermafrostIceSpikeFeatureConfig;
import net.dodogang.marbles.world.gen.feature.StateProvidedChanceDiskFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.MegaPineFoliagePlacer;
import net.minecraft.world.gen.foliage.PineFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
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
                new StraightTrunkPlacer(5, 2, 1),
                new SimpleBlockStateProvider(States.ASPEN_LEAVES),
                new SimpleBlockStateProvider(States.ASPEN_SAPLING),
                new PineFoliagePlacer(
                    ConstantIntProvider.create(2),
                    ConstantIntProvider.create(1),
                    UniformIntProvider.create(1, 4)
                ),
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
                new StraightTrunkPlacer(5, 2, 1),
                new SimpleBlockStateProvider(States.HOOPSI_SPRUCE_LEAVES),
                new SimpleBlockStateProvider(States.HOOPSI_SPRUCE_SAPLING),
                new SpruceFoliagePlacer(
                    UniformIntProvider.create(1, 2),
                    UniformIntProvider.create(0, 2),
                    UniformIntProvider.create(1, 1)
                ),
                new TwoLayersFeatureSize(2, 0, 2)
            ).ignoreVines().build()
        )
    );
    public static final ConfiguredFeature<TreeFeatureConfig, ?> MEGA_HOOPSI_SPRUCE = register(
        "mega_" + MarblesBlocks.HOOPSI_SPRUCE.getId(),
        Feature.TREE.configure(
            new TreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(States.HOOPSI_SPRUCE_LOG),
                new GiantTrunkPlacer(13, 2, 14),
                new SimpleBlockStateProvider(States.HOOPSI_SPRUCE_LEAVES),
                new SimpleBlockStateProvider(States.HOOPSI_SPRUCE_SAPLING),
                new MegaPineFoliagePlacer(
                    ConstantIntProvider.create(0),
                    ConstantIntProvider.create(0),
                    UniformIntProvider.create(4, 13)
                ),
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
                new StraightTrunkPlacer(5, 2, 0),
                new SimpleBlockStateProvider(States.RED_BIRCH_LEAVES),
                new SimpleBlockStateProvider(States.RED_BIRCH_SAPLING),
                new BlobFoliagePlacer(
                    ConstantIntProvider.create(2),
                    ConstantIntProvider.create(0),
                    3
                ),
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
        "yellow_bamboo",
        MarblesFeatures.YELLOW_BAMBOO.configure(new ProbabilityConfig(0.2F))
            .decorate(ConfiguredFeatures.Decorators.HEIGHTMAP_WORLD_SURFACE)
            .spreadHorizontally()
            .decorate(Decorator.COUNT_NOISE_BIASED.configure(new CountNoiseBiasedDecoratorConfig(160, 80.0D, 0.3D)))
    );
    public static final ConfiguredFeature<?, ?> YELLOW_BAMBOO_LIGHT = register(
        "yellow_bamboo_light",
        MarblesFeatures.YELLOW_BAMBOO.configure(new ProbabilityConfig(0.0F))
            .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
            .repeat(16)
    );

    /*
     * PINK SALT CAVES
     */

    public static final ConfiguredFeature<?, ?> PINK_SALT_STUMP_CLUSTER = register(
        "pink_salt_stump_cluster",
        MarblesFeatures.PINK_SALT_STUMP_CLUSTER.configure(FeatureConfig.DEFAULT)
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(UniformHeightProvider.create(YOffset.getBottom(), YOffset.fixed(96)))))
            .spreadHorizontally()
            .decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(2)))
            .decorate(Decorator.COUNT.configure(new CountConfig(3)))
    );

    public static final ConfiguredFeature<?, ?> PINK_SALT_SPIRE = register(
        "pink_salt_spire",
        MarblesFeatures.PINK_SALT_SPIRE.configure(FeatureConfig.DEFAULT)
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(UniformHeightProvider.create(YOffset.getBottom(), YOffset.fixed(96)))))
            .spreadHorizontally()
            .decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(3)))
            .decorate(Decorator.COUNT.configure(new CountConfig(4)))
    );

    public static final ConfiguredFeature<?, ?> PINK_SALT_CAVE_GRANITE_DISK = register(
        "pink_salt_cave_granite_disk",
        MarblesFeatures.DISK
            .configure(new DiskFeatureConfig(
                States.GRANITE,
                UniformIntProvider.create(2, 4),
                3,
                Lists.newArrayList(States.PINK_SALT)
            ))
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(UniformHeightProvider.create(YOffset.getBottom(), YOffset.fixed(96)))))
            .spreadHorizontally()
            .decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(2)))
            .decorate(Decorator.COUNT.configure(new CountConfig(4)))
    );

    public static final ConfiguredFeature<?, ?> PINK_SALT_CAVE_CRUMBLED_SALT_DISK = register(
        "pink_salt_cave_crumbled_salt_disk",
        MarblesFeatures.DISK
            .configure(new DiskFeatureConfig(
                States.CRUMBLED_PINK_SALT,
                UniformIntProvider.create(2, 4),
                3,
                Lists.newArrayList(States.PINK_SALT)
            ))
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(UniformHeightProvider.create(YOffset.getBottom(), YOffset.fixed(96)))))
            .spreadHorizontally()
            .decorate(Decorator.COUNT.configure(new CountConfig(6)))
    );

    public static final ConfiguredFeature<?, ?> PINK_SALT_SPIKE_PATCH = register(
        "pink_salt_spike_patch",
        MarblesFeatures.PINK_SALT_SPIKES
            .configure(FeatureConfig.DEFAULT)
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(UniformHeightProvider.create(YOffset.getBottom(), YOffset.fixed(96)))))
            .spreadHorizontally()
            .decorate(Decorator.COUNT.configure(new CountConfig(6)))
    );

    /*
     * ICE
     */

    public static final ConfiguredFeature<?, ?> ICE_CAVE_FLOESTONE_DISK = register(
        "ice_cave_floestone_disk",
        MarblesFeatures.STATE_PROVIDED_DISK
            .configure(
                new StateProvidedChanceDiskFeatureConfig(
                    new WeightedBlockStateProvider(createStatePool().add(States.FLOESTONE, 1).add(States.RILLED_FLOESTONE, 3)),
                    UniformIntProvider.create(6, 8),
                    4, 0.3f,
                    Lists.newArrayList(States.ICE, States.PACKED_ICE)
                )
            )
            .repeatRandomly(19)
            .spreadHorizontally()
    );

    public static final ConfiguredFeature<?, ?> LARGE_SLUSH_DISK = register(
        "large_slush_disk",
        MarblesFeatures.STATE_PROVIDED_DISK
            .configure(
                new StateProvidedChanceDiskFeatureConfig(
                    new WeightedBlockStateProvider(createStatePool().add(States.SLUSH, 4).add(States.ICE, 1)),
                    UniformIntProvider.create(6, 8),
                    4, 0.3f,
                    Lists.newArrayList(States.FLOESTONE, States.STONE)
                )
            )
            .repeatRandomly(28)
            .uniformRange(YOffset.getBottom(), YOffset.getTop())
            .spreadHorizontally()
    );
    public static final ConfiguredFeature<?, ?> LARGE_ICE_DISK = register(
        "large_ice_disk",
        MarblesFeatures.STATE_PROVIDED_DISK
            .configure(
                new StateProvidedChanceDiskFeatureConfig(
                    new WeightedBlockStateProvider(createStatePool().add(States.ICE, 7).add(States.PACKED_ICE, 1)),
                    UniformIntProvider.create(6, 8),
                    4, 0.3f,
                    Lists.newArrayList(States.FLOESTONE, States.STONE)
                )
            )
            .repeatRandomly(28)
            .uniformRange(YOffset.getBottom(), YOffset.getTop())
            .spreadHorizontally()
    );

    private static final ImmutableList<BlockState> PERMAFROST_TARGET = ImmutableList.of(
        States.PERMAFROST, States.PERMAFROST_SNOWY,
        States.PERMAFROST_DIRT, States.SNOW_BLOCK,
        States.ICE, States.PACKED_ICE, States.FLOESTONE,
        States.SLUSH, States.SNOW, States.GRASS,
        States.TALL_GRASS_TOP, States.TALL_GRASS_BOTTOM
    );
    public static final ConfiguredFeature<?, ?> PERMAFROST_ICE_SPIKE = register(
        "permafrost_ice_spike",
        MarblesFeatures.PERMAFROST_ICE_SPIKE
            .configure(
                new PermafrostIceSpikeFeatureConfig(
                    new WeightedBlockStateProvider(createStatePool().add(States.PACKED_ICE, 7).add(States.ICE, 1)),
                    PERMAFROST_TARGET
                )
            )
            .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
            .repeat(3)
    );
    public static final ConfiguredFeature<?, ?> PERMAFROST_ICE_PATCH = register(
        "permafrost_ice_patch",
        MarblesFeatures.STATE_PROVIDED_DISK
            .configure(
                new StateProvidedChanceDiskFeatureConfig(
                    new WeightedBlockStateProvider(createStatePool().add(States.ICE, 3).add(States.PACKED_ICE, 1)),
                    UniformIntProvider.create(4, 6), 1, 0.3f,
                    PERMAFROST_TARGET
                )
            )
            .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
            .repeat(9)
            .uniformRange(YOffset.getBottom(), YOffset.getTop())
    );

    public static final ConfiguredFeature<?, ?> SNOW_UNDER_TREES = register(
        "snow_under_trees",
        MarblesFeatures.SNOW_UNDER_TREES
            .configure(new DefaultFeatureConfig())
            .decorate(Decorator.NOPE.configure(NopeDecoratorConfig.INSTANCE))
    );

    private static DataPool.Builder<BlockState> createStatePool() {
        return DataPool.builder();
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Marbles.MOD_ID, id), configuredFeature);
    }

    @SuppressWarnings("unused")
    private static class States {
        private static final BlockState ASPEN_LOG = MarblesBlocks.ASPEN.LOG.getDefaultState();
        private static final BlockState ASPEN_LEAVES = MarblesBlocks.ASPEN.LEAVES.getDefaultState();
        private static final BlockState ASPEN_SAPLING = MarblesBlocks.ASPEN.POTTED_SAPLING.getDefaultState();
        private static final BlockState HOOPSI_SPRUCE_LOG = MarblesBlocks.HOOPSI_SPRUCE.LOG.getDefaultState();
        private static final BlockState HOOPSI_SPRUCE_LEAVES = MarblesBlocks.HOOPSI_SPRUCE.LEAVES.getDefaultState();
        private static final BlockState HOOPSI_SPRUCE_SAPLING = MarblesBlocks.HOOPSI_SPRUCE.SAPLING.getDefaultState();
        private static final BlockState RED_BIRCH_LOG = MarblesBlocks.RED_BIRCH.LOG.getDefaultState();
        private static final BlockState RED_BIRCH_LEAVES = MarblesBlocks.RED_BIRCH.LEAVES.getDefaultState();
        private static final BlockState RED_BIRCH_SAPLING = MarblesBlocks.RED_BIRCH.SAPLING.getDefaultState();
        private static final BlockState PINK_SALT = MarblesBlocks.PINK_SALT.getDefaultState();
        private static final BlockState CRUMBLED_PINK_SALT = MarblesBlocks.CRUMBLED_PINK_SALT.getDefaultState();
        private static final BlockState STONE = Blocks.STONE.getDefaultState();
        private static final BlockState GRANITE = Blocks.GRANITE.getDefaultState();
        private static final BlockState FLOESTONE = MarblesBlocks.FLOESTONE.getDefaultState();
        private static final BlockState RILLED_FLOESTONE = MarblesBlocks.RILLED_FLOESTONE.getDefaultState();
        private static final BlockState ICE = Blocks.ICE.getDefaultState();
        private static final BlockState PACKED_ICE = Blocks.PACKED_ICE.getDefaultState();
        private static final BlockState SLUSH = MarblesBlocks.SLUSH.getDefaultState();
        private static final BlockState PERMAFROST_DIRT = MarblesBlocks.PERMAFROST_DIRT.getDefaultState();
        private static final BlockState PERMAFROST = MarblesBlocks.PERMAFROST.getDefaultState();
        private static final BlockState PERMAFROST_SNOWY = MarblesBlocks.PERMAFROST.getDefaultState().with(SnowyBlock.SNOWY, true);
        private static final BlockState PERMAFROST_PODZOL = MarblesBlocks.PERMAFROST_PODZOL.getDefaultState();
        private static final BlockState COARSE_PERMAFROST_DIRT = MarblesBlocks.COARSE_PERMAFROST_DIRT.getDefaultState();
        private static final BlockState PERMAFROST_MYCELIUM = MarblesBlocks.PERMAFROST_MYCELIUM.getDefaultState();
        private static final BlockState SNOW = Blocks.SNOW.getDefaultState();
        private static final BlockState SNOW_BLOCK = Blocks.SNOW_BLOCK.getDefaultState();
        private static final BlockState SCALED_ICE = MarblesBlocks.SCALED_ICE.getDefaultState();
        private static final BlockState GRASS = Blocks.GRASS.getDefaultState();
        private static final BlockState TALL_GRASS_TOP = Blocks.TALL_GRASS.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER);
        private static final BlockState TALL_GRASS_BOTTOM = Blocks.TALL_GRASS.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.LOWER);
    }
}
