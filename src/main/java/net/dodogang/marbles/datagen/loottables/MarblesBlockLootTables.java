package net.dodogang.marbles.datagen.loottables;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import me.andante.chord.block.helper.WoodBlocks;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.block.QuadAttachingBlock;
import net.dodogang.marbles.block.enums.QuadBlockPart;
import net.dodogang.marbles.block.helper.TravertineBlocks;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.init.MarblesItems;
import net.dodogang.marbles.mixin.datagen.BlockLootTableGeneratorInvoker;
import net.minecraft.block.*;
import net.minecraft.block.enums.BedPart;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.DynamicEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.*;
import net.minecraft.loot.operator.BoundedIntUnaryOperator;
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class MarblesBlockLootTables implements Consumer<BiConsumer<Identifier, LootTable.Builder>> {
    protected static final LootCondition.Builder WITH_SILK_TOUCH = MatchToolLootCondition.builder(
        ItemPredicate.Builder.create().enchantment(
            new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))
        )
    );
    protected static final LootCondition.Builder WITHOUT_SILK_TOUCH = WITH_SILK_TOUCH.invert();
    protected static final LootCondition.Builder WITH_SHEARS = MatchToolLootCondition.builder(
        ItemPredicate.Builder.create().items(Items.SHEARS)
    );
    protected static final LootCondition.Builder WITH_SILK_TOUCH_OR_SHEARS = WITH_SHEARS.or(WITH_SILK_TOUCH);
    protected static final LootCondition.Builder WITHOUT_SILK_TOUCH_NOR_SHEARS = WITH_SILK_TOUCH_OR_SHEARS.invert();

    @SuppressWarnings("UnstableApiUsage")
    private static final Set<Item> EXPLOSION_IMMUNE = Stream.of(
        Blocks.DRAGON_EGG,
        Blocks.BEACON,
        Blocks.CONDUIT,
        Blocks.SKELETON_SKULL,
        Blocks.WITHER_SKELETON_SKULL,
        Blocks.PLAYER_HEAD,
        Blocks.ZOMBIE_HEAD,
        Blocks.CREEPER_HEAD,
        Blocks.DRAGON_HEAD,
        Blocks.SHULKER_BOX,
        Blocks.BLACK_SHULKER_BOX,
        Blocks.BLUE_SHULKER_BOX,
        Blocks.BROWN_SHULKER_BOX,
        Blocks.CYAN_SHULKER_BOX,
        Blocks.GRAY_SHULKER_BOX,
        Blocks.GREEN_SHULKER_BOX,
        Blocks.LIGHT_BLUE_SHULKER_BOX,
        Blocks.LIGHT_GRAY_SHULKER_BOX,
        Blocks.LIME_SHULKER_BOX,
        Blocks.MAGENTA_SHULKER_BOX,
        Blocks.ORANGE_SHULKER_BOX,
        Blocks.PINK_SHULKER_BOX,
        Blocks.PURPLE_SHULKER_BOX,
        Blocks.RED_SHULKER_BOX,
        Blocks.WHITE_SHULKER_BOX,
        Blocks.YELLOW_SHULKER_BOX
    ).map(ItemConvertible::asItem).collect(ImmutableSet.toImmutableSet());

    private static final float[] SAPLING_DROP_CHANCES = {1 / 20f, 1 / 16f, 1 / 12f, 1 / 10f};
    private static final float[] RARE_SAPLING_DROP_CHANCES = {1 / 40f, 1 / 36f, 1 / 32f, 1 / 24f, 1 / 10f};

    private final Map<Identifier, LootTable.Builder> lootTables = Maps.newHashMap();


    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> biConsumer) {
        /*
         * WOOD SETS
         */

        addWoodDrops(MarblesBlocks.ASPEN);
        addWoodDrops(MarblesBlocks.HOOPSI_SPRUCE);
        addWoodDrops(MarblesBlocks.RED_BIRCH);

        /*
         * TRAVERTINE SETS
         */

        addTravertineDrops(MarblesBlocks.TRAVERTINE);
        addTravertineDrops(MarblesBlocks.LEMON_TRAVERTINE);
        addTravertineDrops(MarblesBlocks.PEACH_TRAVERTINE);
        addTravertineDrops(MarblesBlocks.TANGERINE_TRAVERTINE);

        addDrop(MarblesBlocks.TRAVERTINE_OBSIDIAN);
        addDrop(MarblesBlocks.TRAVERTINE_NETHER_PORTAL, block -> dropsNothing());

        addDrop(MarblesBlocks.LIMESTONE);
        addDrop(MarblesBlocks.LIMESTONE_SLAB);
        addDrop(MarblesBlocks.LIMESTONE_STAIRS);
        addDrop(MarblesBlocks.LIMESTONE_WALL);
        addDrop(MarblesBlocks.QUARTERED_LIMESTONE, MarblesBlockLootTables::dropsAttachingQuad);
        addDrop(MarblesBlocks.POLISHED_LIMESTONE);
        addDrop(MarblesBlocks.POLISHED_LIMESTONE_SLAB);
        addDrop(MarblesBlocks.POLISHED_LIMESTONE_STAIRS);
        addDrop(MarblesBlocks.POLISHED_LIMESTONE_WALL);
        addDrop(MarblesBlocks.QUARTERED_POLISHED_LIMESTONE, MarblesBlockLootTables::dropsAttachingQuad);

        /*
         * PINK SALT
         */

        addDrop(MarblesBlocks.PINK_SALT_CAVE_AIR, block -> dropsNothing());
        addDrop(MarblesBlocks.ICE_CAVE_AIR, block -> dropsNothing());
        addDrop(MarblesBlocks.PINK_SALT);
        addDrop(MarblesBlocks.PINK_SALT_SLAB);
        addDrop(MarblesBlocks.PINK_SALT_STAIRS);
        addDrop(MarblesBlocks.CRUMBLED_PINK_SALT);
        addDrop(MarblesBlocks.PINK_SALT_BRICKS);
        addDrop(MarblesBlocks.PINK_SALT_BRICK_SLAB);
        addDrop(MarblesBlocks.PINK_SALT_BRICK_STAIRS);
        addDrop(MarblesBlocks.PINK_SALT_PILLAR);
        addDrop(MarblesBlocks.PINK_SALT_SPIRE);
        addDrop(MarblesBlocks.PINK_SALT_STACK, block -> dropsWithSilkTouch(block, MarblesItems.PINK_SALT_SHARD, countRandom(1, 4)));
        addDrop(MarblesBlocks.PINK_SALT_STUMP, block -> dropsWithSilkTouch(block, MarblesItems.PINK_SALT_SHARD, countRandom(1, 2)));
        addDrop(MarblesBlocks.PINK_SALT_SPIKES, block -> dropsWithSilkTouch(block, MarblesItems.PINK_SALT_SHARD, countBiased(2, 0.4f)));

        addDrop(MarblesBlocks.PINK_SALT_COAL_ORE, block -> oreDrops(block, Items.COAL));
        addDrop(MarblesBlocks.PINK_SALT_EMERALD_ORE, block -> oreDrops(block, Items.EMERALD));
        addDrop(MarblesBlocks.PINK_SALT_DIAMOND_ORE, block -> oreDrops(block, Items.DIAMOND));
        addDrop(MarblesBlocks.PINK_SALT_COPPER_ORE, MarblesBlockLootTables::dropsCopperOre);
        addDrop(MarblesBlocks.PINK_SALT_IRON_ORE, block -> oreDrops(block, Items.RAW_IRON));
        addDrop(MarblesBlocks.PINK_SALT_GOLD_ORE, (blockx) -> oreDrops(blockx, Items.RAW_GOLD));
        addDrop(MarblesBlocks.PINK_SALT_LAPIS_ORE, MarblesBlockLootTables::dropsLapisOre);
        addDrop(MarblesBlocks.PINK_SALT_REDSTONE_ORE, MarblesBlockLootTables::dropsRedstoneOre);

        /*
         * LAPIS SETS
         */

        addDrop(MarblesBlocks.LAPIS_SHINGLES);
        addSlabDrop(MarblesBlocks.LAPIS_SHINGLE_SLAB);
        addDrop(MarblesBlocks.LAPIS_SHINGLE_STAIRS);
        addDrop(MarblesBlocks.LAPIS_SPOTLIGHT);
        addDrop(MarblesBlocks.GLAZED_LAPIS);
        addDrop(MarblesBlocks.UMBRAL_LAZULI_ORE, MarblesBlockLootTables::dropsUmbralLazuliOre);
        addDrop(MarblesBlocks.DEEPSLATE_UMBRAL_LAZULI_ORE, MarblesBlockLootTables::dropsUmbralLazuliOre);
        addDrop(MarblesBlocks.PINK_SALT_UMBRAL_LAZULI_ORE, MarblesBlockLootTables::dropsUmbralLazuliOre);
        addDrop(MarblesBlocks.UMBRAL_LAZULI_BLOCK);
        addDrop(MarblesBlocks.UMBRAL_LAZULI_SHINGLES);
        addSlabDrop(MarblesBlocks.UMBRAL_LAZULI_SHINGLE_SLAB);
        addDrop(MarblesBlocks.UMBRAL_LAZULI_SHINGLE_STAIRS);
        addDrop(MarblesBlocks.UMBRAL_LAZULI_SPOTLIGHT);
        addDrop(MarblesBlocks.GLAZED_UMBRAL_LAZULI);

        /*
         * BAMBOO
         */

        addDrop(MarblesBlocks.CHEQUERED_BAMBOO_LATTICE);
        addDrop(MarblesBlocks.CROSSED_BAMBOO_LATTICE);

        addDrop(MarblesBlocks.YELLOW_BAMBOO, MarblesBlocks.YELLOW_BAMBOO);
        addDrop(MarblesBlocks.YELLOW_BAMBOO_SAPLING, MarblesBlocks.YELLOW_BAMBOO);
        addPottedPlantDrop(MarblesBlocks.POTTED_YELLOW_BAMBOO);
        addDrop(MarblesBlocks.YELLOW_SCAFFOLDING);
        addDrop(MarblesBlocks.CHEQUERED_YELLOW_BAMBOO_LATTICE);
        addDrop(MarblesBlocks.CROSSED_YELLOW_BAMBOO_LATTICE);

        addDrop(MarblesBlocks.BAMBOO_TIKI_TORCH);
        addDrop(MarblesBlocks.BAMBOO_TIKI_POLE);

        addDrop(MarblesBlocks.YELLOW_BAMBOO_TIKI_TORCH);
        addDrop(MarblesBlocks.YELLOW_BAMBOO_TIKI_POLE);

        /*
         * SANDSTONE SETS
         */

        addDrop(MarblesBlocks.DAWN.SAND);
        addDrop(MarblesBlocks.DAWN.SANDSTONE);
        addDrop(MarblesBlocks.DAWN.CHISELED_SANDSTONE);
        addDrop(MarblesBlocks.DAWN.CUT_SANDSTONE);
        addDrop(MarblesBlocks.DAWN.SMOOTH_SANDSTONE);
        addSlabDrop(MarblesBlocks.DAWN.SANDSTONE_SLAB);
        addSlabDrop(MarblesBlocks.DAWN.CUT_SANDSTONE_SLAB);
        addSlabDrop(MarblesBlocks.DAWN.SMOOTH_SANDSTONE_SLAB);
        addDrop(MarblesBlocks.DAWN.SANDSTONE_STAIRS);
        addDrop(MarblesBlocks.DAWN.CUT_SANDSTONE_STAIRS);
        addDrop(MarblesBlocks.DAWN.SMOOTH_SANDSTONE_STAIRS);
        addDrop(MarblesBlocks.DAWN.SANDSTONE_WALL);
        addDrop(MarblesBlocks.DAWN.CUT_SANDSTONE_WALL);
        addDrop(MarblesBlocks.DAWN.SMOOTH_SANDSTONE_WALL);

        addDrop(MarblesBlocks.DUSK.SAND);
        addDrop(MarblesBlocks.DUSK.SANDSTONE);
        addDrop(MarblesBlocks.DUSK.CHISELED_SANDSTONE);
        addDrop(MarblesBlocks.DUSK.CUT_SANDSTONE);
        addDrop(MarblesBlocks.DUSK.SMOOTH_SANDSTONE);
        addSlabDrop(MarblesBlocks.DUSK.SANDSTONE_SLAB);
        addSlabDrop(MarblesBlocks.DUSK.CUT_SANDSTONE_SLAB);
        addSlabDrop(MarblesBlocks.DUSK.SMOOTH_SANDSTONE_SLAB);
        addDrop(MarblesBlocks.DUSK.SANDSTONE_STAIRS);
        addDrop(MarblesBlocks.DUSK.CUT_SANDSTONE_STAIRS);
        addDrop(MarblesBlocks.DUSK.SMOOTH_SANDSTONE_STAIRS);
        addDrop(MarblesBlocks.DUSK.SANDSTONE_WALL);
        addDrop(MarblesBlocks.DUSK.CUT_SANDSTONE_WALL);
        addDrop(MarblesBlocks.DUSK.SMOOTH_SANDSTONE_WALL);

        addDrop(MarblesBlocks.MORN_GRASS, MarblesBlockLootTables::dropsGrass);
        addDrop(MarblesBlocks.TALL_MORN_GRASS, block -> MarblesBlockLootTables.dropsDoubleGrass(block, MarblesBlocks.MORN_GRASS));

        /*
         * GRISP
         */

        addDrop(MarblesBlocks.GRISP_DIRT);
        addDrop(MarblesBlocks.COARSE_GRISP_DIRT);
        addDrop(MarblesBlocks.GRISP_PODZOL, block -> dropsWithSilkTouch(block, MarblesBlocks.GRISP_DIRT));
        addDrop(MarblesBlocks.GRISP_MYCELIUM, block -> dropsWithSilkTouch(block, MarblesBlocks.GRISP_DIRT));
        addDrop(MarblesBlocks.GRISP_DIRT_PATH, MarblesBlocks.GRISP_DIRT);
        addDrop(MarblesBlocks.GRISP_PODZOL_PATH, MarblesBlocks.GRISP_DIRT);
        addDrop(MarblesBlocks.GRISP_MYCELIUM_PATH, MarblesBlocks.GRISP_DIRT);
        addDrop(MarblesBlocks.GRISP_FARMLAND, MarblesBlocks.GRISP_DIRT);

        addDrop(MarblesBlocks.POLLENATED_COBBLESTONE);
        addSlabDrop(MarblesBlocks.POLLENATED_COBBLESTONE_SLAB);
        addDrop(MarblesBlocks.POLLENATED_COBBLESTONE_STAIRS);
        addDrop(MarblesBlocks.POLLENATED_COBBLESTONE_WALL);

        addDrop(MarblesBlocks.ASPEN_SPROUTS, MarblesBlockLootTables::dropsGrass);
        addDrop(MarblesBlocks.ASPEN_GRASS, MarblesBlockLootTables::dropsGrass);
        addDrop(MarblesBlocks.TALL_ASPEN_GRASS, block -> MarblesBlockLootTables.dropsDoubleGrass(block, MarblesBlocks.ASPEN_GRASS));

        addDrop(MarblesBlocks.ASPEN_SEAGRASS, MarblesBlockLootTables::dropsWithSilkTouch);
        addDrop(MarblesBlocks.TALL_ASPEN_SEAGRASS, MarblesBlockLootTables::dropsSeagrass);

        /*
         * PLANTAGE
         */

        addDrop(MarblesBlocks.BLUE_PEONY, MarblesBlockLootTables::drops);

        /*
         * POLLEN-GRACED SET
         */

        addDrop(MarblesBlocks.POLLEN_GRACED_WOOL);
        addDrop(MarblesBlocks.POLLEN_GRACED_CARPET);
        addDrop(MarblesBlocks.POLLEN_GRACED_BED, block -> dropsWithProperty(block, BedBlock.PART, BedPart.HEAD));

        /*
         * ICE SET
         */

        addDrop(MarblesBlocks.FLOESTONE);
        addSlabDrop(MarblesBlocks.FLOESTONE_SLAB);
        addDrop(MarblesBlocks.FLOESTONE_STAIRS);
        addDrop(MarblesBlocks.FLOESTONE_WALL);

        addDrop(MarblesBlocks.POLISHED_FLOESTONE);
        addSlabDrop(MarblesBlocks.POLISHED_FLOESTONE_SLAB);
        addDrop(MarblesBlocks.POLISHED_FLOESTONE_STAIRS);
        addDrop(MarblesBlocks.POLISHED_FLOESTONE_WALL);

        addDrop(MarblesBlocks.FLOESTONE_BRICKS);
        addSlabDrop(MarblesBlocks.FLOESTONE_BRICK_SLAB);
        addDrop(MarblesBlocks.FLOESTONE_BRICK_STAIRS);
        addDrop(MarblesBlocks.FLOESTONE_BRICK_WALL);

        addDrop(MarblesBlocks.CHISELED_FLOESTONE);
        addDrop(MarblesBlocks.RILLED_FLOESTONE, MarblesBlockLootTables::dropsWithSilkTouch);
        addDrop(MarblesBlocks.RINGED_FLOESTONE);
        addDrop(MarblesBlocks.RINGED_RILLED_FLOESTONE);

        addDrop(MarblesBlocks.PERMAFROST, block -> dropsWithSilkTouch(block, MarblesBlocks.PERMAFROST_DIRT));
        addDrop(MarblesBlocks.PERMAFROST_PATH, MarblesBlocks.PERMAFROST_DIRT);
        addDrop(MarblesBlocks.PERMAFROST_DIRT);
        addDrop(MarblesBlocks.COARSE_PERMAFROST_DIRT);
        addDrop(MarblesBlocks.PERMAFROST_PODZOL, block -> dropsWithSilkTouch(block, MarblesBlocks.PERMAFROST_DIRT));
        addDrop(MarblesBlocks.PERMAFROST_MYCELIUM, block -> dropsWithSilkTouch(block, MarblesBlocks.PERMAFROST_DIRT));
        addDrop(MarblesBlocks.PERMAFROST_DIRT_PATH, MarblesBlocks.PERMAFROST_DIRT);
        addDrop(MarblesBlocks.PERMAFROST_PODZOL_PATH, MarblesBlocks.PERMAFROST_DIRT);
        addDrop(MarblesBlocks.PERMAFROST_MYCELIUM_PATH, MarblesBlocks.PERMAFROST_DIRT);
        addDrop(MarblesBlocks.PERMAFROST_FARMLAND, MarblesBlocks.PERMAFROST_DIRT);

        addDrop(MarblesBlocks.SCALED_ICE, MarblesBlockLootTables::dropsWithSilkTouch);
        addDrop(MarblesBlocks.MINTED_ICE, MarblesBlockLootTables::dropsWithSilkTouch);

        addDrop(MarblesBlocks.CUT_ICE, MarblesBlockLootTables::dropsWithSilkTouch);
        addDrop(MarblesBlocks.CUT_BLUE_ICE, MarblesBlockLootTables::dropsWithSilkTouch);
        addDrop(MarblesBlocks.CUT_SCALED_ICE, MarblesBlockLootTables::dropsWithSilkTouch);
        addDrop(MarblesBlocks.CUT_MINTED_ICE, MarblesBlockLootTables::dropsWithSilkTouch);

        addDrop(MarblesBlocks.CHISELED_ICE, MarblesBlockLootTables::dropsWithSilkTouch);
        addDrop(MarblesBlocks.CHISELED_BLUE_ICE, MarblesBlockLootTables::dropsWithSilkTouch);
        addDrop(MarblesBlocks.CHISELED_SCALED_ICE, MarblesBlockLootTables::dropsWithSilkTouch);
        addDrop(MarblesBlocks.CHISELED_MINTED_ICE, MarblesBlockLootTables::dropsWithSilkTouch);

        addDrop(MarblesBlocks.ICE_BRICKS, MarblesBlockLootTables::dropsWithSilkTouch);
        addDrop(MarblesBlocks.BLUE_ICE_BRICKS, MarblesBlockLootTables::dropsWithSilkTouch);
        addDrop(MarblesBlocks.SCALED_ICE_BRICKS, MarblesBlockLootTables::dropsWithSilkTouch);
        addDrop(MarblesBlocks.MINTED_ICE_BRICKS, MarblesBlockLootTables::dropsWithSilkTouch);

        addDrop(MarblesBlocks.SLUSH);
        addDrop(MarblesBlocks.BLUE_SLUSH);
        addDrop(MarblesBlocks.SCALED_SLUSH);
        addDrop(MarblesBlocks.MINTED_SLUSH);

        /*
         * MISC
         */

        addDrop(MarblesBlocks.ROPE);

        /*
         * GENERATION
         */

        Set<Identifier> set = Sets.newHashSet();
        Iterable<Block> blocks = Registry.BLOCK
                                     .stream()
                                     .filter(block -> Registry.BLOCK.getId(block).getNamespace().equals(Marbles.MOD_ID))
                                     ::iterator;

        for (Block block : blocks) {
            Identifier id = block.getLootTableId();
            if (id != LootTables.EMPTY && set.add(id)) {
                LootTable.Builder builder = lootTables.remove(id);
                if (builder == null) {
                    throw new IllegalStateException(
                        String.format(
                            "Missing loottable '%s' for '%s'", id,
                            Registry.BLOCK.getId(block)
                        )
                    );
                }

                biConsumer.accept(id, builder);
            }
        }

        if (!lootTables.isEmpty()) {
            throw new IllegalStateException("Created block loot tables for non-blocks: " + lootTables.keySet());
        }
    }

    private void addTravertineDrops(TravertineBlocks blocks) {
        addDrop(blocks.RAW);
        addDrop(blocks.BRICKS);
        addDrop(blocks.CAPPED);
        addDrop(blocks.POLISHED);
        addSlabDrop(blocks.SLAB);
        addSlabDrop(blocks.BRICK_SLAB);
        addSlabDrop(blocks.CAPPED_SLAB);
        addSlabDrop(blocks.POLISHED_SLAB);
        addDrop(blocks.STAIRS);
        addDrop(blocks.BRICK_STAIRS);
        addDrop(blocks.CAPPED_STAIRS);
        addDrop(blocks.POLISHED_STAIRS);
        addDrop(blocks.WALL);
        addDrop(blocks.BRICK_WALL);
        addDrop(blocks.CAPPED_WALL);
        addDrop(blocks.POLISHED_WALL);
        addDrop(blocks.SALT_LAMP);
    }

    private void addWoodDrops(WoodBlocks blocks) {
        addDrop(blocks.PLANKS);
        addDrop(blocks.SAPLING);
        addPottedPlantDrop(blocks.POTTED_SAPLING);
        addDrop(blocks.LOG);
        addDrop(blocks.STRIPPED_LOG);
        addDrop(blocks.WOOD);
        addDrop(blocks.STRIPPED_WOOD);
        addDrop(blocks.LEAVES, block -> dropLeaves(block, blocks.SAPLING, SAPLING_DROP_CHANCES));
        addSlabDrop(blocks.SLAB);
        addDrop(blocks.STAIRS);
        addDrop(blocks.FENCE);
        addDrop(blocks.DOOR, MarblesBlockLootTables::dropsDoor);
        addDrop(blocks.TRAPDOOR);
        addDrop(blocks.FENCE_GATE);
        addDrop(blocks.PRESSURE_PLATE);
        addDrop(blocks.BUTTON);
        addDrop(blocks.SIGN);
        addDrop(blocks.WALL_SIGN);
    }


    private static <T> T explosionFunc(ItemConvertible drop, LootFunctionConsumingBuilder<T> builder) {
        return !EXPLOSION_IMMUNE.contains(drop.asItem())
               ? builder.apply(ExplosionDecayLootFunction.builder())
               : builder.getThis();
    }

    private static <T> T explosionCond(ItemConvertible drop, LootConditionConsumingBuilder<T> builder) {
        return !EXPLOSION_IMMUNE.contains(drop.asItem())
               ? builder.conditionally(SurvivesExplosionLootCondition.builder())
               : builder.getThis();
    }

    private static ConditionalLootFunction.Builder<?> setCount(LootNumberProvider range) {
        return SetCountLootFunction.builder(range);
    }

    private static LootPool.Builder pool(LootNumberProvider rolls) {
        return LootPool.builder().rolls(rolls);
    }

    private static LootPool.Builder pool() {
        return pool(count(1));
    }

    private static LootNumberProvider count(int count) {
        return ConstantLootNumberProvider.create(count);
    }

    private static LootNumberProvider countRandom(float min, float max) {
        return UniformLootNumberProvider.create(min, max);
    }

    private static LootNumberProvider countBiased(int n, float p) {
        return BinomialLootNumberProvider.create(n, p);
    }

    private static <T extends Comparable<T>> StatePredicate.Builder stateProp(Property<T> prop, T val) {
        return StatePredicate.Builder.create().exactMatch(prop, prop.name(val));
    }

    private static <T extends Comparable<T>> BlockStatePropertyLootCondition.Builder stateCond(Block block, Property<T> prop, T val) {
        return BlockStatePropertyLootCondition.builder(block).properties(stateProp(prop, val));
    }

    private static BoundedIntUnaryOperator atLeast(int min) {
        return BoundedIntUnaryOperator.createMin(min);
    }

    private static BoundedIntUnaryOperator atMost(int max) {
        return BoundedIntUnaryOperator.createMax(max);
    }

    private static BoundedIntUnaryOperator minMax(int min, int max) {
        return BoundedIntUnaryOperator.create(min, max);
    }

    private static RandomChanceLootCondition.Builder chance(float chance) {
        return RandomChanceLootCondition.builder(chance);
    }



    protected static LootTable.Builder drops(ItemConvertible drop) {
        return LootTable.builder().pool(
            explosionCond(
                drop,
                LootPool.builder().rolls(count(1))
                        .with(ItemEntry.builder(drop))
            )
        );
    }

    protected static LootTable.Builder drops(ItemConvertible drop, LootNumberProvider count) {
        return LootTable.builder().pool(
            pool().with(explosionFunc(drop, ItemEntry.builder(drop).apply(setCount(count))))
        );
    }

    protected static LootTable.Builder dropsConditionally(ItemConvertible drop, LootCondition.Builder condition, LootPoolEntry.Builder<?> orElse) {
        return LootTable.builder().pool(
            pool().with(ItemEntry.builder(drop).conditionally(condition).alternatively(orElse))
        );
    }

    protected static LootTable.Builder dropsWithSilkTouch(ItemConvertible drop, LootPoolEntry.Builder<?> orElse) {
        return dropsConditionally(drop, WITH_SILK_TOUCH, orElse);
    }

    protected static LootTable.Builder dropsWithShears(ItemConvertible drop, LootPoolEntry.Builder<?> orElse) {
        return dropsConditionally(drop, WITH_SHEARS, orElse);
    }

    protected static LootTable.Builder dropsWithSilkTouchOrShears(ItemConvertible drop, LootPoolEntry.Builder<?> orElse) {
        return dropsConditionally(drop, WITH_SILK_TOUCH_OR_SHEARS, orElse);
    }

    protected static LootTable.Builder dropsWithSilkTouch(ItemConvertible drop, ItemConvertible orElse) {
        return dropsWithSilkTouch(drop, explosionCond(drop, ItemEntry.builder(orElse)));
    }

    protected static LootTable.Builder dropsWithSilkTouch(ItemConvertible drop, ItemConvertible orElse, LootNumberProvider elseCount) {
        return dropsWithSilkTouch(drop, explosionFunc(drop, ItemEntry.builder(orElse).apply(setCount(elseCount))));
    }

    protected static LootTable.Builder dropsWithSilkTouch(ItemConvertible drop) {
        return LootTable.builder().pool(
            pool().conditionally(WITH_SILK_TOUCH)
                  .with(ItemEntry.builder(drop))
        );
    }

    protected static LootTable.Builder dropsFlowerPotWithPlant(ItemConvertible plant) {
        return LootTable.builder().pool(
            explosionCond(
                Blocks.FLOWER_POT,
                pool().with(ItemEntry.builder(Blocks.FLOWER_POT))
            )
        ).pool(
            explosionCond(
                plant,
                pool().with(ItemEntry.builder(plant))
            )
        );
    }

    protected static LootTable.Builder dropsSlab(Block slab) {
        return LootTable.builder().pool(
            pool().with(explosionFunc(
                slab,
                ItemEntry.builder(slab).apply(
                    setCount(count(2)).conditionally(
                        stateCond(slab, SlabBlock.TYPE, SlabType.DOUBLE)
                    )
                )
            )));
    }

    protected static <T extends Comparable<T>> LootTable.Builder dropsWithProperty(Block drop, Property<T> prop, T val) {
        return LootTable.builder().pool(
            explosionCond(
                drop,
                pool().with(ItemEntry.builder(drop).conditionally(stateCond(drop, prop, val)))
            )
        );
    }

    protected static LootTable.Builder dropsNamedContainer(Block drop) {
        return LootTable.builder().pool(explosionCond(
            drop, pool().with(
                ItemEntry.builder(drop)
                         .apply(CopyNameLootFunction.builder(CopyNameLootFunction.Source.BLOCK_ENTITY))
            )
        ));
    }

    protected static LootTable.Builder dropsShulkerBox(Block drop) {
        return LootTable.builder().pool(explosionCond(
            drop, pool().with(
                ItemEntry.builder(drop)
                         .apply(CopyNameLootFunction.builder(CopyNameLootFunction.Source.BLOCK_ENTITY))
                         .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
                                                   .withOperation("Lock", "BlockEntityTag.Lock")
                                                   .withOperation("LootTable", "BlockEntityTag.LootTable")
                                                   .withOperation("LootTableSeed", "BlockEntityTag.LootTableSeed")
                         )
                         .apply(SetContentsLootFunction.builder()
                                                       .withEntry(DynamicEntry.builder(ShulkerBoxBlock.CONTENTS))
                         )
            )
        ));
    }

    protected static LootTable.Builder dropsBanner(Block drop) {
        return LootTable.builder().pool(explosionCond(
            drop, pool().with(
                ItemEntry.builder(drop)
                         .apply(CopyNameLootFunction.builder(CopyNameLootFunction.Source.BLOCK_ENTITY))
                         .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
                                                   .withOperation("Patterns", "BlockEntityTag.Patterns")
                         )
            )
        ));
    }

    private static LootTable.Builder oreDrops(Block dropWithSilkTouch, Item drop) {
        return BlockLootTableGeneratorInvoker.invoke_oreDrops(dropWithSilkTouch, drop);
    }
    private static LootTable.Builder dropsCopperOre(Block ore) {
        return BlockLootTableGeneratorInvoker.invoke_copperOreDrops(ore);
    }
    private static LootTable.Builder dropsRedstoneOre(Block ore) {
        return BlockLootTableGeneratorInvoker.invoke_redstoneOreDrops(ore);
    }
    private static LootTable.Builder dropsLapisOre(Block ore) {
        return BlockLootTableGeneratorInvoker.invoke_lapisOreDrops(ore);
    }
    private static LootTable.Builder dropsUmbralLazuliOre(Block ore) {
        return dropsWithSilkTouch(ore, MarblesItems.UMBRAL_LAZULI, countBiased(4, 0.4f));
    }

    protected static LootTable.Builder dropsBeeNest(Block drop) {
        return LootTable.builder().pool(
            pool().conditionally(WITH_SILK_TOUCH).with(
                ItemEntry.builder(drop)
                         .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
                                                   .withOperation("Bees", "BlockEntityTag.Bees")
                         )
                         .apply(CopyStateFunction.getBuilder(drop).addProperty(BeehiveBlock.HONEY_LEVEL))
            )
        );
    }

    protected static LootTable.Builder dropsBeehive(Block drop) {
        return LootTable.builder().pool(
            pool().with(
                ItemEntry.builder(drop)
                         .conditionally(WITH_SILK_TOUCH)
                         .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
                                                   .withOperation("Bees", "BlockEntityTag.Bees")
                         )
                         .apply(CopyStateFunction.getBuilder(drop).addProperty(BeehiveBlock.HONEY_LEVEL))
                         .alternatively(ItemEntry.builder(drop))
            )
        );
    }

    protected static LootTable.Builder dropsMineral(ItemConvertible ore, ItemConvertible mineral) {
        return dropsWithSilkTouch(
            ore,
            explosionFunc(
                ore,
                ItemEntry.builder(mineral)
                         .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
            )
        );
    }

    protected static LootTable.Builder dropsMushroomBlock(Block block, ItemConvertible mushroom) {
        return dropsWithSilkTouch(
            block,
            explosionFunc(
                block,
                ItemEntry.builder(mushroom)
                         .apply(setCount(countRandom(-6, 2)))
                         .apply(LimitCountLootFunction.builder(atLeast(0)))
            )
        );
    }

    protected static LootTable.Builder dropsGrass(Block grass) {
        return dropsWithShears(
            grass,
            explosionFunc(
                grass,
                ItemEntry.builder(Items.WHEAT_SEEDS)
                         .conditionally(chance(0.125f))
                         .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE, 2))
            )
        );
    }

    private static LootTable.Builder dropsSeagrass(Block seagrass) {
        return LootTable.builder()
            .pool(
                LootPool.builder()
                    .conditionally(WITH_SHEARS)
                    .with(
                        ItemEntry.builder(seagrass)
                            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F)))
                    )
        );
    }

    protected static LootTable.Builder dropsCropStem(Block stem, ItemConvertible seed) {
        return LootTable.builder().pool(explosionFunc(
            stem, pool().with(
                ItemEntry.builder(seed)
                         .apply(setCount(countBiased(3, 1 / 15f)).conditionally(stateCond(stem, StemBlock.AGE, 0)))
                         .apply(setCount(countBiased(3, 1 / 7.5f)).conditionally(stateCond(stem, StemBlock.AGE, 1)))
                         .apply(setCount(countBiased(3, 1 / 5f)).conditionally(stateCond(stem, StemBlock.AGE, 2)))
                         .apply(setCount(countBiased(3, 1 / 3.75f)).conditionally(stateCond(stem, StemBlock.AGE, 3)))
                         .apply(setCount(countBiased(3, 1 / 3f)).conditionally(stateCond(stem, StemBlock.AGE, 4)))
                         .apply(setCount(countBiased(3, 1 / 2.5f)).conditionally(stateCond(stem, StemBlock.AGE, 5)))
                         .apply(setCount(countBiased(3, 1 / 2.142857f)).conditionally(stateCond(stem, StemBlock.AGE, 6)))
                         .apply(setCount(countBiased(3, 1 / 1.875f)).conditionally(stateCond(stem, StemBlock.AGE, 7)))
            )
        ));
    }

    protected static LootTable.Builder dropsCropStemAttached(Block stem, ItemConvertible seed) {
        return LootTable.builder().pool(explosionFunc(
            stem, pool().with(
                ItemEntry.builder(seed).apply(setCount(countBiased(3, 1 / 1.875f)))
            )
        ));
    }

    protected static LootTable.Builder dropsWithShears(ItemConvertible drop) {
        return LootTable.builder().pool(
            pool().conditionally(WITH_SHEARS)
                  .with(ItemEntry.builder(drop))
        );
    }

    protected static LootTable.Builder dropLeaves(Block leaves, ItemConvertible sapling, float... chance) {
        return dropsWithSilkTouchOrShears(
            leaves,
            explosionCond(leaves, ItemEntry.builder(sapling))
                .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, chance))
        ).pool(
            pool().conditionally(WITHOUT_SILK_TOUCH_NOR_SHEARS).with(
                explosionFunc(
                    leaves,
                    ItemEntry.builder(Items.STICK)
                             .apply(setCount(countRandom(1, 2)))
                ).conditionally(
                    TableBonusLootCondition.builder(
                        Enchantments.FORTUNE,
                        1 / 50f, 1 / 45f, 1 / 40f, 1 / 30f, 1 / 10f
                    )
                )
            )
        );
    }

    protected static LootTable.Builder dropLeavesExtra(Block leaves, ItemConvertible sapling, ItemConvertible extra, float... chance) {
        return dropLeaves(leaves, sapling, chance).pool(
            pool().conditionally(WITHOUT_SILK_TOUCH_NOR_SHEARS).with(
                explosionCond(leaves, ItemEntry.builder(extra)).conditionally(
                    TableBonusLootCondition.builder(
                        Enchantments.FORTUNE,
                        1 / 200f, 1 / 180f, 1 / 160f, 1 / 120f, 1 / 40f
                    )
                )
            )
        );
    }

    protected static LootTable.Builder dropsCrop(Block crop, ItemConvertible product, ItemConvertible seeds, LootCondition.Builder condition) {
        return explosionFunc(crop, LootTable.builder().pool(
            pool().with(
                ItemEntry.builder(product)
                         .conditionally(condition)
                         .alternatively(ItemEntry.builder(seeds))
            )
        ).pool(
            LootPool.builder()
                    .conditionally(condition)
                    .with(ItemEntry.builder(seeds).apply(
                        ApplyBonusLootFunction.binomialWithBonusCount(Enchantments.FORTUNE, 1 / 1.75f, 3)
                    ))
        ));
    }

    protected static LootTable.Builder dropsDoublePlant(ItemConvertible drop) {
        return LootTable.builder().pool(
            pool().conditionally(WITH_SHEARS)
                  .with(ItemEntry.builder(drop).apply(setCount(count(2))))
        );
    }

    protected static LootTable.Builder dropsDoubleGrass(Block plant, ItemConvertible shearDrops) {
        LootPoolEntry.Builder<?> seeds
            = ItemEntry.builder(shearDrops)
                       .apply(setCount(count(2)))
                       .conditionally(WITH_SHEARS)
                       .alternatively(
                           explosionCond(plant, ItemEntry.builder(Items.WHEAT_SEEDS))
                               .conditionally(chance(1 / 8f))
                       );

        return LootTable.builder().pool(
            pool().with(seeds)
                  .conditionally(stateCond(plant, TallPlantBlock.HALF, DoubleBlockHalf.LOWER))
                  .conditionally(checkDoublePlantHalf(plant, DoubleBlockHalf.UPPER))
        ).pool(
            pool().with(seeds)
                  .conditionally(stateCond(plant, TallPlantBlock.HALF, DoubleBlockHalf.UPPER))
                  .conditionally(checkDoublePlantHalf(plant, DoubleBlockHalf.LOWER))
        );
    }

    protected static LootTable.Builder dropsAttachingQuad(Block block) {
        return LootTable.builder().pool(
            pool().with(
                AlternativeEntry.builder(
                    ItemEntry.builder(block)
                             .apply(conditionalSetCountQuad(block, 1.0f, QuadBlockPart.SECOND))
                             .apply(conditionalSetCountQuad(block, 2.0f, QuadBlockPart.THIRD))
                             .apply(conditionalSetCountQuad(block, 3.0f, QuadBlockPart.FOURTH))
                )
            )
        );
    }
    private static LootFunction.Builder conditionalSetCountQuad(Block block, float count, QuadBlockPart part) {
        return SetCountLootFunction.builder(ConstantLootNumberProvider.create(count), true)
                                   .conditionally(BlockStatePropertyLootCondition.builder(block)
                                                                                 .properties(StatePredicate.Builder.create()
                                                                                                                   .exactMatch(QuadAttachingBlock.PART, part)));
    }

    private static LocationCheckLootCondition.Builder checkDoublePlantHalf(Block plant, DoubleBlockHalf half) {
        int d = half == DoubleBlockHalf.UPPER ? 1 : -1;
        StatePredicate expectState = stateProp(TallPlantBlock.HALF, half).build();

        return LocationCheckLootCondition.builder(
            LocationPredicate.Builder.create().block(
                BlockPredicate.Builder.create().blocks(plant).state(expectState).build()
            ),
            new BlockPos(0, d, 0)
        );
    }

    protected static LootTable.Builder dropsNothing() {
        return LootTable.builder();
    }

    public static LootTable.Builder dropsDoor(Block block) {
        return dropsWithProperty(block, DoorBlock.HALF, DoubleBlockHalf.LOWER);
    }

    public void addPottedPlantDrop(Block block) {
        addDrop(block, blockx -> dropsFlowerPotWithPlant(((FlowerPotBlock) blockx).getContent()));
    }

    public void addDropWithSilkTouch(Block block, Block drop) {
        addDrop(block, dropsWithSilkTouch(drop));
    }

    public void addDrop(Block block, ItemConvertible drop) {
        addDrop(block, drops(drop));
    }

    public void addDropWithSilkTouch(Block block) {
        addDropWithSilkTouch(block, block);
    }

    public void addDrop(Block block) {
        addDrop(block, block);
    }

    public void addSlabDrop(Block block) {
        addDrop(block, MarblesBlockLootTables::dropsSlab);
    }

    private void addDrop(Block block, Function<Block, LootTable.Builder> function) {
        addDrop(block, function.apply(block));
    }

    private void addDrop(Block block, LootTable.Builder lootTable) {
        lootTables.put(block.getLootTableId(), lootTable);
    }
}
