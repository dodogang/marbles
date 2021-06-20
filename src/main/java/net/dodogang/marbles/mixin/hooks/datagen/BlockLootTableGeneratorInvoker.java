package net.dodogang.marbles.mixin.hooks.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@SuppressWarnings("unused")
@Mixin(BlockLootTableGenerator.class)
public interface BlockLootTableGeneratorInvoker {
    @Invoker("oreDrops")
    static LootTable.Builder oreDrops(Block dropWithSilkTouch, Item drop) {
        throw new AssertionError();
    }
    @Invoker("copperOreDrops")
    static LootTable.Builder copperOreDrops(Block ore) {
        throw new AssertionError();
    }
    @Invoker("lapisOreDrops")
    static LootTable.Builder lapisOreDrops(Block ore) {
        throw new AssertionError();
    }
    @Invoker("redstoneOreDrops")
    static LootTable.Builder redstoneOreDrops(Block ore) {
        throw new AssertionError();
    }
}
