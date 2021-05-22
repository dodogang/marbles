package net.dodogang.marbles.world.gen.chunk.decorator;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.shadew.ptg.noise.Noise3D;
import net.shadew.util.misc.MathUtil;

import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class BridgedCaveBiomeDecorator {
    public Noise3D noise = Noise3D.constant(0);
    public Noise3D offsetNoiseX = Noise3D.constant(0);
    public Noise3D offsetNoiseZ = Noise3D.constant(0);
    public Noise3D smallNoise = Noise3D.constant(0);
    public int sourceX;
    public int sourceY;
    public int sourceZ;
    public int waterLevel;
    public double layerSize = 6;
    public double layerRadius = 16;

    public int layers = 3;

    public final List<ConfiguredFeature<?, ?>> features;

    public BridgedCaveBiomeDecorator(List<ConfiguredFeature<?, ?>> features) {
        this.features = features;
    }

    public static class Layer {
        public float offX;
        public float offZ;
        public float radius;
    }

    public void decorate(StructureWorldAccess world, BlockPos pos, ChunkGenerator gen, Random rng) {
        int bx = pos.getX() + 8 - sourceX;
        int bz = pos.getZ() + 8 - sourceZ;

        double dist = bx * bx + bz * bz;
        double maxD = layerRadius * 1.8;
        if (dist > maxD * maxD) {
            return;
        }

        features.forEach(configuredFeature -> configuredFeature.generate(world, gen, rng, pos));
    }

    public double getValue(double x, double y, double z) {
        double lx = x - sourceX;
        double ly = y - (sourceY - layers * layerSize / 2);
        double lz = z - sourceZ;

        double dsq = lx * lx + lz * lz;
        double maxD = layerRadius * 1.8;
        if (dsq > maxD * maxD) {
            return -8;
        }

        double lly = ly / layerSize;

        double layerWeight = -(1.5 * Math.cos(lly * Math.PI * 2) - 3);
        if (lly < 0.5) {
            double v = 1 - lly * 2;
            layerWeight = MathUtil.lerp(layerWeight, -4, v * v * v * v);
        }
        if (lly - layers > -0.5) {
            double v = 1 - -(lly - layers) * 2;
            layerWeight = MathUtil.lerp(layerWeight, -4, v * v * v * v);
        }

        double offx = offsetNoiseX.generate(sourceX, y, sourceZ);
        double offz = offsetNoiseZ.generate(sourceX, y, sourceZ);
        lx -= offx;
        lz -= offz;
        maxD = layerRadius * 1.5;

        if (lx * lx + lz * lz > maxD * maxD) {
            return -4;
        }

        double dist = Math.sqrt(lx * lx + lz * lz) / layerRadius * 5;
        double v = noise.generate(x, y, z) + layerWeight - dist;

        return Math.min(v, smallNoise.generate(x, y, z));
    }
}
