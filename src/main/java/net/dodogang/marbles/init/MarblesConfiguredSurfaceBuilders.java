package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class MarblesConfiguredSurfaceBuilders {
    public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> TRAVERTINE_STRAWS = register("travertine_straws", MarblesSurfaceBuilders.TRAVERTINE_STRAWS.withConfig(Configs.TRAVERTINE_STRAWS));

    private static <SC extends SurfaceConfig> ConfiguredSurfaceBuilder<SC> register(String id, ConfiguredSurfaceBuilder<SC> configuredSurfaceBuilder) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(Marbles.MOD_ID, id), configuredSurfaceBuilder);
    }

    public static class Configs {
        public static final TernarySurfaceConfig TRAVERTINE_STRAWS = new TernarySurfaceConfig(States.TRAVERTINE, States.TRAVERTINE, States.TRAVERTINE);
    }
    public static class States {
        public static final BlockState TRAVERTINE = MarblesBlocks.TRAVERTINE_BLOCKS.RAW.getDefaultState();
    }
}
