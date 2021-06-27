package net.dodogang.marbles.mixin.hooks;

import net.dodogang.marbles.block.AbstractAttachingBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "isClimbing", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;climbingPos:Ljava/util/Optional;", shift = At.Shift.BEFORE, ordinal = 0), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void correctClimbing(CallbackInfoReturnable<Boolean> cir, BlockPos pos, BlockState state) {
        if (state.getBlock() instanceof AbstractAttachingBlock && state.get(AbstractAttachingBlock.FACING).getAxis().isVertical()) {
            cir.setReturnValue(false);
        }
    }
}
