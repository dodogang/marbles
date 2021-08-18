package net.dodogang.marbles.mixin.entity;

import net.dodogang.marbles.block.AbstractAttachingBlock;
import net.dodogang.marbles.util.Util;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    private LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "isClimbing", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;climbingPos:Ljava/util/Optional;", shift = At.Shift.BEFORE, ordinal = 0), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void correctClimbing(CallbackInfoReturnable<Boolean> cir, BlockPos pos, BlockState state) {
        if (state.getBlock() instanceof AbstractAttachingBlock && state.get(AbstractAttachingBlock.FACING).getAxis().isVertical()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "method_26318", at = @At("RETURN"), cancellable = true)
    private void amendClimbingSpeed(Vec3d vec3d, float f, CallbackInfoReturnable<Vec3d> cir) {
        Util.applyFasterClimbingMovement(LivingEntity.class.cast(this), vec3d, f, cir);
    }
}
