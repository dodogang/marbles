package net.dodogang.marbles.client.config;

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
import net.dodogang.marbles.util.ModLoaded;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
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

@Environment(EnvType.CLIENT)
public class MarblesConfigManager {
    private static final File FILE = FabricLoader.getInstance().getConfigDir().toFile().toPath().resolve(Marbles.MOD_ID + ".json").toFile();
    public static final List<Option<?>> OPTIONS = new LinkedList<>();

    static {
        MarblesConfigManager.load();
    }

    public static void save() {
        JsonObject jsonObject = new JsonObject();
        OPTIONS.forEach(option -> jsonObject.addProperty(option.getId(), option.getValueForSave()));

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

    @SuppressWarnings("ConstantConditions")
    public static void load() {
        try {
            String json = new String(Files.readAllBytes(FILE.toPath()));
            if (!json.isEmpty()) {
                JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);

                MarblesConfig.RenderGroup RENDER = MarblesConfig.RENDER;
                RENDER.additionalCloudLayers.value = MarblesConfigManager.load(jsonObject, RENDER.additionalCloudLayers).getAsBoolean();
            }
        } catch (IOException ignored) {
            Marbles.log(Level.WARN, "Could not load configuration file! Saving and loading default values.");
            MarblesConfigManager.save();
        } catch (NullPointerException e) {
            Marbles.log(Level.WARN, "Configuration failed to load fully from file due to " + e + ". This is probably just a configuration update.");
        } catch (IllegalArgumentException e) {
            Marbles.log(Level.ERROR, "Configuration option failed to load: " + e);
        }
    }
    private static JsonPrimitive load(JsonObject jsonObject, Option<?> option) {
        try {
            return jsonObject.getAsJsonPrimitive(option.getId());
        } catch (RuntimeException e) {
            Object optionDefault = option.getDefault();
            Marbles.log(Level.WARN, option.getId() + " is not present! Defaulting to " + optionDefault);
            if (optionDefault instanceof Boolean) {
                return new JsonPrimitive((Boolean) optionDefault);
            } else if (optionDefault instanceof Integer) {
                return new JsonPrimitive((Integer) optionDefault);
            } else if (optionDefault instanceof Enum<?>) {
                return new JsonPrimitive(String.valueOf(optionDefault));
            }

            return null;
        }
    }

    @SuppressWarnings("deprecation")
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
         *  RENDER CATEGORY
         */

        ConfigCategory RENDER = builder.getOrCreateCategory(createRenderText());
        if (!ModLoaded.CANVAS) {
            TranslatableText additionalCloudLayers = createRenderText(MarblesConfig.RENDER.additionalCloudLayers.getId());
            Option<Boolean> additionalCloudLayersOption = MarblesConfig.RENDER.additionalCloudLayers;
            RENDER.addEntry(
                entryBuilder.startBooleanToggle(additionalCloudLayers, additionalCloudLayersOption.value)
                    .setDefaultValue(additionalCloudLayersOption.getDefault())
                    .setSaveConsumer(value -> additionalCloudLayersOption.value = value)
                    .setTooltip(createTooltip(additionalCloudLayers))
                    .build()
            );
        }

        if (RENDER.getEntries().isEmpty()) {
            RENDER.removeCategory();
            builder.getOrCreateCategory(createCatText("none_available"));
        }

        return builder.build();
    }

    /*
     *  UTILS
     */

    private static TranslatableText createTooltip(TranslatableText text) {
        return new TranslatableText(text.getKey() + ".tooltip");
    }

    private static TranslatableText createRenderText(String label) {
        return createCatText("render" + (label.isEmpty() ? "" : "." + label));
    }
    private static TranslatableText createRenderText() {
        return createRenderText("");
    }

    private static TranslatableText createCatText(String group) {
        return createConfigText("category." + group);
    }
    private static TranslatableText createConfigText(String label) {
        return new TranslatableText("config." + Marbles.MOD_ID + "." + label);
    }
}
