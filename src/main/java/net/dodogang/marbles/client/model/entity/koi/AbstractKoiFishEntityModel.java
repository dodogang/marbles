package net.dodogang.marbles.client.model.entity.koi;

import com.google.common.collect.ImmutableList;
import net.dodogang.marbles.entity.KoiFishEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings({ "FieldCanBeLocal", "unused" })
@Environment(EnvType.CLIENT)
public abstract class AbstractKoiFishEntityModel<T extends KoiFishEntity> extends SinglePartEntityModel<T> {
    protected final ModelPart root;

    public AbstractKoiFishEntityModel(ModelPart root) {
        this.root = root;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    // ---

    public Iterable<ModelPart> getFinsYawNormal() {
        return ImmutableList.of();
    }
    public Iterable<ModelPart> getFinsRollSlow() {
        return ImmutableList.of();
    }

    // ---

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float speed = 1.0F;
        if (!entity.isTouchingWater()) {
            speed = 1.5F;
        }

        float normalAng = -speed * 0.45F * MathHelper.sin(0.6F * animationProgress);
        float slowAng = -speed * 0.45F * MathHelper.sin(0.2F * animationProgress);

        for (ModelPart part : this.getFinsYawNormal()) {
            part.yaw = normalAng;
        }
        for (ModelPart part : this.getFinsRollSlow()) {
            part.roll = slowAng;
        }
    }
}
