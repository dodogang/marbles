package net.dodogang.marbles.init;

import me.andante.chord.block.CBambooBlock;
import me.andante.chord.block.CBambooSaplingBlock;
import me.andante.chord.block.helper.WoodBlocks;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.block.*;
import net.dodogang.marbles.block.sapling.AspenSaplingGenerator;
import net.dodogang.marbles.block.sapling.HoopsiSpruceSaplingGenerator;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class MarblesBlocks {
    //
    // WOOD
    //

    public static final WoodBlocks ASPEN = new WoodBlocks.Builder().saplingGenerator(new AspenSaplingGenerator()).boatType(BoatEntity.Type.BIRCH).itemGroup(Marbles.ITEM_GROUP).build(Marbles.MOD_ID, "aspen");
    public static final WoodBlocks HOOPSI_SPRUCE = new WoodBlocks.Builder().saplingGenerator(new HoopsiSpruceSaplingGenerator()).boatType(BoatEntity.Type.SPRUCE).itemGroup(Marbles.ITEM_GROUP).build(Marbles.MOD_ID, "hoopsi_spruce");

    //
    // YELLOW BAMBOO
    //

    private static final String yellowBamboo = "yellow_bamboo";
    public static final CBambooBlock YELLOW_BAMBOO = (CBambooBlock)register(yellowBamboo, new CBambooBlock(() -> MarblesBlocks.YELLOW_BAMBOO, () -> MarblesBlocks.YELLOW_BAMBOO_SAPLING, FabricBlockSettings.copy(Blocks.BAMBOO)));
    public static final CBambooSaplingBlock YELLOW_BAMBOO_SAPLING = (CBambooSaplingBlock)register(yellowBamboo + "_sapling", new CBambooSaplingBlock(() -> MarblesBlocks.YELLOW_BAMBOO, () -> MarblesBlocks.YELLOW_BAMBOO_SAPLING, FabricBlockSettings.copy(Blocks.BAMBOO_SAPLING)), false);

    public static final Block YELLOW_SCAFFOLDING = register(YellowScaffoldingBlock.id, new YellowScaffoldingBlock(FabricBlockSettings.copy(Blocks.SCAFFOLDING)), false);

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
