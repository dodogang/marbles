package net.dodogang.marbles.mixin.hooks;

import net.dodogang.marbles.entity.BouncerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(method = "takeShieldHit", at = @At("TAIL"))
    private void takeShieldHit(LivingEntity attacker, CallbackInfo ci) {
        if (attacker instanceof BouncerEntity) {
            PlayerEntity $this = PlayerEntity.class.cast(this);
            ((BouncerEntity) attacker).tryAttackPlayerHasShield($this);
        }
    }
}
