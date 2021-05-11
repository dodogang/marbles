package net.dodogang.marbles.tag;

import net.dodogang.marbles.Marbles;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class MarblesBlockTags {
    public static final Tag.Identified<Block> ASPEN_LOGS = register("aspen_logs");
    public static final Tag.Identified<Block> HOOPSI_SPRUCE_LOGS = register("hoopsi_spruce_logs");
    public static final Tag.Identified<Block> RED_BIRCH_LOGS = register("red_birch_logs");

    public static final Tag.Identified<Block> RAW_TRAVERTINE = register("raw_travertine");
    public static final Tag.Identified<Block> TRAVERTINE = register("travertine");
    public static final Tag.Identified<Block> LEMON_TRAVERTINE = register("lemon_travertine");
    public static final Tag.Identified<Block> PEACH_TRAVERTINE = register("peach_travertine");
    public static final Tag.Identified<Block> TANGERINE_TRAVERTINE = register("tangerine_travertine");
    public static final Tag.Identified<Block> TRAVERTINE_BLOCKS = register("travertine_blocks");
    public static final Tag.Identified<Block> TRAVERTINE_SLABS = register("travertine_slabs");
    public static final Tag.Identified<Block> TRAVERTINE_STAIRS = register("travertine_stairs");
    public static final Tag.Identified<Block> TRAVERTINE_WALLS = register("travertine_walls");
    public static final Tag.Identified<Block> TRAVERTINE_LAMPS = register("travertine_lamps");

    public static final Tag.Identified<Block> GRISP_COLOR_SOURCE = register("grisp_color_source");
    public static final Tag.Identified<Block> SPECIAL_ROPE_SUPPORTS = register("special_rope_supports");

    private static Tag.Identified<Block> register(String id) {
        return TagRegistry.create(new Identifier(Marbles.MOD_ID, id), BlockTags::getTagGroup);
    }
}
