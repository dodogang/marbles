package net.dodogang.marbles.client.world.biome;

import com.google.common.collect.ImmutableMap;
import net.dodogang.marbles.init.MarblesBiomes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class BiomeFog {
    public static final ImmutableMap<RegistryKey<Biome>, Function<ClientPlayerEntity, Float>> BIOME_TO_FOG_DISTANCE_MAP = ImmutableMap.of(
        MarblesBiomes.WOODED_PERMAFROST_MOUNTAINS, BiomeFog::permafrost,
        MarblesBiomes.PERMAFROST_ICE_SPIKES, BiomeFog::permafrost
    );

    public static float permafrost(PlayerEntity player) {
        int topY = player.world.getTopY();
        float shift = 3.6f;
        double yOffset = 16;
        float stretch = 0.21f;

        return Math.max((float) ((((player.getY() + yOffset) - (topY / shift)) / topY) * (topY * stretch)), 0.00001f);
    }
}
