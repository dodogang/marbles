package net.dodogang.marbles.mixin.hooks.client;

import net.dodogang.marbles.init.MarblesBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BlockDustParticle.class)
public abstract class BlockDustParticleMixin extends SpriteBillboardParticle {
    @SuppressWarnings("unused")
    protected BlockDustParticleMixin(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Inject(method = "<init>(Lnet/minecraft/client/world/ClientWorld;DDDDDDLnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V", at = @At("TAIL"))
    private void onUpdateColor(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, BlockState state, BlockPos blockPos, CallbackInfo ci) {
        if (state.isOf(MarblesBlocks.GRISP_GRASS_BLOCK) || state.isOf(Blocks.CAULDRON)) {
            // Issue happens for any block that is not a vanilla grass block
            // Fix for our grisp grass, and for cauldrons (fixes MC-132734)
            this.colorRed = 1;
            this.colorGreen = 1;
            this.colorBlue = 1;
        }
    }
}
