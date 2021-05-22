package net.dodogang.marbles.world.gen.chunk.generator;

import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

@SuppressWarnings("unused")
public abstract class MarblesChunkGenerator {
    protected final long seed;
    protected final ChunkRandom random;
    protected final ChunkGenerator generator;

    /**
     * @param seed      The world's seed.
     * @param generator The active {@link ChunkGenerator}.
     */
    protected MarblesChunkGenerator(long seed, ChunkGenerator generator) {
        this.seed = seed;
        this.random = new ChunkRandom(this.seed);
        this.generator = generator;
    }

    public abstract void carve(long seed, BiomeAccess biomes, Chunk chunk, GenerationStep.Carver carver);
    public abstract void decorate(ChunkRegion region, StructureAccessor accessor);
}
