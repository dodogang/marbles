package net.dodogang.marbles.mixin.client.render;

import net.dodogang.marbles.client.registry.SpriteIdentifierRegistry;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(TexturedRenderLayers.class)
public class TexturedRenderLayersMixin {
    @Inject(method = "addDefaultTextures", at = @At("HEAD"))
    private static void injectCustomTextures(Consumer<SpriteIdentifier> consumer, CallbackInfo info) {
        for (SpriteIdentifier identifier : SpriteIdentifierRegistry.INSTANCE.getIdentifiers()) {
            consumer.accept(identifier);
        }
    }
}
