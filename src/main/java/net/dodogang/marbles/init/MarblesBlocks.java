package net.dodogang.marbles.init;

import me.andante.chord.block.CBambooBlock;
import me.andante.chord.block.CBambooSaplingBlock;
import me.andante.chord.block.helper.WoodBlocks;
import me.andante.chord.block.vanilla.PublicStairsBlock;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.block.*;
import net.dodogang.marbles.block.helper.TravertineBlocks;
import net.dodogang.marbles.block.sapling.AspenSaplingGenerator;
import net.dodogang.marbles.block.sapling.HoopsiSpruceSaplingGenerator;
import net.dodogang.marbles.state.property.MarblesProperties;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class MarblesBlocks {
    //
    // WOOD
    //

    public static final WoodBlocks ASPEN = new WoodBlocks.Builder()
                                               .saplingGenerator(new AspenSaplingGenerator())
                                               .boatType(BoatEntity.Type.BIRCH)
                                               .itemGroup(Marbles.ITEM_GROUP)
                                               .build(Marbles.MOD_ID, "aspen");

    public static final WoodBlocks HOOPSI_SPRUCE = new WoodBlocks.Builder()
                                                       .saplingGenerator(new HoopsiSpruceSaplingGenerator())
                                                       .boatType(BoatEntity.Type.SPRUCE)
                                                       .itemGroup(Marbles.ITEM_GROUP)
                                                       .build(Marbles.MOD_ID, "hoopsi_spruce");

    //
    // TRAVERTINE
    //

    public static final TravertineBlocks TRAVERTINE_BLOCKS = new TravertineBlocks(
        Marbles.MOD_ID, "travertine", Marbles.ITEM_GROUP, MaterialColor.WHITE
    );
    public static final TravertineBlocks LEMON_TRAVERTINE_BLOCKS = new TravertineBlocks(
        Marbles.MOD_ID, "lemon_travertine", Marbles.ITEM_GROUP, MaterialColor.WHITE
    );
    public static final TravertineBlocks PEACH_TRAVERTINE_BLOCKS = new TravertineBlocks(
        Marbles.MOD_ID, "peach_travertine", Marbles.ITEM_GROUP, MaterialColor.WHITE_TERRACOTTA
    );
    public static final TravertineBlocks TANGERINE_TRAVERTINE_BLOCKS = new TravertineBlocks(
        Marbles.MOD_ID, "tangerine_travertine", Marbles.ITEM_GROUP, MaterialColor.ORANGE
    );


    public static final Block TRAVERTINE_OBSIDIAN = register(
        "travertine_obsidian", new Block(
            FabricBlockSettings.of(Material.STONE, MaterialColor.BROWN)
                               .strength(50f, 1200f)
                               .requiresTool()
        )
    );
    public static final Block TRAVERTINE_PORTAL = register(
        "travertine_portal", new TravertinePortalBlock(
            FabricBlockSettings.copyOf(Blocks.NETHER_PORTAL)
        ),
        false
    );



    //
    // PINK SALT
    //

    // Used in salt cave features to check whether a position is in a salt cave
    public static final Block SALT_CAVE_AIR = register("salt_cave_air", new PublicAirBlock(FabricBlockSettings.copyOf(Blocks.AIR)), false);

    public static final Block PINK_SALT = register(
        "pink_salt",
        new Block(
            FabricBlockSettings.of(Material.STONE, MaterialColor.ORANGE)
                               .strength(1.2f, 6f)
                               .breakByTool(FabricToolTags.PICKAXES)
                               .materialColor(MaterialColor.ORANGE)
                               .requiresTool()
                               .sounds(MarblesSoundGroups.PINK_SALT)
        )
    );
    public static final Block CRUMBLED_PINK_SALT = register(
        "crumbled_pink_salt",
        new FallingBlock(
            FabricBlockSettings.of(Material.STONE, MaterialColor.ORANGE)
                               .strength(0.9f, 3f)
                               .breakByTool(FabricToolTags.PICKAXES)
                               .materialColor(MaterialColor.ORANGE)
                               .sounds(MarblesSoundGroups.PINK_SALT)
        )
    );
    public static final Block PINK_SALT_SLAB = createSlab(PINK_SALT); // I mean, I hate these methods too but ok
    public static final Block PINK_SALT_STAIRS = createStairs(PINK_SALT);

    public static final Block PINK_SALT_BRICKS = register("pink_salt_bricks", new Block(FabricBlockSettings.copyOf(PINK_SALT).sounds(MarblesSoundGroups.PINK_SALT_BRICKS)));
    public static final Block PINK_SALT_BRICK_SLAB = createSlab("pink_salt_brick", PINK_SALT_BRICKS);
    public static final Block PINK_SALT_BRICK_STAIRS = createStairs("pink_salt_brick", PINK_SALT_BRICKS);
    public static final Block PINK_SALT_PILLAR = register("pink_salt_pillar", new PillarBlock(FabricBlockSettings.copyOf(PINK_SALT).sounds(MarblesSoundGroups.PINK_SALT_BRICKS)));


    public static final Block PINK_SALT_SPIRE = register(
        PinkSaltSpireBlock.id, new PinkSaltSpireBlock(
            FabricBlockSettings.of(MarblesMaterial.PINK_SALT_SPIRE, MaterialColor.ORANGE)
                               .sounds(MarblesSoundGroups.PINK_SALT)
                               .strength(0.9f, 3f)
                               .requiresTool()
                               .breakByTool(FabricToolTags.PICKAXES)
                               .luminance(state -> 4)
        )
    );
    public static final Block PINK_SALT_STACK = register(
        PinkSaltStackBlock.id, new PinkSaltStackBlock(
            FabricBlockSettings.of(MarblesMaterial.PINK_SALT_SPIRE, MaterialColor.ORANGE)
                               .sounds(MarblesSoundGroups.PINK_SALT)
                               .strength(0.15f, 1f)
                               .breakByTool(FabricToolTags.PICKAXES)
                               .luminance(state -> state.get(MarblesProperties.RETAINED_LIGHT))
        )
    );
    public static final Block PINK_SALT_STUMP = register(
        PinkSaltStumpBlock.id, new PinkSaltStumpBlock(
            FabricBlockSettings.of(Material.PLANT, MaterialColor.ORANGE)
                               .sounds(MarblesSoundGroups.PINK_SALT)
                               .strength(0.05f, 1f)
                               .breakByTool(FabricToolTags.PICKAXES)
                               .luminance(state -> state.get(MarblesProperties.RETAINED_LIGHT) / 3)
        )
    );
    public static final Block PINK_SALT_SPIKES = register(
        PinkSaltSpikeBlock.id, new PinkSaltSpikeBlock(
            FabricBlockSettings.of(Material.PLANT, MaterialColor.ORANGE)
                               .sounds(MarblesSoundGroups.PINK_SALT)
                               .breakInstantly()
                               .nonOpaque()
        )
    );


    //
    // LAPIS SETS
    //

    public static final Block LAPIS_SHINGLES = register("lapis_shingles", new Block(FabricBlockSettings.copyOf(Blocks.LAPIS_BLOCK).breakByTool(FabricToolTags.PICKAXES, 1)));
    public static final Block LAPIS_SHINGLE_SLAB = createSlab("lapis_shingle", LAPIS_SHINGLES);
    public static final Block LAPIS_SHINGLE_STAIRS = createStairs("lapis_shingle", LAPIS_SHINGLES);
    public static final Block LAPIS_SPOTLIGHT = register("lapis_spotlight", new SpotlightBlock(FabricBlockSettings.copyOf(Blocks.LAPIS_BLOCK).breakByTool(FabricToolTags.PICKAXES, 1)));

    public static final Block GLAZED_LAPIS = register("glazed_lapis", new DualConnectingBlock(FabricBlockSettings.copyOf(Blocks.LAPIS_BLOCK).breakByTool(FabricToolTags.PICKAXES, 1)));

    public static final Block UMBRAL_LAZULI_ORE = register("umbral_lazuli_ore", new OreBlock(FabricBlockSettings.copyOf(Blocks.LAPIS_ORE).breakByTool(FabricToolTags.PICKAXES, 1)));
    public static final Block UMBRAL_LAZULI_BLOCK = register("umbral_lazuli_block", new Block(FabricBlockSettings.copyOf(Blocks.LAPIS_BLOCK).breakByTool(FabricToolTags.PICKAXES, 1)));
    public static final Block UMBRAL_LAZULI_SHINGLES = copy("umbral_lazuli_shingles", LAPIS_SHINGLES);
    public static final Block UMBRAL_LAZULI_SHINGLE_SLAB = createSlab("umbral_lazuli_shingle", UMBRAL_LAZULI_SHINGLES);
    public static final Block UMBRAL_LAZULI_SHINGLE_STAIRS = createStairs("umbral_lazuli_shingle", UMBRAL_LAZULI_SHINGLES);
    public static final Block UMBRAL_LAZULI_SPOTLIGHT = register("umbral_lazuli_spotlight", new SpotlightBlock(FabricBlockSettings.copyOf(LAPIS_SPOTLIGHT)));

    public static final Block GLAZED_UMBRAL_LAZULI = register("glazed_umbral_lazuli", new DualConnectingBlock(FabricBlockSettings.copyOf(Blocks.LAPIS_BLOCK).breakByTool(FabricToolTags.PICKAXES, 1)));


    //
    // YELLOW BAMBOO
    //

    public static final CBambooBlock YELLOW_BAMBOO = (CBambooBlock) register("yellow_bamboo", new CBambooBlock(() -> MarblesBlocks.YELLOW_BAMBOO, () -> MarblesBlocks.YELLOW_BAMBOO_SAPLING, FabricBlockSettings.copyOf(Blocks.BAMBOO)));
    public static final CBambooSaplingBlock YELLOW_BAMBOO_SAPLING = (CBambooSaplingBlock) register("yellow_bamboo_sapling", new CBambooSaplingBlock(() -> MarblesBlocks.YELLOW_BAMBOO, () -> MarblesBlocks.YELLOW_BAMBOO_SAPLING, FabricBlockSettings.copyOf(Blocks.BAMBOO_SAPLING)), false);

    public static final Block YELLOW_SCAFFOLDING = register(YellowScaffoldingBlock.id, new YellowScaffoldingBlock(FabricBlockSettings.copyOf(Blocks.SCAFFOLDING)), false);


    //
    // SANDS
    //

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
            FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.RED)
                               .strength(0.5f).sounds(BlockSoundGroup.SAND)
        )
    );

    public static final Block DUSK_SANDSTONE = register(
        "dusk_sandstone", new Block(
            FabricBlockSettings.of(Material.STONE, MaterialColor.RED)
                               .requiresTool().strength(0.8F)
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


    //
    // GRISP DIRT
    //

    public static final Block GRISP_DIRT = register("grisp_dirt", new Block(FabricBlockSettings.copyOf(Blocks.DIRT).breakByTool(FabricToolTags.SHOVELS, 0)));
    public static final Block COARSE_GRISP_DIRT = register("coarse_grisp_dirt", new Block(FabricBlockSettings.copyOf(Blocks.COARSE_DIRT).breakByTool(FabricToolTags.SHOVELS, 0)));
    public static final Block GRISP_GRASS_BLOCK = register("grisp_grass_block", new GrispGrassBlock(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).ticksRandomly().breakByTool(FabricToolTags.SHOVELS, 0)));
    public static final Block GRISP_PODZOL = register("grisp_podzol", new GrispPodzolBlock(FabricBlockSettings.copyOf(Blocks.PODZOL).materialColor(MaterialColor.YELLOW).breakByTool(FabricToolTags.SHOVELS, 0)));
    public static final Block GRISP_MYCELIUM = register("grisp_mycelium", new GrispMyceliumBlock(FabricBlockSettings.copyOf(Blocks.MYCELIUM).ticksRandomly().breakByTool(FabricToolTags.SHOVELS, 0)));

    public static final Block GRISP_DIRT_PATH = register("grisp_dirt_path", new MarblesPathBlock(GRISP_DIRT, FabricBlockSettings.copyOf(Blocks.GRASS_PATH).sounds(BlockSoundGroup.GRAVEL).nonOpaque().materialColor(MaterialColor.DIRT).breakByTool(FabricToolTags.SHOVELS, 0)));
    public static final Block GRISP_GRASS_PATH = register("grisp_grass_path", new MarblesPathBlock(GRISP_DIRT, FabricBlockSettings.copyOf(Blocks.GRASS_PATH).nonOpaque().materialColor(MaterialColor.GRASS).breakByTool(FabricToolTags.SHOVELS, 0)));
    public static final Block GRISP_PODZOL_PATH = register("grisp_podzol_path", new MarblesPathBlock(GRISP_DIRT, FabricBlockSettings.copyOf(Blocks.GRASS_PATH).sounds(BlockSoundGroup.GRAVEL).nonOpaque().materialColor(MaterialColor.YELLOW).breakByTool(FabricToolTags.SHOVELS, 0)));
    public static final Block GRISP_MYCELIUM_PATH = register("grisp_mycelium_path", new MarblesPathBlock(GRISP_DIRT, FabricBlockSettings.copyOf(Blocks.GRASS_PATH).nonOpaque().materialColor(MaterialColor.PURPLE_TERRACOTTA).breakByTool(FabricToolTags.SHOVELS, 0)));
    public static final Block GRISP_FARMLAND = register("grisp_farmland", new MarblesFarmlandBlock(FabricBlockSettings.copyOf(Blocks.FARMLAND).nonOpaque().breakByTool(FabricToolTags.SHOVELS, 0)));





    public MarblesBlocks() {
    }

    public static Block register(String id, Block block, boolean registerItem) {
        Identifier identifier = new Identifier(Marbles.MOD_ID, id);

        Block registeredBlock = Registry.register(Registry.BLOCK, identifier, block);
        if (registerItem)
            Registry.register(Registry.ITEM, identifier, new BlockItem(registeredBlock, new Item.Settings().maxCount(64).group(Marbles.ITEM_GROUP)));

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

    private static Block createPillarBricks(Block block) {
        return register(getBlockId(block) + "_bricks", new PillarBlock(FabricBlockSettings.copyOf(block)));
    }

    private static Block createSaltLamp(Block block) {
        return register(getBlockId(block) + "_salt_lamp", new Block(
                            FabricBlockSettings.copyOf(block)
                                               .strength(0.75F, 3.0F)
                                               .luminance(state -> 15)
                        )
        );
    }

    private static Block copy(String id, Block block) {
        return register(id, new Block(FabricBlockSettings.copyOf(block)));
    }

    private static String getBlockId(Block block) {
        return Registry.BLOCK.getId(block).getPath();
    }
}
