package net.dodogang.marbles.world.gen.level.terrain;

import com.google.common.collect.Lists;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.init.MarblesConfiguredFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.shadew.ptg.noise.Noise3D;
import net.shadew.ptg.noise.perlin.FractalPerlin3D;
import net.shadew.ptg.noise.perlin.Perlin3D;

import java.util.List;
import java.util.Random;

public class SaltCaveTerrain extends Terrain {
    private final int radius;
    private final int vradius;
    private final Noise3D noise;
    private final Noise3D caveNoise;

    public SaltCaveTerrain(int x, int y, int z, int radius, int vradius, long seed) {
        super(x, y, z);
        this.radius = radius;
        this.vradius = vradius;
        Random rand = new Random(seed);
        noise = new Perlin3D(rand.nextInt(), 2.5121);
        caveNoise = new FractalPerlin3D(rand.nextInt(), 32.3127, 3.3125, 32.3127, 3);
    }

    private boolean isInside(int x, int y, int z, int extraRadius) {
        int mx = getX();
        int my = getY();
        int mz = getZ();
        double dx = (x - mx) / (double) (radius + extraRadius);
        double dy = (y - my) / (double) (vradius + extraRadius);
        double dz = (z - mz) / (double) (radius + extraRadius);

        double dist = dx * dx + dy * dy + dz * dz;
        return dist < 1 && y > 0 && y < 48;
    }

    @Override
    public double generate(int x, int y, int z, double noise, Chunk chunk) {
        if (y > 0 && y < 48) {
            int mx = getX();
            int my = getY();
            int mz = getZ();
            double dx = (x - mx) / (double) radius;
            double dy = (y - my) / (double) vradius;
            double dz = (z - mz) / (double) radius;

            double dist = dx * dx + dy * dy + dz * dz;
            if (dist <= 1) {
                double cn = caveNoise.generate(x, y, z) + 0.2;
                return MathHelper.lerp(dist * dist * dist * dist, cn * 6, Math.min(noise, 1));
            }
        }
        return noise;
    }

    @Override
    public BlockState applyBlockState(int x, int y, int z, double noise, BlockState current, Chunk chunk) {
        if (isInside(x, y, z, 3)) {
            if (current.getBlock() == Blocks.WATER) {
                return Blocks.AIR.getDefaultState();
            }
        }
        if (isInside(x, y, z, 6)) {
            if (!current.isAir() && current.getBlock() != Blocks.WATER) {
                return MarblesBlocks.PINK_SALT.getDefaultState();
            }
        }
        return current;
    }

    @Override
    public List<ConfiguredFeature<?, ?>> getFeatures(Chunk chunk) {
        return Lists.newArrayList(
            MarblesConfiguredFeatures.SALT_STUMP,
            MarblesConfiguredFeatures.SALT_SPIRE
        );
    }
}
