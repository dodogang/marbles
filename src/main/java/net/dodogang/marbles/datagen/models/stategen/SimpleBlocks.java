package net.dodogang.marbles.datagen.models.stategen;

import net.dodogang.marbles.datagen.models.modelgen.ModelGen;

import static net.dodogang.marbles.datagen.models.modelgen.InheritingModelGen.*;

@SuppressWarnings("unused")
public abstract class SimpleBlocks {
    public static StateGen predefined(String name) {
        return VariantsBlockStateGen.variants(ModelInfo.create(name));
    }

    public static StateGen simple(String name, ModelGen model) {
        return VariantsBlockStateGen.variants(ModelInfo.create(name, model));
    }

    public static StateGen alternate(String name, ModelGen model1, ModelGen model2) {
        return VariantsBlockStateGen.variants(ModelInfo.create(name + "_1", model1), ModelInfo.create(name + "_2", model2));
    }

    public static StateGen farmland(String name, ModelGen model, ModelGen moist) {
        return VariantsBlockStateGen.variants("moisture=0", ModelInfo.create(name, model))
                                    .variant("moisture=1", ModelInfo.create(name, model))
                                    .variant("moisture=2", ModelInfo.create(name, model))
                                    .variant("moisture=3", ModelInfo.create(name, model))
                                    .variant("moisture=4", ModelInfo.create(name, model))
                                    .variant("moisture=5", ModelInfo.create(name, model))
                                    .variant("moisture=6", ModelInfo.create(name, model))
                                    .variant("moisture=7", ModelInfo.create(name + "_moist", moist));
    }

    public static StateGen dualConnecting(String name) {
        return VariantsBlockStateGen.variants("connection=none", ModelInfo.create(name, cubeAll(name)))
                                    .variant("connection=up", ModelInfo.create(name + "_up", cubeColumn(name, name + "_bottom")))
                                    .variant("connection=down", ModelInfo.create(name + "_down", cubeColumn(name, name + "_top")))
                                    .variant("connection=north", ModelInfo.create(name + "_north", cube(name, name + "_left", name, name + "_right", name + "_bottom", name + "_top")))
                                    .variant("connection=south", ModelInfo.create(name + "_south", cube(name, name + "_right", name, name + "_left", name + "_top", name + "_bottom")))
                                    .variant("connection=west", ModelInfo.create(name + "_east", cube(name + "_left", name, name + "_right", name, name + "_right", name + "_right")))
                                    .variant("connection=east", ModelInfo.create(name + "_west", cube(name + "_right", name, name + "_left", name, name + "_left", name + "_left")));
    }

    public static StateGen axisRotated(String name, ModelGen model) {
        return VariantsBlockStateGen.variants("axis=y", ModelInfo.create(name, model).rotate(0, 0))
                                    .variant("axis=z", ModelInfo.create(name, model).rotate(90, 0))
                                    .variant("axis=x", ModelInfo.create(name, model).rotate(90, 90));
    }

    public static StateGen facingRotated(String name, ModelGen model) {
        return VariantsBlockStateGen.variants("facing=up", ModelInfo.create(name, model).rotate(0, 0))
                                    .variant("facing=down", ModelInfo.create(name, model).rotate(180, 0))
                                    .variant("facing=north", ModelInfo.create(name, model).rotate(90, 0))
                                    .variant("facing=east", ModelInfo.create(name, model).rotate(90, 90))
                                    .variant("facing=south", ModelInfo.create(name, model).rotate(90, 180))
                                    .variant("facing=west", ModelInfo.create(name, model).rotate(90, 270));
    }

    public static StateGen facingHorizontalRotated(String name, ModelGen model) {
        return VariantsBlockStateGen.variants("facing=north", ModelInfo.create(name, model).rotate(0, 0))
                                    .variant("facing=east", ModelInfo.create(name, model).rotate(0, 90))
                                    .variant("facing=south", ModelInfo.create(name, model).rotate(0, 180))
                                    .variant("facing=west", ModelInfo.create(name, model).rotate(0, 270));
    }

    public static StateGen randomRotationY(String name, ModelGen model) {
        return VariantsBlockStateGen.variants(
            ModelInfo.create(name, model).rotate(0, 0),
            ModelInfo.create(name, model).rotate(0, 90),
            ModelInfo.create(name, model).rotate(0, 180),
            ModelInfo.create(name, model).rotate(0, 270)
        );
    }

    public static StateGen snowyBlock(String name, ModelGen model, ModelGen snowy) {
        return VariantsBlockStateGen.variants(
            "snowy=false",
            ModelInfo.create(name, model)
        ).variant(
            "snowy=true",
            ModelInfo.create(name + "_snowy", snowy)
        );
    }

    public static StateGen randomRotationXY(String name, ModelGen model) {
        return VariantsBlockStateGen.variants(
            ModelInfo.create(name, model).rotate(0, 0),
            ModelInfo.create(name, model).rotate(0, 90),
            ModelInfo.create(name, model).rotate(0, 180),
            ModelInfo.create(name, model).rotate(0, 270),
            ModelInfo.create(name, model).rotate(90, 0),
            ModelInfo.create(name, model).rotate(90, 90),
            ModelInfo.create(name, model).rotate(90, 180),
            ModelInfo.create(name, model).rotate(90, 270),
            ModelInfo.create(name, model).rotate(180, 0),
            ModelInfo.create(name, model).rotate(180, 90),
            ModelInfo.create(name, model).rotate(180, 180),
            ModelInfo.create(name, model).rotate(180, 270),
            ModelInfo.create(name, model).rotate(270, 0),
            ModelInfo.create(name, model).rotate(270, 90),
            ModelInfo.create(name, model).rotate(270, 180),
            ModelInfo.create(name, model).rotate(270, 270)
        );
    }

    public static StateGen doubleBlock(String lower, ModelGen lowerModel, String upper, ModelGen upperModel) {
        return VariantsBlockStateGen.variants("half=lower", ModelInfo.create(lower, lowerModel))
                                    .variant("half=upper", ModelInfo.create(upper, upperModel));
    }
}
