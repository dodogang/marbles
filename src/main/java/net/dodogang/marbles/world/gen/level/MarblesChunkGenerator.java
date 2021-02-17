package net.dodogang.marbles.world.gen.level;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.dodogang.marbles.mixin.hooks.worldgen.ChunkGenSettingsAccessor;
import net.dodogang.marbles.world.gen.level.terrain.SaltCaveTerrain;
import net.dodogang.marbles.world.gen.level.terrain.Terrain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.structure.JigsawJunction;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Util;
import net.minecraft.util.math.*;
import net.minecraft.util.math.noise.*;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import net.shadew.ptg.noise.Noise2D;
import net.shadew.ptg.noise.opensimplex.OpenSimplex2D;

public final class MarblesChunkGenerator extends ChunkGenerator {
    public static final Codec<MarblesChunkGenerator> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            BiomeSource.CODEC.fieldOf("biome_source")
                             .stable()
                             .forGetter(gen -> gen.populationSource),
            Codec.LONG.fieldOf("seed")
                      .stable()
                      .forGetter(gen -> gen.seed),
            ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings")
                                                 .stable()
                                                 .forGetter(gen -> gen.settings)
        ).apply(instance, instance.stable(MarblesChunkGenerator::new))
    );

    private static final int STRUCTURE_INFLUENCE_RANGE = 12;
    private static final int STRUCTURE_INFLUENCE_DIAMETER = 2 * STRUCTURE_INFLUENCE_RANGE;

    private static final float[] NOISE_WEIGHT_TABLE = Util.make(
        new float[STRUCTURE_INFLUENCE_DIAMETER * STRUCTURE_INFLUENCE_DIAMETER * STRUCTURE_INFLUENCE_DIAMETER],
        weights -> {
            int diameter = STRUCTURE_INFLUENCE_DIAMETER;
            for (int z = 0; z < diameter; z++) {
                for (int x = 0; x < diameter; x++) {
                    for (int y = 0; y < diameter; y++) {
                        weights[z * diameter * diameter + x * diameter + y] = (float) calculateNoiseWeight(
                            x - STRUCTURE_INFLUENCE_RANGE,
                            y - STRUCTURE_INFLUENCE_RANGE,
                            z - STRUCTURE_INFLUENCE_RANGE
                        );
                    }
                }
            }
        }
    );

    private static final int BIOME_INFLUENCE_RANGE = 2;
    private static final int BIOME_INFLUENCE_DIAMETER = 2 * BIOME_INFLUENCE_RANGE + 1;

    private static final float[] BIOME_WEIGHT_TABLE = Util.make(
        new float[BIOME_INFLUENCE_DIAMETER * BIOME_INFLUENCE_DIAMETER],
        weights -> {
            int radius = BIOME_INFLUENCE_RANGE;

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    float dx = x / 2f * radius;
                    float dz = z / 2f * radius;

                    float wgt = 10 / MathHelper.sqrt(dx * dx + dz * dz + 0.2f);
                    weights[x + radius + (z + radius) * BIOME_INFLUENCE_DIAMETER] = wgt;
                }
            }
        }
    );

    private static final double DEFAULT_NOISE_SCALE = 684.412;
    private static final double DEFAULT_ELEVATION = 17;
    private static final double DEFAULT_DEPTH_SCALE = DEFAULT_ELEVATION / 64;
    private static final double RANDOM_DENSITY_BASE = DEFAULT_DEPTH_SCALE / 4;
    private static final double RANDOM_DENSITY_SCALE = 24.575625;

    private static final double SURFACE_DEPTH_SCALE = 0.0625;

    private static final BlockState AIR = Blocks.AIR.getDefaultState();

    private final int vResolution;
    private final int hResolution;

    private final int noiseSizeX;
    private final int noiseSizeY;
    private final int noiseSizeZ;

    private final OctavePerlinNoiseSampler lowerNoise;
    private final OctavePerlinNoiseSampler upperNoise;
    private final OctavePerlinNoiseSampler lerpNoise;

    private final NoiseSampler surfaceDepthNoise;

    private final OctavePerlinNoiseSampler densityNoise;
    private final SimplexNoiseSampler islandNoise;

    private final BlockState defaultBlock;
    private final BlockState defaultFluid;

    private final ChunkRandom random;

    private final long seed;

    private final Supplier<ChunkGeneratorSettings> settings;

    private final int worldHeight;

    private final Object2ObjectLinkedOpenHashMap<ChunkPos, List<Terrain>> terrains = new Object2ObjectLinkedOpenHashMap<>();

    private final Noise2D saltCaveNoise;

    public MarblesChunkGenerator(BiomeSource biomeSource, long seed, Supplier<ChunkGeneratorSettings> settings) {
        this(biomeSource, biomeSource, seed, settings);
    }

    private MarblesChunkGenerator(BiomeSource populationSource, BiomeSource biomeSource, long seed, Supplier<ChunkGeneratorSettings> settingsSupplier) {
        super(populationSource, biomeSource, settingsSupplier.get().getStructuresConfig(), seed);
        this.seed = seed;
        this.settings = settingsSupplier;

        ChunkGeneratorSettings settings = settingsSupplier.get();
        GenerationShapeConfig shapeConfig = settings.getGenerationShapeConfig();

        worldHeight = shapeConfig.getHeight();
        vResolution = shapeConfig.getSizeVertical() * 4;
        hResolution = shapeConfig.getSizeHorizontal() * 4;

        defaultBlock = settings.getDefaultBlock();
        defaultFluid = settings.getDefaultFluid();

        noiseSizeX = 16 / hResolution;
        noiseSizeY = shapeConfig.getHeight() / vResolution;
        noiseSizeZ = 16 / hResolution;

        random = new ChunkRandom(seed);

        lowerNoise = new OctavePerlinNoiseSampler(random, IntStream.rangeClosed(-15, 0));
        upperNoise = new OctavePerlinNoiseSampler(random, IntStream.rangeClosed(-15, 0));
        lerpNoise = new OctavePerlinNoiseSampler(random, IntStream.rangeClosed(-7, 0));

        surfaceDepthNoise = shapeConfig.hasSimplexSurfaceNoise()
                            ? new OctaveSimplexNoiseSampler(random, IntStream.rangeClosed(-3, 0))
                            : new OctavePerlinNoiseSampler(random, IntStream.rangeClosed(-3, 0));

        random.consume(2620);
        densityNoise = new OctavePerlinNoiseSampler(random, IntStream.rangeClosed(-15, 0));

        if (shapeConfig.hasIslandNoiseOverride()) {
            ChunkRandom islandNoiseRng = new ChunkRandom(seed);
            islandNoiseRng.consume(17292);
            islandNoise = new SimplexNoiseSampler(islandNoiseRng);
        } else {
            islandNoise = null;
        }

        saltCaveNoise = new OpenSimplex2D(random.nextInt(), 114.2412);
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ChunkGenerator withSeed(long seed) {
        return new MarblesChunkGenerator(populationSource.withSeed(seed), seed, settings);
    }

    public boolean matchesSettings(long seed, RegistryKey<ChunkGeneratorSettings> settingsKey) {
        return this.seed == seed && settings.get().equals(settingsKey);
    }

    private double sampleNoise(int x, int y, int z, double horizontalScale, double verticalScale, double horizontalStretch, double verticalStretch) {
        double lo = 0;
        double hi = 0;
        double lerp = 0;
        double octaveScale = 1;

        for (int octave = 0; octave < 16; ++octave) {
            double preciseX = OctavePerlinNoiseSampler.maintainPrecision(x * horizontalScale * octaveScale);
            double preciseY = OctavePerlinNoiseSampler.maintainPrecision(y * verticalScale * octaveScale);
            double preciseZ = OctavePerlinNoiseSampler.maintainPrecision(z * horizontalScale * octaveScale);

            double vscale = verticalScale * octaveScale;

            PerlinNoiseSampler lowerSampler = lowerNoise.getOctave(octave);
            if (lowerSampler != null) {
                lo += lowerSampler.sample(preciseX, preciseY, preciseZ, vscale, y * vscale) / octaveScale;
            }

            PerlinNoiseSampler upperSampler = upperNoise.getOctave(octave);
            if (upperSampler != null) {
                hi += upperSampler.sample(preciseX, preciseY, preciseZ, vscale, y * vscale) / octaveScale;
            }

            if (octave < 8) {
                preciseX = OctavePerlinNoiseSampler.maintainPrecision(x * horizontalStretch * octaveScale);
                preciseY = OctavePerlinNoiseSampler.maintainPrecision(y * verticalStretch * octaveScale);
                preciseZ = OctavePerlinNoiseSampler.maintainPrecision(z * horizontalStretch * octaveScale);

                vscale = verticalStretch * octaveScale;

                PerlinNoiseSampler lerpSampler = lerpNoise.getOctave(octave);
                if (lerpSampler != null) {
                    lerp += lerpSampler.sample(preciseX, preciseY, preciseZ, vscale, y * vscale) / octaveScale;
                }
            }

            octaveScale /= 2;
        }

        return MathHelper.clampedLerp(lo / 512, hi / 512, (lerp / 10 + 1) / 2);
    }

    private double[] sampleNoiseColumn(int x, int z) {
        double[] column = new double[noiseSizeY + 1];
        sampleNoiseColumn(column, x, z);
        return column;
    }

    private void sampleNoiseColumn(double[] buffer, int x, int z) {
        GenerationShapeConfig shapeConfig = settings.get().getGenerationShapeConfig();
        double terrainDepth;
        double terrainRoughness;
        if (islandNoise != null) {
            terrainDepth = TheEndBiomeSource.getNoiseAt(islandNoise, x, z) - 8;
            if (terrainDepth > 0) {
                terrainRoughness = 0.25;
            } else {
                terrainRoughness = 1;
            }
        } else {
            float tscl = 0;
            float tdpt = 0;
            float twgt = 0;
            int seaLevel = getSeaLevel();

            // Depth of center biome
            float cdpt = populationSource.getBiomeForNoiseGen(x, seaLevel, z).getDepth();

            // Smooth biomes by blurring biome depth/scale field
            int radius = BIOME_INFLUENCE_RANGE;
            for (int ox = -radius; ox <= radius; ox++) {
                for (int oz = -radius; oz <= radius; oz++) {
                    Biome biome = populationSource.getBiomeForNoiseGen(x + ox, seaLevel, z + oz);

                    float rdpt = biome.getDepth();
                    float rscl = biome.getScale();

                    // Amplified geneartor
                    float adpt;
                    float ascl;
                    if (shapeConfig.isAmplified() && rdpt > 0) {
                        adpt = 1 + rdpt * 2;
                        ascl = 1 + rscl * 4;
                    } else {
                        adpt = rdpt;
                        ascl = rscl;
                    }

                    // If we're lower than another biome, smoothen the weight a bit so we don't make hills too steep
                    float weightInfluence = rdpt > cdpt ? 0.5f : 1;

                    float rwgt = BIOME_WEIGHT_TABLE[ox + radius + (oz + radius) * BIOME_INFLUENCE_DIAMETER];
                    float wgt = weightInfluence * rwgt / (adpt + 2);
                    tscl += ascl * wgt;
                    tdpt += adpt * wgt;
                    twgt += wgt;
                }
            }

            // Local depth/scale
            double depth = tdpt / twgt;
            double scale = tscl / twgt;
            depth = depth / 2 - 0.125;               // Base depth (plains depth = 0.125 -> -0.0625)
            scale = MathHelper.lerp(scale, 0.1, 1);  // Range 0-1 to 0.1-1, since we cause a division by 0 if scale is 0

            terrainDepth = depth * DEFAULT_DEPTH_SCALE;
            terrainRoughness = 96 / scale;
        }

        double hNoiseScale = DEFAULT_NOISE_SCALE * shapeConfig.getSampling().getXZScale();
        double vNoiseScale = DEFAULT_NOISE_SCALE * shapeConfig.getSampling().getYScale();

        double hNoiseSize = hNoiseScale / shapeConfig.getSampling().getXZFactor();
        double vNoiseSize = vNoiseScale / shapeConfig.getSampling().getYFactor();

        SlideConfig topSlide = shapeConfig.getTopSlide();
        SlideConfig bottomSlide = shapeConfig.getBottomSlide();

        double randomDensity = shapeConfig.hasRandomDensityOffset() ? getRandomDensityAt(x, z) : 0;
        double densityFactor = shapeConfig.getDensityFactor(); // overworld = 1
        double densityOffset = shapeConfig.getDensityOffset(); // overworld = -30/64 = -0.46875

        for (int y = 0; y <= noiseSizeY; y++) {
            // Compute raw noise
            double noise = sampleNoise(x, y, z, hNoiseScale, vNoiseScale, hNoiseSize, vNoiseSize);

            double densitySlide = 1 - y * 2d / noiseSizeY + randomDensity;
            double densityInterpolation = densitySlide * densityFactor + densityOffset;
            double density = (densityInterpolation + terrainDepth) * terrainRoughness;

            if (density > 0) {
                noise += density * 4; // Multiply by four, avoids random holes in terrain and removes most overhang
            } else {
                noise += density;
            }

            noise = applySlide(noiseSizeY - y, topSlide, noise);
            noise = applySlide(y, bottomSlide, noise);

            buffer[y] = noise;
        }
    }

    private static double applySlide(double y, SlideConfig slide, double noise) {
        double slideSize = slide.getSize();
        double slideOffset = slide.getOffset();
        double slideTarget = slide.getTarget();

        if (slideSize > 0) {
            double slidev = (y - slideOffset) / slideSize;
            noise = MathHelper.clampedLerp(slideTarget, noise, slidev);
        }
        return noise;
    }

    private double getRandomDensityAt(int x, int z) {
        double noise = densityNoise.sample(x * 200, 10, z * 200, 1, 0, true);
        double dnoise;
        if (noise < 0) {
            dnoise = -noise * 0.3;
        } else {
            dnoise = noise;
        }

        double density = dnoise * RANDOM_DENSITY_SCALE - 2;
        return density < 0
               ? density * (RANDOM_DENSITY_BASE / 7)
               : Math.min(density, 1) * RANDOM_DENSITY_BASE;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmapType) {
        return this.sampleHeightmap(x, z, null, heightmapType.getBlockPredicate());
    }

    @Override
    public BlockView getColumnSample(int x, int z) {
        BlockState[] states = new BlockState[noiseSizeY * vResolution];
        sampleHeightmap(x, z, states, null);
        return new VerticalBlockSample(states);
    }

    private int sampleHeightmap(int x, int z, BlockState[] states, Predicate<BlockState> heightmapPredicate) {
        int ox = Math.floorDiv(x, hResolution);
        int oz = Math.floorDiv(z, hResolution);
        double dx = (double) Math.floorMod(x, hResolution) / hResolution;
        double dz = (double) Math.floorMod(z, hResolution) / hResolution;

        double[][] columns = {
            sampleNoiseColumn(ox, oz),
            sampleNoiseColumn(ox, oz + 1),
            sampleNoiseColumn(ox + 1, oz),
            sampleNoiseColumn(ox + 1, oz + 1)
        };

        for (int oy = noiseSizeY - 1; oy >= 0; oy--) {
            double n000 = columns[0b00][oy];
            double n001 = columns[0b01][oy];
            double n100 = columns[0b10][oy];
            double n101 = columns[0b11][oy];
            double n010 = columns[0b00][oy + 1];
            double n011 = columns[0b01][oy + 1];
            double n110 = columns[0b10][oy + 1];
            double n111 = columns[0b11][oy + 1];

            for (int ly = vResolution - 1; ly >= 0; ly--) {
                double dy = (double) ly / vResolution;
                double density = MathHelper.lerp3(dy, dx, dz, n000, n010, n100, n110, n001, n011, n101, n111);

                int y = oy * vResolution + ly;
                BlockState blockState = getBlockState(density, y);
                if (states != null) {
                    states[y] = blockState;
                }

                if (heightmapPredicate != null && heightmapPredicate.test(blockState)) {
                    return y + 1;
                }
            }
        }

        return 0;
    }

    private BlockState getBlockState(double density, int y) {
        BlockState state;
        if (density > 0) {
            state = defaultBlock;
        } else if (y < getSeaLevel()) {
            state = defaultFluid;
        } else {
            state = AIR;
        }

        return state;
    }

    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {
        ChunkPos cpos = chunk.getPos();
        int cx = cpos.x;
        int cz = cpos.z;

        ChunkRandom rng = new ChunkRandom();
        rng.setTerrainSeed(cx, cz);

        int sx = cpos.getStartX();
        int sz = cpos.getStartZ();

        double dscale = SURFACE_DEPTH_SCALE;

        BlockPos.Mutable mpos = new BlockPos.Mutable();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int gx = sx + x;
                int gz = sz + z;

                int height = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, x, z) + 1;
                double depth = surfaceDepthNoise.sample(gx * dscale, gz * dscale, dscale, x * dscale) * 1;

                region.getBiome(mpos.set(sx + x, height, sz + z))
                      .buildSurface(rng, chunk, gx, gz, height, depth, defaultBlock, defaultFluid, getSeaLevel(), region.getSeed());
            }
        }

        buildBedrock(chunk, rng);
    }

    private void buildBedrock(Chunk chunk, Random rng) {
        BlockPos.Mutable mpos = new BlockPos.Mutable();
        ChunkGeneratorSettings settings = this.settings.get();

        int sx = chunk.getPos().getStartX();
        int sz = chunk.getPos().getStartZ();

        int floorY = settings.getBedrockFloorY();
        int ceilY = worldHeight - 1 - settings.getBedrockCeilingY();

        boolean hasCeiling = ceilY + 4 >= 0 && ceilY < worldHeight;
        boolean hasFloor = floorY + 4 >= 0 && floorY < worldHeight;
        if (hasCeiling || hasFloor) {
            for (BlockPos pos : BlockPos.iterate(sx, 0, sz, sx + 15, 0, sz + 15)) {
                if (hasCeiling) {
                    for (int o = 0; o < 5; o++) {
                        if (o <= rng.nextInt(5)) {
                            chunk.setBlockState(mpos.set(pos.getX(), ceilY - o, pos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                        }
                    }
                }

                if (hasFloor) {
                    for (int o = 4; o >= 0; o--) {
                        if (o <= rng.nextInt(5)) {
                            chunk.setBlockState(mpos.set(pos.getX(), floorY + o, pos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                        }
                    }
                }
            }
        }
    }

    private void modifyNoiseColumn(double[] noise, int x, int z, Chunk chunk, List<Terrain> terrains) {
        for (int y = 0; y <= noiseSizeY; y++) {
            for (Terrain terrain : terrains) {
                noise[y] = terrain.generate(x * hResolution, y * vResolution, z * hResolution, noise[y], chunk);
            }
        }
    }

    @Override
    public void populateNoise(WorldAccess world, StructureAccessor structures, Chunk chunk) {
        ObjectList<StructurePiece> rigidProjectionPieces = new ObjectArrayList<>(10);
        ObjectList<JigsawJunction> intersectingJunctions = new ObjectArrayList<>(32);

        List<Terrain> terrains = getTerrains(chunk);

        ChunkPos cpos = chunk.getPos();
        int chunkX = cpos.x;
        int chunkZ = cpos.z;
        int originX = chunkX << 4;
        int originZ = chunkZ << 4;

        int inflRange = STRUCTURE_INFLUENCE_RANGE;

        for (StructureFeature<?> structure : StructureFeature.JIGSAW_STRUCTURES) {
            structures.getStructuresWithChildren(ChunkSectionPos.from(cpos, 0), structure).forEach(start -> {
                for (StructurePiece piece : start.getChildren()) {
                    if (!piece.intersectsChunk(cpos, inflRange)) {
                        continue;
                    }

                    if (piece instanceof PoolStructurePiece) {
                        PoolStructurePiece poolPiece = (PoolStructurePiece) piece;
                        if (poolPiece.getPoolElement().getProjection() == StructurePool.Projection.RIGID) {
                            rigidProjectionPieces.add(poolPiece);
                        }

                        for (JigsawJunction junction : poolPiece.getJunctions()) {
                            int srcx = junction.getSourceX();
                            int srcz = junction.getSourceZ();
                            if (
                                srcx > originX - inflRange && srcx < originX + 15 + inflRange
                                    && srcz > originZ - inflRange && srcz < originZ + 15 + inflRange
                            ) {
                                intersectingJunctions.add(junction);
                            }
                        }
                    } else {
                        rigidProjectionPieces.add(piece);
                    }
                }
            });
        }

        // Two slices of the chunk's noise buffers
        // They are swapped as we iterate the noise buffer over the x-axis
        double[][][] noiseBuffers = new double[2][noiseSizeZ + 1][];

        for (int oz = 0; oz <= noiseSizeZ; oz++) {
            noiseBuffers[0][oz] = new double[noiseSizeY + 1];
            noiseBuffers[1][oz] = new double[noiseSizeY + 1];
            sampleNoiseColumn(noiseBuffers[0][oz], chunkX * noiseSizeX, chunkZ * noiseSizeZ + oz);
            modifyNoiseColumn(noiseBuffers[0][oz], chunkX * noiseSizeX, chunkZ * noiseSizeZ + oz, chunk, terrains);
        }

        ProtoChunk prototype = (ProtoChunk) chunk;
        Heightmap oceanFloorWg = prototype.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
        Heightmap worldSurfaceWg = prototype.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);

        BlockPos.Mutable mpos = new BlockPos.Mutable();

        ObjectListIterator<StructurePiece> rigidPieceItr = rigidProjectionPieces.iterator();
        ObjectListIterator<JigsawJunction> iscJunctionItr = intersectingJunctions.iterator();

        for (int sampleX = 0; sampleX < noiseSizeX; sampleX++) {
            // Sample next noise slice
            for (int sampleZ = 0; sampleZ <= noiseSizeZ; sampleZ++) {
                sampleNoiseColumn(
                    noiseBuffers[1][sampleZ],
                    chunkX * noiseSizeX + sampleX + 1,
                    chunkZ * noiseSizeZ + sampleZ
                );
                modifyNoiseColumn(
                    noiseBuffers[1][sampleZ],
                    chunkX * noiseSizeX + sampleX + 1,
                    chunkZ * noiseSizeZ + sampleZ,
                    chunk, terrains
                );
            }

            for (int sampleZ = 0; sampleZ < noiseSizeZ; sampleZ++) {
                ChunkSection section = prototype.getSection(15);
                section.lock();

                for (int sampleY = noiseSizeY - 1; sampleY >= 0; sampleY--) {
                    // Noise values at the corners of the sample we are in
                    // We interpolate those
                    double n000 = noiseBuffers[0][sampleZ + 0][sampleY + 0];
                    double n001 = noiseBuffers[0][sampleZ + 1][sampleY + 0];
                    double n100 = noiseBuffers[1][sampleZ + 0][sampleY + 0];
                    double n101 = noiseBuffers[1][sampleZ + 1][sampleY + 0];
                    double n010 = noiseBuffers[0][sampleZ + 0][sampleY + 1];
                    double n011 = noiseBuffers[0][sampleZ + 1][sampleY + 1];
                    double n110 = noiseBuffers[1][sampleZ + 0][sampleY + 1];
                    double n111 = noiseBuffers[1][sampleZ + 1][sampleY + 1];

                    for (int offsetY = vResolution - 1; offsetY >= 0; offsetY--) {
                        int globalY = sampleY * vResolution + offsetY;
                        int blockY = globalY & 15;
                        int sectionY = globalY >> 4;

                        // Lock next section if needed
                        if (section.getYOffset() >> 4 != sectionY) {
                            section.unlock();
                            section = prototype.getSection(sectionY);
                            section.lock();
                        }

                        // Interpolate noise sample values along y axis
                        double deltaY = (double) offsetY / vResolution;
                        double n00 = MathHelper.lerp(deltaY, n000, n010);
                        double n10 = MathHelper.lerp(deltaY, n100, n110);
                        double n01 = MathHelper.lerp(deltaY, n001, n011);
                        double n11 = MathHelper.lerp(deltaY, n101, n111);

                        for (int offsetX = 0; offsetX < hResolution; offsetX++) {
                            int globalX = originX + sampleX * hResolution + offsetX;
                            int blockX = globalX & 15;

                            // Interpolate noise sample values along x axis
                            double deltaX = (double) offsetX / hResolution;
                            double n0 = MathHelper.lerp(deltaX, n00, n10);
                            double n1 = MathHelper.lerp(deltaX, n01, n11);

                            for (int offsetZ = 0; offsetZ < hResolution; offsetZ++) {
                                int globalZ = originZ + sampleZ * hResolution + offsetZ;
                                int blockZ = globalZ & 15;

                                // Interpolate noise sample values along z axis
                                double deltaZ = (double) offsetZ / hResolution;
                                double n = MathHelper.lerp(deltaZ, n0, n1);

                                // Compute density
                                double density = MathHelper.clamp(n / 200, -1, 1);
                                density = density / 2 - density * density * density / STRUCTURE_INFLUENCE_DIAMETER;


                                // Flatten floor for structures
                                while (rigidPieceItr.hasNext()) {
                                    StructurePiece piece = rigidPieceItr.next();
                                    BlockBox bbox = piece.getBoundingBox();

                                    int groundLevelDelta = piece instanceof PoolStructurePiece
                                                           ? ((PoolStructurePiece) piece).getGroundLevelDelta()
                                                           : 0;

                                    int wx = Math.max(0, Math.max(bbox.minX - globalX, globalX - bbox.maxX));
                                    int wy = globalY - (bbox.minY + groundLevelDelta);
                                    int wz = Math.max(0, Math.max(bbox.minZ - globalZ, globalZ - bbox.maxZ));

                                    density += getNoiseWeight(wx, wy, wz) * 0.8;
                                }
                                rigidPieceItr.back(rigidProjectionPieces.size());

                                while (iscJunctionItr.hasNext()) {
                                    JigsawJunction junction = iscJunctionItr.next();
                                    int wx = globalX - junction.getSourceX();
                                    int wy = globalY - junction.getSourceGroundY();
                                    int wz = globalZ - junction.getSourceZ();

                                    density += getNoiseWeight(wx, wy, wz) * 0.4;
                                }
                                iscJunctionItr.back(intersectingJunctions.size());


                                // Place block
                                BlockState state = getBlockState(density, globalY);
                                for (Terrain terrain : terrains) {
                                    state = terrain.applyBlockState(globalX, globalY, globalZ, density, state, chunk);
                                }
                                if (state != AIR) {
                                    if (state.getLuminance() != 0) {
                                        mpos.set(globalX, globalY, globalZ);
                                        prototype.addLightSource(mpos);
                                    }

                                    section.setBlockState(blockX, blockY, blockZ, state, false);

                                    oceanFloorWg.trackUpdate(blockX, globalY, blockZ, state);
                                    worldSurfaceWg.trackUpdate(blockX, globalY, blockZ, state);
                                }
                            }
                        }
                    }
                }

                section.unlock();
            }

            // Swap slices, so that slice 0 becomes slice 1, and slice 1 can be filled with new noise columns
            double[][] tmp = noiseBuffers[0];
            noiseBuffers[0] = noiseBuffers[1];
            noiseBuffers[1] = tmp;
        }

    }

    private static double getNoiseWeight(int x, int y, int z) {
        int dx = x + STRUCTURE_INFLUENCE_RANGE;
        int dy = y + STRUCTURE_INFLUENCE_RANGE;
        int dz = z + STRUCTURE_INFLUENCE_RANGE;

        int diameter = STRUCTURE_INFLUENCE_DIAMETER;
        if (dx >= 0 && dx < diameter && dy >= 0 && dy < diameter && dz >= 0 && dz < diameter) {
            return NOISE_WEIGHT_TABLE[dz * diameter * diameter + dx * diameter + dy];
        }
        return 0;
    }

    private static double calculateNoiseWeight(int x, int y, int z) {
        double oy = y + 0.5;
        double hdSq = x * x + z * z;
        double vdSq = oy * oy;

        double hw = Math.exp(-(vdSq / 16 + hdSq / 16));
        double vw = -oy * MathHelper.fastInverseSqrt(vdSq / 2 + hdSq / 2) / 2;
        return vw * hw;
    }

    @Override
    public int getWorldHeight() {
        return worldHeight;
    }

    @Override
    public int getSeaLevel() {
        return settings.get().getSeaLevel();
    }

    @Override
    public List<SpawnSettings.SpawnEntry> getEntitySpawnList(Biome biome, StructureAccessor accessor, SpawnGroup group, BlockPos pos) {
        if (accessor.getStructureAt(pos, true, StructureFeature.SWAMP_HUT).hasChildren()) {
            if (group == SpawnGroup.MONSTER) {
                return StructureFeature.SWAMP_HUT.getMonsterSpawns();
            }

            if (group == SpawnGroup.CREATURE) {
                return StructureFeature.SWAMP_HUT.getCreatureSpawns();
            }
        }

        if (group == SpawnGroup.MONSTER) {
            if (accessor.getStructureAt(pos, false, StructureFeature.PILLAGER_OUTPOST).hasChildren()) {
                return StructureFeature.PILLAGER_OUTPOST.getMonsterSpawns();
            }

            if (accessor.getStructureAt(pos, false, StructureFeature.MONUMENT).hasChildren()) {
                return StructureFeature.MONUMENT.getMonsterSpawns();
            }

            if (accessor.getStructureAt(pos, true, StructureFeature.FORTRESS).hasChildren()) {
                return StructureFeature.FORTRESS.getMonsterSpawns();
            }
        }

        return super.getEntitySpawnList(biome, accessor, group, pos);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void populateEntities(ChunkRegion region) {
        ChunkGenSettingsAccessor accessor = ChunkGenSettingsAccessor.class.cast(settings.get());
        assert accessor != null;
        if (!accessor.isMobGenerationDisabled()) {
            int cx = region.getCenterChunkX();
            int cz = region.getCenterChunkZ();

            Biome biome = region.getBiome(new ChunkPos(cx, cz).getStartPos());
            ChunkRandom rng = new ChunkRandom();
            rng.setPopulationSeed(region.getSeed(), cx << 4, cz << 4);
            SpawnHelper.populateEntities(region, biome, cx, cz, rng);
        }
    }

    private List<Terrain> getTerrains(Chunk chunk) {
        ChunkPos pos = chunk.getPos();
        if (terrains.containsKey(pos)) {
            return terrains.getAndMoveToFirst(pos);
        } else {
            List<Terrain> terrainList = new ArrayList<>();
            genTerrains(chunk, terrainList);
            terrains.putAndMoveToFirst(pos, terrainList);

            while (terrains.size() > 256) {
                terrains.removeLast();
            }
            return terrainList;
        }
    }

    private void genTerrains(Chunk chunk, List<Terrain> terrains) {
        int cx = chunk.getPos().x;
        int cz = chunk.getPos().z;
        for (int ox = -8; ox <= 8; ox++) {
            for (int oz = -8; oz <= 8; oz++) {
                random.setCarverSeed(seed, cx + ox, cz + oz);
                addTerrains(cx, cz, cx + ox, cz + oz, terrains);
            }
        }
    }

    private void addTerrains(int cx, int cz, int ox, int oz, List<Terrain> terrains) {
        if (random.nextInt(300) == 0) {
            int x = ox * 16 + random.nextInt(15);
            int z = oz * 16 + random.nextInt(15);
            int y = 20 + random.nextInt(15);
            int hr = 64 + random.nextInt(32);
            int vr = 10 + random.nextInt(30);
            terrains.add(new SaltCaveTerrain(x, y, z, hr, vr, random.nextLong()));
        }
    }

    @Override
    public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {
        super.generateFeatures(region, accessor);

        Chunk chunk = region.getChunk(region.getCenterChunkX(), region.getCenterChunkZ());
        List<Terrain> terrains = getTerrains(chunk);

        BlockPos root = new BlockPos(region.getCenterChunkX() * 16, 0, region.getCenterChunkZ() * 16);
        random.setPopulationSeed(seed, region.getCenterChunkX() * 16, region.getCenterChunkZ() * 16);

        for (Terrain terrain : terrains) {
            terrain.getFeatures(chunk).forEach(feature -> feature.generate(region, this, random, root));
        }
    }
}
