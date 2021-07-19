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

        String _netherPortalTexture = new Identifier("block/nether_portal").toString();
        register(TRAVERTINE_NETHER_PORTAL, block -> netherPortal(name(block, "block/%s"), netherPortalNs(_netherPortalTexture), netherPortalEw(_netherPortalTexture)));
        register(TRAVERTINE_OBSIDIAN, BlockStateTable::cubeAllFunc);

        register(LIMESTONE, block -> axisRotated(name(block, "block/%s"), cubeColumn(name(block, "block/%s_top"), name(block, "block/%s"))));
        register(LIMESTONE_SLAB, block -> using(name(block, "block/%s", "_slab"), n -> slabColumn(name(block, "block/%s"), n, n + "_top", n)));
        register(LIMESTONE_STAIRS, block -> using(name(block, "block/%s", "_stairs"), n -> stairsColumn(name(block, "block/%s"), n + "_top", n)));
        register(LIMESTONE_WALL, block -> using(name(block, "block/%s", "_wall"), n -> wallColumn(name(block, "block/%s"), n + "_top", n)));
        register(POLISHED_LIMESTONE, block -> fourPartAttaching(name(block, "block/%s"), quadFirstSided(name(block, "block/%s"), name(block, "block/%s_top")), quadSecondSided(name(block, "block/%s"), name(block, "block/%s_top")), quadThirdSided(name(block, "block/%s"), name(block, "block/%s_top")), cubeColumn(name(block, "block/%s_top"), name(block, "block/%s"))));
        register(POLISHED_LIMESTONE_SLAB, block -> using(name(block, "block/%s", "_slab"), n -> slabColumn(name(block, "block/%s"), n, n + "_top", n)));
        register(POLISHED_LIMESTONE_STAIRS, block -> using(name(block, "block/%s", "_stairs"), n -> stairsColumn(name(block, "block/%s"), n + "_top", n)));
        register(POLISHED_LIMESTONE_WALL, block -> using(name(block, "block/%s", "_wall"), n -> wallColumn(name(block, "block/%s"), n + "_top", n)));

        /*
         * PINK SALT
         */

        register(PINK_SALT_CAVE_AIR, BlockStateTable::emptyFunc);
        register(PINK_SALT, BlockStateTable::cubeAllFunc);
        register(PINK_SALT_SLAB, BlockStateTable::slabAllFunc);
        register(PINK_SALT_STAIRS, BlockStateTable::stairsAllFunc);
        register(CRUMBLED_PINK_SALT, BlockStateTable::cubeAllFunc);
        register(PINK_SALT_BRICKS, BlockStateTable::cubeAllFunc);
        register(PINK_SALT_BRICK_SLAB, block -> using(name(block, "block/%s_bricks", "_brick_slab"), n -> slabAll(name(block, "block/%s"), n, n)));
        register(PINK_SALT_BRICK_STAIRS, block -> using(name(block, "block/%s_bricks", "_brick_stairs"), n -> stairsAll(name(block, "block/%s"), n)));
        register(PINK_SALT_PILLAR, block -> axisRotated(name(block, "block/%s"), cubeColumn(name(block, "block/%s_top"), name(block, "block/%s"))));
        register(PINK_SALT_SPIRE, BlockStateTable::predefinedFunc);
        register(PINK_SALT_STACK, BlockStateTable::predefinedFunc);
        register(PINK_SALT_STUMP, BlockStateTable::predefinedFunc);
        register(PINK_SALT_SPIKES, block -> using(name(block, "block/%s"), name -> alternate(name, doubleCross(name + "_1"), doubleCross(name + "_2"))));

        register(PINK_SALT_GOLD_ORE, BlockStateTable::cubeAllFunc);
        register(PINK_SALT_IRON_ORE, BlockStateTable::cubeAllFunc);
        register(PINK_SALT_COAL_ORE, BlockStateTable::cubeAllFunc);
        register(PINK_SALT_LAPIS_ORE, BlockStateTable::cubeAllFunc);
        register(PINK_SALT_DIAMOND_ORE, BlockStateTable::cubeAllFunc);
        register(PINK_SALT_REDSTONE_ORE, BlockStateTable::cubeAllFunc);
        register(PINK_SALT_EMERALD_ORE, BlockStateTable::cubeAllFunc);
        register(PINK_SALT_COPPER_ORE, BlockStateTable::cubeAllFunc);
        register(PINK_SALT_UMBRAL_LAZULI_ORE, BlockStateTable::cubeAllFunc);

        /*
         * LAPIS SETS
         */

        register(LAPIS_SHINGLES, BlockStateTable::cubeAllFunc);
        register(LAPIS_SHINGLE_SLAB, block -> using(name(block, "block/%s_shingles", "_shingle_slab"), n -> slabAll(name(block, "block/%s"), n, n)));
        register(LAPIS_SHINGLE_STAIRS, block -> using(name(block, "block/%s_shingles", "_shingle_stairs"), n -> stairsAll(name(block, "block/%s"), n)));
        register(LAPIS_SPOTLIGHT, block -> facingRotated(name(block, "block/%s"), cubeSeparateSided(name(block, "block/%s_top"), name(block, "block/%s_side"), name(block, "block/%s_front"), name(block, "block/%s_back"))));
        register(GLAZED_LAPIS, block -> dualConnecting(name(block, "block/%s")));
        register(UMBRAL_LAZULI_ORE, BlockStateTable::cubeAllFunc);
        register(DEEPSLATE_UMBRAL_LAZULI_ORE, BlockStateTable::cubeAllFunc);
        register(UMBRAL_LAZULI_BLOCK, BlockStateTable::cubeAllFunc);
        register(UMBRAL_LAZULI_SHINGLES, BlockStateTable::cubeAllFunc);
        register(UMBRAL_LAZULI_SHINGLE_SLAB, block -> using(name(block, "block/%s_shingles", "_shingle_slab"), n -> slabAll(name(block, "block/%s"), n, n)));
        register(UMBRAL_LAZULI_SHINGLE_STAIRS, block -> using(name(block, "block/%s_shingles", "_shingle_stairs"), n -> stairsAll(name(block, "block/%s"), n)));
        register(UMBRAL_LAZULI_SPOTLIGHT, block -> facingRotated(name(block, "block/%s"), cubeSeparateSided(name(block, "block/%s_top"), name(block, "block/%s_side"), name(block, "block/%s_front"), name(block, "block/%s_back"))));
        register(GLAZED_UMBRAL_LAZULI, block -> dualConnecting(name(block, "block/%s")));

        /*
         * SANDSTONE SETS
         */

        register(DAWN_SAND, BlockStateTable::cubeAllFunc);
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

        register(DUSK_SAND, BlockStateTable::cubeAllFunc);
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

        register(MORN_GRASS, block -> simple(name(block, "block/%s"), octoCross(name(block, "block/%s")).texture("particle", name(block, "item/%s"))));
        register(TALL_MORN_GRASS, block -> doubleBlock(name(block, "block/%s_bottom"), tallOctoCrossBottom(name(block, "block/%s")).texture("particle", name(block, "item/%s")), name(block, "block/%s_top"), tallOctoCrossTop(name(block, "block/%s")).texture("particle", name(block, "item/%s"))));

        /*
         * GRISP SET
         */

        register(GRISP_DIRT, BlockStateTable::cubeAllFunc);
        register(COARSE_GRISP_DIRT, BlockStateTable::cubeAllFunc);
        register(GRISP_PODZOL, block -> snowyBlock(name(block, "block/%s"), cubeBottomTop(name(block, "block/grisp_dirt"), name(block, "block/%s_top"), name(block, "block/%s_side")), cubeBottomTop(name(block, "block/grisp_dirt"), "block/snow", name(block, "block/%s_side_snowy"))));
        register(GRISP_MYCELIUM, block -> snowyBlock(name(block, "block/%s"), cubeBottomTop(name(block, "block/grisp_dirt"), name(block, "block/%s_top"), name(block, "block/%s_side")), cubeBottomTop(name(block, "block/grisp_dirt"), "block/snow", name(block, "block/%s_side_snowy"))));
        register(GRISP_DIRT_PATH, block -> simple(name(block, "block/%s"), flattenedBlock(name(block, "block/grisp_dirt"), name(block, "block/%s_top"), name(block, "block/%s_side"))));
        register(GRISP_PODZOL_PATH, block -> simple(name(block, "block/%s"), flattenedBlock(name(block, "block/grisp_dirt"), name(block, "block/%s_top"), name(block, "block/%s_side"))));
        register(GRISP_MYCELIUM_PATH, block -> simple(name(block, "block/%s"), flattenedBlock(name(block, "block/grisp_dirt"), name(block, "block/%s_top"), name(block, "block/%s_side"))));
        register(GRISP_FARMLAND, block -> farmland(name(block, "block/%s"), flattenedBlock(name(block, "block/grisp_dirt"), name(block, "block/%s"), name(block, "block/grisp_dirt")), flattenedBlock(name(block, "block/grisp_dirt"), name(block, "block/%s_moist"), name(block, "block/grisp_dirt"))));

        register(POLLENATED_COBBLESTONE, BlockStateTable::cubeAllFunc);
        register(POLLENATED_COBBLESTONE_SLAB, BlockStateTable::slabAllFunc);
        register(POLLENATED_COBBLESTONE_STAIRS, BlockStateTable::stairsAllFunc);
        register(POLLENATED_COBBLESTONE_WALL, BlockStateTable::wallAllFunc);

        register(ASPEN_SPROUTS, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(ASPEN_GRASS, block -> simple(name(block, "block/%s"), cross(name(block, "block/%s"))));
        register(TALL_ASPEN_GRASS, block -> doubleBlock(name(block, "block/%s_bottom"), cross(name(block, "block/%s_bottom")), name(block, "block/%s_top"), cross(name(block, "block/%s_top"))));

        /*
         * PLANTAGE
         */

        register(BLUE_PEONY, block -> stackingPlant(name(block, "block/%s"), cross(name(block, "block/%s_upper")), cross(name(block, "block/%s_middle")), cross(name(block, "block/%s_lower"))));

        /*
         * BAMBOO
         */

        register(CHEQUERED_BAMBOO_LATTICE, block -> attaching(name(block, "block/%s"), doubleThin(name(block, "block/%s"))));
        register(CROSSED_BAMBOO_LATTICE, block -> attaching(name(block, "block/%s"), doubleThin(name(block, "block/%s"))));

        register(CHEQUERED_YELLOW_BAMBOO_LATTICE, block -> attaching(name(block, "block/%s"), doubleThin(name(block, "block/%s"))));
        register(CROSSED_YELLOW_BAMBOO_LATTICE, block -> attaching(name(block, "block/%s"), doubleThin(name(block, "block/%s"))));

        register(BAMBOO_TIKI_TORCH, block -> simple(name(block, "block/%s"), torch(name(block, "block/%s"))));
        register(BAMBOO_TIKI_POLE, block -> simple(name(block, "block/%s"), standingPole(name(block, "block/%s"))));

        register(YELLOW_BAMBOO_TIKI_TORCH, block -> simple(name(block, "block/%s"), thickTorch(name(block, "block/%s"))));
        register(YELLOW_BAMBOO_TIKI_POLE, block -> simple(name(block, "block/%s"), thickStandingPole(name(block, "block/%s"))));

        /*
         * POLLEN-GRACED SET
         */

        register(POLLEN_GRACED_WOOL, BlockStateTable::cubeAllFunc);
        register(POLLEN_GRACED_CARPET, block -> simple(name(block, "block/%s"), carpet(name(POLLEN_GRACED_WOOL, "block/%s"))));

        /*
         * FLOESTONE
         */

        register(FLOESTONE, BlockStateTable::cubeAllFunc);
        register(FLOESTONE_SLAB, BlockStateTable::slabAllFunc);
        register(FLOESTONE_STAIRS, BlockStateTable::stairsAllFunc);
        register(FLOESTONE_WALL, BlockStateTable::wallAllFunc);

        register(POLISHED_FLOESTONE, BlockStateTable::cubeAllFunc);
        register(POLISHED_FLOESTONE_SLAB, BlockStateTable::slabAllFunc);
        register(POLISHED_FLOESTONE_STAIRS, BlockStateTable::stairsAllFunc);
        register(POLISHED_FLOESTONE_WALL, BlockStateTable::wallAllFunc);

        register(FLOESTONE_BRICKS, BlockStateTable::cubeAllFunc);
        register(FLOESTONE_BRICK_SLAB, block -> using(name(block, "block/%ss", "_slab"), n -> slabAll(name(block, "block/%s"), n, n)));
        register(FLOESTONE_BRICK_STAIRS, block -> using(name(block, "block/%ss", "_stairs"), n -> stairsAll(name(block, "block/%s"), n)));
        register(FLOESTONE_BRICK_WALL, block -> using(name(block, "block/%ss", "_wall"), n -> wallAll(name(block, "block/%s"), n)));

        register(CHISELED_FLOESTONE, block -> simple(name(block, "block/%s"), cubeColumn(name(block, "block/%s", "^chiseled_", ""), name(block, "block/%s"))));
        register(RILLED_FLOESTONE, BlockStateTable::cubeAllFunc);
        register(RINGED_FLOESTONE, block -> attaching(name(block, "block/%s"), thickRing(name(block, "block/%s"), name(block, "block/%s_side"))));

        /*
         * ICE
         */

        register(ICE_CAVE_AIR, BlockStateTable::emptyFunc);

        register(SCALED_ICE, BlockStateTable::cubeAllFunc);
        register(MINTED_ICE, BlockStateTable::cubeAllFunc);

        register(CUT_ICE, BlockStateTable::cubeAllFunc);
        register(CUT_BLUE_ICE, BlockStateTable::cubeAllFunc);
        register(CUT_SCALED_ICE, BlockStateTable::cubeAllFunc);
        register(CUT_MINTED_ICE, BlockStateTable::cubeAllFunc);

        register(CHISELED_ICE, block -> facingHorizontalRotated(name(block, "block/%s"), horizontalOrientable(name(block, "block/%s"), name(Blocks.ICE, "block/%s"))));
        register(CHISELED_BLUE_ICE, block -> facingHorizontalRotated(name(block, "block/%s"), horizontalOrientable(name(block, "block/%s"), name(Blocks.BLUE_ICE, "block/%s"))));
        register(CHISELED_SCALED_ICE, block -> facingHorizontalRotated(name(block, "block/%s"), horizontalOrientable(name(block, "block/%s"), name(SCALED_ICE, "block/%s"))));
        register(CHISELED_MINTED_ICE, block -> facingHorizontalRotated(name(block, "block/%s"), horizontalOrientable(name(block, "block/%s"), name(MINTED_ICE, "block/%s"))));

        register(ICE_BRICKS, BlockStateTable::cubeAllFunc);
        register(BLUE_ICE_BRICKS, BlockStateTable::cubeAllFunc);
        register(SCALED_ICE_BRICKS, BlockStateTable::cubeAllFunc);
        register(MINTED_ICE_BRICKS, BlockStateTable::cubeAllFunc);

        register(SLUSH, BlockStateTable::cubeAllFunc);
        register(BLUE_SLUSH, BlockStateTable::cubeAllFunc);
        register(SCALED_SLUSH, BlockStateTable::cubeAllFunc);
        register(MINTED_SLUSH, BlockStateTable::cubeAllFunc);
    }

    private static void registerTravertineBlocks(TravertineBlocks blocks) {
        register(blocks.RAW, block -> axisRotated(name(block, "block/%s"), cubeColumn(name(block, "block/%s_top"), name(block, "block/%s"))));
        register(blocks.BRICKS, block -> axisRotated(name(block, "block/%s"), cubeColumn(name(block, "block/%s_top"), name(block, "block/%s"))));
        register(blocks.CAPPED, block -> facingRotated(name(block, "block/%s"), cubeColumn(name(block, "block/%s_top"), name(block, "block/%s"))));
        register(blocks.POLISHED, BlockStateTable::cubeAllFunc);
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
        register(blocks.POLISHED_WALL, BlockStateTable::wallAllFunc);
        register(blocks.SALT_LAMP, BlockStateTable::cubeAllFunc);
    }

    private static void registerWoodBlocks(WoodBlocks blocks) {
        register(blocks.PLANKS, BlockStateTable::cubeAllFunc);
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

    private static StateGen cubeAllFunc(Block block) {
        return simple(name(block, "block/%s"), cubeAll(name(block, "block/%s")));
    }
    private static StateGen slabAllFunc(Block block) {
        return slabAll(name(block, "block/%s"), name(block, "block/%s", "_slab"), name(block, "block/%s", "_slab"));
    }
    private static StateGen stairsAllFunc(Block block) {
        return stairsAll(name(block, "block/%s"), name(block, "block/%s", "_stairs"));
    }
    private static StateGen wallAllFunc(Block block) {
        return using(name(block, "block/%s", "_wall"), n -> wallAll(name(block, "block/%s"), n));
    }
    private static StateGen emptyFunc(Block block) {
        return simple(name(block, "block/%s"), ModelGen.EMPTY);
    }
    private static StateGen predefinedFunc(Block block) {
        return predefined(name(block, "block/%s"));
    }


    private BlockStateTable() {
    }
}
