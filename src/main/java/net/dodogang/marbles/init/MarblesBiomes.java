package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.mixin.world.gen.DefaultBiomeCreatorInvoker;
import net.dodogang.marbles.world.gen.feature.MarblesDefaultBiomeFeatures;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;

@SuppressWarnings({ "unused", "deprecation" })
public class MarblesBiomes {
    /*
     * MISC
     */

    public static final RegistryKey<Biome> TRAVERTINE_STRAWS = register("travertine_straws", createTravertineStraws());

    /*
     * YELLOW BAMBOO
     */

    public static final RegistryKey<Biome> YELLOW_BAMBOO_JUNGLE = register("yellow_bamboo_jungle", createNormalYellowBambooJungle());
    public static final RegistryKey<Biome> YELLOW_BAMBOO_JUNGLE_HILLS = register("yellow_bamboo_jungle_hills", createYellowBambooJungleHills());

    /*
     * PERMAFROST
     */

    public static final RegistryKey<Biome> PERMAFROST_ICE_SPIKES = register("permafrost_ice_spikes", createPermafrostSnowyTundra(1.0f, 0.45F, true, false));
    public static final RegistryKey<Biome> WOODED_PERMAFROST_MOUNTAINS = register("wooded_permafrost_mountains", createPermafrostMountains(1.0F, 0.5F, true));

    /*
     * CAVE BIOMES
     */

    public static final RegistryKey<Biome> PINK_SALT_CAVE = register("pink_salt_cave", createPinkSaltCave());

    public static final RegistryKey<Biome> ICE_CAVE = register("ice_cave", createIceCave());
    public static final RegistryKey<Biome> SCALED_ICE_CAVE = register("scaled_ice_cave", createIceCave());
    public static final RegistryKey<Biome> MINTED_ICE_CAVE = register("minted_ice_cave", createIceCave());

    /*
     * TEST BIOMES
     */

    public static final RegistryKey<Biome> TEST_ASPEN_FOREST = register("aspen_forest", createAspenForest());
    public static final RegistryKey<Biome> TEST_HOOPSI_SPRUCE_FOREST = register("hoopsi_spruce_forest", createHoopsiSpruceForest());
    public static final RegistryKey<Biome> TEST_RED_BIRCH_FOREST = register("red_birch_forest", createRedBirchForest());

    static {
        OverworldBiomes.addContinentalBiome(TRAVERTINE_STRAWS, OverworldClimate.DRY, 0.8d);

        OverworldBiomes.addBiomeVariant(BiomeKeys.BAMBOO_JUNGLE, YELLOW_BAMBOO_JUNGLE, 0.5F);
        OverworldBiomes.addHillsBiome(YELLOW_BAMBOO_JUNGLE, YELLOW_BAMBOO_JUNGLE_HILLS, 1.0F);

        OverworldBiomes.addContinentalBiome(WOODED_PERMAFROST_MOUNTAINS, OverworldClimate.COOL, 1.0d);
        OverworldBiomes.addContinentalBiome(PERMAFROST_ICE_SPIKES, OverworldClimate.COOL, 1.0d);
    }

    public static Biome createPermafrostSnowyTundra(float depth, float scale, boolean iceSpikes, boolean mountains) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder().creatureSpawnProbability(0.07F);
        DefaultBiomeFeatures.addSnowyMobs(spawnSettings);

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder().surfaceBuilder(MarblesConfiguredSurfaceBuilders.PERMAFROST);
        if (!iceSpikes && !mountains) {
            generationSettings.structureFeature(ConfiguredStructureFeatures.VILLAGE_SNOWY).structureFeature(ConfiguredStructureFeatures.IGLOO);
        }

        DefaultBiomeFeatures.addDefaultUndergroundStructures(generationSettings);
        if (!iceSpikes && !mountains) {
            generationSettings.structureFeature(ConfiguredStructureFeatures.PILLAGER_OUTPOST);
        }

        generationSettings.structureFeature(mountains ? ConfiguredStructureFeatures.RUINED_PORTAL_MOUNTAIN : ConfiguredStructureFeatures.RUINED_PORTAL);

        generationSettings.carver(GenerationStep.Carver.AIR, MarblesConfiguredCarvers.PERMAFROST_CAVE);
        generationSettings.carver(GenerationStep.Carver.AIR, MarblesConfiguredCarvers.PERMAFROST_CANYON);

        DefaultBiomeFeatures.addDefaultLakes(generationSettings);
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        if (iceSpikes) {
            generationSettings.feature(GenerationStep.Feature.SURFACE_STRUCTURES, MarblesConfiguredFeatures.PERMAFROST_ICE_SPIKE);
            generationSettings.feature(GenerationStep.Feature.SURFACE_STRUCTURES, MarblesConfiguredFeatures.PERMAFROST_ICE_PATCH);
        }

        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.GLOW_LICHEN);
        generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.ORE_TUFF);
        generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.ORE_DEEPSLATE);
        generationSettings.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.RARE_DRIPSTONE_CLUSTER);
        generationSettings.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.RARE_SMALL_DRIPSTONE);

        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        DefaultBiomeFeatures.addSnowySpruceTrees(generationSettings);
        DefaultBiomeFeatures.addDefaultFlowers(generationSettings);
        DefaultBiomeFeatures.addDefaultGrass(generationSettings);
        DefaultBiomeFeatures.addDefaultMushrooms(generationSettings);
        DefaultBiomeFeatures.addDefaultVegetation(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, MarblesConfiguredFeatures.LARGE_SLUSH_DISK);
        generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, MarblesConfiguredFeatures.LARGE_ICE_DISK);

        generationSettings.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, MarblesConfiguredFeatures.SNOW_UNDER_TREES);

        return new Biome.Builder()
                    .precipitation(Biome.Precipitation.SNOW)
                    .category(Biome.Category.ICY)
                    .depth(depth).scale(scale)
                    .temperature(0.0F).downfall(0.5F)
                    .effects(
                        new BiomeEffects.Builder()
                            .waterColor(0x242356).skyColor(getSkyColor(0.0F))
                            .fogColor(0x344D6B).waterFogColor(0x344D6B)
                            .moodSound(BiomeMoodSound.CAVE)
                            .build()
                    )
                    .spawnSettings(spawnSettings.build())
                    .generationSettings(generationSettings.build())
                .build();
    }

    protected static Biome createPermafrostMountains(float depth, float scale, boolean extraTrees) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(spawnSettings);
        spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.LLAMA, 5, 4, 6));
        spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.GOAT, 10, 4, 6));
        DefaultBiomeFeatures.addBatsAndMonsters(spawnSettings);

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder().surfaceBuilder(MarblesConfiguredSurfaceBuilders.PERMAFROST);
        DefaultBiomeFeatures.addDefaultUndergroundStructures(generationSettings);
        generationSettings.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_MOUNTAIN);

        generationSettings.carver(GenerationStep.Carver.AIR, MarblesConfiguredCarvers.PERMAFROST_CAVE);
        generationSettings.carver(GenerationStep.Carver.AIR, MarblesConfiguredCarvers.PERMAFROST_CANYON);

        DefaultBiomeFeatures.addDefaultLakes(generationSettings);
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);

        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.GLOW_LICHEN);
        generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.ORE_TUFF);
        generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, ConfiguredFeatures.ORE_DEEPSLATE);
        generationSettings.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.RARE_DRIPSTONE_CLUSTER);
        generationSettings.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.RARE_SMALL_DRIPSTONE);

        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        if (extraTrees) {
            DefaultBiomeFeatures.addExtraMountainTrees(generationSettings);
        } else {
            DefaultBiomeFeatures.addMountainTrees(generationSettings);
        }

        DefaultBiomeFeatures.addDefaultFlowers(generationSettings);
        DefaultBiomeFeatures.addDefaultGrass(generationSettings);
        DefaultBiomeFeatures.addDefaultMushrooms(generationSettings);
        DefaultBiomeFeatures.addDefaultVegetation(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addEmeraldOre(generationSettings);
        DefaultBiomeFeatures.addInfestedStone(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, MarblesConfiguredFeatures.LARGE_SLUSH_DISK);
        generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, MarblesConfiguredFeatures.LARGE_ICE_DISK);

        generationSettings.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, MarblesConfiguredFeatures.SNOW_UNDER_TREES);

        float temp = -0.5f;
        return new Biome.Builder()
                    .precipitation(Biome.Precipitation.SNOW)
                    .category(Biome.Category.EXTREME_HILLS)
                    .depth(depth).scale(scale)
                    .temperature(temp).downfall(0.4F)
                    .effects(
                        new BiomeEffects.Builder()
                            .waterColor(0x242356).skyColor(getSkyColor(temp))
                            .fogColor(0x344D6B).waterFogColor(0x344D6B)
                            .moodSound(BiomeMoodSound.CAVE)
                        .build()
                    )
                    .spawnSettings(spawnSettings.build())
                    .generationSettings(generationSettings.build())
                .build();
    }

    // unfinished travertine straws biome, just for people to get travertine from
    protected static Biome createTravertineStraws() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addBatsAndMonsters(spawnSettings);

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder()
            .surfaceBuilder(MarblesConfiguredSurfaceBuilders.TRAVERTINE_STRAWS)
            .structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_MOUNTAIN);

        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        return new Biome.Builder()
                    .precipitation(Biome.Precipitation.NONE)
                    .category(Biome.Category.MESA)
                    .depth(1.4F).scale(0.4F)
                    .temperature(2.0F).downfall(0.0F)
                    .effects(
                        new BiomeEffects.Builder()
                            .waterColor(0x3f76e4).skyColor(getSkyColor(2.0F))
                            .grassColor(0x90814d).foliageColor(0x9e814d)
                            .waterFogColor(0x50533).fogColor(0xc0d8ff)
                            .moodSound(BiomeMoodSound.CAVE)
                            .build()
                    )
                    .spawnSettings(spawnSettings.build())
                    .generationSettings(generationSettings.build())
                .build();
    }

    protected static Biome createAspenForest() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(spawnSettings);
        DefaultBiomeFeatures.addBatsAndMonsters(spawnSettings);

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
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

        return new Biome.Builder()
                    .precipitation(Biome.Precipitation.RAIN)
                    .category(Biome.Category.FOREST)
                    .depth(0.1F).scale(0.2F)
                    .temperature(0.6F).downfall(0.6F)
                    .effects(
                        new BiomeEffects.Builder()
                            .waterColor(0x3f76e4).skyColor(getSkyColor(0.6F))
                            .fogColor(0xc0d8ff).waterFogColor(0x50533)
                            .moodSound(BiomeMoodSound.CAVE)
                            .build()
                    )
                    .spawnSettings(spawnSettings.build())
                    .generationSettings(generationSettings.build())
                .build();
    }

    protected static Biome createHoopsiSpruceForest() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(spawnSettings);
        spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 8, 4, 4)).spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3)).spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4));
        DefaultBiomeFeatures.addBatsAndMonsters(spawnSettings);
        spawnSettings.playerSpawnFriendly();

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
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
        return new Biome.Builder()
                    .precipitation(Biome.Precipitation.SNOW)
                    .category(Biome.Category.TAIGA)
                    .depth(0.2F).scale(0.2F)
                    .temperature(temperature).downfall(0.4F)
                    .effects(
                        new BiomeEffects.Builder()
                            .waterColor(4020182).skyColor(getSkyColor(temperature))
                            .fogColor(12638463).waterFogColor(329011)
                            .moodSound(BiomeMoodSound.CAVE)
                        .build()
                    )
                    .spawnSettings(spawnSettings.build())
                    .generationSettings(generationSettings.build())
                .build();
    }

    protected static Biome createRedBirchForest() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(spawnSettings);
        DefaultBiomeFeatures.addBatsAndMonsters(spawnSettings);

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
        DefaultBiomeFeatures.addDefaultUndergroundStructures(generationSettings);
        generationSettings.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addDefaultLakes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addForestFlowers(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, MarblesConfiguredFeatures.TREES_RED_BIRCH);
        DefaultBiomeFeatures.addDefaultFlowers(generationSettings);
        DefaultBiomeFeatures.addForestGrass(generationSettings);
        DefaultBiomeFeatures.addDefaultMushrooms(generationSettings);
        DefaultBiomeFeatures.addDefaultVegetation(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        return new Biome.Builder()
                    .precipitation(Biome.Precipitation.RAIN)
                    .category(Biome.Category.FOREST)
                    .depth(0.1F).scale(0.2F)
                    .temperature(0.6F).downfall(0.6F)
                    .effects(
                        new BiomeEffects.Builder()
                            .waterColor(4159204).skyColor(getSkyColor(0.6F))
                            .waterFogColor(329011).fogColor(12638463)
                            .moodSound(BiomeMoodSound.CAVE)
                        .build()
                    )
                    .spawnSettings(spawnSettings.build())
                    .generationSettings(generationSettings.build())
                .build();
    }

    /**
     * Dummy biome configuration existing only for biome effects in pink salt caves.
     */
    protected static Biome createPinkSaltCave() {
        return new Biome.Builder() // mostly dummy configuration, apart from spawn settings and biome effects
                    .precipitation(Biome.Precipitation.RAIN)
                    .category(Biome.Category.NONE)
                    .depth(0.0f)
                    .scale(0.0f)
                    .temperature(0.5f)
                    .downfall(0.5f)
                    .generationSettings(new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS).build())

                    .effects(
                        new BiomeEffects.Builder()
                            .particleConfig(new BiomeParticleConfig(MarblesParticles.PINK_SALT, 0.013f))
                            .waterColor(0x242356).skyColor(0xBD6541)
                            .fogColor(0x344D6B).waterFogColor(0x344D6B)

                            .loopSound(MarblesSoundEvents.AMBIENT_PINK_SALT_CAVE_LOOP)
                            .additionsSound(new BiomeAdditionsSound(MarblesSoundEvents.AMBIENT_PINK_SALT_CAVE_ADDITIONS, 0.0111d))
                            .music(new MusicSound(MarblesSoundEvents.MUSIC_PINK_SALT_CAVE, 12000, 24000, false))
                        .build()
                    )
                    .spawnSettings(
                        new SpawnSettings.Builder()
                            // .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIE, 1, 1, 3))
                        .build()
                    )
                .build();
    }

    /**
     * Dummy biome configuration existing only for biome effects in ice caves.
     */
    protected static Biome createIceCave() {
        return new Biome.Builder()
                    .precipitation(Biome.Precipitation.RAIN)
                    .category(Biome.Category.NONE)
                    .depth(0.0f)
                    .scale(0.0f)
                    .temperature(0.5f)
                    .downfall(0.5f)
                    .generationSettings(new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS).build())

                    .effects(
                        new BiomeEffects.Builder()
                            .particleConfig(new BiomeParticleConfig(MarblesParticles.ICE_SPORE, 0.007f))
                            .waterColor(0x242356)
                            .waterFogColor(0x344D6B)
                            .fogColor(0x344D6B)
                            .skyColor(0x344D6B)

                            .loopSound(MarblesSoundEvents.AMBIENT_ICE_CAVE_LOOP)
                            .additionsSound(new BiomeAdditionsSound(MarblesSoundEvents.AMBIENT_ICE_CAVE_ADDITIONS, 0.0111d))
                            .music(new MusicSound(MarblesSoundEvents.MUSIC_ICE_CAVE, 12000, 24000, false))
                        .build()
                    )
                    .spawnSettings(
                        new SpawnSettings.Builder()
                            .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.DROWNED, 1, 1, 1))
                        .build()
                    )
                .build();
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
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
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

        return new Biome.Builder()
                    .precipitation(Biome.Precipitation.RAIN)
                    .category(Biome.Category.JUNGLE)
                    .depth(depth).scale(scale)
                    .temperature(0.95F).downfall(downfall)
                    .effects(
                        new BiomeEffects.Builder()
                            .waterColor(4159204).skyColor(getSkyColor(0.95F))
                            .waterFogColor(329011).fogColor(12638463)
                            .moodSound(BiomeMoodSound.CAVE)
                        .build()
                    )
                    .spawnSettings(spawnSettings.build())
                    .generationSettings(generationSettings.build())
                .build();
    }

    private static int getSkyColor(float temperature) {
        return DefaultBiomeCreatorInvoker.invoke_getSkyColor(temperature);
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
