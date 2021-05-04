package net.dodogang.marbles.tag;

import net.dodogang.marbles.Marbles;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class MarblesEntityTypeTags {
    public static final Tag.Identified<EntityType<?>> BOUNCER_IGNORED_ENTITIES = register("bouncer_ignored_entities");

    private static Tag.Identified<EntityType<?>> register(String id) {
        return TagRegistry.create(new Identifier(Marbles.MOD_ID, id), EntityTypeTags::getTagGroup);
    }
}
