package net.dodogang.marbles.client.network;

import com.mojang.datafixers.util.Pair;
import net.dodogang.marbles.entity.BouncerEntity;
import net.dodogang.marbles.network.MarblesNetwork;
import net.dodogang.marbles.util.SpotlightUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Environment(EnvType.CLIENT)
public class MarblesClientNetwork {
    static {
        ClientPlayNetworking.registerGlobalReceiver(MarblesNetwork.UPDATE_SPOTLIGHT_DATA_PACKET_ID, (client, handler, buf, responseSender) -> {
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

        ClientPlayNetworking.registerGlobalReceiver(MarblesNetwork.BOUNCER_HIT_PLAYER_SHIELD_PACKET_ID, (client, handler, buf, responseSender) -> {
            int attackerId = buf.readInt();
            UUID playerUUID = buf.readUuid();

            if (client.world != null) {
                client.execute(() -> {
                    Entity attacker = client.world.getEntityById(attackerId);
                    PlayerEntity player = client.world.getPlayerByUuid(playerUUID);

                    if (attacker instanceof BouncerEntity && player != null) {
                        ((BouncerEntity) attacker).tryThrowEntity(player, true);
                    }
                });
            }
        });
    }
}
