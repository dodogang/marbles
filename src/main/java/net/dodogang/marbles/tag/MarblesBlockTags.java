package net.dodogang.marbles.tag;

import net.dodogang.marbles.Marbles;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class MarblesBlockTags {
    public static final Tag<Block> RAW_TRAVERTINE = register("raw_travertine");

    private static Tag<Block> register(String id) {
        return TagRegistry.block(new Identifier(Marbles.MOD_ID, id));
    }
}
