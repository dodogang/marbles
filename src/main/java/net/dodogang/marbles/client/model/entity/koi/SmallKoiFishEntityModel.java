package net.dodogang.marbles.client.model.entity.koi;

import com.google.common.collect.ImmutableList;
import net.dodogang.marbles.entity.KoiFishEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;

@SuppressWarnings({ "FieldCanBeLocal", "unused" })
@Environment(EnvType.CLIENT)
public class SmallKoiFishEntityModel extends AbstractKoiFishEntityModel<KoiFishEntity> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftFin;
    private final ModelPart rightFin;
    private final ModelPart dorsalFin;
    private final ModelPart analFin;
    private final ModelPart tail;
    private final ModelPart tailFin;

    public SmallKoiFishEntityModel(ModelPart root) {
        super(root);

        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.leftFin = body.getChild("left_fin");
        this.rightFin = body.getChild("right_fin");
        this.dorsalFin = body.getChild("dorsal_fin");
        this.analFin = body.getChild("anal_fin");
        this.tail = body.getChild("tail");
        this.tailFin = tail.getChild("tail_fin");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
            "body",
            ModelPartBuilder.create()
                .uv(0, 0)
                .mirrored(false)
                .cuboid(-2.5F, -3.0F, -4.0F, 5.0F, 5.0F, 9.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 22.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData head = body.addChild(
            "head",
            ModelPartBuilder.create()
                .uv(19, 2)
                .mirrored(false)
                .cuboid(-1.5F, -2.25F, -6.5F, 4.0F, 4.0F, 3.0F, new Dilation(0.0F)),
            ModelTransform.of(-0.5F, 0.25F, -0.5F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftFin = body.addChild(
            "left_fin",
            ModelPartBuilder.create()
                .uv(18, 16)
                .mirrored(false)
                .cuboid(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F, new Dilation(0.0F)),
            ModelTransform.of(2.5F, 1.0F, 0.0F, 0.0F, 0.0F, -0.3927F)
        );

        ModelPartData rightFin = body.addChild(
            "right_fin",
            ModelPartBuilder.create()
                .uv(18, 16)
                .mirrored(false)
                .cuboid(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F, new Dilation(0.0F)),
            ModelTransform.of(-2.5F, 1.0F, 0.0F, 0.0F, 0.0F, 0.3927F)
        );

        ModelPartData upFin = body.addChild(
            "dorsal_fin",
            ModelPartBuilder.create()
                .uv(10, 7)
                .mirrored(false)
                .cuboid(1.0F, -7.0F, -3.0F, 0.0F, 2.0F, 7.0F, new Dilation(0.0F)),
            ModelTransform.of(-0.5F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData downFin = body.addChild(
            "anal_fin",
            ModelPartBuilder.create()
                .uv(10, 8)
                .mirrored(false)
                .cuboid(0.5F, 0.0F, -5.0F, 0.0F, 3.0F, 10.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData tail = body.addChild(
            "tail",
            ModelPartBuilder.create()
                .uv(0, 14)
                .mirrored(false)
                .cuboid(-1.5F, -1.5F, 1.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, -0.5F, 4.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData tailFin = tail.addChild(
            "tail_fin",
            ModelPartBuilder.create()
                .uv(0, 15)
                .mirrored(false)
                .cuboid(0.0F, -2.75F, 1.0F, 0.0F, 5.0F, 5.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.25F, 1.0F, 0.0F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public Iterable<ModelPart> getFinsYawNormal() {
        return ImmutableList.of(this.tailFin);
    }
}
