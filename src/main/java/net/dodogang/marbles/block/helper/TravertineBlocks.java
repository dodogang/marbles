package net.dodogang.marbles.block.helper;

import me.andante.chord.block.vanilla.PublicStairsBlock;
import net.dodogang.marbles.block.MarblesFacingBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings({"UnusedReturnValue","unused"})
public class TravertineBlocks {
    private final String id;
    private final String modId;
    private final ItemGroup itemGroup;
    private final MaterialColor color;

    public final Block RAW;
    public final Block BRICKS;
    public final Block CAPPED;
    public final Block POLISHED;

    public final Block SLAB;
    public final Block BRICK_SLAB;
    public final Block CAPPED_SLAB;
    public final Block POLISHED_SLAB;

    public final Block STAIRS;
    public final Block BRICK_STAIRS;
    public final Block CAPPED_STAIRS;
    public final Block POLISHED_STAIRS;

    public final Block WALL;
    public final Block BRICK_WALL;
    public final Block CAPPED_WALL;
    public final Block POLISHED_WALL;

    public final Block SALT_LAMP;

    public TravertineBlocks(String modId, String id, ItemGroup itemGroup, MaterialColor color) {
        this.id = id;
        this.modId = modId;
        this.itemGroup = itemGroup;
        this.color = color;

        RAW = register(
            "%s", new PillarBlock(
                FabricBlockSettings.of(Material.STONE, color)
                                   .strength(1.5f, 6f)
                                   .requiresTool()
            )
        );
        BRICKS = register("%s_bricks", new PillarBlock(FabricBlockSettings.copyOf(RAW)));
        CAPPED = register("capped_%s", new MarblesFacingBlock(FabricBlockSettings.copyOf(RAW)));
        POLISHED = register("polished_%s", new Block(FabricBlockSettings.copyOf(RAW)));

        SLAB = createSlab("%s_slab", RAW);
        BRICK_SLAB = createSlab("%s_brick_slab", BRICKS);
        CAPPED_SLAB = createSlab("capped_%s_slab", CAPPED);
        POLISHED_SLAB = createSlab("polished_%s_slab", POLISHED);

        STAIRS = createStairs("%s_stairs", RAW);
        BRICK_STAIRS = createStairs("%s_brick_stairs", BRICKS);
        CAPPED_STAIRS = createStairs("capped_%s_stairs", CAPPED);
        POLISHED_STAIRS = createStairs("polished_%s_stairs", POLISHED);

        WALL = createWall("%s_wall", RAW);
        BRICK_WALL = createWall("%s_brick_wall", BRICKS);
        CAPPED_WALL = createWall("capped_%s_wall", CAPPED);
        POLISHED_WALL = createWall("polished_%s_wall", POLISHED);

        SALT_LAMP = createSaltLamp("%s_salt_lamp", RAW);
    }

    public String getId() {
        return id;
    }

    public String getModId() {
        return modId;
    }

    public ItemGroup getItemGroup() {
        return itemGroup;
    }

    public MaterialColor getColor() {
        return color;
    }

    private Block register(String id, Block block, boolean registerItem) {
        if (registerItem) register(id, new BlockItem(block, new Item.Settings().group(itemGroup)));
        return Registry.register(Registry.BLOCK, new Identifier(modId, String.format(id, this.id)), block);
    }

    private Block register(String id, Block block) {
        return register(id, block, true);
    }

    private Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(modId, String.format(id, this.id)), item);
    }

    private Block createSlab(String id, Block block) {
        return register(id, new SlabBlock(FabricBlockSettings.copyOf(block)));
    }

    private Block createStairs(String id, Block block) {
        return register(id, new PublicStairsBlock(block.getDefaultState(), FabricBlockSettings.copyOf(block)));
    }

    private Block createWall(String id, Block block) {
        return register(id, new WallBlock(FabricBlockSettings.copyOf(block)));
    }

    private Block createSaltLamp(String id, Block block) {
        return register(
            id, new Block(
                FabricBlockSettings.copyOf(block)
                                   .strength(0.75F, 3.0F)
                                   .luminance(state -> 15)
            )
        );
    }
}
