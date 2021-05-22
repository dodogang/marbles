package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.world.gen.chunk.generator.config.BridgedCaveBiomeGeneratorConfig;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

public class MarblesRegistries {
    public static final Registry<BridgedCaveBiomeGeneratorConfig> BRIDGED_CAVE_BIOME_GENERATOR_CONFIG = createSimple(BridgedCaveBiomeGeneratorConfig.class, "bridged_cave_biome_generator_config");

    private static <T> SimpleRegistry<T> createSimple(Class<T> type, String id) {
        return FabricRegistryBuilder.createSimple(type, new Identifier(Marbles.MOD_ID, id)).buildAndRegister();
    }
}
