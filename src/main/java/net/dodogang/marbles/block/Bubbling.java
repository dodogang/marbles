package net.dodogang.marbles.block;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public interface Bubbling {
    default void spawnBubbles(World world, BlockPos pos, Random random, Vec3d offset) {
        if (random.nextFloat() <= 0.5f) {
            Vec3d spawnPos = Vec3d.of(pos).add(offset).add(random.nextDouble(), random.nextDouble(), random.nextDouble());
            world.addParticle(ParticleTypes.BUBBLE, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0.0d, 0.2d, 0.0d);
        }
    }
}
