package net.dodogang.marbles.datagen.models;

import me.andante.chord.block.helper.WoodBlocks;
import net.dodogang.marbles.block.helper.TravertineBlocks;
import net.dodogang.marbles.datagen.models.modelgen.ModelGen;
import net.dodogang.marbles.datagen.models.stategen.StateGen;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static net.dodogang.marbles.datagen.models.modelgen.InheritingModelGen.*;
import static net.dodogang.marbles.datagen.models.modelgen.ParticleOnlyModelGen.particles;
import static net.dodogang.marbles.datagen.models.stategen.BuildingBlocks.slabAll;
import static net.dodogang.marbles.datagen.models.stategen.BuildingBlocks.stairsAll;
import static net.dodogang.marbles.datagen.models.stategen.BuildingBlocks.*;
import static net.dodogang.marbles.datagen.models.stategen.InteractiveBlocks.button;
import static net.dodogang.marbles.datagen.models.stategen.InteractiveBlocks.fenceGate;
import static net.dodogang.marbles.datagen.models.stategen.InteractiveBlocks.pressurePlate;
import static net.dodogang.marbles.datagen.models.stategen.InteractiveBlocks.*;
import static net.dodogang.marbles.datagen.models.stategen.SimpleBlocks.*;
import static net.dodogang.marbles.init.MarblesBlocks.*;

@SuppressWarnings("unused")
public final class BlockStateTable {
    private static BiConsumer<Block, StateGen> consumer;

    public static void registerBlockStates(BiConsumer<Block, StateGen> c) {
        consumer = c;

        /*
         * WOOD SETS
         */

        registerWoodBlocks(ASPEN);
        registerWoodBlocks(HOOPSI_SPRUCE);
        registerWoodBlocks(RED_BIRCH);

        /*
         * TRAVERTINE SETS
         */

        registerTravertineBlocks(TRAVERTINE_BLOCKS);
        registerTravertineBlocks(LEMON_TRAVERTINE_BLOCKS);
        registerTravertineBlocks(PEACH_TRAVERTINE_BLOCKS);
        registerTravertineBlocks(TANGERINE_TRAVERTINE_BLOCKS);

        register(TRAVERTINE_OBSIDIAN, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));

        /*
         * PINK SALT
         */

        register(PINK_SALT_CAVE_AIR, block -> simple(name(block, "block/%s"), ModelGen.EMPTY));
        register(PINK_SALT, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(PINK_SALT_SLAB, block -> slabAll(name(block, "block/%s"), name(block, "block/%s", "_slab"), name(block, "block/%s", "_slab")));
        register(PINK_SALT_STAIRS, block -> stairsAll(name(block, "block/%s"), name(block, "block/%s", "_stairs")));
        register(CRUMBLED_PINK_SALT, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(PINK_SALT_BRICKS, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(PINK_SALT_BRICK_SLAB, block -> using(name(block, "block/%s_bricks", "_brick_slab"), n -> slabAll(name(block, "block/%s"), n, n)));
        register(PINK_SALT_BRICK_STAIRS, block -> using(name(block, "block/%s_bricks", "_brick_stairs"), n -> stairsAll(name(block, "block/%s"), n)));
        register(PINK_SALT_PILLAR, block -> axisRotated(name(block, "block/%s"), cubeColumn(name(block, "block/%s_top"), name(block, "block/%s"))));
        register(PINK_SALT_SPIRE, block -> predefined(name(block, "block/%s")));
        register(PINK_SALT_STACK, block -> predefined(name(block, "block/%s")));
        register(PINK_SALT_STUMP, block -> predefined(name(block, "block/%s")));
        register(PINK_SALT_SPIKES, block -> using(name(block, "block/%s"), name -> alternate(name, doubleCross(name + "_1"), doubleCross(name + "_2"))));

        /*
         * LAPIS SETS
         */

        register(LAPIS_SHINGLES, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(LAPIS_SHINGLE_SLAB, block -> using(name(block, "block/%s_shingles", "_shingle_slab"), n -> slabAll(name(block, "block/%s"), n, n)));
        register(LAPIS_SHINGLE_STAIRS, block -> using(name(block, "block/%s_shingles", "_shingle_stairs"), n -> stairsAll(name(block, "block/%s"), n)));
        register(LAPIS_SPOTLIGHT, block -> facingRotated(name(block, "block/%s"), cubeSeparateSided(name(block, "block/%s_top"), name(block, "block/%s_side"), name(block, "block/%s_front"), name(block, "block/%s_back"))));
        register(GLAZED_LAPIS, block -> dualConnecting(name(block, "block/%s")));
        register(UMBRAL_LAZULI_ORE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(UMBRAL_LAZULI_BLOCK, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(UMBRAL_LAZULI_SHINGLES, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(UMBRAL_LAZULI_SHINGLE_SLAB, block -> using(name(block, "block/%s_shingles", "_shingle_slab"), n -> slabAll(name(block, "block/%s"), n, n)));
        register(UMBRAL_LAZULI_SHINGLE_STAIRS, block -> using(name(block, "block/%s_shingles", "_shingle_stairs"), n -> stairsAll(name(block, "block/%s"), n)));
        register(UMBRAL_LAZULI_SPOTLIGHT, block -> facingRotated(name(block, "block/%s"), cubeSeparateSided(name(block, "block/%s_top"), name(block, "block/%s_side"), name(block, "block/%s_front"), name(block, "block/%s_back"))));
        register(GLAZED_UMBRAL_LAZULI, block -> dualConnecting(name(block, "block/%s")));

        /*
         * SANDSTONE SETS
         */

        register(DAWN_SAND, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(DAWN_SANDSTONE, block -> simple(name(block, "block/%s"), cubeBottomTop(name(block, "block/%s_bottom"), name(block, "block/%s_top"), name(block, "block/%s"))));
        register(CHISELED_DAWN_SANDSTONE, block -> simple(name(block, "block/%s"), cubeColumn(name(block, "block/%s_top", "^chiseled_", ""), name(block, "block/%s"))));
        register(CUT_DAWN_SANDSTONE, block -> simple(name(block, "block/%s"), cubeColumn(name(block, "block/%s_top", "^cut_", ""), name(block, "block/%s"))));
        register(SMOOTH_DAWN_SANDSTONE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s_top", "^smooth_", ""))));
        register(DAWN_SANDSTONE_SLAB, block -> using(name(block, "block/%s", "_slab"), n -> slabSided(name(block, "block/%s"), n, n + "_bottom", n + "_top", n)));
        register(CUT_DAWN_SANDSTONE_SLAB, block -> using(name(block, "block/%s", "(^cut_)|(_slab$)", ""), n -> slabColumn(name(block, "block/%s"), n, n + "_top", name(block, "block/%s", "_slab"))));
        register(SMOOTH_DAWN_SANDSTONE_SLAB, block -> using(name(block, "block/%s", "(^smooth_)|(_slab$)", ""), n -> slabAll(name(block, "block/%s"), n, n + "_top")));
        register(DAWN_SANDSTONE_STAIRS, block -> using(name(block, "block/%s", "_stairs"), n -> stairsSided(name(block, "block/%s"), n + "_bottom", n + "_top", n)));
        register(CUT_DAWN_SANDSTONE_STAIRS, block -> using(name(block, "block/%s", "(^cut_)|(_stairs$)", ""), n -> stairsColumn(name(block, "block/%s"), n + "_top", name(block, "block/%s", "_stairs"))));
        register(SMOOTH_DAWN_SANDSTONE_STAIRS, block -> using(name(block, "block/%s", "(^smooth_)|(_stairs$)", ""), n -> stairsAll(name(block, "block/%s"), n + "_top")));
        register(DAWN_SANDSTONE_WALL, block -> using(name(block, "block/%s", "_wall"), n -> wallSided(name(block, "block/%s"), n + "_bottom", n + "_top", n)));
        register(CUT_DAWN_SANDSTONE_WALL, block -> using(name(block, "block/%s", "(^cut_)|(_wall$)", ""), n -> wallColumn(name(block, "block/%s"), n + "_top", name(block, "block/%s", "_wall"))));
        register(SMOOTH_DAWN_SANDSTONE_WALL, block -> using(name(block, "block/%s", "(^smooth_)|(_wall$)", ""), n -> wallAll(name(block, "block/%s"), n + "_top")));

        register(DUSK_SAND, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(DUSK_SANDSTONE, block -> simple(name(block, "block/%s"), cubeBottomTop(name(block, "block/%s_bottom"), name(block, "block/%s_top"), name(block, "block/%s"))));
        register(CHISELED_DUSK_SANDSTONE, block -> simple(name(block, "block/%s"), cubeColumn(name(block, "block/%s_top", "^chiseled_", ""), name(block, "block/%s"))));
        register(CUT_DUSK_SANDSTONE, block -> simple(name(block, "block/%s"), cubeColumn(name(block, "block/%s_top", "^cut_", ""), name(block, "block/%s"))));
        register(SMOOTH_DUSK_SANDSTONE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s_top", "^smooth_", ""))));
        register(DUSK_SANDSTONE_SLAB, block -> using(name(block, "block/%s", "_slab"), n -> slabSided(name(block, "block/%s"), n, n + "_bottom", n + "_top", n)));
        register(CUT_DUSK_SANDSTONE_SLAB, block -> using(name(block, "block/%s", "(^cut_)|(_slab$)", ""), n -> slabColumn(name(block, "block/%s"), n, n + "_top", name(block, "block/%s", "_slab"))));
        register(SMOOTH_DUSK_SANDSTONE_SLAB, block -> using(name(block, "block/%s", "(^smooth_)|(_slab$)", ""), n -> slabAll(name(block, "block/%s"), n, n + "_top")));
        register(DUSK_SANDSTONE_STAIRS, block -> using(name(block, "block/%s", "_stairs"), n -> stairsSided(name(block, "block/%s"), n + "_bottom", n + "_top", n)));
        register(CUT_DUSK_SANDSTONE_STAIRS, block -> using(name(block, "block/%s", "(^cut_)|(_stairs$)", ""), n -> stairsColumn(name(block, "block/%s"), n + "_top", name(block, "block/%s", "_stairs"))));
        register(SMOOTH_DUSK_SANDSTONE_STAIRS, block -> using(name(block, "block/%s", "(^smooth_)|(_stairs$)", ""), n -> stairsAll(name(block, "block/%s"), n + "_top")));
        register(DUSK_SANDSTONE_WALL, block -> using(name(block, "block/%s", "_wall"), n -> wallSided(name(block, "block/%s"), n + "_bottom", n + "_top", n)));
        register(CUT_DUSK_SANDSTONE_WALL, block -> using(name(block, "block/%s", "(^cut_)|(_wall$)", ""), n -> wallColumn(name(block, "block/%s"), n + "_top", name(block, "block/%s", "_wall"))));
        register(SMOOTH_DUSK_SANDSTONE_WALL, block -> using(name(block, "block/%s", "(^smooth_)|(_wall$)", ""), n -> wallAll(name(block, "block/%s"), n + "_top")));

        /*
         * GRISP SET
         */

        register(GRISP_DIRT, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(COARSE_GRISP_DIRT, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(GRISP_GRASS_BLOCK, block -> snowyBlock(name(block, "block/%s"), grassBlock(name(block, "block/grisp_dirt"), name(block, "block/%s_top"), name(block, "block/%s_side"), name(block, "block/%s_side_overlay")), cubeBottomTop(name(block, "block/grisp_dirt"), "block/snow", name(block, "block/%s_side_snowy"))));
        register(GRISP_PODZOL, block -> snowyBlock(name(block, "block/%s"), cubeBottomTop(name(block, "block/grisp_dirt"), name(block, "block/%s_top"), name(block, "block/%s_side")), cubeBottomTop(name(block, "block/grisp_dirt"), "block/snow", name(block, "block/%s_side_snowy"))));
        register(GRISP_MYCELIUM, block -> snowyBlock(name(block, "block/%s"), cubeBottomTop(name(block, "block/grisp_dirt"), name(block, "block/%s_top"), name(block, "block/%s_side")), cubeBottomTop(name(block, "block/grisp_dirt"), "block/snow", name(block, "block/%s_side_snowy"))));
        register(GRISP_DIRT_PATH, block -> simple(name(block, "block/%s"), flattenedBlock(name(block, "block/grisp_dirt"), name(block, "block/%s_top"), name(block, "block/%s_side"))));
        register(GRISP_GRASS_PATH, block -> simple(name(block, "block/%s"), flattenedBlock(name(block, "block/grisp_dirt"), name(block, "block/%s_top"), name(block, "block/%s_side"))));
        register(GRISP_PODZOL_PATH, block -> simple(name(block, "block/%s"), flattenedBlock(name(block, "block/grisp_dirt"), name(block, "block/%s_top"), name(block, "block/%s_side"))));
        register(GRISP_MYCELIUM_PATH, block -> simple(name(block, "block/%s"), flattenedBlock(name(block, "block/grisp_dirt"), name(block, "block/%s_top"), name(block, "block/%s_side"))));
        register(GRISP_FARMLAND, block -> farmland(name(block, "block/%s"), flattenedBlock(name(block, "block/grisp_dirt"), name(block, "block/%s"), name(block, "block/grisp_dirt")), flattenedBlock(name(block, "block/grisp_dirt"), name(block, "block/%s_moist"), name(block, "block/grisp_dirt"))));

        /*
         * POLLEN-GRACED SET
         */

        register(POLLEN_GRACED_WOOL, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(POLLEN_GRACED_CARPET, block -> simple(name(block, "block/%s"), carpet(name(POLLEN_GRACED_WOOL, "block/%s"))));

        /*
         * ICE
         */

        register(ICE_CAVE_AIR, block -> simple(name(block, "block/%s"), ModelGen.EMPTY));

        register(FLOESTONE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(POLISHED_FLOESTONE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(CHISELED_FLOESTONE, block -> simple(name(block, "block/%s"), cubeColumn(name(block, "block/%s", "^chiseled_", ""), name(block, "block/%s"))));
        register(FLOESTONE_BRICKS, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(RILLED_FLOESTONE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));

        register(SCALED_ICE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MINTED_ICE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));

        register(CUT_ICE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(CUT_BLUE_ICE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(CUT_SCALED_ICE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(CUT_MINTED_ICE, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));

        register(CHISELED_ICE, block -> facingHorizontalRotated(name(block, "block/%s"), horizontalOrientable(name(block, "block/%s"), name(Blocks.ICE, "block/%s"))));
        register(CHISELED_BLUE_ICE, block -> facingHorizontalRotated(name(block, "block/%s"), horizontalOrientable(name(block, "block/%s"), name(Blocks.BLUE_ICE, "block/%s"))));
        register(CHISELED_SCALED_ICE, block -> facingHorizontalRotated(name(block, "block/%s"), horizontalOrientable(name(block, "block/%s"), name(SCALED_ICE, "block/%s"))));
        register(CHISELED_MINTED_ICE, block -> facingHorizontalRotated(name(block, "block/%s"), horizontalOrientable(name(block, "block/%s"), name(MINTED_ICE, "block/%s"))));

        register(ICE_BRICKS, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(BLUE_ICE_BRICKS, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(SCALED_ICE_BRICKS, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(MINTED_ICE_BRICKS, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
    }

    private static void registerTravertineBlocks(TravertineBlocks blocks) {
        register(blocks.RAW, block -> axisRotated(name(block, "block/%s"), cubeColumn(name(block, "block/%s_top"), name(block, "block/%s"))));
        register(blocks.BRICKS, block -> axisRotated(name(block, "block/%s"), cubeColumn(name(block, "block/%s_top"), name(block, "block/%s"))));
        register(blocks.CAPPED, block -> facingRotated(name(block, "block/%s"), cubeColumn(name(block, "block/%s_top"), name(block, "block/%s"))));
        register(blocks.POLISHED, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(blocks.SLAB, block -> using(name(block, "block/%s", "_slab"), n -> slabColumn(name(block, "block/%s"), n, n + "_top", n)));
        register(blocks.BRICK_SLAB, block -> using(name(block, "block/%s_bricks", "_brick_slab"), n -> slabColumn(name(block, "block/%s"), n, n + "_top", n)));
        register(blocks.CAPPED_SLAB, block -> using(name(block, "block/%s", "_slab"), n -> slabColumn(name(block, "block/%s"), n, n + "_top", n)));
        register(blocks.POLISHED_SLAB, block -> using(name(block, "block/%s", "_slab"), n -> slabAll(name(block, "block/%s"), n, n)));
        register(blocks.STAIRS, block -> using(name(block, "block/%s", "_stairs"), n -> stairsColumn(name(block, "block/%s"), n + "_top", n)));
        register(blocks.BRICK_STAIRS, block -> using(name(block, "block/%s_bricks", "_brick_stairs"), n -> stairsColumn(name(block, "block/%s"), n + "_top", n)));
        register(blocks.CAPPED_STAIRS, block -> using(name(block, "block/%s", "_stairs"), n -> stairsColumn(name(block, "block/%s"), n + "_top", n)));
        register(blocks.POLISHED_STAIRS, block -> using(name(block, "block/%s", "_stairs"), n -> stairsAll(name(block, "block/%s"), n)));
        register(blocks.WALL, block -> using(name(block, "block/%s", "_wall"), n -> wallColumn(name(block, "block/%s"), n + "_top", n)));
        register(blocks.BRICK_WALL, block -> using(name(block, "block/%s_bricks", "_brick_wall"), n -> wallColumn(name(block, "block/%s"), n + "_top", n)));
        register(blocks.CAPPED_WALL, block -> using(name(block, "block/%s", "_wall"), n -> wallColumn(name(block, "block/%s"), n + "_top", n)));
        register(blocks.POLISHED_WALL, block -> using(name(block, "block/%s", "_wall"), n -> wallAll(name(block, "block/%s"), n)));
        register(blocks.SALT_LAMP, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
    }

    private static void registerWoodBlocks(WoodBlocks blocks) {
        register(blocks.PLANKS, block -> simple(name(block, "block/%s"), cubeAll(name(block, "block/%s"))));
        register(blocks.SAPLING, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(blocks.POTTED_SAPLING, block -> simple(name(block, "block/%s"), flowerPotCross(name(blocks.SAPLING, "block/%s"))));
        register(blocks.LOG, block -> axisRotated(name(block, "block/%s"), cubeColumn(name(block, "block/%s_top"), name(block, "block/%s"))));
        register(blocks.STRIPPED_LOG, block -> axisRotated(name(block, "block/%s"), cubeColumn(name(block, "block/%s_top"), name(block, "block/%s"))));
        register(blocks.WOOD, block -> axisRotated(name(block, "block/%s"), cubeAll(name(block, "block/%s_log", "_wood"))));
        register(blocks.STRIPPED_WOOD, block -> axisRotated(name(block, "block/%s"), cubeAll(name(block, "block/%s_log", "_wood"))));
        register(blocks.LEAVES, block -> simple(name(block, "block/%s"), leaves(name(block, "block/%s"))));
        register(blocks.SLAB, block -> slabAll(name(block, "block/%s"), name(block, "block/%s_planks", "_slab"), name(block, "block/%s_planks", "_slab")));
        register(blocks.STAIRS, block -> stairsAll(name(block, "block/%s"), name(block, "block/%s_planks", "_stairs")));
        register(blocks.FENCE, block -> fence(name(block, "block/%s"), name(block, "block/%s_planks", "_fence")));
        register(blocks.DOOR, block -> door(name(block, "block/%s")));
        register(blocks.TRAPDOOR, block -> trapdoor(name(block, "block/%s")));
        register(blocks.FENCE_GATE, block -> fenceGate(name(block, "block/%s"), name(block, "block/%s_planks", "_fence_gate")));
        register(blocks.BUTTON, block -> button(name(block, "block/%s"), name(block, "block/%s_planks", "_button")));
        register(blocks.PRESSURE_PLATE, block -> pressurePlate(name(block, "block/%s"), name(block, "block/%s_planks", "_pressure_plate")));
        register(blocks.SIGN, block -> simple(name(block, "block/%s"), particles(name(block, "block/%s_planks", "_sign"))));
        register(blocks.WALL_SIGN, block -> simple(name(block, "block/%s_sign", "_wall_sign"), null)); // same particle-only model as floor sign, avoid double generation
    }

    private static void register(Block block, Function<Block, StateGen> genFactory) {
        consumer.accept(block, genFactory.apply(block));
    }

    private static String name(Block block, String nameFormat) {
        Identifier id = Registry.BLOCK.getId(block);

        return String.format("%s:%s", id.getNamespace(), String.format(nameFormat, id.getPath()));
    }

    private static String name(Block block, String nameFormat, String omitSuffix) {
        Identifier id = Registry.BLOCK.getId(block);

        String path = id.getPath();
        if (path.endsWith(omitSuffix)) {
            path = path.substring(0, path.length() - omitSuffix.length());
        }

        return String.format("%s:%s", id.getNamespace(), String.format(nameFormat, path));
    }

    private static String name(Block block, String nameFormat, String pattern, String reformat) {
        Identifier id = Registry.BLOCK.getId(block);

        String path = id.getPath();
        path = path.replaceAll(pattern, reformat);

        return String.format("%s:%s", id.getNamespace(), String.format(nameFormat, path));
    }

    private static String name(Block block) {
        Identifier id = Registry.BLOCK.getId(block);
        return id.toString();
    }

    private static <T> T using(String name, Function<String, T> func) {
        return func.apply(name);
    }


    private BlockStateTable() {
    }
}
