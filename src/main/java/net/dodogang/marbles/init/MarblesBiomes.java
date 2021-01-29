package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.world.gen.feature.MarblesDefaultBiomeFeatures;
import net.dodogang.marbles.world.gen.feature.YellowBambooFeature;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;

@SuppressWarnings({"unused","deprecation"})
public class MarblesBiomes {
    public static final RegistryKey<Biome> YELLOW_BAMBOO_JUNGLE = register(YellowBambooFeature.id + "_jungle", createNormalYellowBambooJungle());
    public static final RegistryKey<Biome> YELLOW_BAMBOO_JUNGLE_HILLS = register(YellowBambooFeature.id + "_jungle_hills", createYellowBambooJungleHills());

    public static final RegistryKey<Biome> ASPEN_FOREST = register(MarblesBlocks.ASPEN.getId() + "_forest", createAspenForest());
    public static final RegistryKey<Biome> HOOPSI_SPRUCE_FOREST = register(MarblesBlocks.HOOPSI_SPRUCE.getId() + "_forest", createHoopsiSpruceForest());

    public MarblesBiomes() {
        OverworldBiomes.addBiomeVariant(BiomeKeys.BAMBOO_JUNGLE, MarblesBiomes.YELLOW_BAMBOO_JUNGLE, 0.5F, OverworldClimate.TEMPERATE);
        OverworldBiomes.addHillsBiome(MarblesBiomes.YELLOW_BAMBOO_JUNGLE, MarblesBiomes.YELLOW_BAMBOO_JUNGLE_HILLS, 1.0F);
    }

    protected static Biome createAspenForest() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(spawnSettings);
        DefaultBiomeFeatures.addBatsAndMonsters(spawnSettings);

        GenerationSettings.Builder generationSettings = (new GenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
        DefaultBiomeFeatures.addDefaultUndergroundStructures(generationSettings);
        generationSettings.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addDefaultLakes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addForestFlowers(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        MarblesDefaultBiomeFeatures.addAspenTrees(generationSettings);
        DefaultBiomeFeatures.addDefaultFlowers(generationSettings);
        DefaultBiomeFeatures.addForestGrass(generationSettings);
        DefaultBiomeFeatures.addDefaultMushrooms(generationSettings);
        DefaultBiomeFeatures.addDefaultVegetation(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        return new Biome.Builder().precipitation(Biome.Precipitation.RAIN).category(Biome.Category.FOREST).depth(0.1F).scale(0.2F).temperature(0.6F).downfall(0.6F).effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColor(0.6F)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }

    protected static Biome createHoopsiSpruceForest() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(spawnSettings);
        spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 8, 4, 4)).spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3)).spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4));
        DefaultBiomeFeatures.addBatsAndMonsters(spawnSettings);
        spawnSettings.playerSpawnFriendly();

        GenerationSettings.Builder generationSettings = (new GenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
        generationSettings.structureFeature(ConfiguredStructureFeatures.VILLAGE_TAIGA);
        generationSettings.structureFeature(ConfiguredStructureFeatures.PILLAGER_OUTPOST);
        generationSettings.structureFeature(ConfiguredStructureFeatures.IGLOO);

        DefaultBiomeFeatures.addDefaultUndergroundStructures(generationSettings);
        generationSettings.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addDefaultLakes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addLargeFerns(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        MarblesDefaultBiomeFeatures.addHoopsiSpruceTrees(generationSettings);
        DefaultBiomeFeatures.addDefaultFlowers(generationSettings);
        DefaultBiomeFeatures.addTaigaGrass(generationSettings);
        DefaultBiomeFeatures.addDefaultMushrooms(generationSettings);
        DefaultBiomeFeatures.addDefaultVegetation(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addSweetBerryBushesSnowy(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        float temperature = -0.5F;
        return new Biome.Builder().precipitation(Biome.Precipitation.SNOW).category(Biome.Category.TAIGA).depth(0.2F).scale(0.2F).temperature(temperature).downfall(0.4F).effects(new BiomeEffects.Builder().waterColor(4020182).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColor(temperature)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }

    /*
     * Modified version of DefaultBiomeCreator#createNormalBambooJungle for yellow bamboo.
     */
    protected static Biome createNormalYellowBambooJungle() {
        return createYellowBambooJungle(0.1F, 0.2F, 40, 2);
    }
    /*
     * Modified version of DefaultBiomeCreator#createBambooJungleHills for yellow bamboo.
     */
    protected static Biome createYellowBambooJungleHills() {
        return createYellowBambooJungle(0.45F, 0.3F, 10, 1);
    }

    /*
     * Copied from DefaultBiomeCreator#createBambooJungle.
     */
    protected static Biome createYellowBambooJungle(float depth, float scale, int parrotWeight, int parrotMaxGroupSize) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addJungleMobs(builder);
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PARROT, parrotWeight, 1, parrotMaxGroupSize)).spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PANDA, 80, 1, 2)).spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.OCELOT, 2, 1, 1));
        return createJungleFeatures(depth, scale, 0.9F, true, false, false, builder);
    }

    /*
     * Modified version of DefaultBiomeCreator#createJungleFeatures for yellow bamboo.
     */
    protected static Biome createJungleFeatures(float depth, float scale, float downfall, boolean yellowBambooJungle, boolean edge, boolean noStructures, SpawnSettings.Builder spawnSettings) {
        GenerationSettings.Builder generationSettings = (new GenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
        if (!edge && !noStructures) {
            generationSettings.structureFeature(ConfiguredStructureFeatures.JUNGLE_PYRAMID);
        }

        DefaultBiomeFeatures.addDefaultUndergroundStructures(generationSettings);
        generationSettings.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_JUNGLE);
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addDefaultLakes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        if (yellowBambooJungle) {
            MarblesDefaultBiomeFeatures.addYellowBambooJungleTrees(generationSettings);
        } else {
            if (!edge && !noStructures) {
                MarblesDefaultBiomeFeatures.addYellowBamboo(generationSettings);
            }

            if (edge) {
                DefaultBiomeFeatures.addJungleEdgeTrees(generationSettings);
            } else {
                DefaultBiomeFeatures.addJungleTrees(generationSettings);
            }
        }

        DefaultBiomeFeatures.addExtraDefaultFlowers(generationSettings);
        DefaultBiomeFeatures.addJungleGrass(generationSettings);
        DefaultBiomeFeatures.addDefaultMushrooms(generationSettings);
        DefaultBiomeFeatures.addDefaultVegetation(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addJungleVegetation(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        return new Biome.Builder().precipitation(Biome.Precipitation.RAIN).category(Biome.Category.JUNGLE).depth(depth).scale(scale).temperature(0.95F).downfall(downfall).effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColor(0.95F)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }

    /*
     * Same as DefaultBiomeCreator#getSkyColor
     */
    private static int getSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = MathHelper.clamp(f, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }

    private static RegistryKey<Biome> register(String id, Biome biome) {
        Identifier identifier = new Identifier(Marbles.MOD_ID, id);
        BuiltinRegistries.add(BuiltinRegistries.BIOME, identifier, biome);

        return MarblesBiomes.getKey(identifier);
    }
    private static RegistryKey<Biome> getKey(Identifier identifier) {
        return RegistryKey.of(Registry.BIOME_KEY, identifier);
    }
}
