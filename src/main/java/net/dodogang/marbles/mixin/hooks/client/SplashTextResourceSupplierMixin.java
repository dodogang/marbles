package net.dodogang.marbles.mixin.hooks.client;

import net.dodogang.marbles.client.config.MarblesConfig;
import net.dodogang.marbles.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Environment(EnvType.CLIENT)
@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierMixin {
    @Shadow @Final private static Random RANDOM;
    @Shadow @Final private List<String> splashTexts;

    @Inject(method = "apply", at = @At("TAIL"))
    private void appendSplashTexts(List<String> list, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        this.splashTexts.addAll(Util.SPLASH_TEXTS);
    }

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void replaceSplashTexts(CallbackInfoReturnable<String> cir) {
        if (MarblesConfig.Graphics.onlyMarblesSplashTexts.value.getAsBoolean()) {
            cir.setReturnValue(Util.SPLASH_TEXTS.get(RANDOM.nextInt(Util.SPLASH_TEXTS.size())));
        }
    }
}
