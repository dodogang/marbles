package net.dodogang.marbles.tag;

import net.dodogang.marbles.Marbles;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class MarblesEntityTypeTags {
    public static final Tag.Identified<EntityType<?>> BOUNCER_IGNORED_ENTITIES = register("bouncer_ignored_entities");
    public static final Tag.Identified<EntityType<?>> CROSSED_LATICE_FALL_THROUGHABLES = register("crossed_latice_fall_throughables");

    public static final Tag.Identified<EntityType<?>> FALLING_BLOCK_TWEAKS_TRIGGER_FALL_ENTITIES = register(new Identifier("fallingblocktweaks", "trigger_fall_entities"));

    private static Tag.Identified<EntityType<?>> register(String id) {
        return register(new Identifier(Marbles.MOD_ID, id));
    }
    private static Tag.Identified<EntityType<?>> register(Identifier id) {
        return TagRegistry.create(id, EntityTypeTags::getTagGroup);
    }
}
