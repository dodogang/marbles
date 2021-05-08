package net.dodogang.marbles.mixin.hooks.worldgen;

import net.dodogang.marbles.world.gen.level.pink_salt_cave.PinkSaltCaveGenerator;
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
    @Shadow @Final private long seed;
    private PinkSaltCaveGenerator marbles_pinkSaltCaveGenerator;

    private NoiseChunkGeneratorMixin(BiomeSource biomeSource, StructuresConfig structuresConfig) {
        super(biomeSource, structuresConfig);
    }

    private PinkSaltCaveGenerator marbles_getPinkSaltCaveGenerator() {
        if (marbles_pinkSaltCaveGenerator == null) {
            marbles_pinkSaltCaveGenerator = new PinkSaltCaveGenerator(seed, this);
        }
        return marbles_pinkSaltCaveGenerator;
    }

    @Override
    public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
        super.carve(seed, access, chunk, carver);

        if (carver == GenerationStep.Carver.LIQUID) {
            marbles_getPinkSaltCaveGenerator().generate(seed, access, chunk);
        }
    }

    @Override
    public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {
        super.generateFeatures(region, accessor);
        marbles_getPinkSaltCaveGenerator().decorate(region, accessor);
    }
}
