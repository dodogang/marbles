package net.dodogang.marbles;

import me.andante.chord.util.CClientUtils;
import net.dodogang.marbles.init.MarblesBlocks;
import net.fabricmc.api.ClientModInitializer;

public class MarblesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CClientUtils.registerWoodBlocks(MarblesBlocks.ASPEN, MarblesBlocks.HOOPSI);
    }
}
