package net.dodogang.marbles.mixin.hooks.worldgen;

import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkGeneratorSettings.class)
public interface ChunkGenSettingsAccessor {
    @Accessor
    boolean isMobGenerationDisabled();
}
