package net.dodogang.marbles.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.dodogang.marbles.entity.BouncerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("FieldCanBeLocal")
@Environment(EnvType.CLIENT)
public class BouncerEntityModel extends CompositeEntityModel<BouncerEntity> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart arms;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart legs;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public BouncerEntityModel() {
        textureWidth = 64;
        textureHeight = 64;

        body = new ModelPart(this);
        body.setPivot(0.0F, 12.0F, 0.0F);
        body.setTextureOffset(0, 16).addCuboid(-5.0F, -13.0F, -2.0F, 10.0F, 13.0F, 5.0F, 0.0F, false);

        head = new ModelPart(this);
        head.setPivot(0.0F, -9.0F, 0.5F);
        body.addChild(head);
        head.setTextureOffset(0, 0).addCuboid(-4.0F, -11.9962F, -3.9128F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        arms = new ModelPart(this);
        arms.setPivot(0.0F, -10.0F, 0.0F);
        body.addChild(arms);


        leftArm = new ModelPart(this);
        leftArm.setPivot(-5.0F, 0.0F, 0.5F);
        arms.addChild(leftArm);
        leftArm.setTextureOffset(32, 0).addCuboid(-4.0F, -3.0F, -2.0F, 4.0F, 13.0F, 4.0F, 0.0F, false);

        rightArm = new ModelPart(this);
        rightArm.setPivot(9.0F, 0.0F, 0.5F);
        arms.addChild(rightArm);
        rightArm.setTextureOffset(32, 0).addCuboid(-4.0F, -3.0F, -2.0F, 4.0F, 13.0F, 4.0F, 0.0F, true);

        legs = new ModelPart(this);
        legs.setPivot(0.0F, 0.0F, 0.0F);
        body.addChild(legs);


        leftLeg = new ModelPart(this);
        leftLeg.setPivot(-4.0F, 1.0F, 0.0F);
        legs.addChild(leftLeg);
        leftLeg.setTextureOffset(30, 17).addCuboid(-1.0F, -1.0F, -2.0F, 5.0F, 12.0F, 5.0F, 0.0F, true);

        rightLeg = new ModelPart(this);
        rightLeg.setPivot(5.0F, 1.0F, 0.0F);
        legs.addChild(rightLeg);
        rightLeg.setTextureOffset(30, 17).addCuboid(-5.0F, -1.0F, -2.0F, 5.0F, 12.0F, 5.0F, 0.0F, false);
    }

    @Override
    public void setAngles(BouncerEntity ironGolemEntity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;

        this.rightLeg.pitch = -1.5F * MathHelper.method_24504(limbAngle, 13.0F) * limbDistance;
        this.leftLeg.pitch = 1.5F * MathHelper.method_24504(limbAngle, 13.0F) * limbDistance;

        this.rightLeg.yaw = 0.0F;
        this.leftLeg.yaw = 0.0F;
    }

    @Override
    public void animateModel(BouncerEntity entity, float limbAngle, float limbDistance, float tickDelta) {
        int attackTicksLeft = entity.getAttackTicksLeft();
        if (attackTicksLeft > 0) {
            this.rightArm.pitch = -2.0F + 1.5F * MathHelper.method_24504((float)attackTicksLeft, 10.0F);
            this.leftArm.pitch = -2.0F + 1.5F * MathHelper.method_24504((float)attackTicksLeft, 10.0F);
        } else {
            LivingEntity target = entity.getTarget();
            boolean targetInProximity = target != null && entity.shouldAngerAt(target) && target.isOnGround() && entity.squaredDistanceTo(target) <= 12.0d;

            if (targetInProximity) {
                limbAngle = 0;
                limbDistance = 130;
            }

            this.rightArm.pitch = (-0.2F + 1.5F * MathHelper.method_24504(limbAngle, 13.0F)) * limbDistance;
            this.leftArm.pitch = targetInProximity ? this.rightArm.pitch : (-0.2F - 1.5F * MathHelper.method_24504(limbAngle, 13.0F)) * limbDistance;
        }
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.body);
    }
}
