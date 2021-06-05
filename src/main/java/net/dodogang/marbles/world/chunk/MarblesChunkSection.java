package net.dodogang.marbles.world.chunk;

import net.minecraft.nbt.NbtCompound;

public interface MarblesChunkSection {
    int getSpotlightData(int x, int y, int z);
    void setSpotlightData(int x, int y, int z, int data);
    void read(NbtCompound tag);
    void write(NbtCompound tag);
}
