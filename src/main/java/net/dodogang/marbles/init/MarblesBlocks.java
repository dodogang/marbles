package net.dodogang.marbles.init;

import me.andante.chord.block.CBambooBlock;
import me.andante.chord.block.CBambooSaplingBlock;
import me.andante.chord.block.helper.WoodBlocks;
import me.andante.chord.block.vanilla.PublicStairsBlock;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.block.YellowScaffoldingBlock;
import net.dodogang.marbles.block.sapling.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class MarblesBlocks {
    //
    // CODE CLEANLINESS OVERLOAD
    //

    private static final String brick = "_brick";
    private static final String capped = "capped_";
    private static final String polished = "polished_";

    //
    // WOOD
    //

    public static final WoodBlocks ASPEN = new WoodBlocks.Builder().saplingGenerator(new AspenSaplingGenerator()).boatType(BoatEntity.Type.BIRCH).itemGroup(Marbles.ITEM_GROUP).build(Marbles.MOD_ID, "aspen");
    public static final WoodBlocks HOOPSI_SPRUCE = new WoodBlocks.Builder().saplingGenerator(new HoopsiSpruceSaplingGenerator()).boatType(BoatEntity.Type.SPRUCE).itemGroup(Marbles.ITEM_GROUP).build(Marbles.MOD_ID, "hoopsi_spruce");

    //
    // TRAVERTINE
    //

    private static final String travertine = "travertine";
    private static final String travertineBrick = travertine + brick;
    private static final String lemonTravertine = "lemon_" + travertine;
    private static final String lemonTravertineBrick = lemonTravertine + brick;
    private static final String peachTravertine = "peach_" + travertine;
    private static final String peachTravertineBrick = peachTravertine + brick;
    private static final String tangerineTravertine = "tangerine_" + travertine;
    private static final String tangerineTravertineBrick = tangerineTravertine + brick;

    public static final Block TRAVERTINE = register("travertine", new PillarBlock(
            FabricBlockSettings.of(Material.STONE, MaterialColor.STONE)
                .strength(1.5F, 6.0F)
                .requiresTool()
        )
    );
    public static final Block TRAVERTINE_BRICKS = createPillarBricks(TRAVERTINE);
    public static final Block CAPPED_TRAVERTINE = register(capped + travertine, new PillarBlock(FabricBlockSettings.copy(TRAVERTINE)));
    public static final Block POLISHED_TRAVERTINE = register(polished + travertine, new Block(FabricBlockSettings.copy(TRAVERTINE)));
    public static final Block TRAVERTINE_OBSIDIAN = register(travertine + "_obsidian", new Block(
        FabricBlockSettings.of(Material.STONE, MaterialColor.BROWN)
            .strength(50.0F, 1200.0F)
            .requiresTool()
        )
    );
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
    public static final Block CAPPED_LEMON_TRAVERTINE = register(capped + lemonTravertine, new PillarBlock(FabricBlockSettings.copy(LEMON_TRAVERTINE)));
    public static final Block POLISHED_LEMON_TRAVERTINE = register(polished + lemonTravertine, new Block(FabricBlockSettings.copy(LEMON_TRAVERTINE)));
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
    public static final Block CAPPED_PEACH_TRAVERTINE = register(capped + peachTravertine, new PillarBlock(FabricBlockSettings.copy(PEACH_TRAVERTINE)));
    public static final Block POLISHED_PEACH_TRAVERTINE = register(polished + peachTravertine, new Block(FabricBlockSettings.copy(PEACH_TRAVERTINE)));
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
    public static final Block CAPPED_TANGERINE_TRAVERTINE = register(capped + tangerineTravertine, new PillarBlock(FabricBlockSettings.copy(TANGERINE_TRAVERTINE)));
    public static final Block POLISHED_TANGERINE_TRAVERTINE = register(polished + tangerineTravertine, new Block(FabricBlockSettings.copy(TANGERINE_TRAVERTINE)));
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

    //
    // YELLOW BAMBOO
    //

    private static final String yellowBamboo = "yellow_bamboo";
    public static final CBambooBlock YELLOW_BAMBOO = (CBambooBlock)register(yellowBamboo, new CBambooBlock(() -> MarblesBlocks.YELLOW_BAMBOO, () -> MarblesBlocks.YELLOW_BAMBOO_SAPLING, FabricBlockSettings.copy(Blocks.BAMBOO)));
    public static final CBambooSaplingBlock YELLOW_BAMBOO_SAPLING = (CBambooSaplingBlock)register(yellowBamboo + "_sapling", new CBambooSaplingBlock(() -> MarblesBlocks.YELLOW_BAMBOO, () -> MarblesBlocks.YELLOW_BAMBOO_SAPLING, FabricBlockSettings.copy(Blocks.BAMBOO_SAPLING)), false);

    public static final Block YELLOW_SCAFFOLDING = register(YellowScaffoldingBlock.id, new YellowScaffoldingBlock(FabricBlockSettings.copy(Blocks.SCAFFOLDING)), false);

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

    private static String getBlockId(Block block) {
        return Registry.BLOCK.getId(block).getPath();
    }
}
