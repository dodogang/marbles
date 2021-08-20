package net.dodogang.marbles.client.model.entity.koi;

import com.google.common.collect.ImmutableList;
import net.dodogang.marbles.entity.KoiFishEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;

@SuppressWarnings({ "FieldCanBeLocal", "unused" })
@Environment(EnvType.CLIENT)
public class DummyThiccKoiFishEntityModel extends AbstractKoiFishEntityModel<KoiFishEntity> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftFin;
    private final ModelPart rightFin;
    private final ModelPart dorsalFin;
    private final ModelPart analFin;
    private final ModelPart tail;
    private final ModelPart tailFin;

    public DummyThiccKoiFishEntityModel(ModelPart root) {
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
                .cuboid(-7.0F, -6.5F, -7.0F, 14.0F, 14.0F, 20.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 16.5F, -3.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData head = body.addChild(
            "head",
            ModelPartBuilder.create()
                .uv(48, 3)
                .mirrored(false)
                .cuboid(-5.5F, -5.5F, -6.75F, 11.0F, 11.0F, 6.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 1.0F, -6.25F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftFin = body.addChild(
            "left_fin",
            ModelPartBuilder.create()
                .uv(39, 39)
                .mirrored(false)
                .cuboid(0.0F, 0.0F, -2.0F, 0.0F, 4.0F, 4.0F, new Dilation(0.0F)),
            ModelTransform.of(7.0F, 2.5F, 2.0F, 0.0F, 0.0F, -0.3927F)
        );

        ModelPartData rightFin = body.addChild(
            "right_fin",
            ModelPartBuilder.create()
                .uv(39, 39)
                .mirrored(false)
                .cuboid(0.0F, 0.0F, -2.0F, 0.0F, 4.0F, 4.0F, new Dilation(0.0F)),
            ModelTransform.of(-7.0F, 2.5F, 2.0F, 0.0F, 0.0F, 0.3927F)
        );

        ModelPartData dorsalFin = body.addChild(
            "dorsal_fin",
            ModelPartBuilder.create()
                .uv(27, 22)
                .mirrored(false)
                .cuboid(0.0F, -4.0F, -6.0F, 0.0F, 4.0F, 13.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, -6.5F, 5.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData analFin = body.addChild(
            "anal_fin",
            ModelPartBuilder.create()
                .uv(27, 26)
                .mirrored(false)
                .cuboid(-1.0F, -1.0F, -7.75F, 0.0F, 5.0F, 16.0F, new Dilation(0.0F)),
            ModelTransform.of(1.0F, 7.5F, 3.75F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData tail = body.addChild(
            "tail",
            ModelPartBuilder.create()
                .uv(0, 34)
                .mirrored(false)
                .cuboid(-5.0F, -5.0F, 1.0F, 10.0F, 10.0F, 2.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.5F, 12.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData tailFin = tail.addChild(
            "tail_fin",
            ModelPartBuilder.create()
                .uv(0, 34)
                .mirrored(false)
                .cuboid(0.0F, -6.0F, 2.0F, 0.0F, 11.0F, 13.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(data, 128, 128);
    }

    @Override
    public Iterable<ModelPart> getFinsYawNormal() {
        return ImmutableList.of(this.tailFin);
    }
}
