package net.dodogang.marbles.data.tags;

import me.andante.chord.block.helper.WoodBlocks;
import net.dodogang.marbles.data.tags.factory.TagFactory;
import net.dodogang.marbles.data.tags.factory.TagStore;
import net.dodogang.marbles.tag.MarblesBlockTags;
import net.dodogang.marbles.tag.MarblesItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.registry.Registry;

import java.io.IOException;

import static net.dodogang.marbles.init.MarblesBlocks.*;

public class MarblesBlockItemTagProvider implements DataProvider {
    private final TagStore<Block> blocks;
    private final TagStore<Item> items;

    public MarblesBlockItemTagProvider(DataGenerator generator) {
        blocks = new TagStore<>(Registry.BLOCK, generator.getOutput(), "blocks");
        items = new TagStore<>(Registry.ITEM, generator.getOutput(), "items");
    }

    @Override
    public void run(DataCache cache) throws IOException {
        addWoodSet(ASPEN, MarblesBlockTags.ASPEN_LOGS, MarblesItemTags.ASPEN_LOGS);
        addWoodSet(HOOPSI_SPRUCE, MarblesBlockTags.HOOPSI_SPRUCE_LOGS, MarblesItemTags.HOOPSI_SPRUCE_LOGS);



        add(MarblesBlockTags.RAW_TRAVERTINE, MarblesItemTags.RAW_TRAVERTINE,
            TRAVERTINE,
            LEMON_TRAVERTINE,
            PEACH_TRAVERTINE,
            TANGERINE_TRAVERTINE
        );

        add(MarblesBlockTags.TRAVERTINE, MarblesItemTags.TRAVERTINE,
            TRAVERTINE,
            TRAVERTINE_BRICKS,
            CAPPED_TRAVERTINE,
            POLISHED_TRAVERTINE
        );

        add(MarblesBlockTags.LEMON_TRAVERTINE, MarblesItemTags.LEMON_TRAVERTINE,
            LEMON_TRAVERTINE,
            LEMON_TRAVERTINE_BRICKS,
            CAPPED_LEMON_TRAVERTINE,
            POLISHED_LEMON_TRAVERTINE
        );

        add(MarblesBlockTags.PEACH_TRAVERTINE, MarblesItemTags.PEACH_TRAVERTINE,
            PEACH_TRAVERTINE,
            PEACH_TRAVERTINE_BRICKS,
            CAPPED_PEACH_TRAVERTINE,
            POLISHED_PEACH_TRAVERTINE
        );

        add(MarblesBlockTags.TANGERINE_TRAVERTINE, MarblesItemTags.TANGERINE_TRAVERTINE,
            TANGERINE_TRAVERTINE,
            TANGERINE_TRAVERTINE_BRICKS,
            CAPPED_TANGERINE_TRAVERTINE,
            POLISHED_TANGERINE_TRAVERTINE
        );

        add(MarblesBlockTags.TRAVERTINE_BLOCKS, MarblesItemTags.TRAVERTINE_BLOCKS,
            TRAVERTINE,
            TRAVERTINE_BRICKS,
            CAPPED_TRAVERTINE,
            POLISHED_TRAVERTINE,
            LEMON_TRAVERTINE,
            LEMON_TRAVERTINE_BRICKS,
            CAPPED_LEMON_TRAVERTINE,
            POLISHED_LEMON_TRAVERTINE,
            PEACH_TRAVERTINE,
            PEACH_TRAVERTINE_BRICKS,
            CAPPED_PEACH_TRAVERTINE,
            POLISHED_PEACH_TRAVERTINE,
            TANGERINE_TRAVERTINE,
            TANGERINE_TRAVERTINE_BRICKS,
            CAPPED_TANGERINE_TRAVERTINE,
            POLISHED_TANGERINE_TRAVERTINE
        );

        add(MarblesBlockTags.TRAVERTINE_SLABS, MarblesItemTags.TRAVERTINE_SLABS,
            TRAVERTINE_SLAB,
            TRAVERTINE_BRICK_SLAB,
            CAPPED_TRAVERTINE_SLAB,
            POLISHED_TRAVERTINE_SLAB,
            LEMON_TRAVERTINE_SLAB,
            LEMON_TRAVERTINE_BRICK_SLAB,
            CAPPED_LEMON_TRAVERTINE_SLAB,
            POLISHED_LEMON_TRAVERTINE_SLAB,
            PEACH_TRAVERTINE_SLAB,
            PEACH_TRAVERTINE_BRICK_SLAB,
            CAPPED_PEACH_TRAVERTINE_SLAB,
            POLISHED_PEACH_TRAVERTINE_SLAB,
            TANGERINE_TRAVERTINE_SLAB,
            TANGERINE_TRAVERTINE_BRICK_SLAB,
            CAPPED_TANGERINE_TRAVERTINE_SLAB,
            POLISHED_TANGERINE_TRAVERTINE_SLAB
        );
        blocks.factory(BlockTags.SLABS).add(MarblesBlockTags.TRAVERTINE_SLABS);
        items.factory(ItemTags.SLABS).add(MarblesItemTags.TRAVERTINE_SLABS);

        add(MarblesBlockTags.TRAVERTINE_STAIRS, MarblesItemTags.TRAVERTINE_STAIRS,
            TRAVERTINE_STAIRS,
            TRAVERTINE_BRICK_STAIRS,
            CAPPED_TRAVERTINE_STAIRS,
            POLISHED_TRAVERTINE_STAIRS,
            LEMON_TRAVERTINE_STAIRS,
            LEMON_TRAVERTINE_BRICK_STAIRS,
            CAPPED_LEMON_TRAVERTINE_STAIRS,
            POLISHED_LEMON_TRAVERTINE_STAIRS,
            PEACH_TRAVERTINE_STAIRS,
            PEACH_TRAVERTINE_BRICK_STAIRS,
            CAPPED_PEACH_TRAVERTINE_STAIRS,
            POLISHED_PEACH_TRAVERTINE_STAIRS,
            TANGERINE_TRAVERTINE_STAIRS,
            TANGERINE_TRAVERTINE_BRICK_STAIRS,
            CAPPED_TANGERINE_TRAVERTINE_STAIRS,
            POLISHED_TANGERINE_TRAVERTINE_STAIRS
        );
        blocks.factory(BlockTags.STAIRS).add(MarblesBlockTags.TRAVERTINE_STAIRS);
        items.factory(ItemTags.STAIRS).add(MarblesItemTags.TRAVERTINE_STAIRS);

        add(MarblesBlockTags.TRAVERTINE_WALLS, MarblesItemTags.TRAVERTINE_WALLS,
            TRAVERTINE_WALL,
            TRAVERTINE_BRICK_WALL,
            CAPPED_TRAVERTINE_WALL,
            POLISHED_TRAVERTINE_WALL,
            LEMON_TRAVERTINE_WALL,
            LEMON_TRAVERTINE_BRICK_WALL,
            CAPPED_LEMON_TRAVERTINE_WALL,
            POLISHED_LEMON_TRAVERTINE_WALL,
            PEACH_TRAVERTINE_WALL,
            PEACH_TRAVERTINE_BRICK_WALL,
            CAPPED_PEACH_TRAVERTINE_WALL,
            POLISHED_PEACH_TRAVERTINE_WALL,
            TANGERINE_TRAVERTINE_WALL,
            TANGERINE_TRAVERTINE_BRICK_WALL,
            CAPPED_TANGERINE_TRAVERTINE_WALL,
            POLISHED_TANGERINE_TRAVERTINE_WALL
        );
        blocks.factory(BlockTags.WALLS).add(MarblesBlockTags.TRAVERTINE_WALLS);
        items.factory(ItemTags.WALLS).add(MarblesItemTags.TRAVERTINE_WALLS);

        add(MarblesBlockTags.TRAVERTINE_LAMPS, MarblesItemTags.TRAVERTINE_LAMPS,
            TRAVERTINE_SALT_LAMP,
            LEMON_TRAVERTINE_SALT_LAMP,
            PEACH_TRAVERTINE_SALT_LAMP,
            TANGERINE_TRAVERTINE_SALT_LAMP
        );

        add(BlockTags.SLABS, ItemTags.SLABS, LAPIS_SHINGLES_SLAB);
        add(BlockTags.STAIRS, ItemTags.STAIRS, LAPIS_SHINGLES_STAIRS);

        add(
            MarblesItemTags.HIGH_LIGHT_BLOCKS,
            Blocks.GLOWSTONE,
            Blocks.SEA_LANTERN,
            Blocks.TORCH,
            Blocks.SHROOMLIGHT,
            Blocks.LANTERN,
            Blocks.END_ROD,
            Blocks.BEACON,
            Blocks.JACK_O_LANTERN
        );

        add(BlockTags.SLABS, ItemTags.SLABS,
            DAWN_SANDSTONE_SLAB,
            CUT_DAWN_SANDSTONE_SLAB,
            SMOOTH_DAWN_SANDSTONE_SLAB,

            DUSK_SANDSTONE_SLAB,
            CUT_DUSK_SANDSTONE_SLAB,
            SMOOTH_DUSK_SANDSTONE_SLAB
        );

        add(BlockTags.STAIRS, ItemTags.STAIRS,
            DAWN_SANDSTONE_STAIRS,
            CUT_DAWN_SANDSTONE_STAIRS,
            SMOOTH_DAWN_SANDSTONE_STAIRS,

            DUSK_SANDSTONE_STAIRS,
            CUT_DUSK_SANDSTONE_STAIRS,
            SMOOTH_DUSK_SANDSTONE_STAIRS
        );

        add(BlockTags.WALLS, ItemTags.WALLS,
            DAWN_SANDSTONE_WALL,
            CUT_DAWN_SANDSTONE_WALL,
            SMOOTH_DAWN_SANDSTONE_WALL,

            DUSK_SANDSTONE_WALL,
            CUT_DUSK_SANDSTONE_WALL,
            SMOOTH_DUSK_SANDSTONE_WALL
        );

        add(BlockTags.CLIMBABLE, YELLOW_SCAFFOLDING);
        add(BlockTags.BAMBOO_PLANTABLE_ON, YELLOW_BAMBOO, YELLOW_BAMBOO_SAPLING);


        blocks.write(cache);
        items.write(cache);
    }

    public void addWoodSet(WoodBlocks set, Tag.Identified<Block> logsBlockTag, Tag.Identified<Item> logsItemTag) {
        add(BlockTags.PLANKS, ItemTags.PLANKS, set.PLANKS);
        add(BlockTags.SAPLINGS, ItemTags.SAPLINGS, set.SAPLING);
        add(logsBlockTag, logsItemTag, set.LOG, set.STRIPPED_LOG, set.WOOD, set.STRIPPED_WOOD);
        if (set.isFlammable()) {
            blocks.factory(BlockTags.LOGS_THAT_BURN).add(logsBlockTag);
            items.factory(ItemTags.LOGS_THAT_BURN).add(logsItemTag);
        } else {
            blocks.factory(BlockTags.LOGS).add(logsBlockTag);
            items.factory(ItemTags.LOGS).add(logsItemTag);
            add(
                BlockTags.NON_FLAMMABLE_WOOD, ItemTags.NON_FLAMMABLE_WOOD,
                set.LOG, set.STRIPPED_LOG, set.WOOD, set.STRIPPED_WOOD
            );
        }
        add(BlockTags.LEAVES, ItemTags.LEAVES, set.LEAVES);
        add(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS, set.SLAB);
        add(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES, set.PRESSURE_PLATE);
        add(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES, set.FENCE);
        add(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS, set.TRAPDOOR);
        add(BlockTags.FENCE_GATES, set.FENCE_GATE);
        add(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS, set.STAIRS);
        add(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS, set.BUTTON);
        add(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS, set.DOOR);
        add(BlockTags.STANDING_SIGNS, set.SIGN);
        add(BlockTags.WALL_SIGNS, set.WALL_SIGN);
        add(ItemTags.SIGNS, set.SIGN_ITEM);
        add(ItemTags.BOATS, set.BOAT_ITEM);
    }


    private void add(Tag.Identified<Block> blockTag, Block... vals) {
        TagFactory<Block> factory = blocks.factory(blockTag);
        for (Block val : vals) {
            factory.add(val);
        }
    }

    private void add(Tag.Identified<Item> itemTag, ItemConvertible... vals) {
        TagFactory<Item> factory = items.factory(itemTag);
        for (ItemConvertible val : vals) {
            factory.add(val.asItem());
        }
    }

    private void add(Tag.Identified<Block> blockTag, Tag.Identified<Item> itemTag, Block... vals) {
        TagFactory<Block> blockFac = blocks.factory(blockTag);
        TagFactory<Item> itemFac = items.factory(itemTag);
        for (Block val : vals) {
            blockFac.add(val);
            itemFac.add(val.asItem());
        }
    }

    @Override
    public String getName() {
        return "MarblesBlockItemTags";
    }
}
