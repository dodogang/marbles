package net.dodogang.marbles.datagen.recipes;

import me.andante.chord.block.helper.WoodBlocks;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.block.helper.TravertineBlocks;
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

        travertineSet(MarblesBlocks.TRAVERTINE_BLOCKS, MarblesItemTags.TRAVERTINE);
        travertineSet(MarblesBlocks.LEMON_TRAVERTINE_BLOCKS, MarblesItemTags.LEMON_TRAVERTINE);
        travertineSet(MarblesBlocks.PEACH_TRAVERTINE_BLOCKS, MarblesItemTags.PEACH_TRAVERTINE);
        travertineSet(MarblesBlocks.TANGERINE_TRAVERTINE_BLOCKS, MarblesItemTags.TANGERINE_TRAVERTINE);

        generic2x2("lapis/shingles", Blocks.LAPIS_BLOCK, MarblesBlocks.LAPIS_SHINGLES, 4);
        generic3x1("lapis/shingles_slab", MarblesBlocks.LAPIS_SHINGLES, MarblesBlocks.LAPIS_SHINGLE_SLAB, 6);
        stairs("lapis/shingles_stairs", MarblesBlocks.LAPIS_SHINGLES, MarblesBlocks.LAPIS_SHINGLE_STAIRS, 4);
        generic3x2("lapis/glazed", Blocks.LAPIS_BLOCK, MarblesBlocks.GLAZED_LAPIS, 6);
        generic3x3("umbral_lazuli/block", MarblesItems.UMBRAL_LAZULI, MarblesBlocks.UMBRAL_LAZULI_BLOCK, 1);
        shapeless("umbral_lazuli/item_from_block", MarblesBlocks.UMBRAL_LAZULI_BLOCK, MarblesItems.UMBRAL_LAZULI, 9);
        smelting("umbral_lazuli/item_from_smelting", MarblesBlocks.UMBRAL_LAZULI_ORE, MarblesItems.UMBRAL_LAZULI, 0.2);
        blasting("umbral_lazuli/item_from_blasting", MarblesBlocks.UMBRAL_LAZULI_ORE, MarblesItems.UMBRAL_LAZULI, 0.2);
        generic2x2("umbral_lazuli/shingles", MarblesBlocks.UMBRAL_LAZULI_BLOCK, MarblesBlocks.UMBRAL_LAZULI_SHINGLES, 4);
        generic3x1("umbral_lazuli/shingles_slab", MarblesBlocks.UMBRAL_LAZULI_SHINGLES, MarblesBlocks.UMBRAL_LAZULI_SHINGLE_SLAB, 6);
        stairs("umbral_lazuli/shingles_stairs", MarblesBlocks.UMBRAL_LAZULI_SHINGLES, MarblesBlocks.UMBRAL_LAZULI_SHINGLE_STAIRS, 4);
        generic3x2("umbral_lazuli/glazed", MarblesBlocks.UMBRAL_LAZULI_BLOCK, MarblesBlocks.GLAZED_UMBRAL_LAZULI, 6);

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

        generic2x2("pink_salt/bricks", MarblesBlocks.PINK_SALT, MarblesBlocks.PINK_SALT_BRICKS, 4);
        generic3x1("pink_salt/slab", MarblesBlocks.PINK_SALT, MarblesBlocks.PINK_SALT_SLAB, 6);
        stairs("pink_salt/stairs", MarblesBlocks.PINK_SALT, MarblesBlocks.PINK_SALT_STAIRS, 4);
        generic3x1("pink_salt/brick_slab", MarblesBlocks.PINK_SALT_BRICKS, MarblesBlocks.PINK_SALT_BRICK_SLAB, 6);
        stairs("pink_salt/brick_stairs", MarblesBlocks.PINK_SALT_BRICKS, MarblesBlocks.PINK_SALT_BRICK_STAIRS, 4);
    }

    private void travertineSet(TravertineBlocks blocks, Tag<Item> tag) {
        String id = blocks.getId();

        generic2x2(id + "/bricks", blocks.RAW, blocks.BRICKS, 4);
        shapeless(id + "/polished", blocks.RAW, blocks.POLISHED, 1);
        generic1x2(id + "/capped", blocks.SLAB, blocks.CAPPED, 1);
        generic3x1(id + "/slab", blocks.RAW, blocks.SLAB, 6);
        generic3x1(id + "/brick_slab", blocks.BRICKS, blocks.BRICK_SLAB, 6);
        generic3x1(id + "/capped_slab", blocks.CAPPED, blocks.CAPPED_SLAB, 6);
        generic3x1(id + "/polished_slab", blocks.POLISHED, blocks.POLISHED_SLAB, 6);
        stairs(id + "/stairs", blocks.RAW, blocks.STAIRS, 4);
        stairs(id + "/brick_stairs", blocks.BRICKS, blocks.BRICK_STAIRS, 4);
        stairs(id + "/capped_stairs", blocks.CAPPED, blocks.CAPPED_STAIRS, 4);
        stairs(id + "/polished_stairs", blocks.POLISHED, blocks.POLISHED_STAIRS, 4);
        generic3x2(id + "/wall", blocks.RAW, blocks.WALL, 6);
        generic3x2(id + "/brick_wall", blocks.BRICKS, blocks.BRICK_WALL, 6);
        generic3x2(id + "/capped_wall", blocks.CAPPED, blocks.CAPPED_WALL, 6);
        generic3x2(id + "/polished_wall", blocks.POLISHED, blocks.POLISHED_WALL, 6);
        saltLamp(id + "/salt_lamp", tag, blocks.SALT_LAMP, 4);
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

    private void generic3x3(String id, ItemConvertible from, ItemConvertible to, int count) {
        ShapedRecipeJsonFactory.create(to, count)
                               .input('#', from)
                               .pattern("###")
                               .pattern("###")
                               .pattern("###")
                               .criterion("has_ingredient", hasItem(from))
                               .offerTo(consumer, id(id));
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

    private void blasting(String id, ItemConvertible from, ItemConvertible to, double xp) {
        CookingRecipeJsonFactory.createBlasting(Ingredient.ofItems(from), to, (float) xp, 100)
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
