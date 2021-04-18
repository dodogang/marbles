package net.dodogang.marbles.debug;

import net.shadew.debug.api.DebugClientInitializer;
import net.shadew.debug.api.render.DebugViewManager;
import net.shadew.debug.api.status.ServerDebugStatus;

public class MarblesDebugClient implements DebugClientInitializer {
    @Override
    public void onInitializeDebugClient(ServerDebugStatus statusInstance) {
        DebugViewManager.register(new SpotlightDebugView());
    }
}
