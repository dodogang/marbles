package net.dodogang.marbles.world.gen.level;

import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.init.MarblesConfiguredFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.BitSet;
import java.util.Random;

import net.shadew.ptg.noise.Noise2D;
import net.shadew.ptg.noise.Noise3D;
import net.shadew.ptg.noise.opensimplex.FractalOpenSimplex3D;
import net.shadew.ptg.noise.opensimplex.OpenSimplex2D;
import net.shadew.ptg.noise.util.NoiseMath;

public class SaltCaveGenerator {
    private final Noise2D saltCaveField;
    private final Noise2D saltCaveLevel;
    private final Noise3D saltCaveNoise;
    private final long seed;
    private final ChunkRandom random;
    private final ChunkGenerator generator;

    public SaltCaveGenerator(long seed, ChunkGenerator generator) {
        this.seed = seed;
        this.random = new ChunkRandom(seed);
        this.generator = generator;

        Random rand = new Random(seed);

        saltCaveField = new OpenSimplex2D(rand.nextInt(), 114);
        saltCaveLevel = new OpenSimplex2D(rand.nextInt(), 52.23).lerp(10, 40);

        Noise3D saltCaveNoiseA = new FractalOpenSimplex3D(rand.nextInt(), 24.41274, 12.12921, 24.41274, 12);
        Noise3D saltCaveNoiseB = new FractalOpenSimplex3D(rand.nextInt(), 24.41274, 12.12921, 24.41274, 12);
        saltCaveNoise = new FractalOpenSimplex3D(rand.nextInt(), 8.12738, 5.55126, 8.12738, 6)
                            .lerp(-3, 3)
                            .lerp(saltCaveNoiseA, saltCaveNoiseB);
    }


    private static final int SALT_CAVE_CHUNK_HEIGHT = 64;
    private static final int SALT_CAVE_CHUNK_WIDTH = 16;
    private static final int SALT_CAVE_CHUNK_START_Y = 0;
    private static final int SALT_CAVE_CHUNK_LIMIT_Y = 8;
    private static final int SALT_CAVE_CHUNK_END_Y = SALT_CAVE_CHUNK_START_Y + SALT_CAVE_CHUNK_HEIGHT;
    private static final int SALT_CAVE_NOISE_RES = 2;
    private static final double SALT_CAVE_NOISE_RES_D = SALT_CAVE_NOISE_RES;
    private static final int SALT_CAVE_NOISE_HEIGHT = SALT_CAVE_CHUNK_HEIGHT / SALT_CAVE_NOISE_RES;
    private static final int SALT_CAVE_NOISE_WIDTH = SALT_CAVE_CHUNK_WIDTH / SALT_CAVE_NOISE_RES;

    public void generateSaltCaves(long seed, BiomeAccess biomes, Chunk chunk) {
        double[][][] buffers = new double[2][SALT_CAVE_NOISE_WIDTH + 1][SALT_CAVE_NOISE_HEIGHT + 1];

        int cx = chunk.getPos().x;
        int cz = chunk.getPos().z;
        random.setCarverSeed(seed, cx, cz);
        random.consume(41);

        int nx = cx * SALT_CAVE_NOISE_WIDTH;
        int nz = cz * SALT_CAVE_NOISE_WIDTH;

        Heightmap hm = chunk.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);

        BitSet saltMaterial = new BitSet(16 * 16 * 256);
        BlockPos.Mutable mpos = new BlockPos.Mutable();

        for (int oz = 0; oz <= SALT_CAVE_NOISE_WIDTH; oz++) {
            double[] col = buffers[0][oz];
            int gz = nz + oz;
            generateSaltCaveColumn(col, nx, gz, hm.get(0, Math.min(oz * SALT_CAVE_NOISE_RES, 15)));
        }

        for (int ox = 0; ox < SALT_CAVE_NOISE_WIDTH; ox++) {
            for (int oz = 0; oz <= SALT_CAVE_NOISE_WIDTH; oz++) {
                double[] col = buffers[1][oz];
                int gx = nx + ox + 1;
                int gz = nz + oz;
                generateSaltCaveColumn(col, gx, gz, hm.get(Math.min((ox + 1) * SALT_CAVE_NOISE_RES, 15), Math.min(oz * SALT_CAVE_NOISE_RES, 15)));
            }

            for (int oz = 0; oz < SALT_CAVE_NOISE_WIDTH; oz++) {
                double[] c00 = buffers[0][oz];
                double[] c01 = buffers[0][oz + 1];
                double[] c10 = buffers[1][oz];
                double[] c11 = buffers[1][oz + 1];

                for (int oy = SALT_CAVE_NOISE_HEIGHT - 1; oy >= 0; oy--) {
                    double n000 = c00[oy];
                    double n010 = c00[oy + 1];
                    double n001 = c01[oy];
                    double n011 = c01[oy + 1];
                    double n100 = c10[oy];
                    double n110 = c10[oy + 1];
                    double n101 = c11[oy];
                    double n111 = c11[oy + 1];

                    for (int ly = SALT_CAVE_NOISE_RES - 1; ly >= 0; ly--) {
                        int sy = oy * SALT_CAVE_NOISE_RES + ly;

                        double n00 = MathHelper.lerp(ly / SALT_CAVE_NOISE_RES_D, n000, n010);
                        double n01 = MathHelper.lerp(ly / SALT_CAVE_NOISE_RES_D, n001, n011);
                        double n10 = MathHelper.lerp(ly / SALT_CAVE_NOISE_RES_D, n100, n110);
                        double n11 = MathHelper.lerp(ly / SALT_CAVE_NOISE_RES_D, n101, n111);

                        for (int lz = 0; lz < SALT_CAVE_NOISE_RES; lz++) {
                            int sz = oz * SALT_CAVE_NOISE_RES + lz;

                            double n0 = MathHelper.lerp(lz / SALT_CAVE_NOISE_RES_D, n00, n01);
                            double n1 = MathHelper.lerp(lz / SALT_CAVE_NOISE_RES_D, n10, n11);

                            for (int lx = 0; lx < SALT_CAVE_NOISE_RES; lx++) {
                                int sx = ox * SALT_CAVE_NOISE_RES + lx;

                                double n = MathHelper.lerp(lx / SALT_CAVE_NOISE_RES_D, n0, n1);

                                if (n < 0.3) {
                                    int rad = random.nextInt(3) + 2;


                                    if (n < 0) {
                                        mpos.set(sx, sy, sz);
                                        chunk.setBlockState(mpos, Blocks.AIR.getDefaultState(), false);
                                        int si = (sx * 16 + sz) * 256 + sy;
                                        saltMaterial.set(si);
                                    }

                                    for (int ry = -rad; ry <= rad; ry++) {
                                        int vy = sy + ry;
                                        if (vy >= 0 && vy < 256) {
                                            int si = (sx * 16 + sz) * 256 + vy;
                                            mpos.set(sx, vy, sz);
                                            if (!saltMaterial.get(si) && chunk.getBlockState(mpos).isSolidBlock(chunk, mpos)) {
                                                chunk.setBlockState(mpos, MarblesBlocks.PINK_SALT.getDefaultState(), false);
                                                saltMaterial.set(si);
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

    protected void generateSaltCaveColumn(double[] col, int x, int z, int oceanFloorHeight) {
        double field = saltCaveField.generate(x, z) - 0.2;

        int range = (int) Math.max(0, field * 23);
        int level = range > 0 ? (int) saltCaveLevel.generate(x, z) : 0;

        int lo = Math.max(level - range, SALT_CAVE_CHUNK_LIMIT_Y) / SALT_CAVE_NOISE_RES;
        int hi = Math.min(Math.min(level + range, SALT_CAVE_CHUNK_END_Y), oceanFloorHeight - 4) / SALT_CAVE_NOISE_RES;

        for (int y = 0; y <= SALT_CAVE_NOISE_HEIGHT; y++) {
            double noise;
            if (range <= 0 || y < lo || y > hi) {
                noise = 1;
            } else {
                noise = saltCaveNoise.generate(x, y, z);

                int fy = y - lo;
                if (fy < 2) {
                    int iy = 2 - fy;
                    noise += iy * 0.8;
                }

                int cy = hi - y;
                if (cy < 2) {
                    int iy = 2 - cy;
                    noise += iy * 0.8;
                }
            }

            noise = Math.min(noise, 1);

            if (field < 0.01) {
                double i = NoiseMath.unlerp(0, 0.01, field);
                noise = NoiseMath.lerp(1, noise, i);
            }

            col[y] = noise;
        }
    }

    public void decorate(ChunkRegion region, StructureAccessor accessor) {
        int cx = region.getCenterChunkX();
        int cz = region.getCenterChunkZ();
        int sx = cx * 16;
        int sz = cz * 16;
        int nx = cx * SALT_CAVE_NOISE_WIDTH + SALT_CAVE_NOISE_WIDTH / 2;
        int nz = cz * SALT_CAVE_NOISE_WIDTH + SALT_CAVE_NOISE_WIDTH / 2;


        BlockPos blockPos = new BlockPos(sx, 0, sz);
        ChunkRandom rng = new ChunkRandom();
        rng.setPopulationSeed(region.getSeed(), sx, sz);

        double scField = saltCaveField.generate(nx, nz) - 0.2;
        if (scField > -0.1) {
            MarblesConfiguredFeatures.SALT_SPIRE.generate(region, generator, rng, blockPos);
            MarblesConfiguredFeatures.SALT_STUMP.generate(region, generator, rng, blockPos);
        }
    }
}
