package net.dodogang.marbles.client.config;

import com.google.gson.JsonPrimitive;
import me.andante.chord.client.config.Option;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class MarblesConfig {
    public static class Graphics {
        /**
         * Enable or disable any additional cloud layers.
         */
        public static Option<JsonPrimitive> additionalCloudLayers = register(new Option<>("additional_cloud_layers", new JsonPrimitive(true)));
        /**
         * Display only Marbles splash texts.
         */
        public static Option<JsonPrimitive> onlyMarblesSplashTexts = register(new Option<>("only_marbles_splash_texts", new JsonPrimitive(false)));
    }

    private static <T extends Option<JsonPrimitive>> T register(T option) {
        MarblesConfigManager.OPTIONS.add(option);
        return option;
    }
}
