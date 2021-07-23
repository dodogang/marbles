package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.world.gen.surfacebuilder.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class MarblesSurfaceBuilders {
    public static final SurfaceBuilder<TernarySurfaceConfig> TRAVERTINE_STRAWS = register("travertine_straws", new TravertineStrawsSurfaceBuilder(TernarySurfaceConfig.CODEC));
    public static final SurfaceBuilder<TernarySurfaceConfig> PERMAFROST = register("permafrost", new PermafrostSurfaceBuilder(TernarySurfaceConfig.CODEC));

    private static <C extends SurfaceConfig, F extends SurfaceBuilder<C>> F register(String id, F surfaceBuilder) {
        return Registry.register(Registry.SURFACE_BUILDER, new Identifier(Marbles.MOD_ID, id), surfaceBuilder);
    }
}
