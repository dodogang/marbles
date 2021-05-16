package net.dodogang.marbles.mixin.hooks.client;

import net.dodogang.marbles.block.RilledFloestoneBlock;
import net.dodogang.marbles.client.config.MarblesConfig;
import net.dodogang.marbles.sound.MarblesBlockSoundGroup;
import net.dodogang.marbles.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Shadow public abstract void renderClouds(MatrixStack matrices, float tickDelta, double cameraX, double cameraY, double cameraZ);
    @Shadow private ClientWorld world;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderClouds(Lnet/minecraft/client/util/math/MatrixStack;FDDD)V", shift = At.Shift.BEFORE))
    private void renderAdditionalClouds(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci) {
        if (MarblesConfig.RENDER.additionalCloudLayers.value) {
            Vec3d cameraPos = camera.getPos();
            double x = cameraPos.getX();
            double y = cameraPos.getY();
            double z = cameraPos.getZ();

            for (int i = 1; i < Util.ADDITIONAL_CLOUD_COUNT + 1; i++) {
                this.renderClouds(matrices, tickDelta, x + (270 * i), y - (Util.ADDITIONAL_CLOUD_OFFSET * i), z + (270 * i));
            }
        }
    }

    @Inject(method = "processGlobalEvent", at = @At("HEAD"), cancellable = true)
    private void checkCustomEvents(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        if (eventId == RilledFloestoneBlock.BREAK_WITHOUT_SILK_TOUCH_EVENT) {
            BlockSoundGroup blockSoundGroup = MarblesBlockSoundGroup.ICE_BRICKS;
            this.world.playSound(pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0F) / 9.0F, blockSoundGroup.getPitch() * 0.8F, false);

            ci.cancel();
        }
    }
}
