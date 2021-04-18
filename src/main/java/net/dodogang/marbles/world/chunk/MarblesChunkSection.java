package net.dodogang.marbles.world.chunk;

import net.minecraft.nbt.CompoundTag;

public interface MarblesChunkSection {
    int getSpotlightData(int x, int y, int z);
    void setSpotlightData(int x, int y, int z, int data);
    void read(CompoundTag tag);
    void write(CompoundTag tag);
}
