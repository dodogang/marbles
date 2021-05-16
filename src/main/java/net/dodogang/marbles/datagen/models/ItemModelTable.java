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

        register(MarblesBlocks.TRAVERTINE_OBSIDIAN, item -> inherit(name(item, "block/%s")));

        /*
         * PINK SALT
         */

        register(MarblesBlocks.PINK_SALT, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.PINK_SALT_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.PINK_SALT_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CRUMBLED_PINK_SALT, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.PINK_SALT_BRICKS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.PINK_SALT_BRICK_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.PINK_SALT_BRICK_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.PINK_SALT_PILLAR, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.PINK_SALT_SPIRE, item -> generated(name(item, "item/%s")));
        register(MarblesBlocks.PINK_SALT_STACK, item -> generated(name(item, "item/%s")));
        register(MarblesBlocks.PINK_SALT_STUMP, item -> generated(name(item, "item/%s")));
        register(MarblesBlocks.PINK_SALT_SPIKES, item -> generated(name(item, "item/%s")));

        /*
         * LAPIS SETS
         */

        register(MarblesBlocks.LAPIS_SHINGLES, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.LAPIS_SHINGLE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.LAPIS_SHINGLE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.LAPIS_SPOTLIGHT, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.GLAZED_LAPIS, item -> inherit(name(item, "block/%s")));
        register(MarblesItems.UMBRAL_LAZULI, item -> generated(name(item, "item/%s")));
        register(MarblesBlocks.UMBRAL_LAZULI_ORE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.UMBRAL_LAZULI_BLOCK, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.UMBRAL_LAZULI_SHINGLES, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.UMBRAL_LAZULI_SHINGLE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.UMBRAL_LAZULI_SHINGLE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.UMBRAL_LAZULI_SPOTLIGHT, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.GLAZED_UMBRAL_LAZULI, item -> inherit(name(item, "block/%s")));

        /*
         * SANDSTONE SETS
         */

        register(MarblesBlocks.DAWN_SAND, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.DAWN_SANDSTONE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CHISELED_DAWN_SANDSTONE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CUT_DAWN_SANDSTONE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.SMOOTH_DAWN_SANDSTONE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.DAWN_SANDSTONE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CUT_DAWN_SANDSTONE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.SMOOTH_DAWN_SANDSTONE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.DAWN_SANDSTONE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CUT_DAWN_SANDSTONE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.SMOOTH_DAWN_SANDSTONE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.DAWN_SANDSTONE_WALL, item -> using(name(item, "block/%s", "_wall"), n -> wallSidedInventory(n + "_bottom", n + "_top", n)));
        register(MarblesBlocks.CUT_DAWN_SANDSTONE_WALL, item -> using(name(item, "block/%s", "(^cut_)|(_wall$)", ""), n -> wallSidedInventory(n + "_bottom", n + "_top", n)));
        register(MarblesBlocks.SMOOTH_DAWN_SANDSTONE_WALL, item -> wallInventory(name(item, "block/%s_top", "(^smooth_)|(_wall$)", "")));

        register(MarblesBlocks.DUSK_SAND, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.DUSK_SANDSTONE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CHISELED_DUSK_SANDSTONE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CUT_DUSK_SANDSTONE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.SMOOTH_DUSK_SANDSTONE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.DUSK_SANDSTONE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CUT_DUSK_SANDSTONE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.SMOOTH_DUSK_SANDSTONE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.DUSK_SANDSTONE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CUT_DUSK_SANDSTONE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.SMOOTH_DUSK_SANDSTONE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.DUSK_SANDSTONE_WALL, item -> using(name(item, "block/%s", "_wall"), n -> wallSidedInventory(n + "_bottom", n + "_top", n)));
        register(MarblesBlocks.CUT_DUSK_SANDSTONE_WALL, item -> using(name(item, "block/%s", "(^cut_)|(_wall$)", ""), n -> wallSidedInventory(n + "_bottom", n + "_top", n)));
        register(MarblesBlocks.SMOOTH_DUSK_SANDSTONE_WALL, item -> wallInventory(name(item, "block/%s_top", "(^smooth_)|(_wall$)", "")));

        /*
         * GRISP SET
         */

        register(MarblesBlocks.GRISP_DIRT, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.COARSE_GRISP_DIRT, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.GRISP_GRASS_BLOCK, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.GRISP_PODZOL, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.GRISP_MYCELIUM, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.GRISP_DIRT_PATH, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.GRISP_GRASS_PATH, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.GRISP_PODZOL_PATH, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.GRISP_MYCELIUM_PATH, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.GRISP_FARMLAND, item -> inherit(name(item, "block/%s")));

        /*
         * POLLEN-GRACED SET
         */

        register(MarblesBlocks.POLLEN_GRACED_WOOL, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.POLLEN_GRACED_CARPET, item -> inherit(name(item, "block/%s")));

        /*
         * MISC
         */

        register(MarblesItems.YELLOW_SCAFFOLDING, item -> inherit(new Identifier(Marbles.MOD_ID, "block/yellow_scaffolding_stable")));
        register(MarblesBlocks.YELLOW_BAMBOO, item -> generated(name(item, "item/%s")));

        register(MarblesBlocks.ROPE, item -> generated(name(item, "item/%s")));
        register(MarblesItems.PINK_SALT_SHARD, item -> generated(name(item, "item/%s")));

        /*
         * ICE
         */

        register(MarblesBlocks.FLOESTONE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.POLISHED_FLOESTONE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CHISELED_FLOESTONE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.FLOESTONE_BRICKS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.RILLED_FLOESTONE, item -> inherit(name(item, "block/%s")));

        register(MarblesBlocks.SCALED_ICE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.MINTED_ICE, item -> inherit(name(item, "block/%s")));

        register(MarblesBlocks.CUT_ICE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CUT_BLUE_ICE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CUT_SCALED_ICE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CUT_MINTED_ICE, item -> inherit(name(item, "block/%s")));

        register(MarblesBlocks.CHISELED_ICE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CHISELED_BLUE_ICE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CHISELED_SCALED_ICE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CHISELED_MINTED_ICE, item -> inherit(name(item, "block/%s")));

        register(MarblesBlocks.ICE_BRICKS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.BLUE_ICE_BRICKS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.SCALED_ICE_BRICKS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.MINTED_ICE_BRICKS, item -> inherit(name(item, "block/%s")));
    }

    private static void registerTravertineBlocks(TravertineBlocks blocks) {
        register(blocks.RAW, item -> inherit(name(item, "block/%s")));
        register(blocks.BRICKS, item -> inherit(name(item, "block/%s")));
        register(blocks.CAPPED, item -> inherit(name(item, "block/%s")));
        register(blocks.POLISHED, item -> inherit(name(item, "block/%s")));
        register(blocks.SLAB, item -> inherit(name(item, "block/%s")));
        register(blocks.BRICK_SLAB, item -> inherit(name(item, "block/%s")));
        register(blocks.CAPPED_SLAB, item -> inherit(name(item, "block/%s")));
        register(blocks.POLISHED_SLAB, item -> inherit(name(item, "block/%s")));
        register(blocks.STAIRS, item -> inherit(name(item, "block/%s")));
        register(blocks.BRICK_STAIRS, item -> inherit(name(item, "block/%s")));
        register(blocks.CAPPED_STAIRS, item -> inherit(name(item, "block/%s")));
        register(blocks.POLISHED_STAIRS, item -> inherit(name(item, "block/%s")));
        register(blocks.WALL, item -> using(name(item, "block/%s", "_wall"), n -> wallSidedInventory(n + "_top", n + "_top", n)));
        register(blocks.BRICK_WALL, item -> using(name(item, "block/%s_bricks", "_brick_wall"), n -> wallSidedInventory(n + "_top", n + "_top", n)));
        register(blocks.CAPPED_WALL, item -> using(name(item, "block/%s", "_wall"), n -> wallSidedInventory(n + "_top", n + "_top", n)));
        register(blocks.POLISHED_WALL, item -> wallInventory(name(item, "block/%s", "_wall")));
        register(blocks.SALT_LAMP, item -> inherit(name(item, "block/%s")));
    }

    private static void registerWoodBlocks(WoodBlocks blocks) {
        register(blocks.PLANKS, item -> inherit(name(item, "block/%s")));
        register(blocks.SAPLING, item -> generated(name(item, "block/%s")));
        register(blocks.LOG, item -> inherit(name(item, "block/%s")));
        register(blocks.STRIPPED_LOG, item -> inherit(name(item, "block/%s")));
        register(blocks.WOOD, item -> inherit(name(item, "block/%s")));
        register(blocks.STRIPPED_WOOD, item -> inherit(name(item, "block/%s")));
        register(blocks.LEAVES, item -> inherit(name(item, "block/%s")));
        register(blocks.SLAB, item -> inherit(name(item, "block/%s")));
        register(blocks.STAIRS, item -> inherit(name(item, "block/%s")));
        register(blocks.FENCE, item -> fenceInventory(name(item, "block/%s_planks", "_fence")));
        register(blocks.DOOR, item -> generated(name(item, "item/%s")));
        register(blocks.TRAPDOOR, item -> inherit(name(item, "block/%s_bottom")));
        register(blocks.FENCE_GATE, item -> inherit(name(item, "block/%s")));
        register(blocks.PRESSURE_PLATE, item -> inherit(name(item, "block/%s")));
        register(blocks.BUTTON, item -> buttonInventory(name(item, "block/%s_planks", "_button")));
        register(blocks.SIGN_ITEM, item -> generated(name(item, "item/%s")));
        register(blocks.BOAT_ITEM, item -> generated(name(item, "item/%s")));
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

    private ItemModelTable() {
    }
}
