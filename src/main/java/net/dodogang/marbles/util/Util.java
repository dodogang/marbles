package net.dodogang.marbles.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.mixin.entity.LivingEntityAccessor;
import net.dodogang.marbles.tag.MarblesBlockTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

public class Util {
    public static final TrackedData<ItemStack> LLAMA_SADDLE_ITEM_TRACKED_DATA = DataTracker.registerData(LlamaEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    public static final ImmutableList<Double> ADDITIONAL_CLOUD_OFFSETS;
    static {
        Random random = new Random();
        ImmutableList.Builder<Double> cloudOffsets = new ImmutableList.Builder<>();

        double cloudOffset = 5.0d + (random.nextDouble() * 5.0d);
        for (int i = 0; i < Util.getAdditionalCloudCount(); i++) {
            cloudOffsets.add(cloudOffset += (((i + 1) * 10.0d) + ((random.nextDouble() * random.nextDouble()) * 25.0d)));
        }

        ADDITIONAL_CLOUD_OFFSETS = cloudOffsets.build();
    }
    protected static int getAdditionalCloudCount() {
        return 5;
    }

    @Environment(EnvType.CLIENT)
    public static final Identifier POLLEN_GRACED_CARPET_LLAMA_DECOR_TEXTURE = new Identifier("textures/entity/llama/decor/marbles/pollen_graced_wool.png");

    @Environment(EnvType.CLIENT)
    public static TitleScreen currentTitleScreen = null;
    @Environment(EnvType.CLIENT)
    public static final List<String> SPLASH_TEXTS = Lists.newArrayList(
        "Losing my Marbles!", "Fast acting portals!", "¡Perdiendo mis canicas!",
        "Also try Crumbs!", "Also try Sizzle!", "Snazzy cosmetics!", "Why wheat gooey?"
    );

    // ---

    public static void applyFasterClimbingMovement(LivingEntity cast, Vec3d vec3d, float f, CallbackInfoReturnable<Vec3d> cir) {
        if ((cast.horizontalCollision || ((LivingEntityAccessor) cast).isJumping()) && cast.isClimbing() && cast.getBlockStateAtPos().isIn(MarblesBlockTags.CLIMBABLE_FASTER)) {
            Vec3d vel = cir.getReturnValue();

            vel = vel.add(0.0d, Util.getClimbableFasterSpeed(), 0.0d);

            cast.setVelocity(vel);
            cir.setReturnValue(vel);
        }
    }

    public static double getClimbableFasterSpeed() {
        return 0.1135d;
    }

    // ---

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

        double rndCentered = center + random.nextGaussian() * centered_depth_abs; // generate centered random number.

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
