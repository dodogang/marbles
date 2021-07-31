package net.dodogang.marbles.client.init;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.client.registry.SpriteIdentifierRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MarblesSpriteIdentifiers {
    private static SpriteIdentifier register(SpriteIdentifier sprite) {
        return SpriteIdentifierRegistry.INSTANCE.addIdentifier(sprite);
    }
    private static Identifier id(String id) {
        return new Identifier(Marbles.MOD_ID, id);
    }
}
