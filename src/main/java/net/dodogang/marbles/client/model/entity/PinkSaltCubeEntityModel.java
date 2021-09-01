package net.dodogang.marbles.client.model.entity;

import net.dodogang.marbles.entity.PinkSaltCubeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class PinkSaltCubeEntityModel<T extends PinkSaltCubeEntity> extends SinglePartEntityModel<T> {
    protected final ModelPart root;

    protected final ModelPart cube;

    public PinkSaltCubeEntityModel(ModelPart root) {
        this.root = root;

        this.cube = root.getChild("cube");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData cube = root.addChild(
            "cube",
            ModelPartBuilder.create()
                .uv(0, 0)
                .cuboid(-8.0f, 8.0f, -8.0f, 16.0f, 16.0f, 16.0f),
            ModelTransform.NONE
        );

        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {}
}
