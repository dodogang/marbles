package net.dodogang.marbles;

import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.Reflection;
import me.andante.chord.util.CClientUtils;
import net.dodogang.marbles.block.RopeBlock;
import net.dodogang.marbles.client.config.MarblesConfigManager;
import net.dodogang.marbles.client.init.MarblesEntityModelLayers;
import net.dodogang.marbles.client.model.entity.BouncerEntityModel;
import net.dodogang.marbles.client.model.entity.PollenGracedSheepEntityModel;
import net.dodogang.marbles.client.network.MarblesClientNetwork;
import net.dodogang.marbles.client.particle.IceSporeParticle;
import net.dodogang.marbles.client.particle.MarblesParticleFactories;
import net.dodogang.marbles.client.particle.PinkSaltParticle;
import net.dodogang.marbles.client.render.entity.BouncerEntityRenderer;
import net.dodogang.marbles.client.render.entity.PollenGracedSheepEntityRenderer;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.init.MarblesEntities;
import net.dodogang.marbles.init.MarblesParticles;
import net.dodogang.marbles.mixin.hooks.CrossbowItemAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;

import java.util.List;

@Environment(EnvType.CLIENT)
public class MarblesClient implements ClientModInitializer {
    public static BlockState lastNetherPortalState = Blocks.NETHER_PORTAL.getDefaultState();

    @SuppressWarnings({"UnstableApiUsage","deprecation"})
    @Override
    public void onInitializeClient() {
        Marbles.log("Initializing (CLIENT)");

        Reflection.initialize(
            MarblesEntityModelLayers.class,

            MarblesClientNetwork.class,
            MarblesConfigManager.class
        );

        CClientUtils.registerWoodBlocks(MarblesBlocks.ASPEN, MarblesBlocks.HOOPSI_SPRUCE, MarblesBlocks.RED_BIRCH);

        // render layers

        BlockRenderLayerMap brlm = BlockRenderLayerMap.INSTANCE;
        brlm.putBlocks(
            RenderLayer.getCutout(),

            MarblesBlocks.CHEQUERED_BAMBOO_LATTICE,
            MarblesBlocks.CROSSED_BAMBOO_LATTICE,

            MarblesBlocks.YELLOW_BAMBOO,
            MarblesBlocks.YELLOW_BAMBOO_SAPLING,
            MarblesBlocks.YELLOW_SCAFFOLDING,
            MarblesBlocks.CHEQUERED_YELLOW_BAMBOO_LATTICE,
            MarblesBlocks.CROSSED_YELLOW_BAMBOO_LATTICE,

            MarblesBlocks.BAMBOO_TIKI_TORCH,
            MarblesBlocks.BAMBOO_TIKI_POLE,

            MarblesBlocks.BLUE_PEONY,
            MarblesBlocks.PINK_SALT_SPIKES,
            MarblesBlocks.POLLEN_GRACED_WOOL,
            MarblesBlocks.POTTED_YELLOW_BAMBOO,

            MarblesBlocks.YELLOW_BAMBOO_TIKI_TORCH,
            MarblesBlocks.YELLOW_BAMBOO_TIKI_POLE,

            MarblesBlocks.MORN_GRASS,
            MarblesBlocks.TALL_MORN_GRASS,

            MarblesBlocks.ASPEN_SPROUTS,
            MarblesBlocks.ASPEN_GRASS,
            MarblesBlocks.TALL_ASPEN_GRASS,

            MarblesBlocks.RINGED_FLOESTONE,
            MarblesBlocks.RINGED_RILLED_FLOESTONE
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

        brlm.putBlocks(
            RenderLayer.getCutoutMipped(),

            MarblesBlocks.PERMAFROST
        );

        // particles

        ParticleFactoryRegistry pfrInstance = ParticleFactoryRegistry.getInstance();
        pfrInstance.register(MarblesParticles.PINK_SALT, PinkSaltParticle.Factory::new);
        pfrInstance.register(MarblesParticles.ICE_SPORE, IceSporeParticle.Factory::new);
        pfrInstance.register(MarblesParticles.ITEM_ROPE, MarblesParticleFactories.RopeFactory::new);

        // entities

        EntityRendererRegistry errInstance = EntityRendererRegistry.INSTANCE;
        errInstance.register(MarblesEntities.BOUNCER, BouncerEntityRenderer::new);
        errInstance.register(MarblesEntities.POLLEN_GRACED_SHEEP, PollenGracedSheepEntityRenderer::new);
        errInstance.register(MarblesEntities.THROWN_ROPE, FlyingItemEntityRenderer::new);

        ImmutableMap.<EntityModelLayer, EntityModelLayerRegistry.TexturedModelDataProvider>of(
            MarblesEntityModelLayers.BOUNCER, BouncerEntityModel::getTexturedModelData,
            MarblesEntityModelLayers.POLLEN_GRACED_SHEEP, PollenGracedSheepEntityModel::getTexturedModelData
        ).forEach(EntityModelLayerRegistry::registerModelLayer);

        // model predicates

        FabricModelPredicateProviderRegistry.register(Items.CROSSBOW, new Identifier(Marbles.MOD_ID, "rope"), (stack, world, entity, seed) -> {
            Item item = stack.getItem();
            if (item instanceof CrossbowItem) {
                List<ItemStack> projectiles = CrossbowItemAccessor.getProjectiles(stack);
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

        Marbles.log("Initialized (CLIENT)");
    }

    public static Identifier texture(String path) {
        return new Identifier(Marbles.MOD_ID, "textures/" + path + ".png");
    }
}
