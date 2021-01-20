package net.dodogang.marbles.init;

import me.andante.chord.item.CScaffoldingItem;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.block.YellowScaffoldingBlock;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class MarblesItems {
    public static final Item YELLOW_SCAFFOLDING = register(YellowScaffoldingBlock.id, new CScaffoldingItem(MarblesBlocks.YELLOW_SCAFFOLDING, new Item.Settings().group(Marbles.ITEM_GROUP)));

    public MarblesItems() {}

    public static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Marbles.MOD_ID, id), item);
    }
}
