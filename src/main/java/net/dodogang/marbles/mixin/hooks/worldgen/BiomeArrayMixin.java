package net.dodogang.marbles.mixin.hooks.worldgen;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.mixin.MutableBiomeArray;
import net.dodogang.marbles.util.BiomeMath;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeArray;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BiomeArray.class)
public abstract class BiomeArrayMixin implements MutableBiomeArray {
    @Shadow @Final private Biome[] data;

    /**
     * TODO sync on servers :(
     */
    @Override
    public void setBiome(int x, int y, int z, RegistryKey<Biome> biome) {
        if (Marbles.SERVER_INSTANCE != null) {
            this.data[BiomeMath.getBiomeIndex(x, y, z)] = Marbles.SERVER_INSTANCE.getRegistryManager().get(Registry.BIOME_KEY).get(biome);
        }
    }
}
