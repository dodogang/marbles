package net.dodogang.marbles.mixin.hooks.client;

import net.dodogang.marbles.client.color.world.ColorGenerator;
import net.dodogang.marbles.client.color.world.MarblesBiomeColors;
import net.dodogang.marbles.client.color.world.MarblesColorWorld;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.BiomeColorCache;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(ClientWorld.class)
public class ClientWorldMixin implements MarblesColorWorld {
    private final Map<ColorGenerator, BiomeColorCache> marblesColorCache = Util.make(
        new HashMap<>(),
        map -> MarblesBiomeColors.getColorGenerators()
                                 .forEach(g -> map.put(g, new BiomeColorCache()))
    );

    @Override
    public int marbles_getColor(BlockPos pos, ColorGenerator generator) {
        BiomeColorCache cache = marblesColorCache.get(generator);
        return cache.getBiomeColor(pos, () -> computeColor(pos, generator));
    }

    @Inject(method = "reloadColor", at = @At("HEAD"))
    private void onReloadColor(CallbackInfo info) {
        marblesColorCache.forEach((k, v) -> v.reset());
    }

    @Inject(method = "resetChunkColor", at = @At("HEAD"))
    private void onResetChunkColor(int x, int z, CallbackInfo info) {
        marblesColorCache.forEach((k, v) -> v.reset(x, z));
    }

    protected int computeColor(BlockPos pos, ColorGenerator generator) {
        ClientWorld self = ClientWorld.class.cast(this);
        return generator.getColor(self, pos);
    }
}
