package net.dodogang.marbles.client.render.entity;

import net.dodogang.marbles.client.model.entity.PinkSaltCubeEntityModel;
import net.dodogang.marbles.entity.PinkSaltCubeEntity;
import net.dodogang.marbles.init.MarblesEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class PinkSaltCubeEntityRenderer extends MobEntityRenderer<PinkSaltCubeEntity, PinkSaltCubeEntityModel> {
    @SuppressWarnings("unused")
    public PinkSaltCubeEntityRenderer(EntityRenderDispatcher entityRenderDispatcher, @Nullable EntityRendererRegistry.Context ctx) {
        super(entityRenderDispatcher, new PinkSaltCubeEntityModel(), 0.0f);
    }

    @Override
    protected boolean isShaking(PinkSaltCubeEntity entity) {
        return entity.hurtTime > 0;
    }

    @Override
    public Identifier getTexture(PinkSaltCubeEntity entity) {
        return MarblesEntities.texture( PinkSaltCubeEntity.id + "/" + PinkSaltCubeEntity.id);
    }
}
