package net.dodogang.marbles.client.model.entity.koi;

import com.google.common.collect.ImmutableList;
import net.dodogang.marbles.entity.KoiFishEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;

@SuppressWarnings({ "FieldCanBeLocal", "unused" })
@Environment(EnvType.CLIENT)
public class LargeKoiFishEntityModel extends AbstractKoiFishEntityModel<KoiFishEntity> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftFin;
    private final ModelPart rightFin;
    private final ModelPart dorsalFin;
    private final ModelPart analFin;
    private final ModelPart tail;
    private final ModelPart tailFin;
    private final ModelPart leftVentralFin;
    private final ModelPart rightVentralFin;

    public LargeKoiFishEntityModel(ModelPart root) {
        super(root);

        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.leftFin = body.getChild("left_fin");
        this.rightFin = body.getChild("right_fin");
        this.dorsalFin = body.getChild("dorsal_fin");
        this.analFin = body.getChild("anal_fin");
        this.tail = body.getChild("tail");
        this.tailFin = tail.getChild("tail_fin");
        this.rightVentralFin = body.getChild("right_ventral_fin");
        this.leftVentralFin = body.getChild("left_ventral_fin");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
            "body",
            ModelPartBuilder.create()
                .uv(0, 0)
                .mirrored(false)
                .cuboid(-3.5F, -7.0F, -5.0F, 7.0F, 7.0F, 11.0F),
            ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData head = body.addChild(
            "head",
            ModelPartBuilder.create()
                .uv(25, 3)
                .mirrored(false)
                .cuboid(-2.0F, -3.0F, -6.75F, 5.0F, 5.0F, 3.0F),
            ModelTransform.of(-0.5F, -3.0F, -1.25F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftFin = body.addChild(
            "left_fin",
            ModelPartBuilder.create()
                .uv(13, 28)
                .mirrored(false)
                .cuboid(0.0F, 0.0F, -3.0F, 0.0F, 3.0F, 3.0F),
            ModelTransform.of(3.5F, -2.0F, -1.0F, 0.0F, 0.0F, -0.3927F)
        );

        ModelPartData rightFin = body.addChild(
            "right_fin",
            ModelPartBuilder.create()
                .uv(15, 28)
                .mirrored(false)
                .cuboid(0.0F, 0.0F, -3.0F, 0.0F, 3.0F, 3.0F),
            ModelTransform.of(-3.5F, -2.0F, -1.0F, 0.0F, 0.0F, 0.3927F)
        );

        ModelPartData dorsalFin = body.addChild(
            "dorsal_fin",
            ModelPartBuilder.create()
                .uv(0, 10)
                .mirrored(false)
                .cuboid(0.0F, -3.0F, -3.0F, 0.0F, 3.0F, 8.0F),
            ModelTransform.of(0.0F, -7.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData analFin = body.addChild(
            "anal_fin",
            ModelPartBuilder.create()
                .uv(0, 11)
                .mirrored(false)
                .cuboid(0.0F, 0.0F, -5.0F, 0.0F, 3.0F, 11.0F),
            ModelTransform.of(-0.25F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData tail = body.addChild(
            "tail",
            ModelPartBuilder.create()
                .uv(36, 11)
                .mirrored(false)
                .cuboid(-2.5F, -2.5F, 1.0F, 5.0F, 5.0F, 2.0F),
            ModelTransform.of(0.0F, -3.5F, 5.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData tailFin = tail.addChild(
            "tail_fin",
            ModelPartBuilder.create()
                .uv(0, 21)
                .mirrored(false)
                .cuboid(0.0F, -3.5F, 1.0F, 0.0F, 7.0F, 5.0F),
            ModelTransform.of(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftVentralFin = body.addChild(
            "left_ventral_fin",
            ModelPartBuilder.create()
                .uv(4, 1)
                .mirrored(false)
                .cuboid(0.0F, 0.0F, -3.0F, 0.0F, 3.0F, 3.0F),
            ModelTransform.of(1.5F, 0.0F, 3.0F, 0.0F, 0.0F, -1.1781F)
        );

        ModelPartData rightVentralFin = body.addChild(
            "right_ventral_fin",
            ModelPartBuilder.create()
                .uv(4, 1)
                .mirrored(false)
                .cuboid(0.0F, 0.0F, -3.0F, 0.0F, 3.0F, 3.0F),
            ModelTransform.of(-1.5F, 0.0F, 3.0F, 0.0F, 0.0F, 1.1781F)
        );

        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public Iterable<ModelPart> getFinsYawNormal() {
        return ImmutableList.of(this.tailFin);
    }
}
