package net.dodogang.marbles.datagen.tags;

import me.andante.chord.block.helper.WoodBlocks;
import me.andante.chord.tag.CBlockTags;
import net.dodogang.marbles.block.helper.TravertineBlocks;
import net.dodogang.marbles.datagen.tags.factory.TagFactory;
import net.dodogang.marbles.datagen.tags.factory.TagStore;
import net.dodogang.marbles.init.MarblesEntities;
import net.dodogang.marbles.init.MarblesItems;
import net.dodogang.marbles.tag.MarblesBlockTags;
import net.dodogang.marbles.tag.MarblesEntityTypeTags;
import net.dodogang.marbles.tag.MarblesItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.registry.Registry;

import java.io.IOException;

import static net.dodogang.marbles.init.MarblesBlocks.*;

@SuppressWarnings("unused")
public class MarblesTagProvider implements DataProvider {
    private final TagStore<Block> blocks;
    private final TagStore<Item> items;
    private final TagStore<EntityType<?>> entityTypes;

    public MarblesTagProvider(DataGenerator generator) {
        blocks = new TagStore<>(Registry.BLOCK, generator.getOutput(), "blocks");
        items = new TagStore<>(Registry.ITEM, generator.getOutput(), "items");
        entityTypes = new TagStore<>(Registry.ENTITY_TYPE, generator.getOutput(), "entity_types");
    }

    @Override
    public void run(DataCache cache) throws IOException {
        addWoodSet(ASPEN, MarblesBlockTags.ASPEN_LOGS, MarblesItemTags.ASPEN_LOGS);
        addWoodSet(HOOPSI_SPRUCE, MarblesBlockTags.HOOPSI_SPRUCE_LOGS, MarblesItemTags.HOOPSI_SPRUCE_LOGS);
        addWoodSet(RED_BIRCH, MarblesBlockTags.RED_BIRCH_LOGS, MarblesItemTags.RED_BIRCH_LOGS);

        addTravertineSet(TRAVERTINE_BLOCKS, MarblesBlockTags.TRAVERTINE, MarblesItemTags.TRAVERTINE);
        addTravertineSet(LEMON_TRAVERTINE_BLOCKS, MarblesBlockTags.LEMON_TRAVERTINE, MarblesItemTags.LEMON_TRAVERTINE);
        addTravertineSet(PEACH_TRAVERTINE_BLOCKS, MarblesBlockTags.PEACH_TRAVERTINE, MarblesItemTags.PEACH_TRAVERTINE);
        addTravertineSet(TANGERINE_TRAVERTINE_BLOCKS, MarblesBlockTags.TANGERINE_TRAVERTINE, MarblesItemTags.TANGERINE_TRAVERTINE);

        blocks.factory(MarblesBlockTags.SPECIAL_ROPE_SUPPORTS).add(BlockTags.LEAVES);

        blocks.factory(BlockTags.PORTALS).add(TRAVERTINE_PORTAL);

        blocks.factory(BlockTags.SLABS).add(MarblesBlockTags.TRAVERTINE_SLABS);
        items.factory(ItemTags.SLABS).add(MarblesItemTags.TRAVERTINE_SLABS);
        blocks.factory(BlockTags.STAIRS).add(MarblesBlockTags.TRAVERTINE_STAIRS);
        items.factory(ItemTags.STAIRS).add(MarblesItemTags.TRAVERTINE_STAIRS);
        blocks.factory(BlockTags.WALLS).add(MarblesBlockTags.TRAVERTINE_WALLS);
        items.factory(ItemTags.WALLS).add(MarblesItemTags.TRAVERTINE_WALLS);

        add(
            BlockTags.SLABS, ItemTags.SLABS,
            LAPIS_SHINGLE_SLAB
        );
        add(
            BlockTags.STAIRS, ItemTags.STAIRS,
            LAPIS_SHINGLE_STAIRS
        );
        add(
            BlockTags.SLABS, ItemTags.SLABS,
            UMBRAL_LAZULI_SHINGLE_SLAB
        );
        add(
            BlockTags.STAIRS, ItemTags.STAIRS,
            UMBRAL_LAZULI_SHINGLE_STAIRS
        );

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

        add(
            BlockTags.SLABS, ItemTags.SLABS,
            DAWN_SANDSTONE_SLAB,
            CUT_DAWN_SANDSTONE_SLAB,
            SMOOTH_DAWN_SANDSTONE_SLAB,

            DUSK_SANDSTONE_SLAB,
            CUT_DUSK_SANDSTONE_SLAB,
            SMOOTH_DUSK_SANDSTONE_SLAB,

            PINK_SALT_SLAB,
            PINK_SALT_BRICK_SLAB,

            POLLENATED_COBBLESTONE_SLAB
        );

        add(
            BlockTags.STAIRS, ItemTags.STAIRS,
            DAWN_SANDSTONE_STAIRS,
            CUT_DAWN_SANDSTONE_STAIRS,
            SMOOTH_DAWN_SANDSTONE_STAIRS,

            DUSK_SANDSTONE_STAIRS,
            CUT_DUSK_SANDSTONE_STAIRS,
            SMOOTH_DUSK_SANDSTONE_STAIRS,

            PINK_SALT_STAIRS,
            PINK_SALT_BRICK_STAIRS,

            POLLENATED_COBBLESTONE_STAIRS
        );

        add(
            BlockTags.WALLS, ItemTags.WALLS,
            DAWN_SANDSTONE_WALL,
            CUT_DAWN_SANDSTONE_WALL,
            SMOOTH_DAWN_SANDSTONE_WALL,

            DUSK_SANDSTONE_WALL,
            CUT_DUSK_SANDSTONE_WALL,
            SMOOTH_DUSK_SANDSTONE_WALL
        );

        add(
            BlockTags.CLIMBABLE,
            YELLOW_SCAFFOLDING,
            ROPE
        );

        add(
            BlockTags.BAMBOO_PLANTABLE_ON,
            YELLOW_BAMBOO,
            YELLOW_BAMBOO_SAPLING
        );

        add(
            BlockTags.ENDERMAN_HOLDABLE,
            DAWN_SAND,
            DUSK_SAND
        );

        add(
            BlockTags.CARPETS, ItemTags.CARPETS,
            POLLEN_GRACED_CARPET
        );

        add(
            BlockTags.WOOL, ItemTags.WOOL,
            POLLEN_GRACED_WOOL
        );

        add(
            BlockTags.BEDS, ItemTags.BEDS,
            POLLEN_GRACED_BED
        );

        add(
            CBlockTags.DEAD_BUSH_SUPPORTERS,
            DAWN_SAND,
            DUSK_SAND
        );

        addGrispDirtBlockTag(BlockTags.ENDERMAN_HOLDABLE);
        addGrispDirtBlockTag(CBlockTags.PLANT_SUPPORTERS);
        addGrispDirtBlockTag(CBlockTags.WITHER_ROSE_SUPPORTERS);
        addGrispDirtBlockTag(CBlockTags.FEATURE_SUPPORTERS_SOIL);
        addGrispDirtBlockTag(CBlockTags.DEAD_BUSH_SUPPORTERS);
        addGrispDirtBlockTag(MarblesBlockTags.GRISP_COLOR_SOURCE);
        add(
            BlockTags.MUSHROOM_GROW_BLOCK,
            GRISP_MYCELIUM,
            GRISP_PODZOL
        );
        add(
            GRISP_FARMLAND,
            CBlockTags.CROP_SUPPORTERS,
            CBlockTags.STEM_SUPPORTERS,
            CBlockTags.ATTACHED_STEM_SUPPORTERS
        );

        /*
         * ITEM GROUPS
         */

        // pink salt and travertine
        addTravertineSet(TRAVERTINE_BLOCKS, MarblesItemTags.CHORD_GROUP_PINK_SALT_AND_TRAVERTINE);
        addTravertineSet(LEMON_TRAVERTINE_BLOCKS, MarblesItemTags.CHORD_GROUP_PINK_SALT_AND_TRAVERTINE);
        addTravertineSet(PEACH_TRAVERTINE_BLOCKS, MarblesItemTags.CHORD_GROUP_PINK_SALT_AND_TRAVERTINE);
        addTravertineSet(TANGERINE_TRAVERTINE_BLOCKS, MarblesItemTags.CHORD_GROUP_PINK_SALT_AND_TRAVERTINE);
        add(
            MarblesItemTags.CHORD_GROUP_PINK_SALT_AND_TRAVERTINE,

            TRAVERTINE_OBSIDIAN,

            PINK_SALT,
            CRUMBLED_PINK_SALT,
            PINK_SALT_SLAB,
            PINK_SALT_STAIRS,

            PINK_SALT_BRICKS,
            PINK_SALT_BRICK_SLAB,
            PINK_SALT_BRICK_STAIRS,
            PINK_SALT_PILLAR,

            PINK_SALT_SPIRE,
            PINK_SALT_STACK,
            PINK_SALT_STUMP,
            PINK_SALT_SPIKES,
            MarblesItems.PINK_SALT_SHARD
        );

        // sunset grotto
        addWoodSet(HOOPSI_SPRUCE, MarblesItemTags.CHORD_GROUP_SUNSET_GROTTO);
        add(
            MarblesItemTags.CHORD_GROUP_SUNSET_GROTTO,

            DAWN_SAND,
            DAWN_SANDSTONE,
            CHISELED_DAWN_SANDSTONE,
            CUT_DAWN_SANDSTONE,
            SMOOTH_DAWN_SANDSTONE,
            DAWN_SANDSTONE_SLAB,
            CUT_DAWN_SANDSTONE_SLAB,
            SMOOTH_DAWN_SANDSTONE_SLAB,
            DAWN_SANDSTONE_STAIRS,
            CUT_DAWN_SANDSTONE_STAIRS,
            SMOOTH_DAWN_SANDSTONE_STAIRS,
            DAWN_SANDSTONE_WALL,
            CUT_DAWN_SANDSTONE_WALL,
            SMOOTH_DAWN_SANDSTONE_WALL,

            DUSK_SAND,
            DUSK_SANDSTONE,
            CHISELED_DUSK_SANDSTONE,
            CUT_DUSK_SANDSTONE,
            SMOOTH_DUSK_SANDSTONE,
            DUSK_SANDSTONE_SLAB,
            CUT_DUSK_SANDSTONE_SLAB,
            SMOOTH_DUSK_SANDSTONE_SLAB,
            DUSK_SANDSTONE_STAIRS,
            CUT_DUSK_SANDSTONE_STAIRS,
            SMOOTH_DUSK_SANDSTONE_STAIRS,
            DUSK_SANDSTONE_WALL,
            CUT_DUSK_SANDSTONE_WALL,
            SMOOTH_DUSK_SANDSTONE_WALL,

            LAPIS_SHINGLES,
            LAPIS_SHINGLE_SLAB,
            LAPIS_SHINGLE_STAIRS,
            LAPIS_SPOTLIGHT,
            GLAZED_LAPIS,

            MarblesItems.UMBRAL_LAZULI,
            UMBRAL_LAZULI_ORE,
            UMBRAL_LAZULI_BLOCK,
            UMBRAL_LAZULI_SHINGLES,
            UMBRAL_LAZULI_SHINGLE_SLAB,
            UMBRAL_LAZULI_SHINGLE_STAIRS,
            UMBRAL_LAZULI_SPOTLIGHT,
            GLAZED_UMBRAL_LAZULI,

            SpawnEggItem.forEntity(MarblesEntities.BOUNCER)
        );

        // ice
        add(
            MarblesItemTags.CHORD_GROUP_ICE,

            FLOESTONE,
            POLISHED_FLOESTONE,
            CHISELED_FLOESTONE,
            FLOESTONE_BRICKS,
            RILLED_FLOESTONE,

            SCALED_ICE,
            MINTED_ICE,

            CUT_ICE,
            CUT_BLUE_ICE,
            CUT_SCALED_ICE,
            CUT_MINTED_ICE,

            CHISELED_ICE,
            CHISELED_BLUE_ICE,
            CHISELED_SCALED_ICE,
            CHISELED_MINTED_ICE,

            ICE_BRICKS,
            BLUE_ICE_BRICKS,
            SCALED_ICE_BRICKS,
            MINTED_ICE_BRICKS
        );

        // aspen crevices
        addWoodSet(ASPEN, MarblesItemTags.CHORD_GROUP_ASPEN_CREVICES);
        add(
            MarblesItemTags.CHORD_GROUP_ASPEN_CREVICES,

            GRISP_DIRT,
            GRISP_DIRT_PATH,
            COARSE_GRISP_DIRT,
            GRISP_FARMLAND,
            GRISP_PODZOL,
            GRISP_PODZOL_PATH,
            GRISP_MYCELIUM,
            GRISP_MYCELIUM_PATH,

            SpawnEggItem.forEntity(MarblesEntities.POLLEN_GRACED_SHEEP),
            POLLEN_GRACED_WOOL,
            POLLEN_GRACED_CARPET,
            POLLEN_GRACED_BED,

            POLLENATED_COBBLESTONE,
            POLLENATED_COBBLESTONE_SLAB,
            POLLENATED_COBBLESTONE_STAIRS
        );

        // bamboo valley
        add(
            MarblesItemTags.CHORD_GROUP_BAMBOO_VALLEY,

            YELLOW_BAMBOO,
            YELLOW_SCAFFOLDING
        );

        /*
         * ENTITY TYPES
         */

        add(
            MarblesEntityTypeTags.BOUNCER_IGNORED_ENTITIES,

            EntityType.VILLAGER,
            EntityType.IRON_GOLEM,

            EntityType.WANDERING_TRADER,
            EntityType.TRADER_LLAMA
        );
        add(MarblesEntities.THROWN_ROPE, MarblesEntityTypeTags.FALLING_BLOCK_TWEAKS_TRIGGER_FALL_ENTITIES);


        blocks.write(cache);
        items.write(cache);
        entityTypes.write(cache);
    }

    private void addGrispDirtBlockTag(Tag.Identified<Block> tag) {
        add(
            tag,
            GRISP_DIRT,
            COARSE_GRISP_DIRT,
            GRISP_PODZOL,
            GRISP_MYCELIUM
        );
    }

    private void addGrispDirtItemTag(Tag.Identified<Item> tag) {
        add(
            tag,
            GRISP_DIRT,
            COARSE_GRISP_DIRT,
            GRISP_PODZOL,
            GRISP_MYCELIUM
        );
    }

    private void addTravertineSet(TravertineBlocks set, Tag.Identified<Block> blockTag, Tag.Identified<Item> itemTag) {
        add(
            MarblesBlockTags.RAW_TRAVERTINE, MarblesItemTags.RAW_TRAVERTINE,
            set.RAW
        );

        add(
            blockTag, itemTag,
            set.RAW,
            set.BRICKS,
            set.CAPPED,
            set.POLISHED
        );

        add(
            MarblesBlockTags.TRAVERTINE_BLOCKS, MarblesItemTags.TRAVERTINE_BLOCKS,
            set.RAW,
            set.BRICKS,
            set.CAPPED,
            set.POLISHED
        );

        add(
            MarblesBlockTags.TRAVERTINE_SLABS, MarblesItemTags.TRAVERTINE_SLABS,
            set.SLAB,
            set.BRICK_SLAB,
            set.CAPPED_SLAB,
            set.POLISHED_SLAB
        );

        add(
            MarblesBlockTags.TRAVERTINE_STAIRS, MarblesItemTags.TRAVERTINE_STAIRS,
            set.STAIRS,
            set.BRICK_STAIRS,
            set.CAPPED_STAIRS,
            set.POLISHED_STAIRS
        );

        add(
            MarblesBlockTags.TRAVERTINE_WALLS, MarblesItemTags.TRAVERTINE_WALLS,
            set.WALL,
            set.BRICK_WALL,
            set.CAPPED_WALL,
            set.POLISHED_WALL
        );

        add(
            MarblesBlockTags.TRAVERTINE_LAMPS, MarblesItemTags.TRAVERTINE_LAMPS,
            set.SALT_LAMP
        );
    }
    private void addTravertineSet(TravertineBlocks set, Tag.Identified<Item> itemTag) {
        add(
           itemTag,
            set.RAW,
            set.BRICKS,
            set.CAPPED,
            set.POLISHED,
            set.SLAB,
            set.BRICK_SLAB,
            set.CAPPED_SLAB,
            set.POLISHED_SLAB,
            set.STAIRS,
            set.BRICK_STAIRS,
            set.CAPPED_STAIRS,
            set.POLISHED_STAIRS,
            set.WALL,
            set.BRICK_WALL,
            set.CAPPED_WALL,
            set.POLISHED_WALL,
            set.SALT_LAMP
        );
    }

    private void addWoodSet(WoodBlocks set, Tag.Identified<Block> logsBlockTag, Tag.Identified<Item> logsItemTag) {
        add(BlockTags.PLANKS, ItemTags.PLANKS, set.PLANKS);
        add(BlockTags.SAPLINGS, ItemTags.SAPLINGS, set.SAPLING);
        add(BlockTags.FLOWER_POTS, set.POTTED_SAPLING);
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
    private void addWoodSet(WoodBlocks set, Tag.Identified<Item> itemTag) {
        add(
            itemTag,
             set.PLANKS,
             set.SAPLING,
             set.LOG,
             set.STRIPPED_LOG,
             set.STRIPPED_WOOD,
             set.WOOD,
             set.LEAVES,
             set.SLAB,
             set.PRESSURE_PLATE,
             set.FENCE,
             set.TRAPDOOR,
             set.FENCE_GATE,
             set.STAIRS,
             set.BUTTON,
             set.DOOR,
             set.SIGN,
             set.BOAT_ITEM
        );
    }

    @SafeVarargs
    private void add(Block block, Tag<Block>... tags) {
        for (Tag<Block> tag : tags) {
            add(tag, block);
        }
    }

    @SafeVarargs
    private void add(ItemConvertible item, Tag<Item>... tags) {
        for (Tag<Item> tag : tags) {
            add(tag, item);
        }
    }

    @SafeVarargs
    private void add(EntityType<?> entityType, Tag<EntityType<?>>... tags) {
        for (Tag<EntityType<?>> tag : tags) {
            add(tag, entityType);
        }
    }

    private void add(Tag<Block> blockTag, Block... vals) {
        TagFactory<Block> factory = blocks.factory(blockTag);
        for (Block val : vals) {
            factory.add(val);
        }
    }

    private void add(Tag<Item> itemTag, ItemConvertible... vals) {
        TagFactory<Item> factory = items.factory(itemTag);
        for (ItemConvertible val : vals) {
            factory.add(val.asItem());
        }
    }

    private void add(Tag<EntityType<?>> entityTypeTag, EntityType<?>... vals) {
        TagFactory<EntityType<?>> factory = entityTypes.factory(entityTypeTag);
        for (EntityType<?> val : vals) {
            factory.add(val);
        }
    }

    private void add(Tag<Block> blockTag, Tag<Item> itemTag, Block... vals) {
        TagFactory<Block> blockFac = blocks.factory(blockTag);
        TagFactory<Item> itemFac = items.factory(itemTag);
        for (Block val : vals) {
            blockFac.add(val);
            itemFac.add(val.asItem());
        }
    }

    @Override
    public String getName() {
        return "MarblesTags";
    }
}
