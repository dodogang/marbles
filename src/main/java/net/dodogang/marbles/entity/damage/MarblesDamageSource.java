package net.dodogang.marbles.entity.damage;

import net.dodogang.marbles.Marbles;
import net.minecraft.entity.damage.DamageSource;

public class MarblesDamageSource extends DamageSource {
    public static final DamageSource SALT_SPIKES = new MarblesDamageSource("salt_spikes");

    protected MarblesDamageSource(String name) {
        super(Marbles.MOD_ID + "." + name);
    }
}
