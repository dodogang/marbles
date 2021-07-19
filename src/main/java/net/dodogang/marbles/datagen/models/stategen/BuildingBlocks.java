package net.dodogang.marbles.datagen.models.stategen;

import net.dodogang.marbles.block.QuadAttachingBlock;
import net.dodogang.marbles.block.enums.QuadBlockPart;
import net.dodogang.marbles.datagen.models.modelgen.InheritingModelGen;
import net.dodogang.marbles.datagen.models.modelgen.ModelGen;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.util.math.Direction;

import static net.dodogang.marbles.datagen.models.modelgen.InheritingModelGen.fencePost;
import static net.dodogang.marbles.datagen.models.modelgen.InheritingModelGen.fenceSide;

public abstract class BuildingBlocks {
    public static StateGen fence(String name, String texName) {
        return MultipartBlockStateGen.multipart()
                                     .part(ModelInfo.create(name + "_post", fencePost(texName)))
                                     .part(
                                         Selector.and().condition("north", "true"),
                                         ModelInfo.create(name + "_side", fenceSide(texName))
                                     )
                                     .part(
                                         Selector.and().condition("east", "true"),
                                         ModelInfo.create(name + "_side", fenceSide(texName))
                                                  .rotate(0, 90)
                                                  .uvlock(true)
                                     )
                                     .part(
                                         Selector.and().condition("south", "true"),
                                         ModelInfo.create(name + "_side", fenceSide(texName))
                                                  .rotate(0, 180)
                                                  .uvlock(true)
                                     )
                                     .part(
                                         Selector.and().condition("west", "true"),
                                         ModelInfo.create(name + "_side", fenceSide(texName))
                                                  .rotate(0, 270)
                                                  .uvlock(true)
                                     );
    }

    private static StateGen wallGeneric(String name, ModelGen post, ModelGen side, ModelGen tall) {
        ModelInfo postC = ModelInfo.create(name + "_post", post);
        ModelInfo sideN = ModelInfo.create(name + "_side", side).uvlock(true).rotate(0, 0);
        ModelInfo sideE = ModelInfo.create(name + "_side", side).uvlock(true).rotate(0, 90);
        ModelInfo sideS = ModelInfo.create(name + "_side", side).uvlock(true).rotate(0, 180);
        ModelInfo sideW = ModelInfo.create(name + "_side", side).uvlock(true).rotate(0, 270);
        ModelInfo sideTallN = ModelInfo.create(name + "_side_tall", tall).uvlock(true).rotate(0, 0);
        ModelInfo sideTallE = ModelInfo.create(name + "_side_tall", tall).uvlock(true).rotate(0, 90);
        ModelInfo sideTallS = ModelInfo.create(name + "_side_tall", tall).uvlock(true).rotate(0, 180);
        ModelInfo sideTallW = ModelInfo.create(name + "_side_tall", tall).uvlock(true).rotate(0, 270);

        return MultipartBlockStateGen.multipart()
                                     .part(Selector.and().condition("up", "true"), postC)
                                     .part(Selector.and().condition("north", "low"), sideN)
                                     .part(Selector.and().condition("east", "low"), sideE)
                                     .part(Selector.and().condition("south", "low"), sideS)
                                     .part(Selector.and().condition("west", "low"), sideW)
                                     .part(Selector.and().condition("north", "tall"), sideTallN)
                                     .part(Selector.and().condition("east", "tall"), sideTallE)
                                     .part(Selector.and().condition("south", "tall"), sideTallS)
                                     .part(Selector.and().condition("west", "tall"), sideTallW);
    }

    public static StateGen wallAll(String name, String texName) {
        return wallGeneric(
            name,
            InheritingModelGen.wallPost(texName),
            InheritingModelGen.wallSide(texName),
            InheritingModelGen.wallSideTall(texName)
        );
    }

    public static StateGen wallSided(String name, String bottom, String top, String side) {
        return wallGeneric(
            name,
            InheritingModelGen.wallSidedPost(bottom, top, side),
            InheritingModelGen.wallSidedSide(bottom, top, side),
            InheritingModelGen.wallSidedSideTall(bottom, top, side)
        );
    }

    public static StateGen wallColumn(String name, String end, String side) {
        return wallSided(name, end, end, side);
    }

    private static StateGen slabGeneric(String name, String doubleName, ModelGen bottom, ModelGen top, ModelGen full) {
        // No overload because we use cubeAll here instead of cubeBottomTop - this makes the difference
        if (doubleName == null) {
            // Generate custom double model
            return VariantsBlockStateGen
                       .variants("type=bottom", ModelInfo.create(name, bottom))
                       .variant("type=top", ModelInfo.create(name + "_top", top))
                       .variant("type=double", ModelInfo.create(name + "_double", full));
        } else {
            // Use existing model
            return VariantsBlockStateGen
                       .variants("type=bottom", ModelInfo.create(name, bottom))
                       .variant("type=top", ModelInfo.create(name + "_top", top))
                       .variant("type=double", ModelInfo.create(doubleName));
        }
    }

    public static StateGen slabAll(String name, String doubleName, String texName) {
        return slabGeneric(
            name, doubleName,
            InheritingModelGen.slabAll(texName),
            InheritingModelGen.slabAllTop(texName),
            doubleName == null ? InheritingModelGen.cubeAll(texName) : null
        );
    }

    public static StateGen slabSided(String name, String doubleName, String bottom, String top, String side) {
        return slabGeneric(
            name, doubleName,
            InheritingModelGen.slab(bottom, top, side),
            InheritingModelGen.slabTop(bottom, top, side),
            doubleName == null ? InheritingModelGen.cubeBottomTop(bottom, top, side) : null
        );
    }

    public static StateGen slabColumn(String name, String doubleName, String end, String side) {
        return slabSided(name, doubleName, end, end, side);
    }

    private static StateGen stairsGeneric(String name, ModelGen inner, ModelGen outer, ModelGen straight) {
        boolean innerM = false; // Set to true after we supplied a modelgen, so we don't generate it twice
        boolean outerM = false;
        boolean stairsM = false;

        VariantsBlockStateGen gen = VariantsBlockStateGen.variants();
        int y = 270;
        for (Direction dir : Direction.Type.HORIZONTAL) {
            for (BlockHalf half : BlockHalf.values()) {
                int x = half == BlockHalf.TOP ? 180 : 0;
                String state = String.format("facing=%s,half=%s", dir.asString(), half.asString());

                String iname = name + "_inner";
                String oname = name + "_outer";

                int yp = y == 0 ? 270 : y - 90;
                int yn = y == 270 ? 0 : y + 90;

                ModelInfo innerL = ModelInfo.create(iname, innerM ? null : inner).rotate(x, x == 180 ? y : yp).uvlock(true);
                ModelInfo outerL = ModelInfo.create(oname, outerM ? null : outer).rotate(x, x == 180 ? y : yp).uvlock(true);
                ModelInfo innerR = ModelInfo.create(iname).rotate(x, x == 180 ? yn : y).uvlock(true);
                ModelInfo outerR = ModelInfo.create(oname).rotate(x, x == 180 ? yn : y).uvlock(true);
                ModelInfo stairs = ModelInfo.create(name, stairsM ? null : straight).rotate(x, y).uvlock(true);

                innerM = true;
                outerM = true;
                stairsM = true;

                gen.variant(state + ",shape=straight", stairs);
                gen.variant(state + ",shape=inner_left", innerL);
                gen.variant(state + ",shape=inner_right", innerR);
                gen.variant(state + ",shape=outer_left", outerL);
                gen.variant(state + ",shape=outer_right", outerR);
            }
            if (y == 270) y = 0;
            else y += 90;
        }
        return gen;
    }

    public static StateGen stairsAll(String name, String texName) {
        return stairsGeneric(
            name,
            InheritingModelGen.stairsAllInner(texName),
            InheritingModelGen.stairsAllOuter(texName),
            InheritingModelGen.stairsAll(texName)
        );
    }

    public static StateGen stairsSided(String name, String bottom, String top, String side) {
        return stairsGeneric(
            name,
            InheritingModelGen.stairsInner(bottom, top, side),
            InheritingModelGen.stairsOuter(bottom, top, side),
            InheritingModelGen.stairs(bottom, top, side)
        );
    }

    public static StateGen stairsColumn(String name, String end, String side) {
        return stairsSided(name, end, end, side);
    }

    public static StateGen attaching(String name, ModelGen model) {
        return VariantsBlockStateGen.variants("facing=up", ModelInfo.create(name, model).rotate(0, 270))
                                    .variant("facing=down", ModelInfo.create(name, model).rotate(180, 0))
                                    .variant("facing=north", ModelInfo.create(name, model).rotate(90, 0))
                                    .variant("facing=east", ModelInfo.create(name, model).rotate(90, 90))
                                    .variant("facing=south", ModelInfo.create(name, model).rotate(90, 180))
                                    .variant("facing=west", ModelInfo.create(name, model).rotate(90, 270));
    }
    public static StateGen fourPartAttaching(String name, ModelGen first, ModelGen second, ModelGen third, ModelGen fourth) {
        VariantsBlockStateGen state = new VariantsBlockStateGen();

        ModelInfo firstModel = ModelInfo.create(name + "_first", first);
        ModelInfo secondModel = ModelInfo.create(name + "_second", second);
        ModelInfo thirdModel = ModelInfo.create(name + "_third", third);
        ModelInfo fourthModel = ModelInfo.create(name, fourth);

        for (Direction dir : QuadAttachingBlock.FACING.getValues()) {
            for (QuadBlockPart part : QuadAttachingBlock.PART.getValues()) {
                ModelInfo model = switch (part) {
                    default -> firstModel;
                    case SECOND -> secondModel;
                    case THIRD -> thirdModel;
                    case FOURTH -> fourthModel;
                };

                state.variant(
                    String.format("facing=%s,quad=%s", dir.asString(), part.asString()),
                    switch (dir) {
                        default -> model.copy().rotate(0, 270);
                        case DOWN -> model.copy().rotate(180, 0);
                        case NORTH -> model.copy().rotate(90, 0);
                        case EAST -> model.copy().rotate(90, 90);
                        case SOUTH -> model.copy().rotate(90, 180);
                        case WEST -> model.copy().rotate(90, 270);
                    }
                );
            }
        }

        return state;
    }
}
