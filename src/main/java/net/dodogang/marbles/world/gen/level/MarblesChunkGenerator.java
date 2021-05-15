package net.dodogang.marbles.world.gen.level;

import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public abstract class MarblesChunkGenerator {
    protected final long seed;
    protected final ChunkRandom random;
    protected final ChunkGenerator generator;

    /**
     * @param seed      The world's seed.
     * @param generator The active {@link ChunkGenerator}.
     * @param index     The unique numerical ID of this generator. Changing this will change the seed, thus any local {@link ChunkRandom} random usage.
     */
    protected MarblesChunkGenerator(long seed, ChunkGenerator generator, int index) {
        this.seed = seed + index;
        this.random = new ChunkRandom(this.seed);
        this.generator = generator;
    }

    public abstract void carve(long seed, BiomeAccess biomes, Chunk chunk);
    public abstract void decorate(ChunkRegion region, StructureAccessor accessor);
}
