package net.dodogang.marbles.mixin.hooks.worldgen;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.HorizontalVoronoiBiomeAccessType;
import net.minecraft.world.biome.source.VoronoiBiomeAccessType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HorizontalVoronoiBiomeAccessType.class)
public class HorizontalVoronoiBiomeAccessTypeMixin {
    /**
     * Allows for 3D biomes in any dimension.
     * Very almost an @Overwrite but let's pretend it's not.
     */
    @Inject(method = "getBiome", at = @At("HEAD"), cancellable = true)
    private void amendY(long seed, int x, int y, int z, BiomeAccess.Storage storage, CallbackInfoReturnable<Biome> cir) {
        cir.setReturnValue(VoronoiBiomeAccessType.INSTANCE.getBiome(seed, x, y, z, storage));
    }
}
