package net.dodogang.marbles.mixin.hooks;

import me.andante.chord.tag.CBlockTags;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Feature.class)
public class FeatureMixin {
    @Inject(method = "isSoil(Lnet/minecraft/block/Block;)Z", at = @At("HEAD"), cancellable = true)
    private static void onIsSoil(Block block, CallbackInfoReturnable<Boolean> cb) {
        if (block.isIn(CBlockTags.PLANT_SUPPORTERS)) {
            cb.setReturnValue(true);
        }
    }
}
