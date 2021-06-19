package net.dodogang.marbles.client.init;

import me.andante.chord.util.CClientUtils;
import net.dodogang.marbles.init.MarblesBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

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
            RenderLayer.getTranslucent(),
            MarblesBlocks.TRAVERTINE_NETHER_PORTAL,

            MarblesBlocks.CUT_ICE,
            MarblesBlocks.CHISELED_ICE,
            MarblesBlocks.ICE_BRICKS,
            MarblesBlocks.BLUE_ICE_BRICKS,
            MarblesBlocks.SCALED_ICE_BRICKS,
            MarblesBlocks.MINTED_ICE_BRICKS
        );
    }
}
