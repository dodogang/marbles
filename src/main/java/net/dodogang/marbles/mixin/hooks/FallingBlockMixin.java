package net.dodogang.marbles.mixin.hooks;

import net.dodogang.marbles.tag.MarblesBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FallingBlock.class)
public class FallingBlockMixin {
    @Inject(method = "canFallThrough", at = @At("HEAD"), cancellable = true)
    private static void canFallThrough(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.isIn(MarblesBlockTags.CROSSED_LATTICE)) {
            cir.setReturnValue(true);
        }
    }
}
