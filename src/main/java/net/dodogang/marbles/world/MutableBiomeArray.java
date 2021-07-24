package net.dodogang.marbles.world;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public interface MutableBiomeArray {
    void setBiome(int x, int y, int z, RegistryKey<Biome> biome);
}
