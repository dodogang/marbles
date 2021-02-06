package net.dodogang.marbles.data.models.stategen;

import net.dodogang.marbles.data.models.modelgen.ModelGen;

public abstract class SimpleBlocks {
    public static StateGen predefined(String name) {
        return VariantsBlockStateGen.variants(ModelInfo.create(name));
    }

    public static StateGen simple(String name, ModelGen model) {
        return VariantsBlockStateGen.variants(ModelInfo.create(name, model));
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

    public static StateGen randomRotationY(String name, ModelGen model) {
        return VariantsBlockStateGen.variants(
            ModelInfo.create(name, model).rotate(0, 0),
            ModelInfo.create(name, model).rotate(0, 90),
            ModelInfo.create(name, model).rotate(0, 180),
            ModelInfo.create(name, model).rotate(0, 270)
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
