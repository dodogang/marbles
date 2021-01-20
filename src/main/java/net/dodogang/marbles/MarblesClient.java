package net.dodogang.marbles;

import me.andante.chord.util.CClientUtils;
import net.dodogang.marbles.init.MarblesBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class MarblesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CClientUtils.registerWoodBlocks(MarblesBlocks.ASPEN, MarblesBlocks.HOOPSI);

        BlockRenderLayerMap brlmInstance = BlockRenderLayerMap.INSTANCE;
        brlmInstance.putBlocks(RenderLayer.getCutout(), MarblesBlocks.YELLOW_BAMBOO, MarblesBlocks.YELLOW_BAMBOO_SAPLING, MarblesBlocks.YELLOW_SCAFFOLDING);
    }
}
