package net.dodogang.marbles;

import net.dodogang.marbles.debug.MarblesDebugCommand;
import net.dodogang.marbles.handler.ToolClickHandlers;
import net.dodogang.marbles.init.*;
import net.dodogang.marbles.world.gen.level.MarblesNoiseChunkGenerator;
import net.dodogang.marbles.item.MarblesItemGroup;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Marbles implements ModInitializer {
    public static final String MOD_ID = "marbles";
    public static final String MOD_NAME = "Marbles";

    public static final ItemGroup ITEM_GROUP = new MarblesItemGroup();
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    private static boolean initialized = false;

    @Override
    public void onInitialize() {
        if (!initialized) { // We need this because data gen may do an early initialization call
            log("Initializing");

            new MarblesParticles();

            new MarblesBlocks();
            new MarblesItems();

            new MarblesFeatures();
            new MarblesConfiguredFeatures();
            Registry.register(Registry.CHUNK_GENERATOR, "marbles:noise", MarblesNoiseChunkGenerator.CODEC);

            new MarblesSoundEvents();

            new MarblesBiomes();

            new MarblesPointOfInterestTypes();

            CommandRegistrationCallback.EVENT.register(
                (dispatcher, dedicated) -> MarblesDebugCommand.register(dispatcher)
            );

            ToolClickHandlers.init();

            log("Initialized");

            initialized = true;
        }
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

    public static void log(String message) {
        log(Level.INFO, message);
    }
}
