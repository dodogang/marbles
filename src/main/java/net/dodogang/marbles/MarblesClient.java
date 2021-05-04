package net.dodogang.marbles;

import net.dodogang.marbles.client.network.MarblesClientNetwork;
import net.dodogang.marbles.client.particle.PinkSaltParticle;
import net.dodogang.marbles.init.MarblesBlocksClient;
import net.dodogang.marbles.init.MarblesGenTypes;
import net.dodogang.marbles.init.MarblesParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class MarblesClient implements ClientModInitializer {
    public static BlockState lastNetherPortalState = Blocks.NETHER_PORTAL.getDefaultState();

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(MarblesParticles.PINK_SALT, PinkSaltParticle.Factory::new);

        new MarblesGenTypes();

        MarblesBlocksClient.init();

        MarblesClientNetwork.initClient();
    }
}
