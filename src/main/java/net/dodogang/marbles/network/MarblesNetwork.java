package net.dodogang.marbles.network;

import com.mojang.datafixers.util.Pair;
import net.dodogang.marbles.Marbles;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MarblesNetwork {
    public static final Identifier UPDATE_SPOTLIGHT_DATA_PACKET_ID = new Identifier(Marbles.MOD_ID, "update_spotlight_data"); // S2C
    public static final Identifier BOUNCER_HIT_PLAYER_SHIELD_PACKET_ID = new Identifier(Marbles.MOD_ID, "bouncer_hit_player_shield"); // S2C

    public static void sendSpotlightUpdate(ServerWorld world, BlockPos pos, int value) {
        int cx = pos.getX() / 16;
        int cz = pos.getZ() / 16;

        ThreadedAnvilChunkStorage storage = world.toServerWorld().getChunkManager().threadedAnvilChunkStorage;
        storage.getPlayersWatchingChunk(new ChunkPos(cx, cz), false).forEach(
            player -> sendSpotlightUpdate(pos, value, player)
        );
    }

    public static void sendSpotlightUpdate(ServerWorld world, List<Pair<BlockPos, Integer>> data) {

        Set<ServerPlayerEntity> players = new HashSet<>();

        for (Pair<BlockPos, Integer> pair : data) {
            int cx = pair.getFirst().getX() / 16;
            int cz = pair.getFirst().getZ() / 16;

            ThreadedAnvilChunkStorage storage = world.toServerWorld().getChunkManager().threadedAnvilChunkStorage;
            storage.getPlayersWatchingChunk(new ChunkPos(cx, cz), false).forEach(players::add);
        }

        players.forEach(player -> sendSpotlightUpdate(data, player));
    }

    public static void sendSpotlightUpdate(BlockPos pos, int value, ServerPlayerEntity playerEntity) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(1);
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(value);

        ServerPlayNetworking.send(playerEntity, UPDATE_SPOTLIGHT_DATA_PACKET_ID, buf);
    }

    public static void sendSpotlightUpdate(List<Pair<BlockPos, Integer>> data, ServerPlayerEntity playerEntity) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(data.size());
        for (Pair<BlockPos, Integer> pair : data) {
            buf.writeInt(pair.getFirst().getX());
            buf.writeInt(pair.getFirst().getY());
            buf.writeInt(pair.getFirst().getZ());
            buf.writeInt(pair.getSecond());
        }

        ServerPlayNetworking.send(playerEntity, UPDATE_SPOTLIGHT_DATA_PACKET_ID, buf);
    }
}
