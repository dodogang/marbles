package net.dodogang.marbles.mixin.hooks;

import net.dodogang.marbles.mixin.LlamaEntityAccess;
import net.dodogang.marbles.util.Util;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(LlamaEntity.class)
public class LlamaEntityMixin implements LlamaEntityAccess {
    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void appendTrackedData(CallbackInfo ci) {
        LlamaEntity $this = LlamaEntity.class.cast(this);
        $this.getDataTracker().startTracking(Util.LLAMA_SADDLE_ITEM_TRACKED_DATA, ItemStack.EMPTY);
    }

    @Inject(method = "onInventoryChanged", at = @At("TAIL"))
    private void updateSaddleItem(Inventory sender, CallbackInfo ci) {
        LlamaEntity $this = LlamaEntity.class.cast(this);
        $this.getDataTracker().set(Util.LLAMA_SADDLE_ITEM_TRACKED_DATA, Optional.ofNullable(sender.getStack(1)).orElse(ItemStack.EMPTY));
    }

    @NotNull
    @Override
    public ItemStack getSaddleItem() {
        LlamaEntity $this = LlamaEntity.class.cast(this);
        return $this.getDataTracker().get(Util.LLAMA_SADDLE_ITEM_TRACKED_DATA);
    }
}
