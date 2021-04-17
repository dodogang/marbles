package net.dodogang.marbles.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class MyceliumPathBlock extends MarblesPathBlock {
    public MyceliumPathBlock(Block hostBlock, Settings settings) {
        super(hostBlock, settings);
    }


    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (random.nextInt(10) == 0) {
            world.addParticle(
                ParticleTypes.MYCELIUM,
                pos.getX() + random.nextDouble(),
                pos.getY() + 1.1 - 1 / 16d,
                pos.getZ() + random.nextDouble(),
                0, 0, 0
            );
        }
    }
}
