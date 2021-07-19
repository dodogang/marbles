package net.dodogang.marbles.datagen.models;

import me.andante.chord.block.helper.WoodBlocks;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.block.helper.TravertineBlocks;
import net.dodogang.marbles.datagen.models.modelgen.ModelGen;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.init.MarblesItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static net.dodogang.marbles.datagen.models.modelgen.InheritingModelGen.*;

@SuppressWarnings("unused")
public final class ItemModelTable {
    private static BiConsumer<Item, ModelGen> consumer;

    public static void registerItemModels(BiConsumer<Item, ModelGen> c) {
        consumer = c;

        /*
         * WOOD SETS
         */

        registerWoodBlocks(MarblesBlocks.ASPEN);
        registerWoodBlocks(MarblesBlocks.HOOPSI_SPRUCE);
        registerWoodBlocks(MarblesBlocks.RED_BIRCH);

        /*
         * TRAVERTINE SETS
         */

        registerTravertineBlocks(MarblesBlocks.TRAVERTINE_BLOCKS);
        registerTravertineBlocks(MarblesBlocks.LEMON_TRAVERTINE_BLOCKS);
        registerTravertineBlocks(MarblesBlocks.PEACH_TRAVERTINE_BLOCKS);
        registerTravertineBlocks(MarblesBlocks.TANGERINE_TRAVERTINE_BLOCKS);

        register(MarblesBlocks.TRAVERTINE_OBSIDIAN, ItemModelTable::inheritBlockFunc);

        register(MarblesBlocks.LIMESTONE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.LIMESTONE_SLAB, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.LIMESTONE_STAIRS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.LIMESTONE_WALL, ItemModelTable::wallSidedTopFunc);
        register(MarblesBlocks.POLISHED_LIMESTONE, item -> inherit(name(item, "block/%s_first")));
        register(MarblesBlocks.POLISHED_LIMESTONE_SLAB, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.POLISHED_LIMESTONE_STAIRS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.POLISHED_LIMESTONE_WALL, ItemModelTable::wallSidedTopFunc);

        /*
         * PINK SALT
         */

        register(MarblesBlocks.PINK_SALT, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.PINK_SALT_SLAB, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.PINK_SALT_STAIRS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.CRUMBLED_PINK_SALT, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.PINK_SALT_BRICKS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.PINK_SALT_BRICK_SLAB, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.PINK_SALT_BRICK_STAIRS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.PINK_SALT_PILLAR, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.PINK_SALT_SPIRE, ItemModelTable::generatedItemFunc);
        register(MarblesBlocks.PINK_SALT_STACK, ItemModelTable::generatedItemFunc);
        register(MarblesBlocks.PINK_SALT_STUMP, ItemModelTable::generatedItemFunc);
        register(MarblesBlocks.PINK_SALT_SPIKES, ItemModelTable::generatedItemFunc);

        register(MarblesBlocks.PINK_SALT_GOLD_ORE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.PINK_SALT_IRON_ORE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.PINK_SALT_COAL_ORE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.PINK_SALT_LAPIS_ORE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.PINK_SALT_DIAMOND_ORE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.PINK_SALT_REDSTONE_ORE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.PINK_SALT_EMERALD_ORE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.PINK_SALT_COPPER_ORE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.PINK_SALT_UMBRAL_LAZULI_ORE, ItemModelTable::inheritBlockFunc);

        /*
         * LAPIS SETS
         */

        register(MarblesBlocks.LAPIS_SHINGLES, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.LAPIS_SHINGLE_SLAB, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.LAPIS_SHINGLE_STAIRS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.LAPIS_SPOTLIGHT, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.GLAZED_LAPIS, ItemModelTable::inheritBlockFunc);
        register(MarblesItems.UMBRAL_LAZULI, ItemModelTable::generatedItemFunc);
        register(MarblesBlocks.UMBRAL_LAZULI_ORE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.DEEPSLATE_UMBRAL_LAZULI_ORE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.UMBRAL_LAZULI_BLOCK, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.UMBRAL_LAZULI_SHINGLES, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.UMBRAL_LAZULI_SHINGLE_SLAB, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.UMBRAL_LAZULI_SHINGLE_STAIRS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.UMBRAL_LAZULI_SPOTLIGHT, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.GLAZED_UMBRAL_LAZULI, ItemModelTable::inheritBlockFunc);

        /*
         * SANDSTONE SETS
         */

        register(MarblesBlocks.DAWN_SAND, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.DAWN_SANDSTONE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.CHISELED_DAWN_SANDSTONE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.CUT_DAWN_SANDSTONE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.SMOOTH_DAWN_SANDSTONE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.DAWN_SANDSTONE_SLAB, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.CUT_DAWN_SANDSTONE_SLAB, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.SMOOTH_DAWN_SANDSTONE_SLAB, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.DAWN_SANDSTONE_STAIRS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.CUT_DAWN_SANDSTONE_STAIRS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.SMOOTH_DAWN_SANDSTONE_STAIRS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.DAWN_SANDSTONE_WALL, ItemModelTable::wallSidedTopBottomFunc);
        register(MarblesBlocks.CUT_DAWN_SANDSTONE_WALL, item -> using(name(item, "block/%s", "(^cut_)|(_wall$)", ""), n -> wallSidedInventory(n + "_bottom", n + "_top", n)));
        register(MarblesBlocks.SMOOTH_DAWN_SANDSTONE_WALL, item -> wallInventory(name(item, "block/%s_top", "(^smooth_)|(_wall$)", "")));

        register(MarblesBlocks.DUSK_SAND, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.DUSK_SANDSTONE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.CHISELED_DUSK_SANDSTONE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.CUT_DUSK_SANDSTONE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.SMOOTH_DUSK_SANDSTONE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.DUSK_SANDSTONE_SLAB, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.CUT_DUSK_SANDSTONE_SLAB, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.SMOOTH_DUSK_SANDSTONE_SLAB, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.DUSK_SANDSTONE_STAIRS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.CUT_DUSK_SANDSTONE_STAIRS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.SMOOTH_DUSK_SANDSTONE_STAIRS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.DUSK_SANDSTONE_WALL, ItemModelTable::wallSidedTopBottomFunc);
        register(MarblesBlocks.CUT_DUSK_SANDSTONE_WALL, item -> using(name(item, "block/%s", "(^cut_)|(_wall$)", ""), n -> wallSidedInventory(n + "_bottom", n + "_top", n)));
        register(MarblesBlocks.SMOOTH_DUSK_SANDSTONE_WALL, item -> wallInventory(name(item, "block/%s_top", "(^smooth_)|(_wall$)", "")));

        register(MarblesBlocks.MORN_GRASS, ItemModelTable::generatedItemFunc);
        register(MarblesBlocks.TALL_MORN_GRASS, ItemModelTable::generatedItemFunc);

        /*
         * GRISP SET
         */

        register(MarblesBlocks.GRISP_DIRT, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.COARSE_GRISP_DIRT, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.GRISP_PODZOL, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.GRISP_MYCELIUM, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.GRISP_DIRT_PATH, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.GRISP_PODZOL_PATH, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.GRISP_MYCELIUM_PATH, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.GRISP_FARMLAND, ItemModelTable::inheritBlockFunc);

        register(MarblesBlocks.POLLENATED_COBBLESTONE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.POLLENATED_COBBLESTONE_SLAB, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.POLLENATED_COBBLESTONE_STAIRS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.POLLENATED_COBBLESTONE_WALL, ItemModelTable::wallFunc);

        register(MarblesBlocks.ASPEN_SPROUTS, ItemModelTable::generatedBlockFunc);
        register(MarblesBlocks.ASPEN_GRASS, ItemModelTable::generatedBlockFunc);
        register(MarblesBlocks.TALL_ASPEN_GRASS, item -> generated(name(item, "block/%s_top")));

        /*
         * PLANTAGE
         */

        register(MarblesBlocks.BLUE_PEONY, item -> generated(name(item, "block/%s_upper")));

        /*
         * POLLEN-GRACED SET
         */

        register(MarblesBlocks.POLLEN_GRACED_WOOL, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.POLLEN_GRACED_CARPET, ItemModelTable::inheritBlockFunc);

        /*
         * BAMBOO
         */

        register(MarblesBlocks.CHEQUERED_BAMBOO_LATTICE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.CROSSED_BAMBOO_LATTICE, ItemModelTable::inheritBlockFunc);

        register(MarblesItems.YELLOW_SCAFFOLDING, item -> inherit(new Identifier(Marbles.MOD_ID, "block/yellow_scaffolding_stable")));
        register(MarblesBlocks.YELLOW_BAMBOO, ItemModelTable::generatedItemFunc);
        register(MarblesBlocks.CHEQUERED_YELLOW_BAMBOO_LATTICE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.CROSSED_YELLOW_BAMBOO_LATTICE, ItemModelTable::inheritBlockFunc);

        register(MarblesBlocks.BAMBOO_TIKI_TORCH, ItemModelTable::generatedBlockFunc);
        register(MarblesBlocks.BAMBOO_TIKI_POLE, ItemModelTable::inheritBlockFunc);

        register(MarblesBlocks.YELLOW_BAMBOO_TIKI_TORCH, ItemModelTable::generatedBlockFunc);
        register(MarblesBlocks.YELLOW_BAMBOO_TIKI_POLE, ItemModelTable::inheritBlockFunc);

        /*
         * MISC
         */

        register(MarblesBlocks.ROPE, ItemModelTable::generatedItemFunc);
        register(MarblesItems.PINK_SALT_SHARD, ItemModelTable::generatedItemFunc);

        /*
         * FLOESTONE
         */

        register(MarblesBlocks.FLOESTONE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.FLOESTONE_SLAB, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.FLOESTONE_STAIRS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.FLOESTONE_WALL, ItemModelTable::wallFunc);

        register(MarblesBlocks.POLISHED_FLOESTONE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.POLISHED_FLOESTONE_SLAB, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.POLISHED_FLOESTONE_STAIRS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.POLISHED_FLOESTONE_WALL, ItemModelTable::wallFunc);

        register(MarblesBlocks.FLOESTONE_BRICKS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.FLOESTONE_BRICK_SLAB, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.FLOESTONE_BRICK_STAIRS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.FLOESTONE_BRICK_WALL, item -> wallInventory(name(item, "block/%ss", "_wall")));

        register(MarblesBlocks.CHISELED_FLOESTONE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.RILLED_FLOESTONE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.RINGED_FLOESTONE, ItemModelTable::inheritBlockFunc);

        /*
         * ICE
         */

        register(MarblesBlocks.SCALED_ICE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.MINTED_ICE, ItemModelTable::inheritBlockFunc);

        register(MarblesBlocks.CUT_ICE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.CUT_BLUE_ICE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.CUT_SCALED_ICE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.CUT_MINTED_ICE, ItemModelTable::inheritBlockFunc);

        register(MarblesBlocks.CHISELED_ICE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.CHISELED_BLUE_ICE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.CHISELED_SCALED_ICE, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.CHISELED_MINTED_ICE, ItemModelTable::inheritBlockFunc);

        register(MarblesBlocks.ICE_BRICKS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.BLUE_ICE_BRICKS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.SCALED_ICE_BRICKS, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.MINTED_ICE_BRICKS, ItemModelTable::inheritBlockFunc);

        register(MarblesBlocks.SLUSH, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.BLUE_SLUSH, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.SCALED_SLUSH, ItemModelTable::inheritBlockFunc);
        register(MarblesBlocks.MINTED_SLUSH, ItemModelTable::inheritBlockFunc);
    }

    private static void registerTravertineBlocks(TravertineBlocks blocks) {
        register(blocks.RAW, ItemModelTable::inheritBlockFunc);
        register(blocks.BRICKS, ItemModelTable::inheritBlockFunc);
        register(blocks.CAPPED, ItemModelTable::inheritBlockFunc);
        register(blocks.POLISHED, ItemModelTable::inheritBlockFunc);
        register(blocks.SLAB, ItemModelTable::inheritBlockFunc);
        register(blocks.BRICK_SLAB, ItemModelTable::inheritBlockFunc);
        register(blocks.CAPPED_SLAB, ItemModelTable::inheritBlockFunc);
        register(blocks.POLISHED_SLAB, ItemModelTable::inheritBlockFunc);
        register(blocks.STAIRS, ItemModelTable::inheritBlockFunc);
        register(blocks.BRICK_STAIRS, ItemModelTable::inheritBlockFunc);
        register(blocks.CAPPED_STAIRS, ItemModelTable::inheritBlockFunc);
        register(blocks.POLISHED_STAIRS, ItemModelTable::inheritBlockFunc);
        register(blocks.WALL, ItemModelTable::wallSidedTopFunc);
        register(blocks.BRICK_WALL, item -> using(name(item, "block/%s_bricks", "_brick_wall"), n -> wallSidedInventory(n + "_top", n + "_top", n)));
        register(blocks.CAPPED_WALL, ItemModelTable::wallSidedTopFunc);
        register(blocks.POLISHED_WALL, ItemModelTable::wallFunc);
        register(blocks.SALT_LAMP, ItemModelTable::inheritBlockFunc);
    }

    private static void registerWoodBlocks(WoodBlocks blocks) {
        register(blocks.PLANKS, ItemModelTable::inheritBlockFunc);
        register(blocks.SAPLING, ItemModelTable::generatedBlockFunc);
        register(blocks.LOG, ItemModelTable::inheritBlockFunc);
        register(blocks.STRIPPED_LOG, ItemModelTable::inheritBlockFunc);
        register(blocks.WOOD, ItemModelTable::inheritBlockFunc);
        register(blocks.STRIPPED_WOOD, ItemModelTable::inheritBlockFunc);
        register(blocks.LEAVES, ItemModelTable::inheritBlockFunc);
        register(blocks.SLAB, ItemModelTable::inheritBlockFunc);
        register(blocks.STAIRS, ItemModelTable::inheritBlockFunc);
        register(blocks.FENCE, item -> fenceInventory(name(item, "block/%s_planks", "_fence")));
        register(blocks.DOOR, ItemModelTable::generatedItemFunc);
        register(blocks.TRAPDOOR, item -> inherit(name(item, "block/%s_bottom")));
        register(blocks.FENCE_GATE, ItemModelTable::inheritBlockFunc);
        register(blocks.PRESSURE_PLATE, ItemModelTable::inheritBlockFunc);
        register(blocks.BUTTON, item -> buttonInventory(name(item, "block/%s_planks", "_button")));
        register(blocks.SIGN_ITEM, ItemModelTable::generatedItemFunc);
        register(blocks.BOAT_ITEM, ItemModelTable::generatedItemFunc);
    }

    private static void register(ItemConvertible provider, Function<Item, ModelGen> genFactory) {
        Item item = provider.asItem();
        ModelGen gen = genFactory.apply(item);
        consumer.accept(item, gen);
    }

    private static String name(Item item, String nameFormat) {
        Identifier id = Registry.ITEM.getId(item);
        return String.format("%s:%s", id.getNamespace(), String.format(nameFormat, id.getPath()));
    }

    private static String name(Item item) {
        Identifier id = Registry.ITEM.getId(item);
        return id.toString();
    }

    private static String name(Item item, String nameFormat, String omitSuffix) {
        Identifier id = Registry.ITEM.getId(item);

        String path = id.getPath();
        if (path.endsWith(omitSuffix)) {
            path = path.substring(0, path.length() - omitSuffix.length());
        }

        return String.format("%s:%s", id.getNamespace(), String.format(nameFormat, path));
    }

    private static String name(Item item, String nameFormat, String pattern, String reformat) {
        Identifier id = Registry.ITEM.getId(item);

        String path = id.getPath();
        path = path.replaceAll(pattern, reformat);

        return String.format("%s:%s", id.getNamespace(), String.format(nameFormat, path));
    }

    private static <T> T using(String name, Function<String, T> func) {
        return func.apply(name);
    }

    private static ModelGen generatedItemFunc(Item item) {
        return generated(name(item, "item/%s"));
    }
    private static ModelGen generatedBlockFunc(Item item) {
        return generated(name(item, "block/%s"));
    }
    private static ModelGen inheritBlockFunc(Item item) {
        return inherit(name(item, "block/%s"));
    }
    private static ModelGen wallFunc(Item item) {
        return wallInventory(name(item, "block/%s", "_wall"));
    }
    private static ModelGen wallSidedTopFunc(Item item) {
        return using(name(item, "block/%s", "_wall"), n -> wallSidedInventory(n + "_top", n + "_top", n));
    }
    private static ModelGen wallSidedTopBottomFunc(Item item) {
        return using(name(item, "block/%s", "_wall"), n -> wallSidedInventory(n + "_bottom", n + "_top", n));
    }

    private ItemModelTable() {
    }
}
