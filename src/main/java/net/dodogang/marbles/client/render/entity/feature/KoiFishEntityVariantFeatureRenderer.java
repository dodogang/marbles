package net.dodogang.marbles.client.render.entity.feature;

import net.dodogang.marbles.client.init.MarblesEntityModelLayers;
import net.dodogang.marbles.client.model.entity.koi.*;
import net.dodogang.marbles.client.render.entity.KoiFishEntityRenderer;
import net.dodogang.marbles.entity.KoiFishEntity;
import net.dodogang.marbles.entity.enums.KoiColor;
import net.dodogang.marbles.mixin.client.render.LivingEntityRendererInvoker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class KoiFishEntityVariantFeatureRenderer<T extends KoiFishEntity, M extends AbstractKoiFishEntityModel<T>> extends FeatureRenderer<T, M> {
    private final KoiFishEntityRenderer<T, M> renderer;

    protected final SmallKoiFishEntityModel modelSmall;
    protected final LargeKoiFishEntityModel modelLarge;
    protected final ThiccKoiFishEntityModel modelThicc;
    protected final DummyThiccKoiFishEntityModel modelDummyThicc;

    protected final TrackedData<KoiColor> variantType;

    public KoiFishEntityVariantFeatureRenderer(FeatureRendererContext<T, M> rctx, EntityRendererFactory.Context fctx, TrackedData<KoiColor> variantType) {
        super(rctx);
        this.renderer = (KoiFishEntityRenderer<T, M>) rctx;

        this.modelSmall = new SmallKoiFishEntityModel(fctx.getPart(MarblesEntityModelLayers.KOI_SMALL));
        this.modelLarge = new LargeKoiFishEntityModel(fctx.getPart(MarblesEntityModelLayers.KOI_LARGE));
        this.modelThicc = new ThiccKoiFishEntityModel(fctx.getPart(MarblesEntityModelLayers.KOI_THICC));
        this.modelDummyThicc = new DummyThiccKoiFishEntityModel(fctx.getPart(MarblesEntityModelLayers.KOI_DUMMY_THICC));

        this.variantType = variantType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertices, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        M model = this.getModel(entity);
        this.renderer.getModel(entity).copyStateTo(model);
        model.animateModel(entity, limbAngle, limbDistance, tickDelta);
        model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        VertexConsumer vertex = vertices.getBuffer(RenderLayer.getEntityCutoutNoCull(this.getTexture(entity)));
        model.render(matrices, vertex, light, LivingEntityRenderer.getOverlay(entity, ((LivingEntityRendererInvoker<T, M>) this.renderer).invoke_getAnimationCounter(entity, tickDelta)), 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @SuppressWarnings("unchecked")
    public M getModel(@Nullable T entity) {
        if (entity == null) return (M) this.modelSmall;

        return switch (entity.getSize()) {
            default -> (M) this.modelSmall;
            case LARGE -> (M) this.modelLarge;
            case THICC -> (M) this.modelThicc;
            case DUMMY_THICC -> (M) this.modelDummyThicc;
        };
    }

    @Override
    public Identifier getTexture(KoiFishEntity entity) {
        return KoiFishEntityRenderer.getTexture(entity, this.variantType);
    }
}
