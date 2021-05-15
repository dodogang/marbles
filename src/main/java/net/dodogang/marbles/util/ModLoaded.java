package net.dodogang.marbles.util;

import net.fabricmc.loader.api.FabricLoader;

public class ModLoaded {
    private static final FabricLoader FABRIC_LOADER = FabricLoader.getInstance();

    public static final boolean CANVAS = FABRIC_LOADER.isModLoaded("canvas");
    public static final boolean SODIUM = FABRIC_LOADER.isModLoaded("sodium");
}
