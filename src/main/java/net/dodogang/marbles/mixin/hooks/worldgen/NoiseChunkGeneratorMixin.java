package net.dodogang.marbles.mixin.hooks.worldgen;

import net.dodogang.marbles.world.gen.level.MarblesChunkGenerator;
import net.dodogang.marbles.world.gen.level.chunk.generator.PinkSaltCaveGenerator;
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

import java.util.ArrayList;
import java.util.List;

@Mixin(NoiseChunkGenerator.class)
public abstract class NoiseChunkGeneratorMixin extends ChunkGenerator {
    @Shadow @Final private long seed;
    private List<MarblesChunkGenerator> marbles_chunkGenerators = null;

    private NoiseChunkGeneratorMixin(BiomeSource biomeSource, StructuresConfig config) {
        super(biomeSource, config);
    }

    private List<MarblesChunkGenerator> marbles_getChunkGenerators() {
        if (marbles_chunkGenerators == null) {
            marbles_chunkGenerators = new ArrayList<>();

            /*
             * Chunk generator 'registry'.
             */

            marbles_chunkGenerators.add(new PinkSaltCaveGenerator(this.seed, this, 1));
        }

        return marbles_chunkGenerators;
    }

    @Override
    public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
        super.carve(seed, access, chunk, carver);

        if (carver == GenerationStep.Carver.LIQUID) {
            marbles_getChunkGenerators().forEach(generator -> generator.carve(seed, access, chunk));
        }
    }

    @Override
    public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {
        super.generateFeatures(region, accessor);
        marbles_getChunkGenerators().forEach(generator -> generator.decorate(region, accessor));
    }
}
