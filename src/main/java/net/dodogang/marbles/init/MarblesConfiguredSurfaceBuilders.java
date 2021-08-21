package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class MarblesConfiguredSurfaceBuilders {
    public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> TRAVERTINE_STRAWS = register("travertine_straws", MarblesSurfaceBuilders.TRAVERTINE_STRAWS.withConfig(Configs.TRAVERTINE_STRAWS));
    public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> PERMAFROST = register("permafrost", MarblesSurfaceBuilders.PERMAFROST.withConfig(Configs.PERMAFROST));

    private static <SC extends SurfaceConfig> ConfiguredSurfaceBuilder<SC> register(String id, ConfiguredSurfaceBuilder<SC> configuredSurfaceBuilder) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(Marbles.MOD_ID, id), configuredSurfaceBuilder);
    }

    public static class Configs {
        public static final TernarySurfaceConfig TRAVERTINE_STRAWS = new TernarySurfaceConfig(States.TRAVERTINE, States.TRAVERTINE, States.TRAVERTINE);
        public static final TernarySurfaceConfig PERMAFROST = new TernarySurfaceConfig(States.PERMAFROST, States.PERMAFROST_DIRT, States.GRAVEL);
    }
    public static class States {
        public static final BlockState TRAVERTINE = MarblesBlocks.TRAVERTINE.RAW.getDefaultState();
        public static final BlockState PERMAFROST = MarblesBlocks.PERMAFROST.getDefaultState();
        public static final BlockState PERMAFROST_DIRT = MarblesBlocks.PERMAFROST_DIRT.getDefaultState();
        public static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
    }
}
