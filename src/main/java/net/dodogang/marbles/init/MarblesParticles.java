package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.particle.PublicDefaultParticleType;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MarblesParticles {
    public static final DefaultParticleType PINK_SALT = register("pink_salt");
    public static final DefaultParticleType ICE_SPORE = register("ice_spore");
    public static final DefaultParticleType ITEM_ROPE = register("item_rope");

    private static DefaultParticleType register(String id, boolean alwaysShow) {
        return Registry.register(Registry.PARTICLE_TYPE, new Identifier(Marbles.MOD_ID, id), new PublicDefaultParticleType(alwaysShow));
    }
    private static DefaultParticleType register(String id) {
        return register(id, false);
    }
}
