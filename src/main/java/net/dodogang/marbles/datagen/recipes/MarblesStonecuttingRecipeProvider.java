package net.dodogang.marbles.datagen.recipes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import net.dodogang.marbles.block.helper.TravertineBlocks;
import net.dodogang.marbles.init.MarblesBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Recipe provider designed for generating stonecutter recipes. This provider automatically derives all other possible
 * stonecutting recipes from the registered recipes. This way we don't need to care about these 'recursive recipes',
 * which apparently aren't derived by Minecraft itself.
 */
public class MarblesStonecuttingRecipeProvider extends AbstractRecipesProvider {
    private Consumer<RecipeJsonProvider> consumer;

    private final Map<Item, Map<Item, Integer>> recipes = Maps.newHashMap();
    private final Map<Item, List<Pair<Item, Integer>>> compiled = Maps.newHashMap();
    private final Set<String> flushed = Sets.newHashSet();

    public MarblesStonecuttingRecipeProvider(DataGenerator gen) {
        super(gen);
    }


    @Override
    protected void generate(Consumer<RecipeJsonProvider> consumer) {
        this.consumer = consumer;

        /*
         * TRAVERTINE SETS
         */

        registerTravertine(MarblesBlocks.TRAVERTINE_BLOCKS);
        registerTravertine(MarblesBlocks.LEMON_TRAVERTINE_BLOCKS);
        registerTravertine(MarblesBlocks.PEACH_TRAVERTINE_BLOCKS);
        registerTravertine(MarblesBlocks.TANGERINE_TRAVERTINE_BLOCKS);

        /*
         * LAPIS SETS
         */

        register(Blocks.LAPIS_BLOCK, MarblesBlocks.LAPIS_SHINGLES);
        register(MarblesBlocks.LAPIS_SHINGLES, MarblesBlocks.LAPIS_SHINGLE_SLAB, 2);
        register(MarblesBlocks.LAPIS_SHINGLES, MarblesBlocks.LAPIS_SHINGLE_STAIRS);
        register(Blocks.LAPIS_BLOCK, MarblesBlocks.GLAZED_LAPIS);
        register(MarblesBlocks.UMBRAL_LAZULI_BLOCK, MarblesBlocks.UMBRAL_LAZULI_SHINGLES);
        register(MarblesBlocks.UMBRAL_LAZULI_SHINGLES, MarblesBlocks.UMBRAL_LAZULI_SHINGLE_SLAB, 2);
        register(MarblesBlocks.UMBRAL_LAZULI_SHINGLES, MarblesBlocks.UMBRAL_LAZULI_SHINGLE_STAIRS);
        register(MarblesBlocks.UMBRAL_LAZULI_BLOCK, MarblesBlocks.GLAZED_UMBRAL_LAZULI);

        /*
         * SANDSTONE SETS
         */

        register(MarblesBlocks.DAWN_SANDSTONE, MarblesBlocks.CUT_DAWN_SANDSTONE);
        register(MarblesBlocks.DAWN_SANDSTONE, MarblesBlocks.CHISELED_DAWN_SANDSTONE);
        register(MarblesBlocks.DAWN_SANDSTONE, MarblesBlocks.DAWN_SANDSTONE_SLAB, 2);
        register(MarblesBlocks.DAWN_SANDSTONE, MarblesBlocks.DAWN_SANDSTONE_STAIRS);
        register(MarblesBlocks.DAWN_SANDSTONE, MarblesBlocks.DAWN_SANDSTONE_WALL);
        register(MarblesBlocks.CUT_DAWN_SANDSTONE, MarblesBlocks.CUT_DAWN_SANDSTONE_SLAB, 2);
        register(MarblesBlocks.CUT_DAWN_SANDSTONE, MarblesBlocks.CUT_DAWN_SANDSTONE_STAIRS);
        register(MarblesBlocks.CUT_DAWN_SANDSTONE, MarblesBlocks.CUT_DAWN_SANDSTONE_WALL);
        register(MarblesBlocks.SMOOTH_DAWN_SANDSTONE, MarblesBlocks.SMOOTH_DAWN_SANDSTONE_SLAB, 2);
        register(MarblesBlocks.SMOOTH_DAWN_SANDSTONE, MarblesBlocks.SMOOTH_DAWN_SANDSTONE_STAIRS);
        register(MarblesBlocks.SMOOTH_DAWN_SANDSTONE, MarblesBlocks.SMOOTH_DAWN_SANDSTONE_WALL);
        register(MarblesBlocks.CUT_DAWN_SANDSTONE, MarblesBlocks.CUT_DAWN_SANDSTONE_SLAB, 2);
        register(MarblesBlocks.CUT_DAWN_SANDSTONE, MarblesBlocks.CUT_DAWN_SANDSTONE_STAIRS);
        register(MarblesBlocks.CUT_DAWN_SANDSTONE, MarblesBlocks.CUT_DAWN_SANDSTONE_WALL);

        register(MarblesBlocks.DUSK_SANDSTONE, MarblesBlocks.CUT_DUSK_SANDSTONE);
        register(MarblesBlocks.DUSK_SANDSTONE, MarblesBlocks.CHISELED_DUSK_SANDSTONE);
        register(MarblesBlocks.DUSK_SANDSTONE, MarblesBlocks.DUSK_SANDSTONE_SLAB, 2);
        register(MarblesBlocks.DUSK_SANDSTONE, MarblesBlocks.DUSK_SANDSTONE_STAIRS);
        register(MarblesBlocks.DUSK_SANDSTONE, MarblesBlocks.DUSK_SANDSTONE_WALL);
        register(MarblesBlocks.CUT_DUSK_SANDSTONE, MarblesBlocks.CUT_DUSK_SANDSTONE_SLAB, 2);
        register(MarblesBlocks.CUT_DUSK_SANDSTONE, MarblesBlocks.CUT_DUSK_SANDSTONE_STAIRS);
        register(MarblesBlocks.CUT_DUSK_SANDSTONE, MarblesBlocks.CUT_DUSK_SANDSTONE_WALL);
        register(MarblesBlocks.SMOOTH_DUSK_SANDSTONE, MarblesBlocks.SMOOTH_DUSK_SANDSTONE_SLAB, 2);
        register(MarblesBlocks.SMOOTH_DUSK_SANDSTONE, MarblesBlocks.SMOOTH_DUSK_SANDSTONE_STAIRS);
        register(MarblesBlocks.SMOOTH_DUSK_SANDSTONE, MarblesBlocks.SMOOTH_DUSK_SANDSTONE_WALL);
        register(MarblesBlocks.CUT_DUSK_SANDSTONE, MarblesBlocks.CUT_DUSK_SANDSTONE_SLAB, 2);
        register(MarblesBlocks.CUT_DUSK_SANDSTONE, MarblesBlocks.CUT_DUSK_SANDSTONE_STAIRS);
        register(MarblesBlocks.CUT_DUSK_SANDSTONE, MarblesBlocks.CUT_DUSK_SANDSTONE_WALL);

        /*
         * PINK SALT
         */

        register(MarblesBlocks.PINK_SALT, MarblesBlocks.PINK_SALT_BRICKS);
        register(MarblesBlocks.PINK_SALT, MarblesBlocks.PINK_SALT_SLAB, 2);
        register(MarblesBlocks.PINK_SALT, MarblesBlocks.PINK_SALT_STAIRS);
        register(MarblesBlocks.PINK_SALT, MarblesBlocks.PINK_SALT_PILLAR);
        register(MarblesBlocks.PINK_SALT_BRICKS, MarblesBlocks.PINK_SALT_BRICK_SLAB, 2);
        register(MarblesBlocks.PINK_SALT_BRICKS, MarblesBlocks.PINK_SALT_BRICK_STAIRS);

        /*
         * ICE
         */

        register(MarblesBlocks.FLOESTONE, MarblesBlocks.POLISHED_FLOESTONE);
        register(MarblesBlocks.FLOESTONE, MarblesBlocks.CHISELED_FLOESTONE);
        register(MarblesBlocks.FLOESTONE, MarblesBlocks.FLOESTONE_BRICKS);

        register(Blocks.ICE, MarblesBlocks.CUT_ICE);
        register(Blocks.BLUE_ICE, MarblesBlocks.CUT_BLUE_ICE);
        register(MarblesBlocks.SCALED_ICE, MarblesBlocks.CUT_SCALED_ICE);
        register(MarblesBlocks.MINTED_ICE, MarblesBlocks.CUT_MINTED_ICE);

        register(Blocks.ICE, MarblesBlocks.CHISELED_ICE);
        register(Blocks.BLUE_ICE, MarblesBlocks.CHISELED_BLUE_ICE);
        register(MarblesBlocks.SCALED_ICE, MarblesBlocks.CHISELED_SCALED_ICE);
        register(MarblesBlocks.MINTED_ICE, MarblesBlocks.CHISELED_MINTED_ICE);

        register(Blocks.ICE, MarblesBlocks.ICE_BRICKS);
        register(Blocks.BLUE_ICE, MarblesBlocks.BLUE_ICE_BRICKS);
        register(MarblesBlocks.SCALED_ICE, MarblesBlocks.SCALED_ICE_BRICKS);
        register(MarblesBlocks.MINTED_ICE, MarblesBlocks.MINTED_ICE_BRICKS);

        /*
         * GENERATION
         */

        compile();
        flush();
    }

    private void registerTravertine(TravertineBlocks blocks) {
        register(blocks.RAW, blocks.BRICKS);
        register(blocks.RAW, blocks.CAPPED);
        register(blocks.RAW, blocks.POLISHED);
        register(blocks.RAW, blocks.SLAB, 2);
        register(blocks.RAW, blocks.STAIRS);
        register(blocks.RAW, blocks.WALL);
        register(blocks.BRICKS, blocks.BRICK_SLAB, 2);
        register(blocks.BRICKS, blocks.BRICK_STAIRS);
        register(blocks.BRICKS, blocks.BRICK_WALL);
        register(blocks.CAPPED, blocks.CAPPED_SLAB, 2);
        register(blocks.CAPPED, blocks.CAPPED_STAIRS);
        register(blocks.CAPPED, blocks.CAPPED_WALL);
        register(blocks.POLISHED, blocks.POLISHED_SLAB, 2);
        register(blocks.POLISHED, blocks.POLISHED_STAIRS);
        register(blocks.POLISHED, blocks.POLISHED_WALL);
    }

    private void register(ItemConvertible from, ItemConvertible to) {
        register(from, to, 1);
    }

    private void register(ItemConvertible from, ItemConvertible to, int count) {
        Item fromItem = from.asItem();
        Item toItem = to.asItem();
        recipes.computeIfAbsent(fromItem, k -> Maps.newHashMap())
               .put(toItem, count);
    }

    private void compile() {
        for (Item item : recipes.keySet()) {
            List<Pair<Item, Integer>> results = Lists.newArrayList();
            compile(item, 1, results);
            compiled.put(item, results);
        }
    }

    private void compile(Item from, int countMul, List<Pair<Item, Integer>> out) {
        Map<Item, Integer> to = recipes.get(from);
        for (Map.Entry<Item, Integer> entry : to.entrySet()) {
            Item item = entry.getKey();
            int count = entry.getValue() * countMul;
            out.add(Pair.of(item, count));

            // Recursively walk recipes map
            // If you can stonecut stone to bricks and bricks to bricks slab, you can stonecut stone to bricks slab
            if (recipes.containsKey(item)) {
                compile(item, count, out);
            }
        }
    }

    private void flush() {
        for (Map.Entry<Item, List<Pair<Item, Integer>>> entry : compiled.entrySet()) {
            Item from = entry.getKey();

            List<Pair<Item, Integer>> results = entry.getValue();
            for (Pair<Item, Integer> result : results) {
                Item to = result.getFirst();
                int count = result.getSecond();

                String name = recipeName(from, to);

                // Sometimes multiple ways lead to the same item, meaning a recipe can be registered multiple times.
                // We track all the recipe IDs we flushed in a set so that we only register recipes that we didn't
                // register already. We can assume that when a recipe is registered twice, they are exactly the same and
                // the latter one can be safely omitted...
                if (!flushed.contains(name)) {
                    SingleItemRecipeJsonFactory.create(Ingredient.ofItems(from), to, count)
                                               .create(criterionName(from), hasItem(from))
                                               .offerTo(consumer, new Identifier(name));
                    flushed.add(name);
                }
            }
        }
    }

    private static String criterionName(Item from) {
        Identifier id = Registry.ITEM.getId(from);
        return String.format("has_%s", id.getPath());
    }

    private static String recipeName(Item from, Item to) {
        Identifier fromId = Registry.ITEM.getId(from);
        Identifier toId = Registry.ITEM.getId(to);

        return String.format("%s:stonecutting/%s_to_%s", toId.getNamespace(), fromId.getPath(), toId.getPath());
    }

    @Override
    public String getName() {
        return "MarblesStonecuttingRecipes";
    }
}
