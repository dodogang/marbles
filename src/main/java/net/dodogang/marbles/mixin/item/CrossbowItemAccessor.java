package net.dodogang.marbles.mixin.item;

import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@SuppressWarnings("unused")
@Mixin(CrossbowItem.class)
public interface CrossbowItemAccessor {
    @Invoker("getProjectiles")
    static List<ItemStack> getProjectiles(ItemStack crossbow) {
        throw new AssertionError();
    }
}
