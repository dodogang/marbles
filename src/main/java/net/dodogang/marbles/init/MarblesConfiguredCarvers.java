package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.floatprovider.ConstantFloatProvider;
import net.minecraft.util.math.floatprovider.TrapezoidFloatProvider;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.*;
import net.minecraft.world.gen.heightprovider.BiasedToBottomHeightProvider;

public class MarblesConfiguredCarvers {
    public static final ConfiguredCarver<CaveCarverConfig> PERMAFROST_CAVE = register("permafrost_cave", MarblesCarvers.PERMAFROST_CAVE.configure(new CaveCarverConfig(0.14285715F, BiasedToBottomHeightProvider.create(YOffset.fixed(0), YOffset.fixed(127), 8), ConstantFloatProvider.create(0.5F), YOffset.aboveBottom(10), false, CarverDebugConfig.create(false, Blocks.CRIMSON_BUTTON.getDefaultState()), ConstantFloatProvider.create(1.0F), ConstantFloatProvider.create(1.0F), ConstantFloatProvider.create(-0.7F))));
    public static final ConfiguredCarver<RavineCarverConfig> PERMAFROST_CANYON = register("permafrost_canyon", MarblesCarvers.PERMAFROST_CANYON.configure(new RavineCarverConfig(0.02F, BiasedToBottomHeightProvider.create(YOffset.fixed(20), YOffset.fixed(67), 8), ConstantFloatProvider.create(3.0F), YOffset.aboveBottom(10), false, CarverDebugConfig.create(false, Blocks.WARPED_BUTTON.getDefaultState()), UniformFloatProvider.create(-0.125F, 0.125F), new RavineCarverConfig.Shape(UniformFloatProvider.create(0.75F, 1.0F), TrapezoidFloatProvider.create(0.0F, 6.0F, 2.0F), 3, UniformFloatProvider.create(0.75F, 1.0F), 1.0F, 0.0F))));

    private static <WC extends CarverConfig> ConfiguredCarver<WC> register(String id, ConfiguredCarver<WC> configuredCarver) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_CARVER, new Identifier(Marbles.MOD_ID, id), configuredCarver);
    }
}
