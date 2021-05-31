package net.dodogang.marbles.mixin.hooks.worldgen;

import net.dodogang.marbles.init.MarblesRegistries;
import net.dodogang.marbles.world.gen.chunk.generator.BridgedCaveBiomeGenerator;
import net.dodogang.marbles.world.gen.chunk.generator.MarblesChunkGenerator;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
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
import java.util.Random;

@Mixin(NoiseChunkGenerator.class)
public abstract class NoiseChunkGeneratorMixin extends ChunkGenerator {
    @Shadow @Final private long seed;

    @Shadow @Final protected ChunkRandom random;
    private static Random marbles_random = null;
    private static List<MarblesChunkGenerator> marbles_chunkGenerators = null;

    private NoiseChunkGeneratorMixin(BiomeSource biomeSource, StructuresConfig config) {
        super(biomeSource, config);
    }

    private MarblesChunkGenerator marbles_getGenerator() {
        if (marbles_chunkGenerators == null) {
            marbles_chunkGenerators = new ArrayList<>();
            MarblesRegistries.BRIDGED_CAVE_BIOME_GENERATOR_CONFIG.forEach(config -> marbles_chunkGenerators.add(new BridgedCaveBiomeGenerator(config, this.seed, this)));
        }

        if (marbles_random == null) {
            marbles_random = new Random(this.seed);
        }

        return marbles_chunkGenerators.get(random.nextInt(marbles_chunkGenerators.size()));
    }

    @Override
    public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
        super.carve(seed, access, chunk, carver);

        if (carver == GenerationStep.Carver.LIQUID) {
            marbles_getGenerator().carve(seed, access, chunk, carver);
        }
    }

    @Override
    public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {
        super.generateFeatures(region, accessor);
        marbles_getGenerator().decorate(region, accessor);
    }
}
