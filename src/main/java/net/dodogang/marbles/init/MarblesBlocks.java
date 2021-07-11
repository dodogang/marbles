package net.dodogang.marbles.init;

import me.andante.chord.block.CBambooBlock;
import me.andante.chord.block.CBambooSaplingBlock;
import me.andante.chord.block.enums.TripleBlockPart;
import me.andante.chord.block.helper.WoodBlocks;
import me.andante.chord.block.vanilla.PublicStairsBlock;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.block.*;
import net.dodogang.marbles.block.helper.TravertineBlocks;
import net.dodogang.marbles.block.sapling.AspenSaplingGenerator;
import net.dodogang.marbles.block.sapling.HoopsiSpruceSaplingGenerator;
import net.dodogang.marbles.block.sapling.RedBirchSaplingGenerator;
import net.dodogang.marbles.block.vanilla.PublicAirBlock;
import net.dodogang.marbles.block.vanilla.PublicFernBlock;
import net.dodogang.marbles.block.vanilla.PublicTorchBlock;
import net.dodogang.marbles.block.vanilla.PublicTransparentBlock;
import net.dodogang.marbles.item.MarblesItemGroup;
import net.dodogang.marbles.sound.MarblesBlockSoundGroup;
import net.dodogang.marbles.sound.MarblesSoundGroups;
import net.dodogang.marbles.state.property.MarblesProperties;
import net.dodogang.marbles.util.ModLoaded;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;

import java.util.function.ToIntFunction;

@SuppressWarnings("unused")
public class MarblesBlocks {
    /*
     * TRAVERTINE
     */

    public static final TravertineBlocks TRAVERTINE_BLOCKS = new TravertineBlocks(Marbles.MOD_ID, "travertine", MarblesItemGroup.INSTANCE, MapColor.WHITE);
    public static final TravertineBlocks LEMON_TRAVERTINE_BLOCKS = new TravertineBlocks(Marbles.MOD_ID, "lemon_travertine", MarblesItemGroup.INSTANCE, MapColor.WHITE);
    public static final TravertineBlocks PEACH_TRAVERTINE_BLOCKS = new TravertineBlocks(Marbles.MOD_ID, "peach_travertine", MarblesItemGroup.INSTANCE, MapColor.TERRACOTTA_WHITE);
    public static final TravertineBlocks TANGERINE_TRAVERTINE_BLOCKS = new TravertineBlocks(Marbles.MOD_ID, "tangerine_travertine", MarblesItemGroup.INSTANCE, MapColor.ORANGE);

    public static final Block TRAVERTINE_OBSIDIAN = register("travertine_obsidian", new Block(FabricBlockSettings.of(Material.STONE, MapColor.BROWN).requiresTool().strength(50.0f, 1200.0f)));
    public static final Block TRAVERTINE_NETHER_PORTAL = register("travertine_nether_portal", new TravertinePortalBlock(FabricBlockSettings.copyOf(Blocks.NETHER_PORTAL)), false);

    public static final Block LIMESTONE = register("limestone", new PillarBlock(FabricBlockSettings.copyOf(Blocks.STONE).mapColor(MapColor.OFF_WHITE)));
    public static final Block LIMESTONE_SLAB = createSlab(LIMESTONE);
    public static final Block LIMESTONE_STAIRS = createStairs(LIMESTONE);
    public static final Block LIMESTONE_WALL = createWall(LIMESTONE);
    public static final Block POLISHED_LIMESTONE = register("polished_limestone", new PillarBlock(FabricBlockSettings.copyOf(LIMESTONE)));
    public static final Block POLISHED_LIMESTONE_SLAB = createSlab(POLISHED_LIMESTONE);
    public static final Block POLISHED_LIMESTONE_STAIRS = createStairs(POLISHED_LIMESTONE);
    public static final Block POLISHED_LIMESTONE_WALL = createWall(POLISHED_LIMESTONE);

    /*
     * PINK SALT
     */

    /**
     * Used in cave generators and various structures/features to check whether a position is in a cave.
     */
    public static final Block PINK_SALT_CAVE_AIR = register("pink_salt_cave_air", new PublicAirBlock(FabricBlockSettings.copyOf(Blocks.AIR)), false);

    public static final Block PINK_SALT = register(
        "pink_salt",
        new Block(
            FabricBlockSettings.of(Material.STONE, MapColor.ORANGE)
                .strength(1.2f, 6.0f)

                .mapColor(MapColor.ORANGE)
                .requiresTool()
                .sounds(MarblesSoundGroups.PINK_SALT)
        )
    );
    public static final Block CRUMBLED_PINK_SALT = register(
        "crumbled_pink_salt",
        new FallingBlock(
            FabricBlockSettings.of(Material.STONE, MapColor.ORANGE)
                .strength(0.9f, 3.0f)
                .mapColor(MapColor.ORANGE)
                .sounds(MarblesSoundGroups.PINK_SALT)
        )
    );
    public static final Block PINK_SALT_SLAB = createSlab(PINK_SALT);
    public static final Block PINK_SALT_STAIRS = createStairs(PINK_SALT);

    public static final Block PINK_SALT_BRICKS = register("pink_salt_bricks", new Block(FabricBlockSettings.copyOf(PINK_SALT).sounds(MarblesSoundGroups.PINK_SALT_BRICKS)));
    public static final Block PINK_SALT_BRICK_SLAB = createSlab("pink_salt_brick", PINK_SALT_BRICKS);
    public static final Block PINK_SALT_BRICK_STAIRS = createStairs("pink_salt_brick", PINK_SALT_BRICKS);
    public static final Block PINK_SALT_PILLAR = register("pink_salt_pillar", new PillarBlock(FabricBlockSettings.copyOf(PINK_SALT).sounds(MarblesSoundGroups.PINK_SALT_BRICKS)));

    public static final Block PINK_SALT_SPIRE = register(
        "pink_salt_spire", new PinkSaltSpireBlock(
            FabricBlockSettings.of(MarblesMaterial.PINK_SALT_ROCK, MapColor.ORANGE)
                .sounds(MarblesSoundGroups.PINK_SALT)
                .strength(0.9f, 3.0f)
                .requiresTool()
                .dynamicBounds()
                .luminance(state -> 4)
        )
    );
    public static final Block PINK_SALT_STACK = register(
        "pink_salt_stack", new PinkSaltStackBlock(
            FabricBlockSettings.of(MarblesMaterial.PINK_SALT_ROCK, MapColor.ORANGE)
                .sounds(MarblesSoundGroups.PINK_SALT)
                .strength(0.15f, 1.0f)
                .dynamicBounds()
                .luminance(state -> state.get(MarblesProperties.RETAINED_LIGHT))
        )
    );
    public static final Block PINK_SALT_STUMP = register(
        "pink_salt_stump", new PinkSaltStumpBlock(
            FabricBlockSettings.of(Material.PLANT, MapColor.ORANGE)
                .sounds(MarblesSoundGroups.PINK_SALT)
                .strength(0.05f, 1.0f)
                .dynamicBounds()
                .luminance(state -> state.get(MarblesProperties.RETAINED_LIGHT) / 3)
        )
    );
    public static final Block PINK_SALT_SPIKES = register(
        "pink_salt_spikes", new PinkSaltSpikeBlock(
            FabricBlockSettings.of(Material.PLANT, MapColor.ORANGE)
                .sounds(MarblesSoundGroups.PINK_SALT)
                .breakInstantly()
                .nonOpaque()
        )
    );

    public static final Block PINK_SALT_COAL_ORE = register("pink_salt_coal_ore", new OreBlock(FabricBlockSettings.copyOf(Blocks.COAL_ORE).mapColor(MapColor.ORANGE).sounds(MarblesSoundGroups.PINK_SALT), UniformIntProvider.create(0, 2)));
    public static final Block PINK_SALT_IRON_ORE = register("pink_salt_iron_ore", new OreBlock(FabricBlockSettings.copyOf(Blocks.IRON_ORE).mapColor(MapColor.ORANGE).sounds(MarblesSoundGroups.PINK_SALT)));
    public static final Block PINK_SALT_COPPER_ORE = register("pink_salt_copper_ore", new OreBlock(FabricBlockSettings.copyOf(Blocks.COPPER_ORE).mapColor(MapColor.ORANGE).sounds(MarblesSoundGroups.PINK_SALT)));
    public static final Block PINK_SALT_GOLD_ORE = register("pink_salt_gold_ore", new OreBlock(FabricBlockSettings.copyOf(Blocks.GOLD_ORE).mapColor(MapColor.ORANGE).sounds(MarblesSoundGroups.PINK_SALT)));
    public static final Block PINK_SALT_REDSTONE_ORE = register("pink_salt_redstone_ore", new RedstoneOreBlock(FabricBlockSettings.copyOf(Blocks.REDSTONE_ORE).mapColor(MapColor.ORANGE).sounds(MarblesSoundGroups.PINK_SALT)));
    public static final Block PINK_SALT_EMERALD_ORE = register("pink_salt_emerald_ore", new OreBlock(FabricBlockSettings.copyOf(Blocks.EMERALD_ORE).mapColor(MapColor.ORANGE).sounds(MarblesSoundGroups.PINK_SALT), UniformIntProvider.create(3, 7)));
    public static final Block PINK_SALT_LAPIS_ORE = register("pink_salt_lapis_ore", new OreBlock(FabricBlockSettings.copyOf(Blocks.LAPIS_ORE).mapColor(MapColor.ORANGE).sounds(MarblesSoundGroups.PINK_SALT), UniformIntProvider.create(2, 5)));
    public static final Block PINK_SALT_DIAMOND_ORE = register("pink_salt_diamond_ore", new OreBlock(FabricBlockSettings.copyOf(Blocks.DIAMOND_ORE).mapColor(MapColor.ORANGE).sounds(MarblesSoundGroups.PINK_SALT), UniformIntProvider.create(3, 7)));

    /*
     * MISC
     */

    public static final Block ROPE = register("rope", new RopeBlock(FabricBlockSettings.of(Material.WOOL, MapColor.WHITE).breakInstantly().sounds(BlockSoundGroup.WOOL)));

    /*
     * BAMBOO
     */

    private static final FabricBlockSettings BAMBOO_LATTICE_SETTINGS = FabricBlockSettings.of(Material.WOOD, MapColor.PALE_YELLOW)
                                                                                          .nonOpaque()
                                                                                          .sounds(BlockSoundGroup.SCAFFOLDING);

    public static final Block CHEQUERED_BAMBOO_LATTICE = register("chequered_bamboo_lattice", new BambooLatticeBlock(BAMBOO_LATTICE_SETTINGS));
    public static final Block CROSSED_BAMBOO_LATTICE = register("crossed_bamboo_lattice", new BambooLatticeBlock(BAMBOO_LATTICE_SETTINGS));

    public static final Block CHEQUERED_YELLOW_BAMBOO_LATTICE = register("chequered_yellow_bamboo_lattice", new BambooLatticeBlock(FabricBlockSettings.copyOf(CHEQUERED_BAMBOO_LATTICE)));
    public static final Block CROSSED_YELLOW_BAMBOO_LATTICE = register("crossed_yellow_bamboo_lattice", new BambooLatticeBlock(FabricBlockSettings.copyOf(CROSSED_BAMBOO_LATTICE)));

    public static final Block BAMBOO_TIKI_TORCH = register("bamboo_tiki_torch", new PublicTorchBlock(FabricBlockSettings.copyOf(Blocks.TORCH), ParticleTypes.FLAME));
    public static final Block YELLOW_BAMBOO_TIKI_TORCH = register("yellow_bamboo_tiki_torch", new PublicTorchBlock(FabricBlockSettings.copyOf(BAMBOO_TIKI_TORCH), ParticleTypes.FLAME));

    public static final Block BAMBOO_TIKI_POLE = register("bamboo_tiki_pole", new SupportingPoleBlock(state -> state.getBlock() instanceof TorchBlock, FabricBlockSettings.of(Material.DECORATION).luminance(state -> state.get(SupportingPoleBlock.PART) == TripleBlockPart.UPPER ? 14 : 0).nonOpaque()));
    public static final Block YELLOW_BAMBOO_TIKI_POLE = register("yellow_bamboo_tiki_pole", new SupportingPoleBlock(state -> state.getBlock() instanceof TorchBlock, FabricBlockSettings.copyOf(BAMBOO_TIKI_POLE)));

    public static final Block YELLOW_BAMBOO = register("yellow_bamboo", new CBambooBlock(() -> (CBambooBlock) MarblesBlocks.YELLOW_BAMBOO, () -> (CBambooSaplingBlock) MarblesBlocks.YELLOW_BAMBOO_SAPLING, FabricBlockSettings.copyOf(Blocks.BAMBOO)));
    public static final Block YELLOW_BAMBOO_SAPLING = register("yellow_bamboo_sapling", new CBambooSaplingBlock(() -> (CBambooBlock) MarblesBlocks.YELLOW_BAMBOO, () -> (CBambooSaplingBlock) MarblesBlocks.YELLOW_BAMBOO_SAPLING, FabricBlockSettings.copyOf(Blocks.BAMBOO_SAPLING)), false);
    public static final Block POTTED_YELLOW_BAMBOO = register("potted_yellow_bamboo", new FlowerPotBlock(YELLOW_BAMBOO, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque()), false);

    public static final Block YELLOW_SCAFFOLDING = register("yellow_scaffolding", new YellowScaffoldingBlock(FabricBlockSettings.copyOf(Blocks.SCAFFOLDING)), false);

    /*
     * SANDS
     */

    public static final Block DAWN_SAND = register("dawn_sand", new SandBlock(0xA95821, FabricBlockSettings.copyOf(Blocks.RED_SAND)));

    public static final Block DAWN_SANDSTONE = register("dawn_sandstone", new Block(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE)));
    public static final Block CHISELED_DAWN_SANDSTONE = copy("chiseled_dawn_sandstone", DAWN_SANDSTONE);
    public static final Block CUT_DAWN_SANDSTONE = copy("cut_dawn_sandstone", DAWN_SANDSTONE);
    public static final Block SMOOTH_DAWN_SANDSTONE = copy("smooth_dawn_sandstone", DAWN_SANDSTONE);

    public static final Block DAWN_SANDSTONE_SLAB = createSlab(DAWN_SANDSTONE);
    public static final Block CUT_DAWN_SANDSTONE_SLAB = createSlab(CUT_DAWN_SANDSTONE);
    public static final Block SMOOTH_DAWN_SANDSTONE_SLAB = createSlab(SMOOTH_DAWN_SANDSTONE);

    public static final Block DAWN_SANDSTONE_STAIRS = createStairs(DAWN_SANDSTONE);
    public static final Block CUT_DAWN_SANDSTONE_STAIRS = createStairs(CUT_DAWN_SANDSTONE);
    public static final Block SMOOTH_DAWN_SANDSTONE_STAIRS = createStairs(SMOOTH_DAWN_SANDSTONE);

    public static final Block DAWN_SANDSTONE_WALL = createWall(DAWN_SANDSTONE);
    public static final Block CUT_DAWN_SANDSTONE_WALL = createWall(CUT_DAWN_SANDSTONE);
    public static final Block SMOOTH_DAWN_SANDSTONE_WALL = createWall(SMOOTH_DAWN_SANDSTONE);


    public static final Block DUSK_SAND = register(
        "dusk_sand", new SandBlock(
            0x471515,
            FabricBlockSettings.of(Material.AGGREGATE, MapColor.RED)
                .strength(0.5f).sounds(BlockSoundGroup.SAND)
        )
    );

    public static final Block DUSK_SANDSTONE = register(
        "dusk_sandstone", new Block(
            FabricBlockSettings.of(Material.STONE, MapColor.RED)
                .requiresTool().strength(0.8f)
        )
    );
    public static final Block CHISELED_DUSK_SANDSTONE = copy("chiseled_dusk_sandstone", DUSK_SANDSTONE);
    public static final Block CUT_DUSK_SANDSTONE = copy("cut_dusk_sandstone", DUSK_SANDSTONE);
    public static final Block SMOOTH_DUSK_SANDSTONE = copy("smooth_dusk_sandstone", DUSK_SANDSTONE);

    public static final Block DUSK_SANDSTONE_SLAB = createSlab(DUSK_SANDSTONE);
    public static final Block CUT_DUSK_SANDSTONE_SLAB = createSlab(CUT_DUSK_SANDSTONE);
    public static final Block SMOOTH_DUSK_SANDSTONE_SLAB = createSlab(SMOOTH_DUSK_SANDSTONE);

    public static final Block DUSK_SANDSTONE_STAIRS = createStairs(DUSK_SANDSTONE);
    public static final Block CUT_DUSK_SANDSTONE_STAIRS = createStairs(CUT_DUSK_SANDSTONE);
    public static final Block SMOOTH_DUSK_SANDSTONE_STAIRS = createStairs(SMOOTH_DUSK_SANDSTONE);

    public static final Block DUSK_SANDSTONE_WALL = createWall(DUSK_SANDSTONE);
    public static final Block CUT_DUSK_SANDSTONE_WALL = createWall(CUT_DUSK_SANDSTONE);
    public static final Block SMOOTH_DUSK_SANDSTONE_WALL = createWall(SMOOTH_DUSK_SANDSTONE);

    public static final Block MORN_GRASS = register("morn_grass", new PublicFernBlock(FabricBlockSettings.copyOf(Blocks.GRASS)));
    public static final Block TALL_MORN_GRASS = register("tall_morn_grass", new TallPlantBlock(FabricBlockSettings.copyOf(MORN_GRASS)));

    /*
     * LAPIS SETS
     */

    public static final Block LAPIS_SHINGLES = copy("lapis_shingles", Blocks.LAPIS_BLOCK);
    public static final Block LAPIS_SHINGLE_SLAB = createSlab("lapis_shingle", LAPIS_SHINGLES);
    public static final Block LAPIS_SHINGLE_STAIRS = createStairs("lapis_shingle", LAPIS_SHINGLES);
    public static final Block LAPIS_SPOTLIGHT = register("lapis_spotlight", new SpotlightBlock(FabricBlockSettings.copyOf(Blocks.LAPIS_BLOCK)));

    public static final Block GLAZED_LAPIS = register("glazed_lapis", new DualConnectingBlock(FabricBlockSettings.copyOf(Blocks.LAPIS_BLOCK)));

    public static final Block UMBRAL_LAZULI_ORE = register("umbral_lazuli_ore", new OreBlock(FabricBlockSettings.copyOf(Blocks.LAPIS_ORE)));
    public static final Block DEEPSLATE_UMBRAL_LAZULI_ORE = register("deepslate_umbral_lazuli_ore", new OreBlock(FabricBlockSettings.copyOf(UMBRAL_LAZULI_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5F, 3.0F).sounds(BlockSoundGroup.DEEPSLATE), UniformIntProvider.create(2, 5)));
    public static final Block PINK_SALT_UMBRAL_LAZULI_ORE = register("pink_salt_umbral_lazuli_ore", new OreBlock(FabricBlockSettings.copyOf(UMBRAL_LAZULI_ORE).mapColor(MapColor.ORANGE).sounds(MarblesSoundGroups.PINK_SALT), UniformIntProvider.create(2, 5)));
    public static final Block UMBRAL_LAZULI_BLOCK = copy("umbral_lazuli_block", Blocks.LAPIS_BLOCK);
    public static final Block UMBRAL_LAZULI_SHINGLES = copy("umbral_lazuli_shingles", LAPIS_SHINGLES);
    public static final Block UMBRAL_LAZULI_SHINGLE_SLAB = createSlab("umbral_lazuli_shingle", UMBRAL_LAZULI_SHINGLES);
    public static final Block UMBRAL_LAZULI_SHINGLE_STAIRS = createStairs("umbral_lazuli_shingle", UMBRAL_LAZULI_SHINGLES);
    public static final Block UMBRAL_LAZULI_SPOTLIGHT = register("umbral_lazuli_spotlight", new SpotlightBlock(FabricBlockSettings.copyOf(LAPIS_SPOTLIGHT)));

    public static final Block GLAZED_UMBRAL_LAZULI = register("glazed_umbral_lazuli", new DualConnectingBlock(FabricBlockSettings.copyOf(Blocks.LAPIS_BLOCK)));

    /*
     * ICE
     */

    /**
     * Used in cave generators and various structures/features to check whether a position is in a cave.
     */
    public static final Block ICE_CAVE_AIR = register("ice_cave_air", new PublicAirBlock(FabricBlockSettings.copyOf(Blocks.AIR)), false);

    public static final Block FLOESTONE = register("floestone", new Block(FabricBlockSettings.of(Material.STONE, MapColor.BROWN).sounds(MarblesBlockSoundGroup.FLOESTONE).requiresTool().strength(1.5F, 6.0F)));
    public static final Block FLOESTONE_SLAB = createSlab(FLOESTONE);
    public static final Block FLOESTONE_STAIRS = createStairs(FLOESTONE);
    public static final Block FLOESTONE_WALL = createWall(FLOESTONE);

    public static final Block POLISHED_FLOESTONE = copy("polished_floestone", FLOESTONE);
    public static final Block POLISHED_FLOESTONE_SLAB = createSlab(POLISHED_FLOESTONE);
    public static final Block POLISHED_FLOESTONE_STAIRS = createStairs(POLISHED_FLOESTONE);
    public static final Block POLISHED_FLOESTONE_WALL = createWall(POLISHED_FLOESTONE);

    public static final Block FLOESTONE_BRICKS = copy("floestone_bricks", FLOESTONE);
    public static final Block FLOESTONE_BRICK_SLAB = createSlab("floestone_brick", FLOESTONE_BRICKS);
    public static final Block FLOESTONE_BRICK_STAIRS = createStairs("floestone_brick", FLOESTONE_BRICKS);
    public static final Block FLOESTONE_BRICK_WALL = createWall("floestone_brick", FLOESTONE_BRICKS);

    public static final Block CHISELED_FLOESTONE = copy("chiseled_floestone", FLOESTONE);
    public static final Block RILLED_FLOESTONE = register("rilled_floestone", new Block(FabricBlockSettings.copyOf(FLOESTONE).emissiveLighting((state, world, pos) -> !ModLoaded.CANVAS).postProcess((state, world, pos) -> !ModLoaded.CANVAS).luminance(8).slipperiness(0.85F)));
    public static final Block RINGED_FLOESTONE = register("ringed_floestone", new RingedFloestoneBlock(FabricBlockSettings.copyOf(FLOESTONE).nonOpaque()));

    /**
     * Modification of vanilla ice without {@link AbstractBlock.Settings#ticksRandomly}
     */
    private static final FabricBlockSettings ICE_SETTINGS = FabricBlockSettings.of(Material.ICE)
                                                                              .slipperiness(0.98F)
                                                                              .strength(0.5F)
                                                                              .sounds(BlockSoundGroup.GLASS)
                                                                              .requiresTool()
                                                                              .allowsSpawning((state, world, pos, entityType) -> entityType == EntityType.POLAR_BEAR);
    private static final FabricBlockSettings BLUE_ICE_SETTINGS = FabricBlockSettings.copyOf(ICE_SETTINGS).slipperiness(0.989F);

    public static final Block SCALED_ICE = register("scaled_ice", new Block(ICE_SETTINGS));
    public static final Block MINTED_ICE = register("minted_ice", new Block(ICE_SETTINGS));

    public static final Block CUT_ICE = register("cut_ice", new PublicTransparentBlock(ICE_SETTINGS.nonOpaque()));
    public static final Block CUT_BLUE_ICE = register("cut_blue_ice", new Block(BLUE_ICE_SETTINGS));
    public static final Block CUT_SCALED_ICE = copy("cut_scaled_ice", SCALED_ICE);
    public static final Block CUT_MINTED_ICE = copy("cut_minted_ice", MINTED_ICE);

    public static final Block CHISELED_ICE = register("chiseled_ice", new HorizontalFacingTransparentBlock(ICE_SETTINGS.nonOpaque()));
    public static final Block CHISELED_BLUE_ICE = register("chiseled_blue_ice", new HorizontalFacingTransparentBlock(BLUE_ICE_SETTINGS));
    public static final Block CHISELED_SCALED_ICE = register("chiseled_scaled_ice", new HorizontalFacingTransparentBlock(FabricBlockSettings.copyOf(SCALED_ICE)));
    public static final Block CHISELED_MINTED_ICE = register("chiseled_minted_ice", new HorizontalFacingTransparentBlock(FabricBlockSettings.copyOf(MINTED_ICE)));

    public static final Block ICE_BRICKS = register("ice_bricks", new PublicTransparentBlock(ICE_SETTINGS.nonOpaque().sounds(MarblesBlockSoundGroup.ICE_BRICKS)));
    public static final Block BLUE_ICE_BRICKS = register("blue_ice_bricks", new PublicTransparentBlock(BLUE_ICE_SETTINGS.nonOpaque().sounds(MarblesBlockSoundGroup.ICE_BRICKS)));
    public static final Block SCALED_ICE_BRICKS = register("scaled_ice_bricks", new PublicTransparentBlock(FabricBlockSettings.copyOf(SCALED_ICE).nonOpaque().sounds(MarblesBlockSoundGroup.ICE_BRICKS)));
    public static final Block MINTED_ICE_BRICKS = register("minted_ice_bricks", new PublicTransparentBlock(FabricBlockSettings.copyOf(MINTED_ICE).nonOpaque().sounds(MarblesBlockSoundGroup.ICE_BRICKS)));

    private static final FabricBlockSettings SLUSH_SETTINGS = FabricBlockSettings.copyOf(Blocks.GRAVEL).sounds(MarblesBlockSoundGroup.SLUSH).slipperiness(0.85F);
    public static final Block SLUSH = register("slush", new FallingBlock(SLUSH_SETTINGS));
    public static final Block BLUE_SLUSH = register("blue_slush", new FallingBlock(SLUSH_SETTINGS));
    public static final Block SCALED_SLUSH = register("scaled_slush", new FallingBlock(SLUSH_SETTINGS));
    public static final Block MINTED_SLUSH = register("minted_slush", new FallingBlock(SLUSH_SETTINGS));

    /*
     * GRISP SET
     */

    public static final Block GRISP_PODZOL = register("grisp_podzol", new GrispPodzolBlock(FabricBlockSettings.copyOf(Blocks.PODZOL).mapColor(MapColor.YELLOW)));
    public static final Block GRISP_PODZOL_PATH = register("grisp_podzol_path", new MarblesPathBlock(() -> MarblesBlocks.GRISP_DIRT, FabricBlockSettings.copyOf(Blocks.DIRT_PATH).sounds(BlockSoundGroup.GRAVEL).nonOpaque().mapColor(MapColor.YELLOW)));
    public static final Block GRISP_DIRT = register("grisp_dirt", new Block(FabricBlockSettings.copyOf(Blocks.DIRT)));
    public static final Block GRISP_DIRT_PATH = register("grisp_dirt_path", new MarblesPathBlock(() -> MarblesBlocks.GRISP_DIRT, FabricBlockSettings.copyOf(Blocks.DIRT_PATH).sounds(BlockSoundGroup.GRAVEL).nonOpaque().mapColor(MapColor.DIRT_BROWN)));
    public static final Block COARSE_GRISP_DIRT = register("coarse_grisp_dirt", new Block(FabricBlockSettings.copyOf(Blocks.COARSE_DIRT)));
    public static final Block GRISP_FARMLAND = register("grisp_farmland", new MarblesFarmlandBlock(FabricBlockSettings.copyOf(Blocks.FARMLAND).nonOpaque()));
    public static final Block GRISP_MYCELIUM = register("grisp_mycelium", new GrispMyceliumBlock(FabricBlockSettings.copyOf(Blocks.MYCELIUM).ticksRandomly()));
    public static final Block GRISP_MYCELIUM_PATH = register("grisp_mycelium_path", new MarblesPathBlock(() -> MarblesBlocks.GRISP_DIRT, FabricBlockSettings.copyOf(Blocks.DIRT_PATH).nonOpaque().mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final Block POLLENATED_COBBLESTONE = copy("pollenated_cobblestone", Blocks.COBBLESTONE);
    public static final Block POLLENATED_COBBLESTONE_SLAB = createSlab(POLLENATED_COBBLESTONE);
    public static final Block POLLENATED_COBBLESTONE_STAIRS = createStairs(POLLENATED_COBBLESTONE);
    public static final Block POLLENATED_COBBLESTONE_WALL = createWall(POLLENATED_COBBLESTONE);

    public static final Block ASPEN_SPROUTS = register("aspen_sprouts", new PublicFernBlock(FabricBlockSettings.copyOf(Blocks.GRASS)));
    public static final Block ASPEN_GRASS = register("aspen_grass", new PublicFernBlock(FabricBlockSettings.copyOf(Blocks.GRASS)));
    public static final Block TALL_ASPEN_GRASS = register("tall_aspen_grass", new TallPlantBlock(FabricBlockSettings.copyOf(ASPEN_GRASS)));

    /*
     * PLANTAGE
     */

    public static final Block BLUE_PEONY = register("blue_peony", new StackingPlantBlock(FabricBlockSettings.copyOf(Blocks.PEONY).mapColor(DyeColor.BLUE)));

    /*
     * POLLEN-GRACED SHEEP
     */

    public static final Block POLLEN_GRACED_WOOL = register("pollen_graced_wool", new Block(
        FabricBlockSettings.of(Material.WOOL, MapColor.TERRACOTTA_YELLOW)
            .strength(0.8F)
            .sounds(BlockSoundGroup.WOOL)
            .breakByTool(FabricToolTags.SHEARS)
        )
    );
    public static final Block POLLEN_GRACED_CARPET = register("pollen_graced_carpet", new CarpetBlock(
        FabricBlockSettings.of(Material.CARPET, MapColor.TERRACOTTA_YELLOW)
            .strength(0.1F)
            .sounds(BlockSoundGroup.WOOL)
            .breakByTool(FabricToolTags.SHEARS)
        )
    );
    public static final Block POLLEN_GRACED_BED = register("pollen_graced_bed", new PollenGracedBedBlock(
        FabricBlockSettings.copyOf(Blocks.YELLOW_BED)
            .sounds(BlockSoundGroup.WOOD)
            .strength(0.2F)
            .nonOpaque()
        )
    );

    /*
     * WOOD
     */

    public static final WoodBlocks ASPEN = new WoodBlocks.Builder()
        .saplingGenerator(new AspenSaplingGenerator())
        .boatType(BoatEntity.Type.BIRCH)
        .itemGroup(MarblesItemGroup.INSTANCE)
        .build(Marbles.MOD_ID, "aspen");

    public static final WoodBlocks HOOPSI_SPRUCE = new WoodBlocks.Builder()
        .saplingGenerator(new HoopsiSpruceSaplingGenerator())
        .boatType(BoatEntity.Type.SPRUCE)
        .itemGroup(MarblesItemGroup.INSTANCE)
        .build(Marbles.MOD_ID, "hoopsi_spruce");

    public static final WoodBlocks RED_BIRCH = new WoodBlocks.Builder()
        .saplingGenerator(new RedBirchSaplingGenerator())
        .boatType(BoatEntity.Type.ACACIA)
        // .itemGroup(MarblesItemGroup.INSTANCE)
        .build(Marbles.MOD_ID, "red_birch");

    /*
     * REGISTRY
     */

    public static Block register(String id, Block block, boolean registerItem) {
        Identifier identifier = new Identifier(Marbles.MOD_ID, id);

        Block registeredBlock = Registry.register(Registry.BLOCK, identifier, block);
        if (registerItem) {
            Registry.register(Registry.ITEM, identifier, new BlockItem(registeredBlock, new Item.Settings().maxCount(64).group(MarblesItemGroup.INSTANCE)));
        }

        return registeredBlock;
    }

    public static Block register(String id, Block block) {
        return register(id, block, true);
    }

    private static Block createSlab(Block block) {
        return createSlab(getBlockId(block), block);
    }
    private static Block createSlab(String id, Block block) {
        return register(id + "_slab", new SlabBlock(FabricBlockSettings.copyOf(block)));
    }

    private static Block createWall(Block block) {
        return createWall(getBlockId(block), block);
    }
    private static Block createWall(String id, Block block) {
        return register(id + "_wall", new WallBlock(FabricBlockSettings.copyOf(block)));
    }

    private static Block createStairs(Block block) {
        return createStairs(getBlockId(block), block);
    }
    private static Block createStairs(String id, Block block) {
        return register(id + "_stairs", new PublicStairsBlock(block.getDefaultState(), FabricBlockSettings.copyOf(block)));
    }

    private static Block copy(String id, Block block) {
        return register(id, new Block(FabricBlockSettings.copyOf(block)));
    }

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(Properties.LIT) ? litLevel : 0;
    }

    private static String getBlockId(Block block) {
        return Registry.BLOCK.getId(block).getPath();
    }
}
