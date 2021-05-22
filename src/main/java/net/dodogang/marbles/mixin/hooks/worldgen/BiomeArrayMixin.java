package net.dodogang.marbles.mixin.hooks.worldgen;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.mixin.MutableBiomeArray;
import net.dodogang.marbles.util.BiomeMath;
import net.dodogang.marbles.util.Util;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeArray;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(BiomeArray.class)
public abstract class BiomeArrayMixin implements MutableBiomeArray {
    @Shadow @Final private Biome[] data;

    @Override
    public void setBiome(int x, int y, int z, RegistryKey<Biome> biome) {
        if (Marbles.SERVER_INSTANCE != null && this.data != null) {
            this.data[BiomeMath.getBiomeIndex(x, y, z)] = Util.getBiomeFromKey(biome);
        }
    }
}
