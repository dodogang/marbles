package net.dodogang.marbles.world.gen.level.terrain;

import net.minecraft.block.BlockState;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Collections;
import java.util.List;

public abstract class Terrain {
    private final int x;
    private final int y;
    private final int z;

    public Terrain(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public abstract double generate(int x, int y, int z, double noise, Chunk chunk);

    public abstract BlockState applyBlockState(int x, int y, int z, double noise, BlockState current, Chunk chunk);

    public List<ConfiguredFeature<?, ?>> getFeatures(Chunk chunk) {
        return Collections.emptyList();
    }
}
