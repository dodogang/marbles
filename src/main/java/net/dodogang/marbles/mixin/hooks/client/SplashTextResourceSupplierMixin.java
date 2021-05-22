package net.dodogang.marbles.mixin.hooks.client;

import net.dodogang.marbles.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Environment(EnvType.CLIENT)
@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierMixin {
    @Shadow @Final private static Random RANDOM;

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void appendSplashes(CallbackInfoReturnable<String> cir) {
        if (RANDOM.nextFloat() <= 0.156f) {
            cir.setReturnValue(Util.SPLASHES.get(RANDOM.nextInt(Util.SPLASHES.size())));
        }
    }
}
