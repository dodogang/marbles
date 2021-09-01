package net.dodogang.marbles.client.render.entity;

import net.dodogang.marbles.client.init.MarblesEntityModelLayers;
import net.dodogang.marbles.client.model.entity.PinkSaltCubeEntityModel;
import net.dodogang.marbles.entity.PinkSaltCubeEntity;
import net.dodogang.marbles.init.MarblesEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PinkSaltCubeEntityRenderer extends MobEntityRenderer<PinkSaltCubeEntity, PinkSaltCubeEntityModel<PinkSaltCubeEntity>> {
    public static final Identifier TEXTURE = MarblesEntities.texture("pink_salt_cube/pink_salt_cube");

    public PinkSaltCubeEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PinkSaltCubeEntityModel<>(ctx.getPart(MarblesEntityModelLayers.PINK_SALT_CUBE)), 0.0f);
    }

    @Override
    public Identifier getTexture(PinkSaltCubeEntity entity) {
        return TEXTURE;
    }
}
