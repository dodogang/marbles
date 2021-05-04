package net.dodogang.marbles.client.color.world;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.CuboidBlockIterator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.shadew.util.misc.ColorUtil;

public interface BiomeBlendingColorGenerator extends ColorGenerator {
    int getBiomeColor(ClientWorld world, BlockPos pos, Biome biome);

    @Override
    default int getColor(ClientWorld world, BlockPos pos) {
        int radius = MinecraftClient.getInstance().options.biomeBlendRadius;
        if (radius == 0) {
            return getBiomeColor(world, pos, world.getBiome(pos));
        } else {
            int area = (radius * 2 + 1) * (radius * 2 + 1);
            int r = 0, g = 0, b = 0;

            CuboidBlockIterator itr = new CuboidBlockIterator(
                pos.getX() - radius,
                pos.getY(),
                pos.getZ() - radius,
                pos.getX() + radius,
                pos.getY(),
                pos.getZ() + radius
            );

            BlockPos.Mutable mpos = new BlockPos.Mutable();
            while (itr.step()) {
                mpos.set(itr.getX(), itr.getY(), itr.getZ());

                int c = getBiomeColor(world, mpos, world.getBiome(pos));
                r += ColorUtil.redi(c);
                g += ColorUtil.greeni(c);
                b += ColorUtil.bluei(c);
            }

            return ColorUtil.rgb(r / area, g / area, b / area);
        }
    }
}
