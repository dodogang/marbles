package net.dodogang.marbles.client.color.world;

import com.google.common.collect.Sets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.shadew.ptg.noise.Noise3D;
import net.shadew.ptg.noise.opensimplex.FractalOpenSimplex3D;
import net.shadew.util.misc.ColorUtil;
import net.shadew.util.misc.MathUtil;

import java.util.Set;

@Environment(EnvType.CLIENT)
public class MarblesBiomeColors {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static Set<ColorGenerator> getColorGenerators() {
        return Sets.newHashSet(GRISP_GRASS);
    }

    public static int getColor(BlockPos pos, ColorGenerator generator) {
        if (client.world == null) {
            return -1;
        }
        MarblesColorWorld cworld = (MarblesColorWorld) client.world;
        return cworld.marbles_getColor(pos, generator);
    }


    public static final int GRISP_GRASS_DEFAULT = 0x74C05A;

    private static final double GRISP_GRASS_HUE = ColorUtil.hued(GRISP_GRASS_DEFAULT);
    private static final double GRISP_GRASS_SATURATION = ColorUtil.saturationd(GRISP_GRASS_DEFAULT);
    private static final double GRISP_GRASS_VALUE = ColorUtil.valued(GRISP_GRASS_DEFAULT);
    private static final Noise3D GRISP_GRASS_HUE_NOISE = new FractalOpenSimplex3D(0x63FC_27C4, 11.4269, 4);
    private static final Noise3D GRISP_GRASS_VALUE_NOISE = new FractalOpenSimplex3D(0xCAFE_0069, 11.4269, 4);

    public static final ColorGenerator GRISP_GRASS = (world, pos) -> {
        double hn = GRISP_GRASS_HUE_NOISE.generate(pos.getX(), pos.getY(), pos.getZ());
        double vn = GRISP_GRASS_VALUE_NOISE.generate(pos.getX(), pos.getY(), pos.getZ());

        double h = GRISP_GRASS_HUE + hn * 20;
        double v = GRISP_GRASS_VALUE + vn * 0.08 - 0.08;

        if (h < 0) h += 360;
        v = MathUtil.clamp(v, 0, 1);
        return ColorUtil.hsv(h, GRISP_GRASS_SATURATION, v);
    };

    public static int getGrispGrassColor(BlockPos pos) {
        return getColor(pos, GRISP_GRASS);
    }
}
