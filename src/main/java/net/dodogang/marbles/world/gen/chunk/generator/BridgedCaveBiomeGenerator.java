package net.dodogang.marbles.world.gen.chunk.generator;

import net.dodogang.marbles.mixin.MutableBiomeArray;
import net.dodogang.marbles.world.gen.chunk.decorator.BridgedCaveBiomeDecorator;
import net.dodogang.marbles.world.gen.chunk.generator.config.BridgedCaveBiomeGeneratorConfig;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.shadew.ptg.noise.Noise2D;
import net.shadew.ptg.noise.Noise3D;
import net.shadew.ptg.noise.opensimplex.OpenSimplex2D;
import net.shadew.ptg.noise.opensimplex.OpenSimplex3D;
import net.shadew.ptg.noise.perlin.FractalPerlin3D;
import net.shadew.ptg.noise.perlin.Perlin3D;
import net.shadew.util.misc.MathUtil;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class BridgedCaveBiomeGenerator extends MarblesChunkGenerator {
    /*
     * Sampling factors: affect performance and quality of the caves
     */

    private static final int VERTICAL_SPACE   = 96;
    private static final int HORIZONTAL_SPACE = 16;

    private static final int MIN_Y = 0;
    private static final int LIMIT_Y = 4;
    private static final int MAX_Y = MIN_Y + VERTICAL_SPACE;

    private static final int CELL_SIZE = 4;
    private static final double CELL_SIZE_D = CELL_SIZE;

    private static final int RESOLUTION_H = HORIZONTAL_SPACE / CELL_SIZE;
    private static final int RESOLUTION_V = VERTICAL_SPACE / CELL_SIZE;

    private final BridgedCaveBiomeGeneratorConfig config;

    private final Noise2D fieldNoise;
    private final Noise2D levelNoise;
    private final Noise3D caveNoise;
    private final Noise3D offsetNoiseX;
    private final Noise3D offsetNoiseZ;
    private final Noise3D bridgeNoise;

    public BridgedCaveBiomeGenerator(BridgedCaveBiomeGeneratorConfig config, long seed, ChunkGenerator generator) {
        super(seed, generator);
        this.config = config;

        Random rand = new Random(seed);

        fieldNoise = new OpenSimplex2D(rand.nextInt(), config.getFieldSize());
        levelNoise = new OpenSimplex2D(rand.nextInt(), config.getLevelSize()).lerp(10, 40);

        caveNoise = new FractalPerlin3D(rand.nextInt(), config.getNoiseSize(), 4).multiply(config.getNoiseScale());
        double offsetSize = config.getOffsetSize();
        double offsetScale = config.getOffsetScale();
        offsetNoiseX = new Perlin3D(rand.nextInt(), offsetSize).multiply(offsetScale);
        offsetNoiseZ = new Perlin3D(rand.nextInt(), offsetSize).multiply(offsetScale);

        double bridgeSizeH = config.getBridgeSizeH();
        double bridgeSizeV = config.getBridgeSizeV();
        double bridgeScale = config.getBridgeScale();
        double bridgeAdd = config.getBridgeAdd();
        Noise3D a = new OpenSimplex3D(rand.nextInt(), bridgeSizeH, bridgeSizeV, bridgeSizeH)
                        .ridge().inverse().multiply(bridgeScale).add(bridgeAdd);
        Noise3D b = new OpenSimplex3D(rand.nextInt(), bridgeSizeH, bridgeSizeV, bridgeSizeH)
                        .ridge().inverse().multiply(bridgeScale).add(bridgeAdd);
        bridgeNoise = (x, y, z) -> Math.max(a.generate(x, y, z), b.generate(x, y, z));
    }

    @Override
    public void carve(long seed, BiomeAccess biomes, Chunk chunk, GenerationStep.Carver carver) {
        double[][][] buffers = new double[2][RESOLUTION_H + 1][RESOLUTION_V + 1];

        int cx = chunk.getPos().x;
        int cz = chunk.getPos().z;

        int nx = cx * RESOLUTION_H;
        int nz = cz * RESOLUTION_H;

        Heightmap hm = chunk.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);

        BitSet material = new BitSet(16 * 16 * 256);
        BlockPos.Mutable mpos = new BlockPos.Mutable();

        List<BridgedCaveBiomeDecorator> caves = getCaves(cx, cz);

        random.setCarverSeed(seed, cx, cz);
        random.consume(41);

        for (int oz = 0; oz <= RESOLUTION_H; oz++) {
            double[] col = buffers[0][oz];
            int gz = nz + oz;
            generateCaveColumn(col, nx, gz, hm.get(0, Math.min(oz * CELL_SIZE, 15)), caves);
        }

        for (int ox = 0; ox < RESOLUTION_H; ox++) {
            for (int oz = 0; oz <= RESOLUTION_H; oz++) {
                double[] col = buffers[1][oz];
                int gx = nx + ox + 1;
                int gz = nz + oz;
                generateCaveColumn(col, gx, gz, hm.get(Math.min((ox + 1) * CELL_SIZE, 15), Math.min(oz * CELL_SIZE, 15)), caves);
            }

            for (int oz = 0; oz < RESOLUTION_H; oz++) {
                double[] c00 = buffers[0][oz];
                double[] c01 = buffers[0][oz + 1];
                double[] c10 = buffers[1][oz];
                double[] c11 = buffers[1][oz + 1];

                for (int oy = RESOLUTION_V - 1; oy >= 0; oy--) {
                    double n000 = c00[oy];
                    double n010 = c00[oy + 1];
                    double n001 = c01[oy];
                    double n011 = c01[oy + 1];
                    double n100 = c10[oy];
                    double n110 = c10[oy + 1];
                    double n101 = c11[oy];
                    double n111 = c11[oy + 1];

                    for (int ly = CELL_SIZE - 1; ly >= 0; ly--) {
                        int sy = oy * CELL_SIZE + ly;

                        double n00 = MathHelper.lerp(ly / CELL_SIZE_D, n000, n010);
                        double n01 = MathHelper.lerp(ly / CELL_SIZE_D, n001, n011);
                        double n10 = MathHelper.lerp(ly / CELL_SIZE_D, n100, n110);
                        double n11 = MathHelper.lerp(ly / CELL_SIZE_D, n101, n111);

                        for (int lz = 0; lz < CELL_SIZE; lz++) {
                            int sz = oz * CELL_SIZE + lz;

                            double n0 = MathHelper.lerp(lz / CELL_SIZE_D, n00, n01);
                            double n1 = MathHelper.lerp(lz / CELL_SIZE_D, n10, n11);

                            for (int lx = 0; lx < CELL_SIZE; lx++) {
                                int sx = ox * CELL_SIZE + lx;

                                double n = MathHelper.lerp(lx / CELL_SIZE_D, n0, n1);

                                if (n < 0.8) {
                                    int rad = random.nextInt(3) + 2;

                                    if (n < 0) {
                                        mpos.set(sx, sy, sz);
                                        chunk.setBlockState(mpos, this.config.getFillerBlockState(mpos, random), false);
                                        int si = (sx * 16 + sz) * 256 + sy;
                                        material.set(si);
                                        this.setBiome(mpos, chunk);
                                    }

                                    for (int ry = -rad; ry <= rad; ry++) {
                                        int vy = sy + ry;
                                        if (vy >= 0 && vy < 256) {
                                            int si = (sx * 16 + sz) * 256 + vy;
                                            mpos.set(sx, vy, sz);
                                            if (!material.get(si) && isReplaceableBlock(chunk.getBlockState(mpos), chunk, mpos)) {
                                                chunk.setBlockState(mpos, this.config.getBodyBlockState(mpos, random), false);
                                                material.set(si);
                                                this.setBiome(mpos, chunk);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            double[][] tmp = buffers[0];
            buffers[0] = buffers[1];
            buffers[1] = tmp;
        }
    }

    private void setBiome(BlockPos pos, Chunk chunk) {
        BiomeArray biomeArray = chunk.getBiomeArray();
        if (biomeArray instanceof MutableBiomeArray) {
            ((MutableBiomeArray) biomeArray).setBiome(pos.getX(), pos.getY(), pos.getZ(), this.config.getBiome());
            chunk.setShouldSave(true);
        }
    }

    private boolean isReplaceableBlock(BlockState state, BlockView world, BlockPos pos) {
        return state.getHardness(world, pos) >= 0;
    }

    protected void generateCaveColumn(double[] col, int x, int z, int oceanFloorHeight, List<BridgedCaveBiomeDecorator> caves) {
        int lo = LIMIT_Y / CELL_SIZE;
        int hi = Math.min(MAX_Y, oceanFloorHeight - 4) / CELL_SIZE;

        for (int y = 0; y <= RESOLUTION_V; y++) {
            double noise = -getNoise(x, y, z, caves);

            int fy = y - lo;
            if (fy < 2) {
                int iy = 2 - fy;
                noise += iy * 4;
            }

            int cy = hi - y;
            if (cy < 2) {
                int iy = 2 - cy;
                noise += iy * 4;
            }

            noise = Math.min(noise, 1);

            col[y] = noise;
        }
    }

    private double getNoise(double x, double y, double z, List<BridgedCaveBiomeDecorator> caves) {
        double n = Double.NEGATIVE_INFINITY;
        for (BridgedCaveBiomeDecorator cave : caves) {
            n = Math.max(n, cave.getValue(x * CELL_SIZE_D, y * CELL_SIZE_D, z * CELL_SIZE_D));
        }
        return n;
    }

    private List<BridgedCaveBiomeDecorator> getCaves(int cx, int cz) {
        List<BridgedCaveBiomeDecorator> caves = new ArrayList<>();
        for (int ix = -5; ix <= 5; ix++) {
            for (int iz = -5; iz <= 5; iz++) {
                int bx = cx + ix;
                int bz = cz + iz;

                random.setCarverSeed(seed, bx, bz);
                random.consume(27);

                double field = fieldNoise.generate(bx * 16, bz * 16);
                double r = random.nextDouble() * this.config.getCaveRarity();

                if (r < field) {
                    BridgedCaveBiomeDecorator cave = new BridgedCaveBiomeDecorator(this.config.getFeatures());
                    cave.noise = caveNoise;
                    cave.offsetNoiseX = offsetNoiseX;
                    cave.offsetNoiseZ = offsetNoiseZ;
                    cave.smallNoise = bridgeNoise;

                    cave.layers = random.nextInt(3) + 2;
                    double caveSize = this.config.getCaveSize();
                    cave.layerRadius = MathUtil.lerp(this.config.getMinRadius(), this.config.getMaxRadius(), random.nextDouble()) * caveSize;
                    cave.layerSize = MathUtil.lerp(this.config.getMinLayerSize(), this.config.getMaxLayerSize(), random.nextDouble()) * caveSize;

                    double level = levelNoise.generate(bx * 16, bz * 16) / CELL_SIZE_D;
                    cave.sourceX = random.nextInt(16) + bx * 16;
                    cave.sourceZ = random.nextInt(16) + bz * 16;
                    cave.sourceY = (int) level + random.nextInt(12);

                    caves.add(cave);
                }
            }
        }

        return caves;
    }

    @Override
    public void decorate(ChunkRegion region, StructureAccessor accessor) {
        int cx = region.getCenterChunkX();
        int cz = region.getCenterChunkZ();
        int sx = cx * 16;
        int sz = cz * 16;

        BlockPos blockPos = new BlockPos(sx, 0, sz);
        ChunkRandom rng = new ChunkRandom();
        rng.setPopulationSeed(region.getSeed(), sx, sz);

        List<BridgedCaveBiomeDecorator> caves = getCaves(cx, cz);
        caves.forEach(cave -> cave.decorate(region, blockPos, generator, rng));
    }
}
