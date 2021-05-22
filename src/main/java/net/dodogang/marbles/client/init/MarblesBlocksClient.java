package net.dodogang.marbles.client.init;

import me.andante.chord.client.util.CClientUtils;
import net.dodogang.marbles.client.color.world.MarblesBiomeColors;
import net.dodogang.marbles.init.MarblesBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemConvertible;

@Environment(EnvType.CLIENT)
public class MarblesBlocksClient {
    static {
        CClientUtils.registerWoodBlocks(MarblesBlocks.ASPEN, MarblesBlocks.HOOPSI_SPRUCE, MarblesBlocks.RED_BIRCH);

        BlockRenderLayerMap brlm = BlockRenderLayerMap.INSTANCE;
        brlm.putBlocks(
            RenderLayer.getCutout(),
            MarblesBlocks.YELLOW_BAMBOO,
            MarblesBlocks.YELLOW_BAMBOO_SAPLING,
            MarblesBlocks.YELLOW_SCAFFOLDING,
            MarblesBlocks.PINK_SALT_SPIKES,
            MarblesBlocks.POLLEN_GRACED_WOOL,
            MarblesBlocks.POTTED_YELLOW_BAMBOO
        );

        brlm.putBlocks(
            RenderLayer.getCutoutMipped(),
            MarblesBlocks.GRISP_GRASS_BLOCK
        );

        brlm.putBlocks(
            RenderLayer.getTranslucent(),
            MarblesBlocks.TRAVERTINE_PORTAL,

            MarblesBlocks.CUT_ICE,
            MarblesBlocks.CHISELED_ICE,
            MarblesBlocks.ICE_BRICKS,
            MarblesBlocks.BLUE_ICE_BRICKS,
            MarblesBlocks.SCALED_ICE_BRICKS,
            MarblesBlocks.MINTED_ICE_BRICKS
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
