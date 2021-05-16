package net.dodogang.marbles.util;

import net.dodogang.marbles.Marbles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

// We have to do things reflectively. If IP is not present we must still be able to run.
@SuppressWarnings("FieldCanBeLocal")
public class ImmersivePortalsCompatHelper {
    private static final Logger LOGGER = LogManager.getLogger();
    private static boolean isPresent;
    private static final MethodHandles.Lookup lookup = MethodHandles.lookup();
    private static Class<?> global;
    private static Class<?> netherPortalMode;
    private static MethodHandle global_netherPortalMode;

    public static String getNetherPortalMode() {
        if (isPresent) {
            try {
                return global_netherPortalMode.invoke() + "";
            } catch (Throwable exc) {
                LOGGER.error("Failed to get nether portal mode", exc);
            }
        }
        return "";
    }

    static {
        isPresent = true;
        try {
            global = Class.forName("com.qouteall.immersive_portals.Global");
            netherPortalMode = Class.forName("com.qouteall.immersive_portals.Global$NetherPortalMode");
            global_netherPortalMode = lookup.findStaticGetter(global, "netherPortalMode", netherPortalMode);
        } catch (Exception exc) {
            Marbles.LOGGER.debug("Could not find immersive portals", exc);
            isPresent = false;
        }
        Marbles.log("Immersive portals is " + (isPresent ? "" : "not ") + "present");
    }
}
