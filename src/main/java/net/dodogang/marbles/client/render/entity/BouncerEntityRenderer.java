package net.dodogang.marbles.client.render.entity;

import net.dodogang.marbles.client.model.entity.BouncerEntityModel;
import net.dodogang.marbles.entity.BouncerEntity;
import net.dodogang.marbles.init.MarblesEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class BouncerEntityRenderer extends MobEntityRenderer<BouncerEntity, BouncerEntityModel> {
    private static final Identifier TEXTURE = MarblesEntities.texture(BouncerEntity.id + "/" + BouncerEntity.id);

    @SuppressWarnings("unused")
    public BouncerEntityRenderer(EntityRenderDispatcher dispatcher, @Nullable EntityRendererRegistry.Context ctx) {
        super(dispatcher, new BouncerEntityModel(), 0.7F);
    }

    @Override
    public Identifier getTexture(BouncerEntity entity) {
        return BouncerEntityRenderer.TEXTURE;
    }

    @Override
    protected void setupTransforms(BouncerEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
        int size = entity.getSize();

        this.shadowRadius = 0.3f + (0.4f * size);

        float scale = 0.6f + (size * 0.5f);
        matrices.scale(scale, scale, scale);

        if (!((double)entity.limbDistance < 0.01D)) {
            float limbDelta = entity.limbAngle - entity.limbDistance * (1.0F - tickDelta) + 6.0F;
            float angle = (Math.abs(limbDelta % 13.0F - 6.5F) - 3.25F) / 3.25F;
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(6.5F * angle));
        }
    }
}
