package net.dodogang.marbles;

import net.dodogang.marbles.client.particle.PinkSaltParticle;
import net.dodogang.marbles.init.MarblesBlocksClient;
import net.dodogang.marbles.init.MarblesParticles;
import net.dodogang.marbles.net.MarblesNetwork;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class MarblesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(MarblesParticles.PINK_SALT, PinkSaltParticle.Factory::new);

        MarblesBlocksClient.init();

        MarblesNetwork.initClient();
    }
}
