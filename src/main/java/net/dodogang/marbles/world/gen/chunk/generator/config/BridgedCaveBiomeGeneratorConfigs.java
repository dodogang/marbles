package net.dodogang.marbles.world.gen.chunk.generator.config;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.init.MarblesRegistries;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class BridgedCaveBiomeGeneratorConfigs {
    public static final BridgedCaveBiomeGeneratorConfig PINK_SALT_CAVE = register("pink_salt_cave", new PinkSaltCaveGeneratorConfig());
    // public static final BridgedCaveBiomeGeneratorConfig ICE_CAVE = register("ice_cave", new IceCaveGeneratorConfig(MarblesBiomes.ICE_CAVE, MarblesConfiguredFeatureLists.ICE_CAVE, Blocks.ICE));
    // public static final BridgedCaveBiomeGeneratorConfig SCALED_ICE_CAVE = register("scaled_ice_cave", new IceCaveGeneratorConfig(MarblesBiomes.SCALED_ICE_CAVE, MarblesConfiguredFeatureLists.SCALED_ICE_CAVE, MarblesBlocks.SCALED_ICE));
    // public static final BridgedCaveBiomeGeneratorConfig MINTED_ICE_CAVE = register("minted_ice_cave", new IceCaveGeneratorConfig(MarblesBiomes.MINTED_ICE_CAVE, MarblesConfiguredFeatureLists.MINTED_ICE_CAVE, MarblesBlocks.MINTED_ICE));

    private static BridgedCaveBiomeGeneratorConfig register(String id, BridgedCaveBiomeGeneratorConfig function) {
        return Registry.register(MarblesRegistries.BRIDGED_CAVE_BIOME_GENERATOR_CONFIG, new Identifier(Marbles.MOD_ID, id), function);
    }
}
