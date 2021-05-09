package net.dodogang.marbles;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.dodogang.marbles.client.config.MarblesConfigManager;

public class MarblesModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return MarblesConfigManager::createScreen;
    }
}
