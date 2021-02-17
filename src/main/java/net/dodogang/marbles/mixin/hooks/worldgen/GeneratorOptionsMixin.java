package net.dodogang.marbles.mixin.hooks.worldgen;

import net.dodogang.marbles.world.gen.level.MarblesNoiseChunkGenerator;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSource;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GeneratorOptions.class)
public class GeneratorOptionsMixin {

    private static MarblesNoiseChunkGenerator createMarblesGenerator(Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> settingsRegistry, long seed) {
        return new MarblesNoiseChunkGenerator(new VanillaLayeredBiomeSource(seed, false, false, biomeRegistry), seed, () -> settingsRegistry.getOrThrow(ChunkGeneratorSettings.OVERWORLD));
    }

    @Inject(method = "createOverworldGenerator", at = @At(value = "HEAD"), cancellable = true)
    private static void onCreateOverworldGenerator(Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> settingsRegistry, long seed, CallbackInfoReturnable<NoiseChunkGenerator> info) {
        info.setReturnValue(createMarblesGenerator(biomeRegistry, settingsRegistry, seed));
    }
}
