package net.dodogang.marbles;

import net.dodogang.marbles.client.gui.itemgroup.MarblesItemGroup;
import net.dodogang.marbles.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Marbles implements ModInitializer {
    public static final String MOD_ID = "marbles";
    public static final String MOD_NAME = "Marbles";

    public static final ItemGroup ITEM_GROUP = new MarblesItemGroup(new Identifier(Marbles.MOD_ID, "item_group"));
    public static final Item LOGO = Registry.register(Registry.ITEM, new Identifier(Marbles.MOD_ID, "logo"), new Item(new FabricItemSettings().food(new FoodComponent.Builder().alwaysEdible().hunger(20).build())));

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

            new MarblesSoundEvents();

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
