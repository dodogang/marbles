package net.dodogang.marbles.client.render.entity;

import net.dodogang.marbles.client.init.MarblesEntityModelLayers;
import net.dodogang.marbles.client.model.entity.koi.*;
import net.dodogang.marbles.client.render.entity.feature.KoiFishEntityAppendageFeatureRenderer;
import net.dodogang.marbles.entity.KoiFishEntity;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class KoiFishEntityRenderer<T extends KoiFishEntity, M extends AbstractKoiFishEntityModel<T>> extends MobEntityRenderer<T, M> {
    public static final Identifier TEXTURE_SMALL = MarblesEntities.texture("koi_fish/koi_fish_small");
    public static final Identifier TEXTURE_LARGE = MarblesEntities.texture("koi_fish/koi_fish_large");
    public static final Identifier TEXTURE_THICC = MarblesEntities.texture("koi_fish/koi_fish_thicc");
    public static final Identifier TEXTURE_DUMMY_THICC = MarblesEntities.texture("koi_fish/koi_fish_dummy_thicc");

    protected final SmallKoiFishEntityModel modelSmall;
    protected final LargeKoiFishEntityModel modelLarge;
    protected final ThiccKoiFishEntityModel modelThicc;
    protected final DummyThiccKoiFishEntityModel modelDummyThicc;

    public KoiFishEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, null, 0.15f);

        // this.addFeature(new KoiFishEntityAppendageFeatureRenderer<>(this));

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

    @Nullable
    public RenderLayer getRenderLayer(T entity, boolean showBody, boolean translucent, boolean showOutline, Identifier identifier) {
        if (translucent) {
            return RenderLayer.getItemEntityTranslucentCull(identifier);
        } else if (showBody) {
            return this.getModel(entity).getLayer(identifier);
        } else {
            return showOutline ? RenderLayer.getOutline(identifier) : null;
        }
    }

    @SuppressWarnings("unchecked")
    public M getModel(T entity) {
        return switch (entity.getSize()) {
            default -> (M) this.modelSmall;
            case LARGE -> (M) this.modelLarge;
            case THICC -> (M) this.modelThicc;
            case DUMMY_THICC -> (M) this.modelDummyThicc;
        };
    }

    @Override
    public Identifier getTexture(KoiFishEntity entity) {
        return switch (entity.getSize()) {
            default -> TEXTURE_SMALL;
            case LARGE -> TEXTURE_LARGE;
            case THICC -> TEXTURE_THICC;
            case DUMMY_THICC -> TEXTURE_DUMMY_THICC;
        };
    }
}
