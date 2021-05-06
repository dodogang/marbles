package net.dodogang.marbles.client.color.world;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public interface ColorGenerator {
    int getColor(ClientWorld world, BlockPos pos);
}
