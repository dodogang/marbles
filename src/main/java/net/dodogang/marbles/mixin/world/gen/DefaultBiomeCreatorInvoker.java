package net.dodogang.marbles.mixin.world.gen;

import net.minecraft.world.biome.DefaultBiomeCreator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@SuppressWarnings("unused")
@Mixin(DefaultBiomeCreator.class)
public interface DefaultBiomeCreatorInvoker {
    @Invoker("getSkyColor")
    static int invoke_getSkyColor(float temperature) {
        throw new AssertionError();
    }
}
