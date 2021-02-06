package net.dodogang.marbles.data.recipes;

import me.andante.chord.block.helper.WoodBlocks;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.init.MarblesItems;
import net.dodogang.marbles.tag.MarblesItemTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.recipe.CookingRecipeJsonFactory;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Consumer;

public class MarblesRecipeProvider extends AbstractRecipesProvider {
    private Consumer<RecipeJsonProvider> consumer;

    public MarblesRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void generate(Consumer<RecipeJsonProvider> consumer) {
        this.consumer = consumer;

        woodSet(MarblesBlocks.ASPEN);
        woodSet(MarblesBlocks.HOOPSI_SPRUCE);

        generic2x2("travertine/bricks", MarblesBlocks.TRAVERTINE, MarblesBlocks.TRAVERTINE_BRICKS, 4);
        shapeless("travertine/polished", MarblesBlocks.TRAVERTINE, MarblesBlocks.POLISHED_TRAVERTINE, 1);
        generic1x2("travertine/capped", MarblesBlocks.TRAVERTINE_SLAB, MarblesBlocks.CAPPED_TRAVERTINE, 1);
        generic3x1("travertine/slab", MarblesBlocks.TRAVERTINE, MarblesBlocks.TRAVERTINE_SLAB, 6);
        generic3x1("travertine/brick_slab", MarblesBlocks.TRAVERTINE_BRICKS, MarblesBlocks.TRAVERTINE_BRICK_SLAB, 6);
        generic3x1("travertine/capped_slab", MarblesBlocks.CAPPED_TRAVERTINE, MarblesBlocks.CAPPED_TRAVERTINE_SLAB, 6);
        generic3x1("travertine/polished_slab", MarblesBlocks.POLISHED_TRAVERTINE, MarblesBlocks.POLISHED_TRAVERTINE_SLAB, 6);
        stairs("travertine/stairs", MarblesBlocks.TRAVERTINE, MarblesBlocks.TRAVERTINE_STAIRS, 4);
        stairs("travertine/brick_stairs", MarblesBlocks.TRAVERTINE_BRICKS, MarblesBlocks.TRAVERTINE_BRICK_STAIRS, 4);
        stairs("travertine/capped_stairs", MarblesBlocks.CAPPED_TRAVERTINE, MarblesBlocks.CAPPED_TRAVERTINE_STAIRS, 4);
        stairs("travertine/polished_stairs", MarblesBlocks.POLISHED_TRAVERTINE, MarblesBlocks.POLISHED_TRAVERTINE_STAIRS, 4);
        generic3x2("travertine/wall", MarblesBlocks.TRAVERTINE, MarblesBlocks.TRAVERTINE_WALL, 6);
        generic3x2("travertine/brick_wall", MarblesBlocks.TRAVERTINE_BRICKS, MarblesBlocks.TRAVERTINE_BRICK_WALL, 6);
        generic3x2("travertine/capped_wall", MarblesBlocks.CAPPED_TRAVERTINE, MarblesBlocks.CAPPED_TRAVERTINE_WALL, 6);
        generic3x2("travertine/polished_wall", MarblesBlocks.POLISHED_TRAVERTINE, MarblesBlocks.POLISHED_TRAVERTINE_WALL, 6);
        saltLamp("travertine/salt_lamp", MarblesItemTags.TRAVERTINE, MarblesBlocks.TRAVERTINE_SALT_LAMP, 4);

        generic2x2("lemon_travertine/bricks", MarblesBlocks.LEMON_TRAVERTINE, MarblesBlocks.LEMON_TRAVERTINE_BRICKS, 4);
        shapeless("lemon_travertine/polished", MarblesBlocks.LEMON_TRAVERTINE, MarblesBlocks.POLISHED_LEMON_TRAVERTINE, 1);
        generic1x2("lemon_travertine/capped", MarblesBlocks.LEMON_TRAVERTINE_SLAB, MarblesBlocks.CAPPED_LEMON_TRAVERTINE, 1);
        generic3x1("lemon_travertine/slab", MarblesBlocks.LEMON_TRAVERTINE, MarblesBlocks.LEMON_TRAVERTINE_SLAB, 6);
        generic3x1("lemon_travertine/brick_slab", MarblesBlocks.LEMON_TRAVERTINE_BRICKS, MarblesBlocks.LEMON_TRAVERTINE_BRICK_SLAB, 6);
        generic3x1("lemon_travertine/capped_slab", MarblesBlocks.CAPPED_LEMON_TRAVERTINE, MarblesBlocks.CAPPED_LEMON_TRAVERTINE_SLAB, 6);
        generic3x1("lemon_travertine/polished_slab", MarblesBlocks.POLISHED_LEMON_TRAVERTINE, MarblesBlocks.POLISHED_LEMON_TRAVERTINE_SLAB, 6);
        stairs("lemon_travertine/stairs", MarblesBlocks.LEMON_TRAVERTINE, MarblesBlocks.LEMON_TRAVERTINE_STAIRS, 4);
        stairs("lemon_travertine/brick_stairs", MarblesBlocks.LEMON_TRAVERTINE_BRICKS, MarblesBlocks.LEMON_TRAVERTINE_BRICK_STAIRS, 4);
        stairs("lemon_travertine/capped_stairs", MarblesBlocks.CAPPED_LEMON_TRAVERTINE, MarblesBlocks.CAPPED_LEMON_TRAVERTINE_STAIRS, 4);
        stairs("lemon_travertine/polished_stairs", MarblesBlocks.POLISHED_LEMON_TRAVERTINE, MarblesBlocks.POLISHED_LEMON_TRAVERTINE_STAIRS, 4);
        generic3x2("lemon_travertine/wall", MarblesBlocks.LEMON_TRAVERTINE, MarblesBlocks.LEMON_TRAVERTINE_WALL, 6);
        generic3x2("lemon_travertine/brick_wall", MarblesBlocks.LEMON_TRAVERTINE_BRICKS, MarblesBlocks.LEMON_TRAVERTINE_BRICK_WALL, 6);
        generic3x2("lemon_travertine/capped_wall", MarblesBlocks.CAPPED_LEMON_TRAVERTINE, MarblesBlocks.CAPPED_LEMON_TRAVERTINE_WALL, 6);
        generic3x2("lemon_travertine/polished_wall", MarblesBlocks.POLISHED_LEMON_TRAVERTINE, MarblesBlocks.POLISHED_LEMON_TRAVERTINE_WALL, 6);
        saltLamp("lemon_travertine/salt_lamp", MarblesItemTags.LEMON_TRAVERTINE, MarblesBlocks.LEMON_TRAVERTINE_SALT_LAMP, 4);

        generic2x2("peach_travertine/bricks", MarblesBlocks.PEACH_TRAVERTINE, MarblesBlocks.PEACH_TRAVERTINE_BRICKS, 4);
        shapeless("peach_travertine/polished", MarblesBlocks.PEACH_TRAVERTINE, MarblesBlocks.POLISHED_PEACH_TRAVERTINE, 1);
        generic1x2("peach_travertine/capped", MarblesBlocks.PEACH_TRAVERTINE_SLAB, MarblesBlocks.CAPPED_PEACH_TRAVERTINE, 1);
        generic3x1("peach_travertine/slab", MarblesBlocks.PEACH_TRAVERTINE, MarblesBlocks.PEACH_TRAVERTINE_SLAB, 6);
        generic3x1("peach_travertine/brick_slab", MarblesBlocks.PEACH_TRAVERTINE_BRICKS, MarblesBlocks.PEACH_TRAVERTINE_BRICK_SLAB, 6);
        generic3x1("peach_travertine/capped_slab", MarblesBlocks.CAPPED_PEACH_TRAVERTINE, MarblesBlocks.CAPPED_PEACH_TRAVERTINE_SLAB, 6);
        generic3x1("peach_travertine/polished_slab", MarblesBlocks.POLISHED_PEACH_TRAVERTINE, MarblesBlocks.POLISHED_PEACH_TRAVERTINE_SLAB, 6);
        stairs("peach_travertine/stairs", MarblesBlocks.PEACH_TRAVERTINE, MarblesBlocks.PEACH_TRAVERTINE_STAIRS, 4);
        stairs("peach_travertine/brick_stairs", MarblesBlocks.PEACH_TRAVERTINE_BRICKS, MarblesBlocks.PEACH_TRAVERTINE_BRICK_STAIRS, 4);
        stairs("peach_travertine/capped_stairs", MarblesBlocks.CAPPED_PEACH_TRAVERTINE, MarblesBlocks.CAPPED_PEACH_TRAVERTINE_STAIRS, 4);
        stairs("peach_travertine/polished_stairs", MarblesBlocks.POLISHED_PEACH_TRAVERTINE, MarblesBlocks.POLISHED_PEACH_TRAVERTINE_STAIRS, 4);
        generic3x2("peach_travertine/wall", MarblesBlocks.PEACH_TRAVERTINE, MarblesBlocks.PEACH_TRAVERTINE_WALL, 6);
        generic3x2("peach_travertine/brick_wall", MarblesBlocks.PEACH_TRAVERTINE_BRICKS, MarblesBlocks.PEACH_TRAVERTINE_BRICK_WALL, 6);
        generic3x2("peach_travertine/capped_wall", MarblesBlocks.CAPPED_PEACH_TRAVERTINE, MarblesBlocks.CAPPED_PEACH_TRAVERTINE_WALL, 6);
        generic3x2("peach_travertine/polished_wall", MarblesBlocks.POLISHED_PEACH_TRAVERTINE, MarblesBlocks.POLISHED_PEACH_TRAVERTINE_WALL, 6);
        saltLamp("peach_travertine/salt_lamp", MarblesItemTags.PEACH_TRAVERTINE, MarblesBlocks.PEACH_TRAVERTINE_SALT_LAMP, 4);

        generic2x2("tangerine_travertine/bricks", MarblesBlocks.TANGERINE_TRAVERTINE, MarblesBlocks.TANGERINE_TRAVERTINE_BRICKS, 4);
        shapeless("tangerine_travertine/polished", MarblesBlocks.TANGERINE_TRAVERTINE, MarblesBlocks.POLISHED_TANGERINE_TRAVERTINE, 1);
        generic1x2("tangerine_travertine/capped", MarblesBlocks.TANGERINE_TRAVERTINE_SLAB, MarblesBlocks.CAPPED_TANGERINE_TRAVERTINE, 1);
        generic3x1("tangerine_travertine/slab", MarblesBlocks.TANGERINE_TRAVERTINE, MarblesBlocks.TANGERINE_TRAVERTINE_SLAB, 6);
        generic3x1("tangerine_travertine/brick_slab", MarblesBlocks.TANGERINE_TRAVERTINE_BRICKS, MarblesBlocks.TANGERINE_TRAVERTINE_BRICK_SLAB, 6);
        generic3x1("tangerine_travertine/capped_slab", MarblesBlocks.CAPPED_TANGERINE_TRAVERTINE, MarblesBlocks.CAPPED_TANGERINE_TRAVERTINE_SLAB, 6);
        generic3x1("tangerine_travertine/polished_slab", MarblesBlocks.POLISHED_TANGERINE_TRAVERTINE, MarblesBlocks.POLISHED_TANGERINE_TRAVERTINE_SLAB, 6);
        stairs("tangerine_travertine/stairs", MarblesBlocks.TANGERINE_TRAVERTINE, MarblesBlocks.TANGERINE_TRAVERTINE_STAIRS, 4);
        stairs("tangerine_travertine/brick_stairs", MarblesBlocks.TANGERINE_TRAVERTINE_BRICKS, MarblesBlocks.TANGERINE_TRAVERTINE_BRICK_STAIRS, 4);
        stairs("tangerine_travertine/capped_stairs", MarblesBlocks.CAPPED_TANGERINE_TRAVERTINE, MarblesBlocks.CAPPED_TANGERINE_TRAVERTINE_STAIRS, 4);
        stairs("tangerine_travertine/polished_stairs", MarblesBlocks.POLISHED_TANGERINE_TRAVERTINE, MarblesBlocks.POLISHED_TANGERINE_TRAVERTINE_STAIRS, 4);
        generic3x2("tangerine_travertine/wall", MarblesBlocks.TANGERINE_TRAVERTINE, MarblesBlocks.TANGERINE_TRAVERTINE_WALL, 6);
        generic3x2("tangerine_travertine/brick_wall", MarblesBlocks.TANGERINE_TRAVERTINE_BRICKS, MarblesBlocks.TANGERINE_TRAVERTINE_BRICK_WALL, 6);
        generic3x2("tangerine_travertine/capped_wall", MarblesBlocks.CAPPED_TANGERINE_TRAVERTINE, MarblesBlocks.CAPPED_TANGERINE_TRAVERTINE_WALL, 6);
        generic3x2("tangerine_travertine/polished_wall", MarblesBlocks.POLISHED_TANGERINE_TRAVERTINE, MarblesBlocks.POLISHED_TANGERINE_TRAVERTINE_WALL, 6);
        saltLamp("tangerine_travertine/salt_lamp", MarblesItemTags.TANGERINE_TRAVERTINE, MarblesBlocks.TANGERINE_TRAVERTINE_SALT_LAMP, 4);

        generic2x2("lapis/shingles", Blocks.LAPIS_BLOCK, MarblesBlocks.TANGERINE_TRAVERTINE, 4);
        generic3x1("lapis/shingles_slab", MarblesBlocks.LAPIS_SHINGLES, MarblesBlocks.LAPIS_SHINGLES_SLAB, 6);
        stairs("lapis/shingles_stairs", MarblesBlocks.LAPIS_SHINGLES, MarblesBlocks.LAPIS_SHINGLES_STAIRS, 4);

        scaffolding("yellow_scaffolding", MarblesBlocks.YELLOW_BAMBOO, MarblesBlocks.YELLOW_SCAFFOLDING, 6);

        generic2x2("dawn_sand/sandstone", MarblesBlocks.DAWN_SAND, MarblesBlocks.DAWN_SANDSTONE, 1);
        generic2x2("dawn_sand/cut_sandstone", MarblesBlocks.DAWN_SANDSTONE, MarblesBlocks.CUT_DAWN_SANDSTONE, 4);
        generic2x1("dawn_sand/chiseled_sandstone", MarblesBlocks.DAWN_SANDSTONE_SLAB, MarblesBlocks.CHISELED_DAWN_SANDSTONE, 4);
        smelting("dawn_sand/smooth_sandstone", MarblesBlocks.DAWN_SANDSTONE, MarblesBlocks.SMOOTH_DAWN_SANDSTONE, 0.1);
        generic3x1("dawn_sand/sandstone_slab", MarblesBlocks.DAWN_SANDSTONE, MarblesBlocks.DAWN_SANDSTONE_SLAB, 6);
        generic3x1("dawn_sand/cut_sandstone_slab", MarblesBlocks.CUT_DAWN_SANDSTONE, MarblesBlocks.CUT_DAWN_SANDSTONE_SLAB, 6);
        generic3x1("dawn_sand/smooth_sandstone_slab", MarblesBlocks.SMOOTH_DAWN_SANDSTONE, MarblesBlocks.SMOOTH_DAWN_SANDSTONE_SLAB, 6);
        stairs("dawn_sand/sandstone_stairs", MarblesBlocks.DAWN_SANDSTONE, MarblesBlocks.DAWN_SANDSTONE_STAIRS, 4);
        stairs("dawn_sand/cut_sandstone_stairs", MarblesBlocks.CUT_DAWN_SANDSTONE, MarblesBlocks.CUT_DAWN_SANDSTONE_STAIRS, 4);
        stairs("dawn_sand/smooth_sandstone_stairs", MarblesBlocks.SMOOTH_DAWN_SANDSTONE, MarblesBlocks.SMOOTH_DAWN_SANDSTONE_STAIRS, 4);
        generic3x2("dawn_sand/sandstone_wall", MarblesBlocks.DAWN_SANDSTONE, MarblesBlocks.DAWN_SANDSTONE_WALL, 6);
        generic3x2("dawn_sand/cut_sandstone_wall", MarblesBlocks.CUT_DAWN_SANDSTONE, MarblesBlocks.CUT_DAWN_SANDSTONE_WALL, 6);
        generic3x2("dawn_sand/smooth_sandstone_wall", MarblesBlocks.SMOOTH_DAWN_SANDSTONE, MarblesBlocks.SMOOTH_DAWN_SANDSTONE_WALL, 6);

        generic2x2("dusk_sand/sandstone", MarblesBlocks.DUSK_SAND, MarblesBlocks.DUSK_SANDSTONE, 1);
        generic2x2("dusk_sand/cut_sandstone", MarblesBlocks.DUSK_SANDSTONE, MarblesBlocks.CUT_DUSK_SANDSTONE, 4);
        generic2x1("dusk_sand/chiseled_sandstone", MarblesBlocks.DUSK_SANDSTONE_SLAB, MarblesBlocks.CHISELED_DUSK_SANDSTONE, 4);
        smelting("dusk_sand/smooth_sandstone", MarblesBlocks.DUSK_SANDSTONE, MarblesBlocks.SMOOTH_DUSK_SANDSTONE, 0.1);
        generic3x1("dusk_sand/sandstone_slab", MarblesBlocks.DUSK_SANDSTONE, MarblesBlocks.DUSK_SANDSTONE_SLAB, 6);
        generic3x1("dusk_sand/cut_sandstone_slab", MarblesBlocks.CUT_DUSK_SANDSTONE, MarblesBlocks.CUT_DUSK_SANDSTONE_SLAB, 6);
        generic3x1("dusk_sand/smooth_sandstone_slab", MarblesBlocks.SMOOTH_DUSK_SANDSTONE, MarblesBlocks.SMOOTH_DUSK_SANDSTONE_SLAB, 6);
        stairs("dusk_sand/sandstone_stairs", MarblesBlocks.DUSK_SANDSTONE, MarblesBlocks.DUSK_SANDSTONE_STAIRS, 4);
        stairs("dusk_sand/cut_sandstone_stairs", MarblesBlocks.CUT_DUSK_SANDSTONE, MarblesBlocks.CUT_DUSK_SANDSTONE_STAIRS, 4);
        stairs("dusk_sand/smooth_sandstone_stairs", MarblesBlocks.SMOOTH_DUSK_SANDSTONE, MarblesBlocks.SMOOTH_DUSK_SANDSTONE_STAIRS, 4);
        generic3x2("dusk_sand/sandstone_wall", MarblesBlocks.DUSK_SANDSTONE, MarblesBlocks.DUSK_SANDSTONE_WALL, 6);
        generic3x2("dusk_sand/cut_sandstone_wall", MarblesBlocks.CUT_DUSK_SANDSTONE, MarblesBlocks.CUT_DUSK_SANDSTONE_WALL, 6);
        generic3x2("dusk_sand/smooth_sandstone_wall", MarblesBlocks.SMOOTH_DUSK_SANDSTONE, MarblesBlocks.SMOOTH_DUSK_SANDSTONE_WALL, 6);
    }

    private void woodSet(WoodBlocks blocks) {
        String baseFolder = blocks.getId() + "/";

        ItemConvertible[] logs = {blocks.LOG, blocks.STRIPPED_LOG, blocks.WOOD, blocks.STRIPPED_WOOD};

        shapeless(baseFolder + "planks", logs, blocks.PLANKS, 4);
        shapeless(baseFolder + "wood", blocks.LOG, blocks.WOOD, 3);
        shapeless(baseFolder + "stripped_wood", blocks.STRIPPED_LOG, blocks.STRIPPED_WOOD, 3);
        generic3x1(baseFolder + "slab", blocks.PLANKS, blocks.SLAB, 6);
        stairs(baseFolder + "stairs", blocks.PLANKS, blocks.STAIRS, 4);
        fence(baseFolder + "fence", blocks.PLANKS, blocks.FENCE, 3);
        generic2x3(baseFolder + "door", blocks.PLANKS, blocks.DOOR, 3);
        generic3x2(baseFolder + "trapdoor", blocks.PLANKS, blocks.TRAPDOOR, 2);
        generic2x1(baseFolder + "pressure_plate", blocks.PLANKS, blocks.PRESSURE_PLATE, 1);
        shapeless(baseFolder + "button", blocks.PLANKS, blocks.BUTTON, 1);
        fenceGate(baseFolder + "fence_gate", blocks.PLANKS, blocks.FENCE_GATE, 1);
        sign(baseFolder + "sign", blocks.PLANKS, blocks.SIGN, 3);
        boat(baseFolder + "boat", blocks.PLANKS, blocks.BOAT_ITEM, 1);
    }

    private void generic2x2(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("##")
                               .pattern("##")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, id(id));
    }

    private void generic2x1(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("##")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, id(id));
    }

    private void generic2x3(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("##")
                               .pattern("##")
                               .pattern("##")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, id(id));
    }

    private void shapeless(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapelessRecipeJsonFactory.create(to, count)
                                  .input(from)
                                  .criterion("has_ingredient", hasItem(from))
                                  .offerTo(consumer, id(id));
    }

    private void shapeless(String id, ItemConvertible[] from, ItemConvertible to, int count) {
        ShapelessRecipeJsonFactory factory = ShapelessRecipeJsonFactory.create(to, count)
                                                                       .input(Ingredient.ofItems(from));
        for (ItemConvertible itemC : from) {
            Item item = itemC.asItem();
            String itemId = Registry.ITEM.getId(item).getPath();
            factory.criterion("has_" + itemId, hasItem(itemC));
        }

        factory.offerTo(consumer, id(id));
    }

    private void fence(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .input('/', Items.STICK)
                               .pattern("#/#")
                               .pattern("#/#")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, id(id));
    }

    private void fenceGate(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .input('/', Items.STICK)
                               .pattern("/#/")
                               .pattern("/#/")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, id(id));
    }

    private void sign(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .input('/', Items.STICK)
                               .pattern("###")
                               .pattern("###")
                               .pattern(" / ")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, id(id));
    }

    private void boat(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("# #")
                               .pattern("###")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, id(id));
    }

    private void saltLamp(String id, Tag<Item> travertine, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', travertine)
                               .input('L', MarblesItemTags.HIGH_LIGHT_BLOCKS)
                               .input('S', MarblesItems.PINK_SALT_SHARD)
                               .pattern("#S#")
                               .pattern("SLS")
                               .pattern("#S#")
                               .criterion("has_travertine", hasItems(travertine))
                               .criterion("has_shard", hasItem(MarblesItems.PINK_SALT_SHARD))
                               .criterion("has_light", hasItems(MarblesItemTags.HIGH_LIGHT_BLOCKS))
                               .offerTo(consumer, id(id));
    }

    private void scaffolding(String id, ItemConvertible bamboo, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('/', bamboo)
                               .input('s', Items.STRING)
                               .pattern("/s/")
                               .pattern("/ /")
                               .pattern("/ /")
                               .criterion("has_ingredient", hasItem(bamboo))
                               .offerTo(consumer, id(id));
    }

    private void generic1x2(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("#")
                               .pattern("#")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, id(id));
    }

    private void generic1x3(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("#")
                               .pattern("#")
                               .pattern("#")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, id(id));
    }

    private void generic3x1(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("###")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, id(id));
    }

    private void generic3x2(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("###")
                               .pattern("###")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, id(id));
    }

    private void stairs(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("#  ")
                               .pattern("## ")
                               .pattern("###")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, id(id));
    }

    private void step(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("#  ")
                               .pattern("## ")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, id(id));
    }

    private void smelting(String id, ItemConvertible from, ItemConvertible to, double xp) {
        CookingRecipeJsonFactory.createSmelting(Ingredient.ofItems(from), to, (float) xp, 200)
                                .criterion("has_ingredient", hasItem(from))
                                .offerTo(consumer, id(id));
    }

    private static Identifier id(String id) {
        return new Identifier(Marbles.MOD_ID + ":" + id);
    }

    @Override
    public String getName() {
        return "MarblesRecipes";
    }
}
