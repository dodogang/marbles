package net.dodogang.marbles.client.render.entity.feature;

import net.dodogang.marbles.client.init.MarblesEntityModelLayers;
import net.dodogang.marbles.client.model.entity.koi.*;
import net.dodogang.marbles.client.render.entity.KoiFishEntityRenderer;
import net.dodogang.marbles.entity.KoiFishEntity;
import net.dodogang.marbles.entity.enums.KoiSize;
import net.dodogang.marbles.entity.enums.KoiVariant;
import net.dodogang.marbles.init.MarblesEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

@Environment(EnvType.CLIENT)
public class KoiFishEntityVariantFeatureRenderer<T extends KoiFishEntity, M extends AbstractKoiFishEntityModel<T>> extends FeatureRenderer<T, M> {
    private final KoiFishEntityRenderer<T, M> renderer;

    protected final SmallKoiFishEntityModel modelSmall;
    protected final LargeKoiFishEntityModel modelLarge;
    protected final ThiccKoiFishEntityModel modelThicc;
    protected final DummyThiccKoiFishEntityModel modelDummyThicc;

    protected final TrackedData<KoiVariant> variantType;

    public KoiFishEntityVariantFeatureRenderer(FeatureRendererContext<T, M> rctx, EntityRendererFactory.Context fctx, TrackedData<KoiVariant> variantType) {
        super(rctx);
        this.renderer = (KoiFishEntityRenderer<T, M>) rctx;

        this.modelSmall = new SmallKoiFishEntityModel(fctx.getPart(MarblesEntityModelLayers.KOI_SMALL));
        this.modelLarge = new LargeKoiFishEntityModel(fctx.getPart(MarblesEntityModelLayers.KOI_LARGE));
        this.modelThicc = new ThiccKoiFishEntityModel(fctx.getPart(MarblesEntityModelLayers.KOI_THICC));
        this.modelDummyThicc = new DummyThiccKoiFishEntityModel(fctx.getPart(MarblesEntityModelLayers.KOI_DUMMY_THICC));

        this.variantType = variantType;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertices, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        M model = this.getModel(entity);
        this.renderer.getModel(entity).copyStateTo(model);
        model.animateModel(entity, limbAngle, limbDistance, tickDelta);
        model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        VertexConsumer vertex = vertices.getBuffer(RenderLayer.getEntityCutoutNoCull(this.getTexture(entity)));
        model.render(matrices, vertex, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
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
        return createTexture(entity.getSize(), entity.getDataTracker().get(this.variantType));
    }

    public Identifier createTexture(KoiSize size, KoiVariant variant) {
        return MarblesEntities.texture(String.format("koi_fish/koi_fish_%s_%s_%s", size.name().toLowerCase(Locale.ROOT), KoiFishEntity.getNameForVariantType(this.variantType), variant.name().toLowerCase(Locale.ROOT)));
    }
}
