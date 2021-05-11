package net.dodogang.marbles.tag;

import net.dodogang.marbles.Marbles;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class MarblesItemTags {
    public static final Tag.Identified<Item> ASPEN_LOGS = register("aspen_logs");
    public static final Tag.Identified<Item> HOOPSI_SPRUCE_LOGS = register("hoopsi_spruce_logs");
    public static final Tag.Identified<Item> RED_BIRCH_LOGS = register("red_birch_logs");

    public static final Tag.Identified<Item> RAW_TRAVERTINE = register("raw_travertine");
    public static final Tag.Identified<Item> TRAVERTINE = register("travertine");
    public static final Tag.Identified<Item> LEMON_TRAVERTINE = register("lemon_travertine");
    public static final Tag.Identified<Item> PEACH_TRAVERTINE = register("peach_travertine");
    public static final Tag.Identified<Item> TANGERINE_TRAVERTINE = register("tangerine_travertine");
    public static final Tag.Identified<Item> TRAVERTINE_BLOCKS = register("travertine_blocks");
    public static final Tag.Identified<Item> TRAVERTINE_SLABS = register("travertine_slabs");
    public static final Tag.Identified<Item> TRAVERTINE_STAIRS = register("travertine_stairs");
    public static final Tag.Identified<Item> TRAVERTINE_WALLS = register("travertine_walls");
    public static final Tag.Identified<Item> TRAVERTINE_LAMPS = register("travertine_lamps");

    public static final Tag.Identified<Item> HIGH_LIGHT_BLOCKS = register("high_light_blocks");

    private static Tag.Identified<Item> register(String id) {
        return TagRegistry.create(new Identifier(Marbles.MOD_ID, id), ItemTags::getTagGroup);
    }
}
