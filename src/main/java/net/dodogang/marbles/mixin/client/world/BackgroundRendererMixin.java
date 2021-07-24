package net.dodogang.marbles.mixin.client.world;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dodogang.marbles.client.world.biome.BiomeFog;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.function.Function;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Inject(method = "applyFog", at = @At(value = "HEAD"), cancellable = true)
    private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            World world = client.world;
            ClientPlayerEntity player = client.player;

            if (world != null && player != null && !player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
                Optional<RegistryKey<Biome>> biomeKeyOpt = world.getBiomeKey(player.getBlockPos());
                if (biomeKeyOpt.isPresent()) {
                    Function<ClientPlayerEntity, Float> fogDistanceFunc = BiomeFog.BIOME_TO_FOG_DISTANCE_MAP.get(biomeKeyOpt.get());
                    if (fogDistanceFunc != null) {
                        if (fogType == BackgroundRenderer.FogType.FOG_TERRAIN) {
                            float fogDistance = fogDistanceFunc.apply(player);

                            RenderSystem.setShaderFogStart(fogDistance * 16);
                            RenderSystem.setShaderFogEnd((fogDistance + 1.5f) * 16);
                            ci.cancel();
                        }
                    }
                }
            }
        }
    }
}
