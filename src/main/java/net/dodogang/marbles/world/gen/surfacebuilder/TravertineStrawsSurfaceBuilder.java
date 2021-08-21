package net.dodogang.marbles.world.gen.surfacebuilder;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.tag.MarblesBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class TravertineStrawsSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    private static final BlockState TRAVERTINE = MarblesBlocks.PEACH_TRAVERTINE.RAW.getDefaultState();
    private static final BlockState LEMON_TRAVERTINE = MarblesBlocks.LEMON_TRAVERTINE.RAW.getDefaultState();
    private static final BlockState PEACH_TRAVERTINE = MarblesBlocks.TRAVERTINE.RAW.getDefaultState();
    private static final BlockState TANGERINE_TRAVERTINE = MarblesBlocks.TANGERINE_TRAVERTINE.RAW.getDefaultState();

    protected BlockState[] layerBlocks;
    protected long seed;
    protected OctaveSimplexNoiseSampler heightCutoffNoise;
    protected OctaveSimplexNoiseSampler heightNoise;
    protected OctaveSimplexNoiseSampler layerNoise;

    public TravertineStrawsSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int minY, long seed, TernarySurfaceConfig config) {
        int relX = x & 15;
        int relZ = z & 15;
        BlockState blockState3 = TANGERINE_TRAVERTINE;
        BlockState baseState = TRAVERTINE;
        int q = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        boolean bl = Math.cos(noise / 3.0D * 3.141592653589793D) > 0.0D;
        int r = -1;
        boolean bl2 = false;
        int s = 0;
        BlockPos.Mutable mpos = new BlockPos.Mutable(relX, 0, relZ);

        for (int y = height; y >= minY; --y) {
            if (s < 15) {
                mpos.setY(y);
                BlockState istate = chunk.getBlockState(mpos);
                if (istate.isAir()) {
                    r = -1;
                } else if (istate.isOf(defaultBlock.getBlock())) {
                    if (r == -1) {
                        bl2 = false;
                        if (q <= 0) {
                            blockState3 = Blocks.AIR.getDefaultState();
                            baseState = defaultBlock;
                        } else if (y >= seaLevel - 4 && y <= seaLevel + 1) {
                            blockState3 = TANGERINE_TRAVERTINE;
                            baseState = TRAVERTINE;
                        }

                        if (y < seaLevel && (blockState3 == null || blockState3.isAir())) {
                            blockState3 = defaultFluid;
                        }

                        r = q + Math.max(0, y - seaLevel);
                        if (y >= seaLevel - 1) {
                            if (y > seaLevel + 3 + q) {
                                BlockState layerState;
                                if (y >= 64 && y <= 127) {
                                    if (bl) {
                                        layerState = TravertineStrawsSurfaceBuilder.TRAVERTINE;
                                    } else {
                                        layerState = this.calculateLayerBlockState(x, y, z);
                                    }
                                } else {
                                    layerState = PEACH_TRAVERTINE;
                                }

                                chunk.setBlockState(mpos, layerState, false);
                            } else {
                                chunk.setBlockState(mpos, TRAVERTINE, false);
                                bl2 = true;
                            }
                        } else {
                            chunk.setBlockState(mpos, baseState, false);
                            if (MarblesBlockTags.TRAVERTINE_BLOCKS.contains(baseState.getBlock())) {
                                chunk.setBlockState(mpos, PEACH_TRAVERTINE, false);
                            }
                        }
                    } else if (r > 0) {
                        --r;
                        if (bl2) {
                            chunk.setBlockState(mpos, PEACH_TRAVERTINE, false);
                        } else {
                            chunk.setBlockState(mpos, this.calculateLayerBlockState(x, y, z), false);
                        }
                    }

                    ++s;
                }
            }
        }


        for (int y = chunk.getBottomY(); y < 128; y++) {
            mpos.setY(y);
            if (chunk.getBlockState(mpos).equals(defaultBlock)) {
                chunk.setBlockState(mpos, LEMON_TRAVERTINE, false);
            }
        }
    }

    @Override
    public void initSeed(long seed) {
        if (this.seed != seed || this.layerBlocks == null) {
            this.initLayerBlocks(seed);
        }

        if (this.seed != seed || this.heightCutoffNoise == null || this.heightNoise == null) {
            ChunkRandom random = new ChunkRandom(seed);
            this.heightCutoffNoise = new OctaveSimplexNoiseSampler(random, IntStream.rangeClosed(-3, 0));
            this.heightNoise = new OctaveSimplexNoiseSampler(random, ImmutableList.of(0));
        }

        this.seed = seed;
    }

    protected void initLayerBlocks(long seed) {
        this.layerBlocks = new BlockState[64];
        Arrays.fill(this.layerBlocks, TRAVERTINE);
        ChunkRandom random = new ChunkRandom(seed);
        this.layerNoise = new OctaveSimplexNoiseSampler(random, ImmutableList.of(0));

        int j;
        for(j = 0; j < 64; ++j) {
            this.layerBlocks[j] = PEACH_TRAVERTINE;
        }

        j = random.nextInt(4) + 2;

        int o;
        int t;
        int y;
        int z;
        for(o = 0; o < j; ++o) {
            t = random.nextInt(3) + 1;
            y = random.nextInt(64);

            for(z = 0; y + z < 64 && z < t; ++z) {
                this.layerBlocks[y + z] = LEMON_TRAVERTINE;
            }
        }

        o = random.nextInt(4) + 2;

        int w;
        for(t = 0; t < o; ++t) {
            y = random.nextInt(3) + 2;
            z = random.nextInt(64);

            for(w = 0; z + w < 64 && w < y; ++w) {
                this.layerBlocks[z + w] = TANGERINE_TRAVERTINE;
            }
        }

        t = random.nextInt(4) + 2;

        for(y = 0; y < t; ++y) {
            z = random.nextInt(3) + 1;
            w = random.nextInt(64);

            for(int x = 0; w + x < 64 && x < z; ++x) {
                this.layerBlocks[w + x] = TANGERINE_TRAVERTINE;
            }
        }

        y = random.nextInt(3) + 3;
        z = 0;

        for(w = 0; w < y; ++w) {
            z += random.nextInt(16) + 4;

            for(int ac = 0; z + ac < 64 && ac < 1; ++ac) {
                this.layerBlocks[z + ac] = TANGERINE_TRAVERTINE;
                if (z + ac > 1 && random.nextBoolean()) {
                    this.layerBlocks[z + ac - 1] = TANGERINE_TRAVERTINE;
                }

                if (z + ac < 63 && random.nextBoolean()) {
                    this.layerBlocks[z + ac + 1] = TANGERINE_TRAVERTINE;
                }
            }
        }

    }

    protected BlockState calculateLayerBlockState(int x, int y, int z) {
        int i = (int)Math.round(this.layerNoise.sample((double)x / 512.0D, (double)z / 512.0D, false) * 2.0D);
        return this.layerBlocks[(y + i + 64) % 64];
    }
}
