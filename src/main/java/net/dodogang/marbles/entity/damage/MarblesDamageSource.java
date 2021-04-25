package net.dodogang.marbles.entity.damage;

import net.minecraft.entity.damage.DamageSource;

public class MarblesDamageSource extends DamageSource {
    public static final DamageSource SALT_SPIKES = new MarblesDamageSource("salt_spikes");

    protected MarblesDamageSource(String name) {
        super("marbles." + name);
    }
}
