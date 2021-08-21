package net.dodogang.marbles.block.helper;

import me.andante.chord.block.vanilla.PublicStairsBlock;
import net.dodogang.marbles.block.SnowySandBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings({"UnusedReturnValue","unused"})
public class SandBlocks {
    private final String id;
    private final String modId;
    private final ItemGroup itemGroup;
    private final MapColor mapColor;
    private final int sandColor;

    public final Block SAND;

    public final Block SANDSTONE;
    public final Block CHISELED_SANDSTONE;
    public final Block CUT_SANDSTONE;
    public final Block SMOOTH_SANDSTONE;

    public final Block SANDSTONE_SLAB;
    public final Block CUT_SANDSTONE_SLAB;
    public final Block SMOOTH_SANDSTONE_SLAB;

    public final Block SANDSTONE_STAIRS;
    public final Block CUT_SANDSTONE_STAIRS;
    public final Block SMOOTH_SANDSTONE_STAIRS;

    public final Block SANDSTONE_WALL;
    public final Block CUT_SANDSTONE_WALL;
    public final Block SMOOTH_SANDSTONE_WALL;

    public SandBlocks(String modId, String id, ItemGroup itemGroup, MapColor mapColor, int sandColor) {
        this.id = id;
        this.modId = modId;
        this.itemGroup = itemGroup;
        this.mapColor = mapColor;
        this.sandColor = sandColor;

        SAND = register("%s_sand", new SnowySandBlock(sandColor, FabricBlockSettings.of(Material.AGGREGATE, mapColor).strength(0.5F).sounds(BlockSoundGroup.SAND)));

        SANDSTONE = register("%s_sandstone", new Block(FabricBlockSettings.of(Material.STONE, mapColor).requiresTool().strength(0.8F)));
        CHISELED_SANDSTONE = copy("chiseled_%s_sandstone", SANDSTONE);
        CUT_SANDSTONE = copy("cut_%s_sandstone", SANDSTONE);
        SMOOTH_SANDSTONE = copy("smooth_%s_sandstone", SANDSTONE);

        SANDSTONE_SLAB = createSlab("%s_sandstone_slab", SANDSTONE);
        CUT_SANDSTONE_SLAB = createSlab("cut_%s_sandstone_slab", CUT_SANDSTONE);
        SMOOTH_SANDSTONE_SLAB = createSlab("smooth_%s_sandstone_slab", SMOOTH_SANDSTONE);

        SANDSTONE_STAIRS = createStairs("%s_sandstone_stairs", SANDSTONE);
        CUT_SANDSTONE_STAIRS = createStairs("cut_%s_sandstone_stairs", CUT_SANDSTONE);
        SMOOTH_SANDSTONE_STAIRS = createStairs("smooth_%s_sandstone_stairs", SMOOTH_SANDSTONE);

        SANDSTONE_WALL = createWall("%s_sandstone_wall", SANDSTONE);
        CUT_SANDSTONE_WALL = createWall("cut_%s_sandstone_wall", CUT_SANDSTONE);
        SMOOTH_SANDSTONE_WALL = createWall("smooth_%s_sandstone_wall", SMOOTH_SANDSTONE);
    }

    public String getId() {
        return this.id;
    }

    public String getModId() {
        return this.modId;
    }

    public ItemGroup getItemGroup() {
        return this.itemGroup;
    }

    public MapColor getMapColor() {
        return this.mapColor;
    }

    public int getSandColor() {
        return this.sandColor;
    }

    // ---

    private Block register(String id, Block block, boolean registerItem) {
        if (registerItem) register(id, new BlockItem(block, new FabricItemSettings().group(itemGroup)));
        return Registry.register(Registry.BLOCK, new Identifier(modId, String.format(id, this.id)), block);
    }

    private Block register(String id, Block block) {
        return register(id, block, true);
    }

    private Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(modId, String.format(id, this.id)), item);
    }

    private Block copy(String id, Block block) {
        return register(id, new Block(FabricBlockSettings.copyOf(block)));
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
}
