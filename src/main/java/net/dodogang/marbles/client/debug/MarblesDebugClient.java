package net.dodogang.marbles.client.debug;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.shadew.debug.api.DebugClientInitializer;
import net.shadew.debug.api.render.DebugViewManager;
import net.shadew.debug.api.status.ServerDebugStatus;

@Environment(EnvType.CLIENT)
public class MarblesDebugClient implements DebugClientInitializer {
    @Override
    public void onInitializeDebugClient(ServerDebugStatus statusInstance) {
        DebugViewManager.register(new SpotlightDebugView());
    }
}
