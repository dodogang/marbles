package net.dodogang.marbles.world.gen.level.terrain;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.chunk.Chunk;

public class ClearAreaTerrain extends Terrain {
    private final int radius;

    public ClearAreaTerrain(int x, int y, int z, int radius) {
        super(x, y, z);
        this.radius = radius;
    }

    @Override
    public double generate(int x, int y, int z, double noise, Chunk chunk) {
        int mx = getX();
        int my = getY();
        int mz = getZ();
        int dx = Math.abs(x - mx);
        int dy = Math.abs(y - my);
        int dz = Math.abs(z - mz);

        if (dx < radius && dy < radius && dz < radius) {
            return -Math.abs(noise) - 1;
        }
        return noise;
    }

    @Override
    public BlockState applyBlockState(int x, int y, int z, double noise, BlockState current, Chunk chunk) {
        int mx = getX();
        int my = getY();
        int mz = getZ();
        int dx = Math.abs(x - mx);
        int dy = Math.abs(y - my);
        int dz = Math.abs(z - mz);

        if (dx < radius + 1 && dy < radius + 1 && dz < radius + 1) {
            if (current.getBlock() == Blocks.WATER) {
                return Blocks.AIR.getDefaultState();
            }
        }
        return current;
    }
}
