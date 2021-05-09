package net.dodogang.marbles.client.particle.vanilla;

import net.minecraft.client.particle.CrackParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;

public class PublicCrackParticle extends CrackParticle {
    public PublicCrackParticle(ClientWorld world, double x, double y, double z, ItemStack stack) {
        super(world, x, y, z, stack);
    }
}
