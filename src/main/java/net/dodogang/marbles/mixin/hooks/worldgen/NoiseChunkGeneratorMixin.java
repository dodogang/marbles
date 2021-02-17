package net.dodogang.marbles.mixin.hooks.worldgen;

import net.dodogang.marbles.world.gen.level.SaltCaveGenerator;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NoiseChunkGenerator.class)
public abstract class NoiseChunkGeneratorMixin extends ChunkGenerator {
    @Shadow
    @Final
    private long seed;
    private SaltCaveGenerator saltCaveGenerator;

    @SuppressWarnings("unused")
    private NoiseChunkGeneratorMixin(BiomeSource biomeSource, StructuresConfig structuresConfig) {
        super(biomeSource, structuresConfig);
    }

    @SuppressWarnings("unused")
    private NoiseChunkGeneratorMixin(BiomeSource populationSource, BiomeSource biomeSource, StructuresConfig structuresConfig, long worldSeed) {
        super(populationSource, biomeSource, structuresConfig, worldSeed);
    }

    private SaltCaveGenerator getSaltCaveGenerator() {
        if (saltCaveGenerator == null) {
            saltCaveGenerator = new SaltCaveGenerator(seed, this);
        }
        return saltCaveGenerator;
    }

    @Override
    public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
        super.carve(seed, access, chunk, carver);

        if (carver == GenerationStep.Carver.AIR) {
            getSaltCaveGenerator().generateSaltCaves(seed, access, chunk);
        }
    }

    @Override
    public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {
        super.generateFeatures(region, accessor);
        getSaltCaveGenerator().decorate(region, accessor);
    }
}
