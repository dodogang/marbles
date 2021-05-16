package net.dodogang.marbles.datagen.models.modelgen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dodogang.marbles.Marbles;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class InheritingModelGen implements ModelGen {
    private final Identifier parent;
    private final Map<String, String> textureRef = new LinkedHashMap<>();

    public InheritingModelGen(Identifier parent) {
        this.parent = parent;
    }
    public InheritingModelGen(String parent) {
        this(Identifier.tryParse(parent));
    }

    public InheritingModelGen texture(String reference, String newReference) {
        textureRef.put(reference, newReference);
        return this;
    }

    public InheritingModelGen texture(String reference, Identifier id) {
        textureRef.put(reference, id.toString());
        return this;
    }

    @Override
    public JsonElement makeJson(Identifier name) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", parent.toString());
        if (!textureRef.isEmpty()) {
            JsonObject textures = new JsonObject();
            for (Map.Entry<String, String> ref : textureRef.entrySet()) {
                textures.addProperty(ref.getKey(), ref.getValue());
            }
            root.add("textures", textures);
        }
        return root;
    }

    public static InheritingModelGen inherit(String parent) {
        return new InheritingModelGen(parent);
    }
    public static InheritingModelGen inherit(Identifier parent) {
        return inherit(parent.toString());
    }

    public static InheritingModelGen cubeAll(String texture) {
        return new InheritingModelGen("block/cube_all")
                   .texture("all", texture);
    }

    public static InheritingModelGen leaves(String texture) {
        return new InheritingModelGen("block/leaves")
                   .texture("all", texture);
    }

    public static InheritingModelGen slabAll(String texture) {
        return new InheritingModelGen("block/slab")
                   .texture("bottom", texture)
                   .texture("top", texture)
                   .texture("side", texture);
    }

    public static InheritingModelGen slabAllTop(String texture) {
        return new InheritingModelGen("block/slab_top")
                   .texture("bottom", texture)
                   .texture("top", texture)
                   .texture("side", texture);
    }

    public static InheritingModelGen slab(String bottom, String top, String side) {
        return new InheritingModelGen("block/slab")
                   .texture("bottom", bottom)
                   .texture("top", top)
                   .texture("side", side);
    }

    public static InheritingModelGen slabTop(String bottom, String top, String side) {
        return new InheritingModelGen("block/slab_top")
                   .texture("bottom", bottom)
                   .texture("top", top)
                   .texture("side", side);
    }

    public static InheritingModelGen stairsAll(String texture) {
        return new InheritingModelGen("block/stairs")
                   .texture("bottom", texture)
                   .texture("top", texture)
                   .texture("side", texture);
    }

    public static InheritingModelGen stairsAllInner(String texture) {
        return new InheritingModelGen("block/inner_stairs")
                   .texture("bottom", texture)
                   .texture("top", texture)
                   .texture("side", texture);
    }

    public static InheritingModelGen stairsAllOuter(String texture) {
        return new InheritingModelGen("block/outer_stairs")
                   .texture("bottom", texture)
                   .texture("top", texture)
                   .texture("side", texture);
    }

    public static InheritingModelGen stairs(String bottom, String top, String side) {
        return new InheritingModelGen("block/stairs")
                   .texture("bottom", bottom)
                   .texture("top", top)
                   .texture("side", side);
    }

    public static InheritingModelGen stairsInner(String bottom, String top, String side) {
        return new InheritingModelGen("block/inner_stairs")
                   .texture("bottom", bottom)
                   .texture("top", top)
                   .texture("side", side);
    }

    public static InheritingModelGen stairsOuter(String bottom, String top, String side) {
        return new InheritingModelGen("block/outer_stairs")
                   .texture("bottom", bottom)
                   .texture("top", top)
                   .texture("side", side);
    }

    public static InheritingModelGen cubeMirroredAll(String texture) {
        return new InheritingModelGen("block/cube_mirrored_all")
                   .texture("all", texture);
    }

    public static InheritingModelGen cubeColumn(String end, String side) {
        return new InheritingModelGen("block/cube_column")
                   .texture("end", end)
                   .texture("side", side);
    }

    public static InheritingModelGen cubeColumnHoriz(String end, String side) {
        return new InheritingModelGen("block/cube_column_horizontal")
                   .texture("end", end)
                   .texture("side", side);
    }

    public static InheritingModelGen horizontalOrientable(String face, String others) {
        return new InheritingModelGen("block/orientable")
                   .texture("top", others)
                   .texture("front", face)
                   .texture("side", others);
    }

    public static InheritingModelGen cubeBottomTop(String bottom, String top, String side) {
        return new InheritingModelGen("block/cube_bottom_top")
                   .texture("bottom", bottom)
                   .texture("top", top)
                   .texture("side", side);
    }

    public static InheritingModelGen grassBlock(String bottom, String top, String side, String overlay) {
        return new InheritingModelGen(new Identifier(Marbles.MOD_ID, "block/layered_grass_block"))
                   .texture("bottom", bottom)
                   .texture("top", top)
                   .texture("side", side)
                   .texture("overlay", overlay);
    }

    public static InheritingModelGen flattenedBlock(String bottom, String top, String side) {
        return new InheritingModelGen(new Identifier(Marbles.MOD_ID, "block/flattened_block"))
                   .texture("bottom", bottom)
                   .texture("top", top)
                   .texture("side", side);
    }

    public static InheritingModelGen cube(String north, String east, String south, String west, String up, String down) {
        return new InheritingModelGen("block/cube")
                   .texture("north", north)
                   .texture("east", east)
                   .texture("south", south)
                   .texture("west", west)
                   .texture("up", up)
                   .texture("down", down)
                   .texture("particle", down);
    }

    public static InheritingModelGen cubeMirrored(String north, String east, String south, String west, String up, String down) {
        return new InheritingModelGen("block/cube_mirrored")
                   .texture("north", north)
                   .texture("east", east)
                   .texture("south", south)
                   .texture("west", west)
                   .texture("up", up)
                   .texture("down", down)
                   .texture("particle", down);
    }

    public static InheritingModelGen cubeFrontSided(String front, String side, String top, String bottom) {
        return new InheritingModelGen("block/cube")
                   .texture("north", front)
                   .texture("east", side)
                   .texture("south", side)
                   .texture("west", side)
                   .texture("up", top)
                   .texture("down", bottom)
                   .texture("particle", bottom);
    }

    public static InheritingModelGen cubeSeparateSided(String frontBack, String side, String top, String bottom) {
        return new InheritingModelGen("block/cube")
                   .texture("north", frontBack)
                   .texture("east", side)
                   .texture("south", frontBack)
                   .texture("west", side)
                   .texture("up", top)
                   .texture("down", bottom)
                   .texture("particle", bottom);
    }

    public static InheritingModelGen cubeFrontBackSided(String front, String back, String side, String top, String bottom) {
        return new InheritingModelGen("block/cube")
                   .texture("north", front)
                   .texture("east", side)
                   .texture("south", back)
                   .texture("west", side)
                   .texture("up", top)
                   .texture("down", bottom)
                   .texture("particle", bottom);
    }

    public static InheritingModelGen generated(String... layers) {
        InheritingModelGen gen = new InheritingModelGen("item/generated");
        for (int i = 0, l = layers.length; i < l; i++) {
            gen.texture("layer" + i, layers[i]);
        }
        return gen;
    }

    public static InheritingModelGen cross(String texture) {
        return new InheritingModelGen("block/cross")
                   .texture("cross", texture);
    }

    public static InheritingModelGen tintedCross(String texture) {
        return new InheritingModelGen("block/tinted_cross")
                   .texture("cross", texture);
    }

    public static InheritingModelGen doubleCross(String texture) {
        return new InheritingModelGen(new Identifier(Marbles.MOD_ID, "block/double_cross"))
                   .texture("cross", texture);
    }

    public static InheritingModelGen flowerPotCross(String texture) {
        return new InheritingModelGen("block/flower_pot_cross")
            .texture("plant", texture);
    }

    public static InheritingModelGen carpet(String texture) {
        return new InheritingModelGen("block/carpet")
            .texture("wool", texture);
    }

    public static InheritingModelGen fenceSide(String texture) {
        return new InheritingModelGen("block/fence_side")
                   .texture("texture", texture);
    }

    public static InheritingModelGen fencePost(String texture) {
        return new InheritingModelGen("block/fence_post")
                   .texture("texture", texture);
    }

    public static InheritingModelGen fenceInventory(String texture) {
        return new InheritingModelGen("block/fence_inventory")
                   .texture("texture", texture);
    }

    public static InheritingModelGen wallSide(String texture) {
        return new InheritingModelGen("block/template_wall_side")
                   .texture("wall", texture);
    }

    public static InheritingModelGen wallSideTall(String texture) {
        return new InheritingModelGen("block/template_wall_side_tall")
                   .texture("wall", texture);
    }

    public static InheritingModelGen wallPost(String texture) {
        return new InheritingModelGen("block/template_wall_post")
                   .texture("wall", texture);
    }

    public static InheritingModelGen wallInventory(String texture) {
        return new InheritingModelGen("block/wall_inventory")
                   .texture("wall", texture);
    }

    public static InheritingModelGen wallSidedSide(String bottom, String top, String side) {
        return new InheritingModelGen(new Identifier(Marbles.MOD_ID, "block/template_wall_sided_side"))
                   .texture("bottom", bottom)
                   .texture("top", top)
                   .texture("side", side);
    }

    public static InheritingModelGen wallSidedSideTall(String bottom, String top, String side) {
        return new InheritingModelGen(new Identifier(Marbles.MOD_ID, "block/template_wall_sided_side_tall"))
                   .texture("bottom", bottom)
                   .texture("top", top)
                   .texture("side", side);
    }

    public static InheritingModelGen wallSidedPost(String bottom, String top, String side) {
        return new InheritingModelGen(new Identifier(Marbles.MOD_ID, "block/template_wall_sided_post"))
                   .texture("bottom", bottom)
                   .texture("top", top)
                   .texture("side", side);
    }

    public static InheritingModelGen wallSidedInventory(String bottom, String top, String side) {
        return new InheritingModelGen(new Identifier(Marbles.MOD_ID, "block/wall_sided_inventory"))
                   .texture("bottom", bottom)
                   .texture("top", top)
                   .texture("side", side);
    }

    public static InheritingModelGen doorTop(String top, String bottom, boolean rightHinge) {
        return new InheritingModelGen("block/door_top" + (rightHinge ? "_rh" : ""))
                   .texture("top", top)
                   .texture("bottom", bottom);
    }

    public static InheritingModelGen doorBottom(String bottom, boolean rightHinge) {
        return new InheritingModelGen("block/door_bottom" + (rightHinge ? "_rh" : ""))
                   .texture("bottom", bottom);
    }

    public static InheritingModelGen trapdoorTop(String texture) {
        return new InheritingModelGen("block/template_orientable_trapdoor_top")
                   .texture("texture", texture);
    }

    public static InheritingModelGen trapdoorBottom(String texture) {
        return new InheritingModelGen("block/template_orientable_trapdoor_bottom")
                   .texture("texture", texture);
    }

    public static InheritingModelGen trapdoorOpen(String texture) {
        return new InheritingModelGen("block/template_orientable_trapdoor_open")
                   .texture("texture", texture);
    }

    public static InheritingModelGen pressurePlate(String texture, boolean down) {
        return new InheritingModelGen("block/pressure_plate_" + (down ? "down" : "up"))
                   .texture("texture", texture);
    }

    public static InheritingModelGen button(String texture, boolean pressed) {
        return new InheritingModelGen("block/button" + (pressed ? "_pressed" : ""))
                   .texture("texture", texture);
    }

    public static InheritingModelGen buttonInventory(String texture) {
        return new InheritingModelGen("block/button_inventory")
                   .texture("texture", texture);
    }

    public static InheritingModelGen fenceGate(String texture, boolean open) {
        return new InheritingModelGen("block/template_fence_gate" + (open ? "_open" : ""))
                   .texture("texture", texture);
    }

    public static InheritingModelGen fenceGateWall(String texture, boolean open) {
        return new InheritingModelGen("block/template_fence_gate_wall" + (open ? "_open" : ""))
                   .texture("texture", texture);
    }
}
