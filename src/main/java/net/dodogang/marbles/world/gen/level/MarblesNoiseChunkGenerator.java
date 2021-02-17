package net.dodogang.marbles.world.gen.level;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

import java.util.function.Supplier;

public class MarblesNoiseChunkGenerator extends NoiseChunkGenerator {
    public static final Codec<MarblesNoiseChunkGenerator> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            BiomeSource.CODEC.fieldOf("biome_source")
                             .forGetter(gen -> gen.populationSource),
            Codec.LONG.fieldOf("seed")
                      .stable()
                      .forGetter(gen -> gen.seed),
            ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings")
                                                 .forGetter(gen -> gen.settings)
        ).apply(instance, instance.stable(MarblesNoiseChunkGenerator::new))
    );


    private final SaltCaveGenerator saltCaveGenerator;
    private final long seed;

    public MarblesNoiseChunkGenerator(BiomeSource biomeSource, long seed, Supplier<ChunkGeneratorSettings> settings) {
        this(biomeSource, biomeSource, seed, settings);
    }

    protected MarblesNoiseChunkGenerator(BiomeSource populationSource, BiomeSource biomeSource, long seed, Supplier<ChunkGeneratorSettings> settings) {
        super(populationSource, biomeSource, seed, settings);
        this.seed = seed;
        this.saltCaveGenerator = new SaltCaveGenerator(seed, this);
    }


    @Override
    @Environment(EnvType.CLIENT)
    public ChunkGenerator withSeed(long seed) {
        return new MarblesNoiseChunkGenerator(populationSource.withSeed(seed), seed, settings);
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
        super.carve(seed, access, chunk, carver);

        if (carver == GenerationStep.Carver.AIR) {
            saltCaveGenerator.generateSaltCaves(seed, access, chunk);
        }
    }

    @Override
    public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {
        super.generateFeatures(region, accessor);
        saltCaveGenerator.decorate(region, accessor);
    }
}
