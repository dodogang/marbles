package net.dodogang.marbles.client.config;

import com.google.common.reflect.Reflection;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import me.andante.chord.client.config.Option;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.MarblesClient;
import net.dodogang.marbles.mixin.client.gui.TitleScreenAccessor;
import net.dodogang.marbles.util.ModLoaded;
import net.dodogang.marbles.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
@Environment(EnvType.CLIENT)
public class MarblesConfigManager {
    private static final File FILE = FabricLoader.getInstance().getConfigDir().toFile().toPath().resolve(Marbles.MOD_ID + ".json").toFile();
    public static final List<Option<JsonPrimitive>> OPTIONS = new LinkedList<>();

    private static JsonObject loaded;

    static {
        Reflection.initialize(MarblesConfig.class);
        MarblesConfigManager.load();
    }

    public static void save() {
        JsonObject jsonObject = loaded == null ? new JsonObject() : loaded;
        OPTIONS.forEach(option -> jsonObject.add(option.getId(), option.value));

        try (PrintWriter out = new PrintWriter(FILE)) {
            StringWriter stringWriter = new StringWriter();

            JsonWriter jsonWriter = new JsonWriter(stringWriter);
            jsonWriter.setLenient(true);
            jsonWriter.setIndent("  ");

            Streams.write(jsonObject, jsonWriter);
            out.println(stringWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try {
            String json = new String(Files.readAllBytes(FILE.toPath()));
            if (!json.isEmpty()) {
                JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);
                loaded = jsonObject;

                OPTIONS.forEach(option -> option.value = load(jsonObject, option));
            }
        } catch (IOException ignored) {
            Marbles.log(Level.WARN, "Could not load configuration file! Saving and loading default values.");
            MarblesConfigManager.save();
        } catch (NullPointerException e) {
            Marbles.log(Level.WARN, "Configuration failed to load fully from file due to " + e + ". This is probably just a configuration update.");
        } catch (IllegalArgumentException e) {
            Marbles.log(Level.ERROR, "Configuration option failed to load: " + e);
        } catch (Exception ignored) {}
    }
    private static JsonPrimitive load(JsonObject jsonObject, Option<JsonPrimitive> option) {
        try {
            return Optional.ofNullable(jsonObject.getAsJsonPrimitive(option.getId())).orElseGet(() -> loadDefault(option));
        } catch (RuntimeException e) {
            return loadDefault(option);
        }
    }
    private static JsonPrimitive loadDefault(Option<JsonPrimitive> option) {
        JsonPrimitive optionDefault = option.getDefault();
        Marbles.log(Level.WARN, "Config option " + option.getId() + " is not present! Defaulting to " + optionDefault);
        return optionDefault;
    }

    public static Screen createScreen(Screen parentScreen) {
        ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(parentScreen)
            .setDefaultBackgroundTexture(MarblesClient.texture("block/grisp_dirt"))
            .setTitle(createConfigText("title"))
            .setSavingRunnable(MarblesConfigManager::save);

        builder.setGlobalized(true);
        builder.setGlobalizedExpanded(false);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        /*
         * GRAPHICS CATEGORY
         */

        ConfigCategory GRAPHICS = builder.getOrCreateCategory(createGraphicsText());
        if (!ModLoaded.CANVAS) {
            TranslatableText additionalCloudLayers = createGraphicsText(MarblesConfig.Graphics.additionalCloudLayers.getId());
            Option<JsonPrimitive> additionalCloudLayersOption = MarblesConfig.Graphics.additionalCloudLayers;
            GRAPHICS.addEntry(
                entryBuilder.startBooleanToggle(additionalCloudLayers, additionalCloudLayersOption.value.getAsBoolean())
                    .setDefaultValue(additionalCloudLayersOption.getDefault().getAsBoolean())
                    .setSaveConsumer(value -> additionalCloudLayersOption.value = new JsonPrimitive(value))
                    .setTooltip(createTooltip(additionalCloudLayers))
                    .build()
            );
        }
        TranslatableText onlyMarblesSplashes = createGraphicsText(MarblesConfig.Graphics.onlyMarblesSplashTexts.getId());
        Option<JsonPrimitive> onlyMarblesSplashesOption = MarblesConfig.Graphics.onlyMarblesSplashTexts;
        GRAPHICS.addEntry(
            entryBuilder.startBooleanToggle(onlyMarblesSplashes, onlyMarblesSplashesOption.value.getAsBoolean())
                        .setDefaultValue(onlyMarblesSplashesOption.getDefault().getAsBoolean())
                        .setSaveConsumer(value -> {
                            boolean valueChanged = value != onlyMarblesSplashesOption.value.getAsBoolean();
                            onlyMarblesSplashesOption.value = new JsonPrimitive(value);

                            if (valueChanged && Util.currentTitleScreen != null) {
                                ((TitleScreenAccessor) Util.currentTitleScreen).setSplashText(MinecraftClient.getInstance().getSplashTextLoader().get());
                            }
                        })
                        .setTooltip(createTooltip(onlyMarblesSplashes))
                        .build()
        );

        return builder.build();
    }

    /*
     *  UTILS
     */

    private static TranslatableText createTooltip(TranslatableText text) {
        return new TranslatableText(text.getKey() + ".tooltip");
    }

    private static TranslatableText createGraphicsText(String label) {
        return createCatText("graphics" + (label.isEmpty() ? "" : "." + label));
    }
    private static TranslatableText createGraphicsText() {
        return createGraphicsText("");
    }

    private static TranslatableText createCatText(String group) {
        return createConfigText("category." + group);
    }
    private static TranslatableText createConfigText(String label) {
        return new TranslatableText("config." + Marbles.MOD_ID + "." + label);
    }
}
