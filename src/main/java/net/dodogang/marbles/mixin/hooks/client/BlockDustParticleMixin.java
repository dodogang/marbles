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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BlockDustParticle.class)
public abstract class BlockDustParticleMixin extends SpriteBillboardParticle {
    @Shadow
    @Final
    private BlockState blockState;

    @SuppressWarnings("unused")
    protected BlockDustParticleMixin(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Inject(method = "updateColor", at = @At("HEAD"), cancellable = true)
    private void onUpdateColor(BlockPos pos, CallbackInfo cb) {
        if (blockState.isOf(MarblesBlocks.GRISP_GRASS_BLOCK) || blockState.isOf(Blocks.CAULDRON)) {
            // Issue happens for any block that is not a vanilla grass block
            // Fix for our grisp grass, and for cauldrons (fixes MC-132734)
            colorRed = 1;
            colorGreen = 1;
            colorBlue = 1;
            cb.cancel();
        }
    }
}
