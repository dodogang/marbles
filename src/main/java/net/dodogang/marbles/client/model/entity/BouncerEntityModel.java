package net.dodogang.marbles.client.model.entity;

import net.dodogang.marbles.entity.BouncerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("FieldCanBeLocal")
@Environment(EnvType.CLIENT)
public class BouncerEntityModel extends SinglePartEntityModel<BouncerEntity> {
    private final ModelPart root;

    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart arms;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart legs;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public BouncerEntityModel(ModelPart root) {
        this.root       = root;

        this.body       = root.getChild("body");
        this.head       = body.getChild("head");

        this.arms       = body.getChild("arms");
        this.leftArm    = arms.getChild("left_arm");
        this.rightArm   = arms.getChild("right_arm");

        this.legs       = body.getChild("legs");
        this.leftLeg    = legs.getChild("left_leg");
        this.rightLeg   = legs.getChild("right_leg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();

        ModelPartData root = data.getRoot();
        ModelPartData body = root.addChild(
            "body",
            ModelPartBuilder.create()
                .uv(0, 16)
                .cuboid(-5.0F, -13.0F, -2.0F, 10.0F, 13.0F, 5.0F),
            ModelTransform.pivot(0.0F, 12.0F, 0.0F)
        );
        body.addChild(
            "head",
            ModelPartBuilder.create()
                .uv(0, 0)
                .cuboid(-4.0F, -11.9962F, -3.9128F, 8.0F, 8.0F, 8.0F),
            ModelTransform.pivot(0.0F, -9.0F, 0.5F)
        );

        ModelPartData arms = body.addChild(
            "arms",
            ModelPartBuilder.create(),
            ModelTransform.pivot(0.0F, -10.0F, 0.0F)
        );
        arms.addChild(
            "left_arm",
            ModelPartBuilder.create()
                .uv(32, 0)
                .cuboid(-4.0F, -3.0F, -2.0F, 4.0F, 13.0F, 4.0F),
            ModelTransform.pivot(-5.0F, 0.0F, 0.5F)
        );
        arms.addChild(
            "right_arm",
            ModelPartBuilder.create()
                .uv(32, 0)
                .cuboid(-4.0F, -3.0F, -2.0F, 4.0F, 13.0F, 4.0F, true),
            ModelTransform.pivot(9.0F, 0.0F, 0.5F)
        );

        ModelPartData legs = body.addChild(
            "legs",
            ModelPartBuilder.create(),
            ModelTransform.NONE
        );
        legs.addChild(
            "left_leg",
            ModelPartBuilder.create()
                .uv(30, 17)
                .cuboid(-1.0F, -1.0F, -2.0F, 5.0F, 12.0F, 5.0F, true),
            ModelTransform.pivot(-4.0F, 1.0F, 0.0F)
        );
        legs.addChild(
            "right_leg",
            ModelPartBuilder.create()
                .uv(30, 17)
                .cuboid(-5.0F, -1.0F, -2.0F, 5.0F, 12.0F, 5.0F, false),
            ModelTransform.pivot(5.0F, 1.0F, 0.0F)
        );

        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public void setAngles(BouncerEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;

        this.rightLeg.pitch = -1.5F * MathHelper.wrap(limbAngle, 13.0F) * limbDistance;
        this.leftLeg.pitch = 1.5F * MathHelper.wrap(limbAngle, 13.0F) * limbDistance;

        this.rightLeg.yaw = 0.0F;
        this.leftLeg.yaw = 0.0F;
    }

    @Override
    public void animateModel(BouncerEntity entity, float limbAngle, float limbDistance, float tickDelta) {
        int attackTicksLeft = entity.getAttackTicksLeft();
        if (attackTicksLeft > 0) {
            this.rightArm.pitch = -2.0F + 1.5F * MathHelper.wrap((float)attackTicksLeft, 10.0F);
            this.leftArm.pitch = -2.0F + 1.5F * MathHelper.wrap((float)attackTicksLeft, 10.0F);
        } else {
            LivingEntity target = entity.getTarget();
            boolean targetInProximity = target != null && entity.shouldAngerAt(target) && target.isOnGround() && entity.squaredDistanceTo(target) <= 12.0d;

            if (targetInProximity) {
                limbAngle = 0;
                limbDistance = 130;
            }

            this.rightArm.pitch = (-0.2F + 1.5F * MathHelper.wrap(limbAngle, 13.0F)) * limbDistance;
            this.leftArm.pitch = targetInProximity ? this.rightArm.pitch : (-0.2F - 1.5F * MathHelper.wrap(limbAngle, 13.0F)) * limbDistance;
        }
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}
