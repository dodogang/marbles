package net.dodogang.marbles.init;

import me.andante.chord.block.helper.WoodBlocks;
import net.dodogang.marbles.Marbles;
import net.minecraft.block.Block;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MarblesBlocks {
    public static final WoodBlocks ASPEN = new WoodBlocks.Builder().boatType(BoatEntity.Type.BIRCH).itemGroup(Marbles.ITEM_GROUP).build(Marbles.MOD_ID, "aspen");
    public static final WoodBlocks HOOPSI = new WoodBlocks.Builder().boatType(BoatEntity.Type.SPRUCE).itemGroup(Marbles.ITEM_GROUP).build(Marbles.MOD_ID, "hoopsi_spruce");

    public MarblesBlocks() {}

    public static Block register(String id, Block block, boolean registerItem) {
        Identifier identifier = new Identifier(Marbles.MOD_ID, id);

        Block registeredBlock = Registry.register(Registry.BLOCK, identifier, block);
        if (registerItem) {
            Registry.register(Registry.ITEM, identifier, new BlockItem(registeredBlock, new Item.Settings().maxCount(64).group(Marbles.ITEM_GROUP)));
        }

        return registeredBlock;
    }
    public static Block register(String id, Block block) {
        return register(id, block, true);
    }
}
