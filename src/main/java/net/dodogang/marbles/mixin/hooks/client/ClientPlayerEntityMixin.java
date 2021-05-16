package net.dodogang.marbles.mixin.hooks.client;

import net.dodogang.marbles.MarblesClient;
import net.dodogang.marbles.block.TravertinePortalBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Shadow public float nextNauseaStrength;

    @Inject(method = "updateNausea", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerEntity;nextNauseaStrength:F", ordinal = 2, shift = At.Shift.BEFORE))
    private void increaseNauseaForTravertine(CallbackInfo ci) {
        Block block = MarblesClient.lastNetherPortalState.getBlock();
        if (block instanceof TravertinePortalBlock) {
            this.nextNauseaStrength += 0.0125F * ((TravertinePortalBlock) block).getPortalTravelSpeedAdditional();
        }
    }
}
