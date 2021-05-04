package net.dodogang.marbles.client.network;

import com.mojang.datafixers.util.Pair;
import net.dodogang.marbles.network.MarblesNetwork;
import net.dodogang.marbles.util.SpotlightUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class MarblesClientNetwork {
    @Environment(EnvType.CLIENT)
    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(MarblesNetwork.UPDATE_SPOTLIGHT_DATA, (client, handler, buf, responseSender) -> {
            List<Pair<BlockPos, Integer>> list = new ArrayList<>();
            int i = buf.readInt();
            while (i > 0) {
                int x = buf.readInt();
                int y = buf.readInt();
                int z = buf.readInt();
                int v = buf.readInt();
                list.add(Pair.of(new BlockPos(x, y, z), v));
                i--;
            }
            client.execute(() -> {
                if (client.world != null) {
                    for (Pair<BlockPos, Integer> pair : list) {
                        SpotlightUtil.setSpotlightData(client.world, pair.getFirst(), pair.getSecond(), false);
                    }
                }
            });
        });
    }
}
