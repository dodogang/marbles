package net.dodogang.marbles.client.render.entity;

import com.google.common.collect.ImmutableMap;
import net.dodogang.marbles.client.init.MarblesEntityModelLayers;
import net.dodogang.marbles.client.model.entity.koi.*;
import net.dodogang.marbles.client.render.entity.feature.KoiFishEntityVariantFeatureRenderer;
import net.dodogang.marbles.entity.KoiFishEntity;
import net.dodogang.marbles.entity.enums.KoiColor;
import net.dodogang.marbles.entity.enums.KoiSize;
import net.dodogang.marbles.init.MarblesEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unchecked")
@Environment(EnvType.CLIENT)
public class KoiFishEntityRenderer<T extends KoiFishEntity, M extends AbstractKoiFishEntityModel<T>> extends MobEntityRenderer<T, M> {
    public static ImmutableMap<String, Identifier> TEXTURES_MAP;
    static {
        ImmutableMap.Builder<String, Identifier> builder = ImmutableMap.builder();

        for (KoiSize size : KoiSize.values()) {
            for (TrackedData<KoiColor> variantType : new TrackedData[]{ KoiFishEntity.COL_BASE, KoiFishEntity.COL_BODY, KoiFishEntity.COL_FINS }) {
                for (KoiColor color : KoiColor.values()) {
                    String formatted = getTexture(size, variantType, color);
                    builder.put(formatted, MarblesEntities.texture(String.format("koi/koi_%s", formatted)));
                }
            }
        }

        TEXTURES_MAP = builder.build();
    }

    protected final SmallKoiFishEntityModel modelSmall;
    protected final LargeKoiFishEntityModel modelLarge;
    protected final ThiccKoiFishEntityModel modelThicc;
    protected final DummyThiccKoiFishEntityModel modelDummyThicc;

    public KoiFishEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, null, 0.15f);

        this.addFeature(new KoiFishEntityVariantFeatureRenderer<>(this, ctx, KoiFishEntity.COL_FINS));
        this.addFeature(new KoiFishEntityVariantFeatureRenderer<>(this, ctx, KoiFishEntity.COL_BODY));

        this.modelSmall = new SmallKoiFishEntityModel(ctx.getPart(MarblesEntityModelLayers.KOI_SMALL));
        this.modelLarge = new LargeKoiFishEntityModel(ctx.getPart(MarblesEntityModelLayers.KOI_LARGE));
        this.modelThicc = new ThiccKoiFishEntityModel(ctx.getPart(MarblesEntityModelLayers.KOI_THICC));
        this.modelDummyThicc = new DummyThiccKoiFishEntityModel(ctx.getPart(MarblesEntityModelLayers.KOI_DUMMY_THICC));
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertices, int light) {
        M model = this.getModel(entity);

        matrices.push();
        model.handSwingProgress = this.getHandSwingProgress(entity, tickDelta);
        model.riding = entity.hasVehicle();
        model.child = entity.isBaby();
        float bodyYawLrp = MathHelper.lerpAngleDegrees(tickDelta, entity.prevBodyYaw, entity.bodyYaw);
        float headYawLrp = MathHelper.lerpAngleDegrees(tickDelta, entity.prevHeadYaw, entity.headYaw);
        float headYaw = headYawLrp - bodyYawLrp;
        float animationProgress;
        if (entity.hasVehicle() && entity.getVehicle() instanceof LivingEntity mobEntity2) {
            bodyYawLrp = MathHelper.lerpAngleDegrees(tickDelta, mobEntity2.prevBodyYaw, mobEntity2.bodyYaw);
            headYaw = headYawLrp - bodyYawLrp;
            animationProgress = MathHelper.wrapDegrees(headYaw);
            if (animationProgress < -85.0F) {
                animationProgress = -85.0F;
            }

            if (animationProgress >= 85.0F) {
                animationProgress = 85.0F;
            }

            bodyYawLrp = headYawLrp - animationProgress;
            if (animationProgress * animationProgress > 2500.0F) {
                bodyYawLrp += animationProgress * 0.2F;
            }

            headYaw = headYawLrp - bodyYawLrp;
        }

        float headPitch = MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch());
        float limbDistance;
        if (entity.getPose() == EntityPose.SLEEPING) {
            Direction direction = entity.getSleepingDirection();
            if (direction != null) {
                limbDistance = entity.getEyeHeight(EntityPose.STANDING) - 0.1F;
                matrices.translate((float)(-direction.getOffsetX()) * limbDistance, 0.0D, (float)(-direction.getOffsetZ()) * limbDistance);
            }
        }

        animationProgress = this.getAnimationProgress(entity, tickDelta);
        this.setupTransforms(entity, matrices, animationProgress, bodyYawLrp, tickDelta);
        matrices.scale(-1.0F, -1.0F, 1.0F);
        this.scale(entity, matrices, tickDelta);
        matrices.translate(0.0D, -1.5010000467300415D, 0.0D);
        limbDistance = 0.0F;
        float limbAngle = 0.0F;
        if (!entity.hasVehicle() && entity.isAlive()) {
            limbDistance = MathHelper.lerp(tickDelta, entity.lastLimbDistance, entity.limbDistance);
            limbAngle = entity.limbAngle - entity.limbDistance * (1.0F - tickDelta);
            if (entity.isBaby()) {
                limbAngle *= 3.0F;
            }

            if (limbDistance > 1.0F) {
                limbDistance = 1.0F;
            }
        }

        model.animateModel(entity, limbAngle, limbDistance, tickDelta);
        model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        MinecraftClient client = MinecraftClient.getInstance();
        boolean visible = this.isVisible(entity);
        boolean visibleFully = !visible && !entity.isInvisibleTo(client.player);
        boolean outlined = client.hasOutline(entity);
        RenderLayer renderLayer = this.getRenderLayer(entity, visible, visibleFully, outlined);
        if (renderLayer != null) {
            VertexConsumer vertexConsumer = vertices.getBuffer(renderLayer);
            int overlay = getOverlay(entity, this.getAnimationCounter(entity, tickDelta));
            model.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, visibleFully ? 0.15F : 1.0F);
        }

        if (!entity.isSpectator()) {
            for (FeatureRenderer<T, M> featureRenderer : this.features) {
                featureRenderer.render(matrices, vertices, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
            }
        }

        matrices.pop();
    }

    @Override
    protected void setupTransforms(T entity, MatrixStack matrixStack, float animationProgress, float bodyYaw, float headYaw) {
        super.setupTransforms(entity, matrixStack, animationProgress, bodyYaw, headYaw);
        float degrees = 4.3F * MathHelper.sin(0.6F * animationProgress);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(degrees));
        if (!entity.isTouchingWater()) {
            matrixStack.translate(0.1D, 0.1D, -0.1D);
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
        }
    }

    // ---

    @Override
    @Nullable
    protected RenderLayer getRenderLayer(T entity, boolean showBody, boolean translucent, boolean showOutline) {
        Identifier identifier = this.getTexture(entity);
        if (translucent) {
            return RenderLayer.getItemEntityTranslucentCull(identifier);
        } else if (showBody) {
            return this.getModel(entity).getLayer(identifier);
        } else {
            return showOutline ? RenderLayer.getOutline(identifier) : null;
        }
    }

    @Override
    public M getModel() {
        return this.getModel(null);
    }

    @Override
    public Identifier getTexture(KoiFishEntity entity) {
        return KoiFishEntityRenderer.getTexture(entity, KoiFishEntity.COL_BASE);
    }

    // ---

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

    public static String getTexture(KoiSize size, TrackedData<KoiColor> variantType, KoiColor color) {
        String variantTypeName = KoiFishEntity.getNameForVariantType(variantType);
        return String.format("%s_%s%s", size.toString(), variantTypeName.isEmpty() ? "" : variantTypeName + "_", color.toString());
    }

    public static Identifier getTexture(KoiFishEntity entity, TrackedData<KoiColor> variantType) {
        return TEXTURES_MAP.get(getTexture(entity.getSize(), variantType, entity.getDataTracker().get(variantType)));
    }
}
