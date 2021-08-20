package net.dodogang.marbles.client.render.entity.feature;

import net.dodogang.marbles.client.model.entity.koi.AbstractKoiFishEntityModel;
import net.dodogang.marbles.client.render.entity.KoiFishEntityRenderer;
import net.dodogang.marbles.entity.KoiFishEntity;
import net.dodogang.marbles.mixin.client.render.LivingEntityRendererInvoker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class KoiFishEntityAppendageFeatureRenderer<T extends KoiFishEntity, M extends AbstractKoiFishEntityModel<T>>  extends FeatureRenderer<T, M> {
    private final KoiFishEntityRenderer<T, M> renderer;

    public KoiFishEntityAppendageFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
        this.renderer = (KoiFishEntityRenderer<T, M>) context;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertices, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        float scale = 1 + 0.1f;
        matrices.scale(scale, scale, scale);

        LivingEntityRendererInvoker<T, M> invoker = (LivingEntityRendererInvoker<T, M>) this.renderer;

        MinecraftClient client = MinecraftClient.getInstance();
        boolean visible = invoker.invoke_isVisible(entity);
        boolean visibleFully = !visible && !entity.isInvisibleTo(client.player);
        boolean outlined = client.hasOutline(entity);

        RenderLayer renderLayer = this.renderer.getRenderLayer(entity, visible, visibleFully, outlined, new Identifier("e"));

        VertexConsumer vertex = vertices.getBuffer(renderLayer);
        this.renderer.getModel(entity).render(matrices, vertex, light, LivingEntityRenderer.getOverlay(entity, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
    }
}
