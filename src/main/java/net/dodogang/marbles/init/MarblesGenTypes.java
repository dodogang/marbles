package net.dodogang.marbles.init;

import net.dodogang.marbles.mixin.hooks.worldgen.GeneratorTypeAccessor;
import net.dodogang.marbles.world.gen.level.MarblesChunkGenerator;
import net.minecraft.client.world.GeneratorType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

public class MarblesGenTypes {
    public static final GeneratorType MARBLES = new GeneratorType("marbles") {
        @Override
        protected ChunkGenerator getChunkGenerator(Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> settingsRegistry, long seed) {
            return new MarblesChunkGenerator(
                new VanillaLayeredBiomeSource(seed, false, false, biomeRegistry),
                seed,
                () -> settingsRegistry.getOrThrow(ChunkGeneratorSettings.OVERWORLD)
            );
        }
    };

    static {
        GeneratorTypeAccessor.getValues().add(MARBLES);
    }
}
