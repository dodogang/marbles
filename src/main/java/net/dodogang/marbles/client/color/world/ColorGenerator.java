package net.dodogang.marbles.client.color.world;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

public interface ColorGenerator {
    int getColor(ClientWorld world, BlockPos pos);
}
