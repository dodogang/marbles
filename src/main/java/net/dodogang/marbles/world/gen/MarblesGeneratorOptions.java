package net.dodogang.marbles.world.gen;

import com.google.common.base.MoreObjects;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSource;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.chunk.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class MarblesGeneratorOptions {
    private static final Logger LOGGER = LogManager.getLogger();


    public static GeneratorOptions forDemo(DynamicRegistryManager dynamicRegistryManager) {
        Registry<Biome> registry = dynamicRegistryManager.get(Registry.BIOME_KEY);
        int seed = "North Carolina".hashCode();

        Registry<DimensionType> registry2 = dynamicRegistryManager.get(Registry.DIMENSION_TYPE_KEY);
        Registry<ChunkGeneratorSettings> registry3 = dynamicRegistryManager.get(Registry.NOISE_SETTINGS_WORLDGEN);
        return new GeneratorOptions(seed, true, true, GeneratorOptions.method_28608(registry2, DimensionType.createDefaultDimensionOptions(registry2, registry, registry3, seed), GeneratorOptions.createOverworldGenerator(registry, registry3, seed)));
    }

    public static GeneratorOptions fromProperties(DynamicRegistryManager registries, Properties properties) {
        String generatorSettings = MoreObjects.firstNonNull(properties.getProperty("generator-settings"), "");
        properties.put("generator-settings", generatorSettings);

        String levelSeed = MoreObjects.firstNonNull(properties.getProperty("level-seed"), "");
        properties.put("level-seed", levelSeed);

        String generateStructuresStr = properties.getProperty("generate-structures");
        boolean generateStructures = generateStructuresStr == null || Boolean.parseBoolean(generateStructuresStr);
        properties.put("generate-structures", Objects.toString(generateStructures));

        String levelTypeRaw = properties.getProperty("level-type");
        String levelType = Optional.ofNullable(levelTypeRaw)
                                   .map(type -> type.toLowerCase(Locale.ROOT))
                                   .orElse("default");
        properties.put("level-type", levelType);

        long seed = new Random().nextLong();
        if (!levelSeed.isEmpty()) {
            try {
                long parsedSeed = Long.parseLong(levelSeed);
                if (parsedSeed != 0L) {
                    seed = parsedSeed;
                }
            } catch (NumberFormatException var18) {
                seed = levelSeed.hashCode();
            }
        }

        Registry<DimensionType> dimensionRegistry = registries.get(Registry.DIMENSION_TYPE_KEY);
        Registry<Biome> biomeRegistry = registries.get(Registry.BIOME_KEY);
        Registry<ChunkGeneratorSettings> settingsRegistry = registries.get(Registry.NOISE_SETTINGS_WORLDGEN);
        SimpleRegistry<DimensionOptions> dimOptionsRegistry = DimensionType.createDefaultDimensionOptions(
            dimensionRegistry, biomeRegistry, settingsRegistry, seed
        );

        switch (levelType) {
            case "flat":
                JsonObject settings = !generatorSettings.isEmpty()
                                      ? JsonHelper.deserialize(generatorSettings)
                                      : new JsonObject();

                Dynamic<JsonElement> dynamic = new Dynamic<>(JsonOps.INSTANCE, settings);
                DataResult<FlatChunkGeneratorConfig> result = FlatChunkGeneratorConfig.CODEC.parse(dynamic);
                return new GeneratorOptions(
                    seed,
                    generateStructures,
                    false,
                    GeneratorOptions.method_28608(
                        dimensionRegistry,
                        dimOptionsRegistry,
                        new FlatChunkGenerator(
                            result.resultOrPartial(LOGGER::error)
                                  .orElseGet(() -> FlatChunkGeneratorConfig.getDefaultConfig(biomeRegistry))
                        )
                    )
                );
            case "debug_all_block_states":
                return new GeneratorOptions(
                    seed,
                    generateStructures,
                    false,
                    GeneratorOptions.method_28608(
                        dimensionRegistry,
                        dimOptionsRegistry,
                        new DebugChunkGenerator(biomeRegistry)
                    )
                );
            case "amplified":
                return new GeneratorOptions(
                    seed,
                    generateStructures,
                    false,
                    GeneratorOptions.method_28608(
                        dimensionRegistry,
                        dimOptionsRegistry,
                        new NoiseChunkGenerator(
                            new VanillaLayeredBiomeSource(seed, false, false, biomeRegistry),
                            seed,
                            () -> settingsRegistry.getOrThrow(ChunkGeneratorSettings.AMPLIFIED)
                        )
                    )
                );
            case "largebiomes":
                return new GeneratorOptions(
                    seed,
                    generateStructures,
                    false,
                    GeneratorOptions.method_28608(
                        dimensionRegistry,
                        dimOptionsRegistry,
                        new NoiseChunkGenerator(
                            new VanillaLayeredBiomeSource(seed, false, true, biomeRegistry),
                            seed,
                            () -> settingsRegistry.getOrThrow(ChunkGeneratorSettings.OVERWORLD)
                        )
                    )
                );
            default:
                return new GeneratorOptions(
                    seed,
                    generateStructures,
                    false,
                    GeneratorOptions.method_28608(
                        dimensionRegistry,
                        dimOptionsRegistry,
                        GeneratorOptions.createOverworldGenerator(biomeRegistry, settingsRegistry, seed)
                    )
                );
        }
    }
}
