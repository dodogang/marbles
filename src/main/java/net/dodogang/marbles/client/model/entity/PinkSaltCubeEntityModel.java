package net.dodogang.marbles.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.dodogang.marbles.entity.PinkSaltCubeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.Direction;

@Environment(EnvType.CLIENT)
public class PinkSaltCubeEntityModel extends CompositeEntityModel<PinkSaltCubeEntity> {
    private final ModelPart cube;

    public PinkSaltCubeEntityModel() {
        textureWidth = 64;
        textureHeight = 64;

        cube = new ModelPart(this);
        cube.setPivot(0.0F, 24.0F, 0.0F);
        cube.setTextureOffset(0, 0).addCuboid(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
    }

    @Override
    public void setAngles(PinkSaltCubeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        Direction rollDirection = entity.getRollDirection();
        float rotation = (float) entity.getRollProgress() / PinkSaltCubeEntity.MAX_ROLL_PROGRESS;
        switch (rollDirection) {
            default:
            case NORTH:
                this.cube.pitch = -rotation;
                break;
            case SOUTH:
                this.cube.pitch =  rotation;
                break;
            case EAST:
                this.cube.roll  =  rotation;
                break;
            case WEST:
                this.cube.roll  = -rotation;
                break;
        }
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(cube);
    }
}
