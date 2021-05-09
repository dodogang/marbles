package net.dodogang.marbles.mixin.hooks;

import net.dodogang.marbles.entity.PollenGracedSheepEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DyeItem.class)
public class DyeItemMixin {
    @Inject(method = "useOnEntity", at = @At("HEAD"), cancellable = true)
    private void cancelPollenGracedSheepUsage(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (entity instanceof PollenGracedSheepEntity) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }
}
