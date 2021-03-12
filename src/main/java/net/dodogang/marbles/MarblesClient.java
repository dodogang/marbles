package net.dodogang.marbles;

import me.andante.chord.util.CClientUtils;
import net.dodogang.marbles.client.particle.PinkSaltParticle;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.init.MarblesParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.render.RenderLayer;

public class MarblesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CClientUtils.registerWoodBlocks(MarblesBlocks.ASPEN, MarblesBlocks.HOOPSI_SPRUCE);
        ParticleFactoryRegistry.getInstance().register(MarblesParticles.PINK_SALT, PinkSaltParticle.Factory::new);

        BlockRenderLayerMap brlmInstance = BlockRenderLayerMap.INSTANCE;
        brlmInstance.putBlocks(RenderLayer.getCutout(), MarblesBlocks.YELLOW_BAMBOO, MarblesBlocks.YELLOW_BAMBOO_SAPLING, MarblesBlocks.YELLOW_SCAFFOLDING);
    }
}
