package net.dodogang.marbles;

import net.dodogang.marbles.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Marbles implements ModInitializer {
    public static final String MOD_ID = "marbles";
    public static final String MOD_NAME = "Marbles";

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "item_group"), () -> new ItemStack(MarblesBlocks.TRAVERTINE));
    public static Logger LOGGER = LogManager.getLogger(MOD_ID);

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

            new MarblesBiomes();

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
