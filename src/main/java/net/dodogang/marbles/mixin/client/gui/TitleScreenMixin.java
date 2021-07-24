package net.dodogang.marbles.mixin.client.gui;

import net.dodogang.marbles.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.TitleScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Shadow @Nullable private String splashText;

    @Inject(method = "init", at = @At("HEAD"))
    private void catchTitleScreen(CallbackInfo ci) {
        Util.currentTitleScreen = TitleScreen.class.cast(this);
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawCenteredText(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V", ordinal = 0), index = 5)
    private int modifySplashColor(int original) {
        if (Util.SPLASH_TEXTS.contains(this.splashText)) {
            return 0x98CFEA;
        }

        return original;
    }
}
