package net.dodogang.marbles;

import com.google.common.reflect.Reflection;
import net.dodogang.marbles.debug.MarblesDebugCommand;
import net.dodogang.marbles.handler.ToolClickHandlers;
import net.dodogang.marbles.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Marbles implements ModInitializer {
    public static final String MOD_ID = "marbles";
    public static final String MOD_NAME = "Marbles";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    private static boolean initialized = false;

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        if (!initialized) { // We need this because data gen may do an early initialization call
            log("Initializing");

            Reflection.initialize(
                MarblesBlocks.class,
                MarblesItems.class,
                MarblesEntities.class,

                MarblesFeatures.class,
                MarblesConfiguredFeatures.class,
                MarblesBiomes.class,

                MarblesParticles.class,
                MarblesSoundEvents.class,

                ToolClickHandlers.class
            );

            CommandRegistrationCallback.EVENT.register(
                (dispatcher, dedicated) -> MarblesDebugCommand.register(dispatcher)
            );

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
