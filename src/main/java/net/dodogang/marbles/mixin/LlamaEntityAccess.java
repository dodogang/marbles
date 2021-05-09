package net.dodogang.marbles.mixin;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface LlamaEntityAccess {
    @NotNull ItemStack getSaddleItem();
}
