package net.dodogang.marbles.client.color.world;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public interface MarblesColorWorld {
    int marbles_getColor(BlockPos pos, ColorGenerator generator);
}
