package net.dodogang.marbles.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.dodogang.marbles.entity.PollenGracedSheepEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
@Environment(EnvType.CLIENT)
public class PollenGracedSheepEntityModel<E extends PollenGracedSheepEntity> extends CompositeEntityModel<E> {
    private final ModelPart body;
    private final ModelPart bodyCube;
    private final ModelPart bodyFur;
    private final ModelPart bodyFurCube;
    private final ModelPart head;
    private final ModelPart headFur;
    private final ModelPart backLeftLeg;
    private final ModelPart backRightLeg;
    private final ModelPart frontLeftLeg;
    private final ModelPart frontRightLeg;

    private final List<ModelPart> fur;
    private float headPitchModifier;

    public PollenGracedSheepEntityModel() {
        textureWidth = 128;
        textureHeight = 128;
        body = new ModelPart(this);
        body.setPivot(0.0F, 5.0F, 2.0F);

        bodyCube = new ModelPart(this);
        bodyCube.setPivot(0.0F, 2.0F, 19.0F);
        body.addChild(bodyCube);
        setRotationAngle(bodyCube, 1.5708F, 0.0F, 0.0F);
        bodyCube.setTextureOffset(0, 14).addCuboid(-4.0F, -29.0F, -5.0F, 8.0F, 16.0F, 6.0F, 0.0F, false);

        bodyFur = new ModelPart(this);
        bodyFur.setPivot(0.0F, 0.0F, 0.0F);
        body.addChild(bodyFur);

        bodyFurCube = new ModelPart(this);
        bodyFurCube.setPivot(0.0F, 2.0F, 19.0F);
        bodyFur.addChild(bodyFurCube);
        setRotationAngle(bodyFurCube, 1.5708F, 0.0F, 0.0F);
        bodyFurCube.setTextureOffset(0, 36).addCuboid(-7.0F, -29.75F, -8.0F, 14.0F, 20.0F, 13.0F, 0.0F, false);

        head = new ModelPart(this);
        head.setPivot(0.0F, 6.0F, -8.0F);
        head.setTextureOffset(0, 0).addCuboid(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 8.0F, 0.0F, false);
        head.setTextureOffset(44, 17).addCuboid(-4.0F, -5.0F, -5.0F, 8.0F, 8.0F, 6.0F, -0.1F, false);

        headFur = new ModelPart(this);
        headFur.setPivot(-0.5F, 0.0F, 0.0F);
        head.addChild(headFur);
        headFur.setTextureOffset(28, 0).addCuboid(-4.0F, -6.0F, -7.0F, 9.0F, 5.0F, 8.0F, 0.0F, false);

        backLeftLeg = new ModelPart(this);
        backLeftLeg.setPivot(3.0F, 12.0F, 7.0F);
        backLeftLeg.setTextureOffset(28, 20).addCuboid(-8.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);
        backLeftLeg.setTextureOffset(44, 31).addCuboid(-9.0F, -0.1F, -3.0F, 6.0F, 7.0F, 6.0F, 0.0F, true);

        backRightLeg = new ModelPart(this);
        backRightLeg.setPivot(-3.0F, 12.0F, 7.0F);
        backRightLeg.setTextureOffset(28, 20).addCuboid(4.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        backRightLeg.setTextureOffset(44, 31).addCuboid(3.0F, -0.1F, -3.0F, 6.0F, 7.0F, 6.0F, 0.0F, false);

        frontLeftLeg = new ModelPart(this);
        frontLeftLeg.setPivot(3.0F, 12.0F, -5.0F);
        frontLeftLeg.setTextureOffset(28, 20).addCuboid(-8.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);
        frontLeftLeg.setTextureOffset(44, 31).addCuboid(-9.0F, -0.1F, -3.0F, 6.0F, 7.0F, 6.0F, 0.0F, true);

        frontRightLeg = new ModelPart(this);
        frontRightLeg.setPivot(-3.0F, 12.0F, -5.0F);
        frontRightLeg.setTextureOffset(28, 20).addCuboid(4.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        frontRightLeg.setTextureOffset(44, 31).addCuboid(3.0F, -0.1F, -3.0F, 6.0F, 7.0F, 6.0F, 0.0F, false);

        this.fur = ImmutableList.of(this.bodyFur, this.headFur);
    }

    @Override
    public void animateModel(E entity, float f, float g, float h) {
        super.animateModel(entity, f, g, h);
        this.head.pivotY = 6.0F + entity.getNeckAngle(h) * 9.0F;
        this.headPitchModifier = entity.getHeadAngle(h);
    }

    @Override
    public void setAngles(E entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        fur.forEach(part -> part.visible = !entity.isSheared());

        this.head.pitch = headPitch * 0.017453292F;
        this.head.yaw = headYaw * 0.017453292F;
        this.backRightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.backLeftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.frontRightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.frontLeftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;

        this.head.pitch = this.headPitchModifier;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.headFur.visible = !this.child;

        if (this.child) {
            /*
             * HEAD
             */

            matrices.push();

            matrices.translate(0.0D, 0.5f, 0.25f);
            this.head.render(matrices, vertices, light, overlay, red, green, blue, alpha);

            matrices.pop();

            /*
             * BODY
             */

            matrices.push();

            float scale = 0.5f;
            matrices.scale(scale, scale, scale);
            matrices.translate(0.0D, 1.5f, 0.0D);

            this.getBodyParts().forEach((part) -> part.render(matrices, vertices, light, overlay, red, green, blue, alpha));

            matrices.pop();
        } else {
            this.head.render(matrices, vertices, light, overlay, red, green, blue, alpha);
            this.getBodyParts().forEach((part) -> part.render(matrices, vertices, light, overlay, red, green, blue, alpha));
        }
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(body, head, backLeftLeg, backRightLeg, frontLeftLeg, frontRightLeg);
    }
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(body, backLeftLeg, backRightLeg, frontLeftLeg, frontRightLeg);
    }

    public void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;
    }
}
