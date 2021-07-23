package net.dodogang.marbles.world.gen.carver;

import com.mojang.serialization.Codec;
import net.dodogang.marbles.init.MarblesBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.gen.carver.CaveCarver;
import net.minecraft.world.gen.carver.CaveCarverConfig;

import java.util.HashSet;

public class PermafrostCaveCarver extends CaveCarver {
    public PermafrostCaveCarver(Codec<CaveCarverConfig> codec) {
        super(codec);

        HashSet<Block> newAlwaysCarvableBlocks = new HashSet<>(this.alwaysCarvableBlocks);
        newAlwaysCarvableBlocks.add(MarblesBlocks.FLOESTONE);
        this.alwaysCarvableBlocks = newAlwaysCarvableBlocks;
    }
}
