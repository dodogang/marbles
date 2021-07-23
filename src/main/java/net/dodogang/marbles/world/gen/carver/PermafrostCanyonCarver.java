package net.dodogang.marbles.world.gen.carver;

import com.mojang.serialization.Codec;
import net.dodogang.marbles.init.MarblesBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.gen.carver.RavineCarver;
import net.minecraft.world.gen.carver.RavineCarverConfig;

import java.util.HashSet;

public class PermafrostCanyonCarver extends RavineCarver {
    public PermafrostCanyonCarver(Codec<RavineCarverConfig> codec) {
        super(codec);

        HashSet<Block> newAlwaysCarvableBlocks = new HashSet<>(this.alwaysCarvableBlocks);
        newAlwaysCarvableBlocks.add(MarblesBlocks.FLOESTONE);
        this.alwaysCarvableBlocks = newAlwaysCarvableBlocks;
    }
}
