package net.dodogang.marbles.init;

import me.andante.chord.block.CBambooBlock;
import me.andante.chord.block.CBambooSaplingBlock;
import me.andante.chord.block.helper.WoodBlocks;
import me.andante.chord.block.vanilla.PublicStairsBlock;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.block.*;
import net.dodogang.marbles.block.enums.SpirePart;
import net.dodogang.marbles.block.sapling.AspenSaplingGenerator;
import net.dodogang.marbles.block.sapling.HoopsiSpruceSaplingGenerator;
import net.dodogang.marbles.state.property.MarblesProperties;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
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

    public static final WoodBlocks ASPEN = new WoodBlocks.Builder().saplingGenerator(new AspenSaplingGenerator()).boatType(BoatEntity.Type.BIRCH).itemGroup(Marbles.ITEM_GROUP).build(Marbles.MOD_ID, "aspen");
    public static final WoodBlocks HOOPSI_SPRUCE = new WoodBlocks.Builder().saplingGenerator(new HoopsiSpruceSaplingGenerator()).boatType(BoatEntity.Type.SPRUCE).itemGroup(Marbles.ITEM_GROUP).build(Marbles.MOD_ID, "hoopsi_spruce");

    //
    // TRAVERTINE
    //

    private static final String travertine = "travertine";
    private static final String travertineBrick = travertine + "_brick";
    private static final String lemonTravertine = "lemon_" + travertine;
    private static final String lemonTravertineBrick = lemonTravertine + "_brick";
    private static final String peachTravertine = "peach_" + travertine;
    private static final String peachTravertineBrick = peachTravertine + "_brick";
    private static final String tangerineTravertine = "tangerine_" + travertine;
    private static final String tangerineTravertineBrick = tangerineTravertine + "_brick";

    public static final Block TRAVERTINE = register("travertine", new PillarBlock(
            FabricBlockSettings.of(Material.STONE, MaterialColor.STONE)
                .strength(1.5F, 6.0F)
                .requiresTool()
        )
    );
    public static final Block TRAVERTINE_BRICKS = createPillarBricks(TRAVERTINE);
    public static final Block CAPPED_TRAVERTINE = register("capped_" + travertine, new MarblesFacingBlock(FabricBlockSettings.copy(TRAVERTINE)));
    public static final Block POLISHED_TRAVERTINE = copy("polished_" + travertine, TRAVERTINE);
    public static final Block TRAVERTINE_SLAB = createSlab(TRAVERTINE);
    public static final Block TRAVERTINE_BRICK_SLAB = createSlab(travertineBrick, TRAVERTINE_BRICKS);
    public static final Block CAPPED_TRAVERTINE_SLAB = createSlab(CAPPED_TRAVERTINE);
    public static final Block POLISHED_TRAVERTINE_SLAB = createSlab(POLISHED_TRAVERTINE);
    public static final Block TRAVERTINE_STAIRS = createStairs(TRAVERTINE);
    public static final Block TRAVERTINE_BRICK_STAIRS = createStairs(travertineBrick, TRAVERTINE_BRICKS);
    public static final Block CAPPED_TRAVERTINE_STAIRS = createStairs(CAPPED_TRAVERTINE);
    public static final Block POLISHED_TRAVERTINE_STAIRS = createStairs(POLISHED_TRAVERTINE);
    public static final Block TRAVERTINE_WALL = createWall(TRAVERTINE);
    public static final Block TRAVERTINE_BRICK_WALL = createWall(travertineBrick, TRAVERTINE_BRICKS);
    public static final Block CAPPED_TRAVERTINE_WALL = createWall(CAPPED_TRAVERTINE);
    public static final Block POLISHED_TRAVERTINE_WALL = createWall(POLISHED_TRAVERTINE);

    public static final Block LEMON_TRAVERTINE = register(lemonTravertine, new PillarBlock(FabricBlockSettings.copy(TRAVERTINE)));
    public static final Block LEMON_TRAVERTINE_BRICKS = createPillarBricks(LEMON_TRAVERTINE);
    public static final Block CAPPED_LEMON_TRAVERTINE = register("capped_" + lemonTravertine, new MarblesFacingBlock(FabricBlockSettings.copy(LEMON_TRAVERTINE)));
    public static final Block POLISHED_LEMON_TRAVERTINE = copy("polished_" + lemonTravertine, LEMON_TRAVERTINE);
    public static final Block LEMON_TRAVERTINE_SLAB = createSlab(LEMON_TRAVERTINE);
    public static final Block LEMON_TRAVERTINE_BRICK_SLAB = createSlab(lemonTravertineBrick, LEMON_TRAVERTINE_BRICKS);
    public static final Block CAPPED_LEMON_TRAVERTINE_SLAB = createSlab(CAPPED_LEMON_TRAVERTINE);
    public static final Block POLISHED_LEMON_TRAVERTINE_SLAB = createSlab(POLISHED_LEMON_TRAVERTINE);
    public static final Block LEMON_TRAVERTINE_STAIRS = createStairs(LEMON_TRAVERTINE);
    public static final Block LEMON_TRAVERTINE_BRICK_STAIRS = createStairs(lemonTravertineBrick, LEMON_TRAVERTINE_BRICKS);
    public static final Block CAPPED_LEMON_TRAVERTINE_STAIRS = createStairs(CAPPED_LEMON_TRAVERTINE);
    public static final Block POLISHED_LEMON_TRAVERTINE_STAIRS = createStairs(POLISHED_LEMON_TRAVERTINE);
    public static final Block LEMON_TRAVERTINE_WALL = createWall(LEMON_TRAVERTINE);
    public static final Block LEMON_TRAVERTINE_BRICK_WALL = createWall(lemonTravertineBrick, LEMON_TRAVERTINE_BRICKS);
    public static final Block CAPPED_LEMON_TRAVERTINE_WALL = createWall(CAPPED_LEMON_TRAVERTINE);
    public static final Block POLISHED_LEMON_TRAVERTINE_WALL = createWall(POLISHED_LEMON_TRAVERTINE);

    public static final Block PEACH_TRAVERTINE = register(peachTravertine, new PillarBlock(FabricBlockSettings.copy(TRAVERTINE)));
    public static final Block PEACH_TRAVERTINE_BRICKS = createPillarBricks(PEACH_TRAVERTINE);
    public static final Block CAPPED_PEACH_TRAVERTINE = register("capped_" + peachTravertine, new MarblesFacingBlock(FabricBlockSettings.copy(PEACH_TRAVERTINE)));
    public static final Block POLISHED_PEACH_TRAVERTINE = copy("polished_" + peachTravertine, PEACH_TRAVERTINE);
    public static final Block PEACH_TRAVERTINE_SLAB = createSlab(PEACH_TRAVERTINE);
    public static final Block PEACH_TRAVERTINE_BRICK_SLAB = createSlab(peachTravertineBrick, PEACH_TRAVERTINE_BRICKS);
    public static final Block CAPPED_PEACH_TRAVERTINE_SLAB = createSlab(CAPPED_PEACH_TRAVERTINE);
    public static final Block POLISHED_PEACH_TRAVERTINE_SLAB = createSlab(POLISHED_PEACH_TRAVERTINE);
    public static final Block PEACH_TRAVERTINE_STAIRS = createStairs(PEACH_TRAVERTINE);
    public static final Block PEACH_TRAVERTINE_BRICK_STAIRS = createStairs(peachTravertineBrick, PEACH_TRAVERTINE_BRICKS);
    public static final Block CAPPED_PEACH_TRAVERTINE_STAIRS = createStairs(CAPPED_PEACH_TRAVERTINE);
    public static final Block POLISHED_PEACH_TRAVERTINE_STAIRS = createStairs(POLISHED_PEACH_TRAVERTINE);
    public static final Block PEACH_TRAVERTINE_WALL = createWall(PEACH_TRAVERTINE);
    public static final Block PEACH_TRAVERTINE_BRICK_WALL = createWall(peachTravertineBrick, PEACH_TRAVERTINE_BRICKS);
    public static final Block CAPPED_PEACH_TRAVERTINE_WALL = createWall(CAPPED_PEACH_TRAVERTINE);
    public static final Block POLISHED_PEACH_TRAVERTINE_WALL = createWall(POLISHED_PEACH_TRAVERTINE);

    public static final Block TANGERINE_TRAVERTINE = register(tangerineTravertine, new PillarBlock(FabricBlockSettings.copy(TRAVERTINE)));
    public static final Block TANGERINE_TRAVERTINE_BRICKS = createPillarBricks(TANGERINE_TRAVERTINE);
    public static final Block CAPPED_TANGERINE_TRAVERTINE = register("capped_" + tangerineTravertine, new MarblesFacingBlock(FabricBlockSettings.copy(TANGERINE_TRAVERTINE)));
    public static final Block POLISHED_TANGERINE_TRAVERTINE = copy("polished_" + tangerineTravertine, TANGERINE_TRAVERTINE);
    public static final Block TANGERINE_TRAVERTINE_SLAB = createSlab(TANGERINE_TRAVERTINE);
    public static final Block TANGERINE_TRAVERTINE_BRICK_SLAB = createSlab(tangerineTravertineBrick, TANGERINE_TRAVERTINE_BRICKS);
    public static final Block CAPPED_TANGERINE_TRAVERTINE_SLAB = createSlab(CAPPED_TANGERINE_TRAVERTINE);
    public static final Block POLISHED_TANGERINE_TRAVERTINE_SLAB = createSlab(POLISHED_TANGERINE_TRAVERTINE);
    public static final Block TANGERINE_TRAVERTINE_STAIRS = createStairs(TANGERINE_TRAVERTINE);
    public static final Block TANGERINE_TRAVERTINE_BRICK_STAIRS = createStairs(tangerineTravertineBrick, TANGERINE_TRAVERTINE_BRICKS);
    public static final Block CAPPED_TANGERINE_TRAVERTINE_STAIRS = createStairs(CAPPED_TANGERINE_TRAVERTINE);
    public static final Block POLISHED_TANGERINE_TRAVERTINE_STAIRS = createStairs(POLISHED_TANGERINE_TRAVERTINE);
    public static final Block TANGERINE_TRAVERTINE_WALL = createWall(TANGERINE_TRAVERTINE);
    public static final Block TANGERINE_TRAVERTINE_BRICK_WALL = createWall(tangerineTravertineBrick, TANGERINE_TRAVERTINE_BRICKS);
    public static final Block CAPPED_TANGERINE_TRAVERTINE_WALL = createWall(CAPPED_TANGERINE_TRAVERTINE);
    public static final Block POLISHED_TANGERINE_TRAVERTINE_WALL = createWall(POLISHED_TANGERINE_TRAVERTINE);

    public static final Block TRAVERTINE_OBSIDIAN = register(travertine + "_obsidian", new Block(
        FabricBlockSettings.of(Material.STONE, MaterialColor.BROWN)
            .strength(50.0F, 1200.0F)
            .requiresTool()
        )
    );

    public static final Block TRAVERTINE_SALT_LAMP = createSaltLamp(TRAVERTINE);
    public static final Block LEMON_TRAVERTINE_SALT_LAMP = createSaltLamp(LEMON_TRAVERTINE);
    public static final Block PEACH_TRAVERTINE_SALT_LAMP = createSaltLamp(PEACH_TRAVERTINE);
    public static final Block TANGERINE_TRAVERTINE_SALT_LAMP = createSaltLamp(TANGERINE_TRAVERTINE);



    //
    // PINK SALT
    //

    public static final Block PINK_SALT = register("pink_salt", new Block(FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block CRUMBLED_PINK_SALT = copy("crumbled_pink_salt", PINK_SALT);
    public static final Block PINK_SALT_SPIRE = register(PinkSaltSpireBlock.id, new PinkSaltSpireBlock(
        FabricBlockSettings.copy(PINK_SALT)
            .luminance(
                state -> {
                    SpirePart spirePart = state.get(MarblesProperties.SPIRE_PART);
                    return spirePart == SpirePart.TIP || spirePart == SpirePart.TIP_MERGE ? 4 : 1;
                }
            )
        )
    );
    public static final Block PINK_SALT_STACK = register(PinkSaltStackBlock.id, new PinkSaltStackBlock(
        FabricBlockSettings.copy(PINK_SALT)
            .luminance(
                state -> state.get(MarblesProperties.RETAINED_LIGHT)
            )
            .breakInstantly()
        )
    );
    public static final Block PINK_SALT_STUMP = register(PinkSaltStumpBlock.id, new PinkSaltStumpBlock(
        FabricBlockSettings.copy(PINK_SALT)
            .luminance(
                state -> state.get(MarblesProperties.RETAINED_LIGHT) / 3
            )
            .breakInstantly()
                                                         )
    );

    //
    // LAPIS SET
    //

    public static final Block LAPIS_SHINGLES = register("lapis_shingles", new Block(FabricBlockSettings.copy(Blocks.LAPIS_BLOCK)));
    public static final Block LAPIS_SHINGLES_SLAB = createSlab(LAPIS_SHINGLES);
    public static final Block LAPIS_SHINGLES_STAIRS = createStairs(LAPIS_SHINGLES);
    public static final Block LAPIS_SPOTLIGHT = register("lapis_spotlight", new SpotlightBlock(FabricBlockSettings.copy(Blocks.LAPIS_BLOCK)));
    public static final Block SPOTLIGHT_AIR = register("spotlight_air", new SpotlightAirBlock(
        FabricBlockSettings.of(Material.AIR, MaterialColor.CLEAR)
                           .blockVision((state, world, pos) -> true)
                           .suffocates((state, world, pos) -> false)
                           .allowsSpawning((state, world, pos, type) -> true)
                           .air()
                           .luminance(state -> {
                               int dist = 31 - state.get(MarblesProperties.DISTANCE_0_31);
                               return Math.min(15, dist / 5 + 10);
                           })
    ), false);

    //
    // YELLOW BAMBOO
    //

    public static final CBambooBlock YELLOW_BAMBOO = (CBambooBlock) register("yellow_bamboo", new CBambooBlock(() -> MarblesBlocks.YELLOW_BAMBOO, () -> MarblesBlocks.YELLOW_BAMBOO_SAPLING, FabricBlockSettings.copy(Blocks.BAMBOO)));
    public static final CBambooSaplingBlock YELLOW_BAMBOO_SAPLING = (CBambooSaplingBlock) register("yellow_bamboo_sapling", new CBambooSaplingBlock(() -> MarblesBlocks.YELLOW_BAMBOO, () -> MarblesBlocks.YELLOW_BAMBOO_SAPLING, FabricBlockSettings.copy(Blocks.BAMBOO_SAPLING)), false);

    public static final Block YELLOW_SCAFFOLDING = register(YellowScaffoldingBlock.id, new YellowScaffoldingBlock(FabricBlockSettings.copy(Blocks.SCAFFOLDING)), false);

    //
    // SANDS
    //

    private static final String sandstone = "sandstone";

    private static final String dawn = "dawn_";
    private static final String dawnSandstone = dawn + sandstone;
    public static final Block DAWN_SAND = register(dawn + "sand", new SandBlock(11098145, FabricBlockSettings.copy(Blocks.RED_SAND)));
    public static final Block DAWN_SANDSTONE = register(dawnSandstone, new Block(FabricBlockSettings.copy(Blocks.RED_SANDSTONE)));
    public static final Block CHISELED_DAWN_SANDSTONE = copy("chiseled_" + dawnSandstone, DAWN_SANDSTONE);
    public static final Block CUT_DAWN_SANDSTONE = copy("cut_" + dawnSandstone, DAWN_SANDSTONE);
    public static final Block SMOOTH_DAWN_SANDSTONE = copy("smooth_" + dawnSandstone, DAWN_SANDSTONE);
    public static final Block DAWN_SANDSTONE_SLAB = createSlab(DAWN_SANDSTONE);
    public static final Block CUT_DAWN_SANDSTONE_SLAB = createSlab(CUT_DAWN_SANDSTONE);
    public static final Block SMOOTH_DAWN_SANDSTONE_SLAB = createSlab(SMOOTH_DAWN_SANDSTONE);
    public static final Block DAWN_SANDSTONE_STAIRS = createStairs(DAWN_SANDSTONE);
    public static final Block CUT_DAWN_SANDSTONE_STAIRS = createStairs(CUT_DAWN_SANDSTONE);
    public static final Block SMOOTH_DAWN_SANDSTONE_STAIRS = createStairs(SMOOTH_DAWN_SANDSTONE);
    public static final Block DAWN_SANDSTONE_WALL = createWall(DAWN_SANDSTONE);
    public static final Block CUT_DAWN_SANDSTONE_WALL = createWall(CUT_DAWN_SANDSTONE);
    public static final Block SMOOTH_DAWN_SANDSTONE_WALL = createWall(SMOOTH_DAWN_SANDSTONE);

    private static final String dusk = "dusk_";
    private static final String duskSandstone = dusk + sandstone;
    public static final Block DUSK_SAND = register(dusk + "sand", new SandBlock(
        4658453,
        FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.RED)
            .strength(0.5F).sounds(BlockSoundGroup.SAND)
        )
    );
    public static final Block DUSK_SANDSTONE = register(duskSandstone, new Block(
        FabricBlockSettings.of(Material.STONE, MaterialColor.RED)
            .requiresTool().strength(0.8F)
        )
    );
    public static final Block CHISELED_DUSK_SANDSTONE = copy("chiseled_" + duskSandstone, DUSK_SANDSTONE);
    public static final Block CUT_DUSK_SANDSTONE = copy("cut_" + duskSandstone, DUSK_SANDSTONE);
    public static final Block SMOOTH_DUSK_SANDSTONE = copy("smooth_" + duskSandstone, DUSK_SANDSTONE);
    public static final Block DUSK_SANDSTONE_SLAB = createSlab(DUSK_SANDSTONE);
    public static final Block CUT_DUSK_SANDSTONE_SLAB = createSlab(CUT_DUSK_SANDSTONE);
    public static final Block SMOOTH_DUSK_SANDSTONE_SLAB = createSlab(SMOOTH_DUSK_SANDSTONE);
    public static final Block DUSK_SANDSTONE_STAIRS = createStairs(DUSK_SANDSTONE);
    public static final Block CUT_DUSK_SANDSTONE_STAIRS = createStairs(CUT_DUSK_SANDSTONE);
    public static final Block SMOOTH_DUSK_SANDSTONE_STAIRS = createStairs(SMOOTH_DUSK_SANDSTONE);
    public static final Block DUSK_SANDSTONE_WALL = createWall(DUSK_SANDSTONE);
    public static final Block CUT_DUSK_SANDSTONE_WALL = createWall(CUT_DUSK_SANDSTONE);
    public static final Block SMOOTH_DUSK_SANDSTONE_WALL = createWall(SMOOTH_DUSK_SANDSTONE);

    public MarblesBlocks() {}

    public static Block register(String id, Block block, boolean registerItem) {
        Identifier identifier = new Identifier(Marbles.MOD_ID, id);

        Block registeredBlock = Registry.register(Registry.BLOCK, identifier, block);
        if (registerItem) Registry.register(Registry.ITEM, identifier, new BlockItem(registeredBlock, new Item.Settings().maxCount(64).group(Marbles.ITEM_GROUP)));

        return registeredBlock;
    }
    public static Block register(String id, Block block) {
        return register(id, block, true);
    }

    private static Block createSlab(Block block) {
        return createSlab(getBlockId(block), block);
    }
    private static Block createSlab(String id, Block block) {
        return register(id + "_slab", new SlabBlock(FabricBlockSettings.copy(block)));
    }
    private static Block createWall(Block block) {
        return createWall(getBlockId(block), block);
    }
    private static Block createWall(String id, Block block) {
        return register(id + "_wall", new WallBlock(FabricBlockSettings.copy(block)));
    }
    private static Block createStairs(Block block) {
        return createStairs(getBlockId(block), block);
    }
    private static Block createStairs(String id, Block block) {
        return register(id + "_stairs", new PublicStairsBlock(block.getDefaultState(), FabricBlockSettings.copy(block)));
    }

    private static Block createPillarBricks(Block block) {
        return register(getBlockId(block) + "_bricks", new PillarBlock(FabricBlockSettings.copy(block)));
    }
    private static Block createSaltLamp(Block block) {
        return register(getBlockId(block) + "_salt_lamp", new Block(
            FabricBlockSettings.copy(block)
                               .strength(0.75F, 3.0F)
                               .luminance(state -> 15)
            )
        );
    }
    private static Block copy(String id, Block block) {
        return register(id, new Block(FabricBlockSettings.copy(block)));
    }

    private static String getBlockId(Block block) {
        return Registry.BLOCK.getId(block).getPath();
    }
}
