package net.dodogang.marbles.mixin.hooks;

import net.dodogang.marbles.util.SpotlightUtil;
import net.dodogang.marbles.world.chunk.MarblesChunkSection;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.world.chunk.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkSection.class)
public class ChunkSectionMixin implements MarblesChunkSection {
    private static final int SPOTLIGHT_BUFFER_SIZE = 16 * 16 * 16;
    private final int[] spotlightData = new int[SPOTLIGHT_BUFFER_SIZE];
    private int nonEmptySpotlightData = 0;

    @Override
    public int getSpotlightData(int x, int y, int z) {
        return spotlightData[(x * 16 + z) * 16 + y];
    }

    @Override
    public void setSpotlightData(int x, int y, int z, int data) {
        int current = getSpotlightData(x, y, z);
        boolean cdata = SpotlightUtil.hasSpotlightValue(current);
        boolean ndata = SpotlightUtil.hasSpotlightValue(data);
        if (cdata && !ndata) {
            nonEmptySpotlightData--;
        } else if (!cdata && ndata) {
            nonEmptySpotlightData++;
        }
        spotlightData[(x * 16 + z) * 16 + y] = data;
    }

    @Override
    public void read(CompoundTag tag) {
        int[] arr = tag.getIntArray("Spotlight");
        System.arraycopy(arr, 0, spotlightData, 0, Math.min(arr.length, SPOTLIGHT_BUFFER_SIZE));
    }

    @Override
    public void write(CompoundTag tag) {
        tag.putIntArray("Spotlight", spotlightData);
    }

    @Environment(EnvType.CLIENT)
    @Inject(method = "fromPacket", at = @At("RETURN"))
    private void onFromPacket(PacketByteBuf buf, CallbackInfo cb) {
        int[] arr = spotlightData;
        for (int i = 0; i < SPOTLIGHT_BUFFER_SIZE; i++) {
            arr[i] = buf.readInt();
        }
    }

    @Inject(method = "toPacket", at = @At("RETURN"))
    private void onToPacket(PacketByteBuf buf, CallbackInfo cb) {
        int[] arr = spotlightData;
        for (int l : arr) {
            buf.writeInt(l);
        }
    }

    @Inject(method = "getPacketSize", at = @At("RETURN"), cancellable = true)
    private void onGetPacketSize(CallbackInfoReturnable<Integer> cb) {
        int rv = cb.getReturnValueI();
        cb.setReturnValue(rv + SPOTLIGHT_BUFFER_SIZE * 4);
    }

    @Inject(method = "calculateCounts", at = @At("HEAD"))
    private void onCalculateCounts(CallbackInfo info) {
        nonEmptySpotlightData = 0;
        for (int i = 0; i < SPOTLIGHT_BUFFER_SIZE; i++) {
            int v = spotlightData[i];
            if (SpotlightUtil.hasSpotlightValue(v)) {
                nonEmptySpotlightData++;
            }
        }
    }

    @Inject(method = "isEmpty()Z", at = @At("RETURN"), cancellable = true)
    private void onIsEmpty(CallbackInfoReturnable<Boolean> cb) {
        cb.setReturnValue(cb.getReturnValueZ() && nonEmptySpotlightData == 0);
    }
}
