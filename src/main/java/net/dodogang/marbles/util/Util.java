package net.dodogang.marbles.util;

import com.google.common.collect.Lists;
import net.dodogang.marbles.Marbles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class Util {
    /*
     * SHARED CONSTANTS
     */
    public static final TrackedData<ItemStack> LLAMA_SADDLE_ITEM_TRACKED_DATA = DataTracker.registerData(LlamaEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    public static final int ADDITIONAL_CLOUD_COUNT = 2;
    public static final double ADDITIONAL_CLOUD_OFFSET = 40.0d;

    @Environment(EnvType.CLIENT)
    public static final Identifier POLLEN_GRACED_CARPET_LLAMA_DECOR_TEXTURE = new Identifier("textures/entity/llama/decor/marbles/pollen_graced_wool.png");
    @Environment(EnvType.CLIENT)
    public static final List<String> SPLASHES = Lists.newArrayList(
        "Losing my Marbles!", "Fast acting portals!", "¡Perdiendo mis canicas!"
    );

    /*
     * UTILITY METHODS
     */

    @Nullable
    public static Biome getBiomeFromKey(RegistryKey<Biome> biomeKey) {
        if (Marbles.SERVER_INSTANCE != null) {
            return Marbles.SERVER_INSTANCE.getRegistryManager()
                                          .get(Registry.BIOME_KEY)
                                          .get(biomeKey);
        }

        return null;
    }

    /**
     * @see <a href="https://stackoverflow.com/questions/17936619/efficient-bounded-biased-random-number-generator">Stack Overflow</a>
     */
    public static double getBiasedRandom(double bias, double min, double max, Random random) {
        double rndBiased;
        double centered_depth_perc = 0.3d;
        double centered_depth_abs = (max - min) * centered_depth_perc;
        double center = (min + max) / 2;

        double rndCentered = center  + random.nextGaussian() * centered_depth_abs; // generate centered random number.

        if (rndCentered >= center) {
            rndBiased = (rndCentered - center) * (max - bias) + bias;
        }
        else {
            rndBiased = bias - (center - rndCentered) * (bias - min);
        }

        // the following two tests will be as more important as centered_depth_perc
        // get bigger.
        if (rndBiased > max)
            rndBiased = max;

        if (rndBiased < min)
            rndBiased = min;

        return rndBiased;
    }
}
