package net.dodogang.marbles.client.color.world;

import net.minecraft.util.math.BlockPos;

public interface MarblesColorWorld {
    int marbles_getColor(BlockPos pos, ColorGenerator generator);
}
