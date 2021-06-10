package net.dodogang.marbles.client.config;

import com.google.gson.JsonPrimitive;
import me.andante.chord.client.config.Option;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class MarblesConfig {
    public static RenderGroup RENDER = new RenderGroup();
    public static class RenderGroup {
        /**
         * Enable or disable any additional cloud layers.
         */
        public Option<JsonPrimitive> additionalCloudLayers = register(new Option<>("additional_cloud_layers", new JsonPrimitive(true)));
    }

    private static <T extends Option<JsonPrimitive>> T register(T option) {
        MarblesConfigManager.OPTIONS.add(option);
        return option;
    }
}
