package net.dodogang.marbles.data.models;

import me.andante.chord.block.helper.WoodBlocks;
import net.dodogang.marbles.data.models.modelgen.ModelGen;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.init.MarblesItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static net.dodogang.marbles.data.models.modelgen.InheritingModelGen.*;

public final class ItemModelTable {
    private static BiConsumer<Item, ModelGen> consumer;

    public static void registerItemModels(BiConsumer<Item, ModelGen> c) {
        consumer = c;

        registerWoodBlocks(MarblesBlocks.ASPEN);
        registerWoodBlocks(MarblesBlocks.HOOPSI_SPRUCE);

        register(MarblesBlocks.TRAVERTINE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.TRAVERTINE_BRICKS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CAPPED_TRAVERTINE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.POLISHED_TRAVERTINE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.TRAVERTINE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.TRAVERTINE_BRICK_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CAPPED_TRAVERTINE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.POLISHED_TRAVERTINE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.TRAVERTINE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.TRAVERTINE_BRICK_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CAPPED_TRAVERTINE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.POLISHED_TRAVERTINE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.TRAVERTINE_WALL, item -> using(name(item, "block/%s", "_wall"), n -> wallSidedInventory(n + "_top", n + "_top", n)));
        register(MarblesBlocks.TRAVERTINE_BRICK_WALL, item -> using(name(item, "block/%s_bricks", "_brick_wall"), n -> wallSidedInventory(n + "_top", n + "_top", n)));
        register(MarblesBlocks.CAPPED_TRAVERTINE_WALL, item -> using(name(item, "block/%s", "_wall"), n -> wallSidedInventory(n + "_top", n + "_top", n)));
        register(MarblesBlocks.POLISHED_TRAVERTINE_WALL, item -> wallInventory(name(item, "block/%s", "_wall")));

        register(MarblesBlocks.LEMON_TRAVERTINE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.LEMON_TRAVERTINE_BRICKS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CAPPED_LEMON_TRAVERTINE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.POLISHED_LEMON_TRAVERTINE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.LEMON_TRAVERTINE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.LEMON_TRAVERTINE_BRICK_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CAPPED_LEMON_TRAVERTINE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.POLISHED_LEMON_TRAVERTINE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.LEMON_TRAVERTINE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.LEMON_TRAVERTINE_BRICK_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CAPPED_LEMON_TRAVERTINE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.POLISHED_LEMON_TRAVERTINE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.LEMON_TRAVERTINE_WALL, item -> using(name(item, "block/%s", "_wall"), n -> wallSidedInventory(n + "_top", n + "_top", n)));
        register(MarblesBlocks.LEMON_TRAVERTINE_BRICK_WALL, item -> using(name(item, "block/%s_bricks", "_brick_wall"), n -> wallSidedInventory(n + "_top", n + "_top", n)));
        register(MarblesBlocks.CAPPED_LEMON_TRAVERTINE_WALL, item -> using(name(item, "block/%s", "_wall"), n -> wallSidedInventory(n + "_top", n + "_top", n)));
        register(MarblesBlocks.POLISHED_LEMON_TRAVERTINE_WALL, item -> wallInventory(name(item, "block/%s", "_wall")));

        register(MarblesBlocks.PEACH_TRAVERTINE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.PEACH_TRAVERTINE_BRICKS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CAPPED_PEACH_TRAVERTINE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.POLISHED_PEACH_TRAVERTINE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.PEACH_TRAVERTINE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.PEACH_TRAVERTINE_BRICK_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CAPPED_PEACH_TRAVERTINE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.POLISHED_PEACH_TRAVERTINE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.PEACH_TRAVERTINE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.PEACH_TRAVERTINE_BRICK_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CAPPED_PEACH_TRAVERTINE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.POLISHED_PEACH_TRAVERTINE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.PEACH_TRAVERTINE_WALL, item -> using(name(item, "block/%s", "_wall"), n -> wallSidedInventory(n + "_top", n + "_top", n)));
        register(MarblesBlocks.PEACH_TRAVERTINE_BRICK_WALL, item -> using(name(item, "block/%s_bricks", "_brick_wall"), n -> wallSidedInventory(n + "_top", n + "_top", n)));
        register(MarblesBlocks.CAPPED_PEACH_TRAVERTINE_WALL, item -> using(name(item, "block/%s", "_wall"), n -> wallSidedInventory(n + "_top", n + "_top", n)));
        register(MarblesBlocks.POLISHED_PEACH_TRAVERTINE_WALL, item -> wallInventory(name(item, "block/%s", "_wall")));

        register(MarblesBlocks.TANGERINE_TRAVERTINE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.TANGERINE_TRAVERTINE_BRICKS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CAPPED_TANGERINE_TRAVERTINE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.POLISHED_TANGERINE_TRAVERTINE, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.TANGERINE_TRAVERTINE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.TANGERINE_TRAVERTINE_BRICK_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CAPPED_TANGERINE_TRAVERTINE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.POLISHED_TANGERINE_TRAVERTINE_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.TANGERINE_TRAVERTINE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.TANGERINE_TRAVERTINE_BRICK_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CAPPED_TANGERINE_TRAVERTINE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.POLISHED_TANGERINE_TRAVERTINE_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.TANGERINE_TRAVERTINE_WALL, item -> using(name(item, "block/%s", "_wall"), n -> wallSidedInventory(n + "_top", n + "_top", n)));
        register(MarblesBlocks.TANGERINE_TRAVERTINE_BRICK_WALL, item -> using(name(item, "block/%s_bricks", "_brick_wall"), n -> wallSidedInventory(n + "_top", n + "_top", n)));
        register(MarblesBlocks.CAPPED_TANGERINE_TRAVERTINE_WALL, item -> using(name(item, "block/%s", "_wall"), n -> wallSidedInventory(n + "_top", n + "_top", n)));
        register(MarblesBlocks.POLISHED_TANGERINE_TRAVERTINE_WALL, item -> wallInventory(name(item, "block/%s", "_wall")));

        register(MarblesBlocks.TRAVERTINE_OBSIDIAN, item -> inherit(name(item, "block/%s")));

        register(MarblesBlocks.TRAVERTINE_SALT_LAMP, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.LEMON_TRAVERTINE_SALT_LAMP, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.PEACH_TRAVERTINE_SALT_LAMP, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.TANGERINE_TRAVERTINE_SALT_LAMP, item -> inherit(name(item, "block/%s")));

        register(MarblesBlocks.PINK_SALT, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.CRUMBLED_PINK_SALT, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.PINK_SALT_SPIRE, item -> generated(name(item, "item/%s")));
        register(MarblesBlocks.PINK_SALT_STACK, item -> generated(name(item, "item/%s")));
        register(MarblesBlocks.PINK_SALT_STUMP, item -> generated(name(item, "item/%s")));

        register(MarblesBlocks.LAPIS_SHINGLES, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.LAPIS_SHINGLES_SLAB, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.LAPIS_SHINGLES_STAIRS, item -> inherit(name(item, "block/%s")));
        register(MarblesBlocks.LAPIS_SPOTLIGHT, item -> inherit(name(item, "block/%s")));

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

        register(MarblesBlocks.YELLOW_BAMBOO, item -> generated(name(item, "item/%s")));

        register(MarblesItems.YELLOW_SCAFFOLDING, item -> inherit("marbles:block/yellow_scaffolding_stable"));
        register(MarblesItems.PINK_SALT_SHARD, item -> generated(name(item, "item/%s")));
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
