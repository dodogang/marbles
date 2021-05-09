package net.dodogang.marbles.client.particle;

import net.dodogang.marbles.client.particle.vanilla.PublicCrackParticle;
import net.dodogang.marbles.init.MarblesBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;

public class MarblesParticleFactories {
    @Environment(EnvType.CLIENT)
    public static class RopeFactory implements ParticleFactory<DefaultParticleType> {
        @SuppressWarnings("unused")
        public RopeFactory(@Nullable SpriteProvider spriteProvider) {}

        @Override
        public Particle createParticle(DefaultParticleType particle, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new PublicCrackParticle(world, x, y, z, new ItemStack(MarblesBlocks.ROPE));
        }
    }
}
