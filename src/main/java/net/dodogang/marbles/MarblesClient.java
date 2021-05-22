package net.dodogang.marbles;

import com.google.common.reflect.Reflection;
import net.dodogang.marbles.block.RopeBlock;
import net.dodogang.marbles.client.config.MarblesConfigManager;
import net.dodogang.marbles.client.init.MarblesBlocksClient;
import net.dodogang.marbles.client.network.MarblesClientNetwork;
import net.dodogang.marbles.client.particle.IceSporeParticle;
import net.dodogang.marbles.client.particle.MarblesParticleFactories;
import net.dodogang.marbles.client.particle.PinkSaltParticle;
import net.dodogang.marbles.client.render.entity.BouncerEntityRenderer;
import net.dodogang.marbles.client.render.entity.PollenGracedSheepEntityRenderer;
import net.dodogang.marbles.entity.ThrownRopeEntity;
import net.dodogang.marbles.init.MarblesEntities;
import net.dodogang.marbles.init.MarblesParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;

import java.util.List;

public class MarblesClient implements ClientModInitializer {
    public static BlockState lastNetherPortalState = Blocks.NETHER_PORTAL.getDefaultState();

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry pfrInstance = ParticleFactoryRegistry.getInstance();
        pfrInstance.register(MarblesParticles.PINK_SALT, PinkSaltParticle.Factory::new);
        pfrInstance.register(MarblesParticles.ICE_SPORE, IceSporeParticle.Factory::new);
        pfrInstance.register(MarblesParticles.ITEM_ROPE, MarblesParticleFactories.RopeFactory::new);

        EntityRendererRegistry errInstance = EntityRendererRegistry.INSTANCE;
        errInstance.register(MarblesEntities.BOUNCER, BouncerEntityRenderer::new);
        errInstance.register(MarblesEntities.POLLEN_GRACED_SHEEP, PollenGracedSheepEntityRenderer::new);
        errInstance.register(MarblesEntities.THROWN_ROPE, (dispatcher, context) -> new FlyingItemEntityRenderer<ThrownRopeEntity>(dispatcher, context.getItemRenderer()));

        FabricModelPredicateProviderRegistry.register(Items.CROSSBOW, new Identifier(Marbles.MOD_ID, "rope"), (stack, world, entity) -> {
            Item item = stack.getItem();
            if (item instanceof CrossbowItem) {
                List<ItemStack> projectiles = CrossbowItem.getProjectiles(stack);
                projectiles.removeIf(istack -> {
                    Item iitem = istack.getItem();
                    return !(iitem instanceof BlockItem && ((BlockItem) iitem).getBlock() instanceof RopeBlock);
                });

                if (!projectiles.isEmpty()) {
                    return 1.0f;
                }
            }

            return 0.0f;
        });

        Reflection.initialize(
            MarblesBlocksClient.class,
            MarblesClientNetwork.class,

            MarblesConfigManager.class
        );
    }

    public static Identifier texture(String path) {
        return new Identifier(Marbles.MOD_ID, "textures/" + path + ".png");
    }
}
