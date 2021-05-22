package net.dodogang.marbles.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.AscendingParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class IceSporeParticle extends AscendingParticle {
    public IceSporeParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteProvider spriteProvider) {
        super(world, x, y, z, 0.04F, -0.01F, 0.04F, velocityX, velocityY, velocityZ, scaleMultiplier, spriteProvider, 1.0F, 60, -0.0005D, false);

        this.colorAlpha = 1.0F;
        this.colorRed = 1.0F - (random.nextFloat() / 2);
        this.colorGreen = 1.0F - (random.nextFloat() / 2);
        this.colorBlue = 1.0F - (random.nextFloat() / 2);

        this.scale = 0.07F;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType particleType, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new IceSporeParticle(world, x, y, z, velocityX, velocityY, velocityZ, 0.8F, this.spriteProvider);
        }
    }
}
