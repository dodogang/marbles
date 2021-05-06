package net.dodogang.marbles.debug;

import net.dodogang.marbles.client.debug.SpotlightDebugView;
import net.minecraft.text.TranslatableText;
import net.shadew.debug.api.DebugMenuInitializer;
import net.shadew.debug.api.menu.BooleanOption;
import net.shadew.debug.api.menu.DebugMenu;
import net.shadew.debug.api.menu.DebugMenuManager;
import net.shadew.debug.api.menu.OptionSelectContext;
import net.shadew.debug.api.status.ServerDebugStatus;

public class MarblesDebugMenu implements DebugMenuInitializer {
    @Override
    public void onInitializeDebugMenu(DebugMenu root, DebugMenuManager factory, ServerDebugStatus debugStatus) {
        DebugMenu display = factory.getMenu("debug:display");

        display.addOption(new BooleanOption(new TranslatableText("debug.option.marbles.spotlight")) {
            @Override
            protected void toggle(OptionSelectContext context) {
                SpotlightDebugView.enabled = !SpotlightDebugView.enabled;
            }

            @Override
            protected boolean get() {
                return SpotlightDebugView.enabled;
            }
        });
    }
}
