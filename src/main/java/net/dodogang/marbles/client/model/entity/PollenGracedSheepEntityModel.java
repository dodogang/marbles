package net.dodogang.marbles.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.dodogang.marbles.entity.PollenGracedSheepEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

import java.util.List;

@SuppressWarnings({"FieldCanBeLocal","unused"})
@Environment(EnvType.CLIENT)
public class PollenGracedSheepEntityModel<E extends PollenGracedSheepEntity> extends SinglePartEntityModel<E> {
    private final ModelPart root;
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

    public PollenGracedSheepEntityModel(ModelPart root) {
        this.root           = root;

        this.body           = root.getChild("body");
        this.bodyCube       = body.getChild("body_cube");

        this.bodyFur        = body.getChild("body_fur");
        this.bodyFurCube    = bodyFur.getChild("body_fur_cube");

        this.head           = root.getChild("head");
        this.headFur        = head.getChild("head_fur");

        this.backLeftLeg    = root.getChild("back_left_leg");
        this.backRightLeg   = root.getChild("back_right_leg");
        this.frontLeftLeg   = root.getChild("front_left_leg");
        this.frontRightLeg  = root.getChild("front_right_leg");

        this.fur            = ImmutableList.of(this.bodyFur, this.headFur);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
            "body",
            ModelPartBuilder.create(),
            ModelTransform.pivot(0.0F, 5.0F, 2.0F)
        );
        body.addChild(
            "body_cube",
            ModelPartBuilder.create()
                .uv(0, 14)
                .cuboid(-4.0F, -29.0F, -5.0F, 8.0F, 16.0F, 6.0F),
            ModelTransform.of(0.0F, 2.0F, 19.0F, 1.5708F, 0.0F, 0.0F)
        );

        ModelPartData bodyFur = body.addChild(
            "body_fur",
            ModelPartBuilder.create(),
            ModelTransform.NONE
        );
        bodyFur.addChild(
            "body_fur_cube",
            ModelPartBuilder.create()
                .uv(0, 36)
                .cuboid(-7.0F, -29.75F, -8.0F, 14.0F, 20.0F, 13.0F),
            ModelTransform.of(0.0F, 2.0F, 19.0F, 1.5708F, 0.0F, 0.0F)
        );

        ModelPartData head = root.addChild(
            "head",
            ModelPartBuilder.create()
                .uv(0, 0)
                .cuboid(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 8.0F)

                .uv(44, 17)
                .cuboid(-4.0F, -5.0F, -5.0F, 8.0F, 8.0F, 6.0F, new Dilation(-0.1F)),
            ModelTransform.pivot(0.0F, 6.0F, -8.0F)
        );
        head.addChild(
            "head_fur",
            ModelPartBuilder.create()
                .uv(28, 0)
                .cuboid(-4.0F, -6.0F, -7.0F, 9.0F, 5.0F, 8.0F),
            ModelTransform.pivot(-0.5F, 0.0F, 0.0F)
        );

        root.addChild(
            "back_left_leg",
            ModelPartBuilder.create()
                .uv(28, 20)
                .cuboid(-8.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, true)

                .uv(44, 31)
                .cuboid(-9.0F, -0.1F, -3.0F, 6.0F, 7.0F, 6.0F, true),
            ModelTransform.pivot(3.0F, 12.0F, 7.0F)
        );
        root.addChild(
            "back_right_leg",
            ModelPartBuilder.create()
                .uv(28, 20)
                .cuboid(4.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F)

                .uv(44, 31)
                .cuboid(3.0F, -0.1F, -3.0F, 6.0F, 7.0F, 6.0F),
            ModelTransform.pivot(-3.0F, 12.0F, 7.0F)
        );
        root.addChild(
            "front_left_leg",
            ModelPartBuilder.create()
                .uv(28, 20)
                .cuboid(-8.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, true)

                .uv(44, 31)
                .cuboid(-9.0F, -0.1F, -3.0F, 6.0F, 7.0F, 6.0F, true),
            ModelTransform.pivot(3.0F, 12.0F, -5.0F)
        );
        root.addChild(
            "front_right_leg",
            ModelPartBuilder.create()
                .uv(28, 20)
                .cuboid(4.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F)

                .uv(44, 31)
                .cuboid(3.0F, -0.1F, -3.0F, 6.0F, 7.0F, 6.0F),
            ModelTransform.pivot(-3.0F, 12.0F, -5.0F)
        );

        return TexturedModelData.of(data, 128, 128);
    }

    @Override
    public void animateModel(E entity, float limbAngle, float limbDistance, float tickDelta) {
        super.animateModel(entity, limbAngle, limbDistance, tickDelta);
        this.head.pivotY = 6.0F + entity.getNeckAngle(tickDelta) * 9.0F;
        this.headPitchModifier = entity.getHeadAngle(tickDelta);
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
    public ModelPart getPart() {
        return this.root;
    }

    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body, this.backLeftLeg, this.backRightLeg, this.frontLeftLeg, this.frontRightLeg);
    }
}
