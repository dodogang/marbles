package net.dodogang.marbles.init;

import me.andante.chord.util.CClientUtils;
import net.dodogang.marbles.client.color.world.MarblesBiomeColors;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemConvertible;

public class MarblesBlocksClient {
    public static void init() {
        CClientUtils.registerWoodBlocks(MarblesBlocks.ASPEN, MarblesBlocks.HOOPSI_SPRUCE);

        BlockRenderLayerMap brlm = BlockRenderLayerMap.INSTANCE;
        brlm.putBlocks(
            RenderLayer.getCutout(),
            MarblesBlocks.YELLOW_BAMBOO,
            MarblesBlocks.YELLOW_BAMBOO_SAPLING,
            MarblesBlocks.YELLOW_SCAFFOLDING
        );

        brlm.putBlocks(
            RenderLayer.getCutoutMipped(),
            MarblesBlocks.GRISP_GRASS_BLOCK
        );

        brlm.putBlocks(
            RenderLayer.getTranslucent(),
            MarblesBlocks.TRAVERTINE_PORTAL
        );

        ColorProviderRegistry<Block, BlockColorProvider> blockColors = ColorProviderRegistry.BLOCK;
        ColorProviderRegistry<ItemConvertible, ItemColorProvider> itemColors = ColorProviderRegistry.ITEM;

        blockColors.register(
            (state, world, pos, tintIndex) -> {
                if (pos == null)
                    return MarblesBiomeColors.GRISP_GRASS_DEFAULT;
                return MarblesBiomeColors.getGrispGrassColor(pos);
            },
            MarblesBlocks.GRISP_GRASS_BLOCK
        );
        itemColors.register(
            (stack, tintIndex) -> MarblesBiomeColors.GRISP_GRASS_DEFAULT,
            MarblesBlocks.GRISP_GRASS_BLOCK
        );
    }
}
