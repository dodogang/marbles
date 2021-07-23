package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.world.gen.carver.PermafrostCaveCarver;
import net.dodogang.marbles.world.gen.carver.PermafrostCanyonCarver;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverConfig;
import net.minecraft.world.gen.carver.CaveCarverConfig;
import net.minecraft.world.gen.carver.RavineCarverConfig;

public class MarblesCarvers {
    public static final Carver<CaveCarverConfig> PERMAFROST_CAVE = register("permafrost_cave", new PermafrostCaveCarver(CaveCarverConfig.CAVE_CODEC));
    public static final Carver<RavineCarverConfig> PERMAFROST_CANYON = register("permafrost_canyon", new PermafrostCanyonCarver(RavineCarverConfig.RAVINE_CODEC));

    private static <C extends CarverConfig, F extends Carver<C>> F register(String id, F carver) {
        return Registry.register(Registry.CARVER, new Identifier(Marbles.MOD_ID, id), carver);
    }
}
