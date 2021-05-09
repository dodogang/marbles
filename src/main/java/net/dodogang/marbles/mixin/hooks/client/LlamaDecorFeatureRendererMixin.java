package net.dodogang.marbles.mixin.hooks.client;

import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.mixin.LlamaEntityAccess;
import net.dodogang.marbles.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.LlamaDecorFeatureRenderer;
import net.minecraft.client.render.entity.model.LlamaEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(LlamaDecorFeatureRenderer.class)
public class LlamaDecorFeatureRendererMixin {
    @Shadow @Final private LlamaEntityModel<LlamaEntity> model;

    /**
     * @reason Replaces the llama's carpet decor with the pollen-graced carpet decor texture if it is worn.
     */
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void replaceCarpet(MatrixStack matrices, VertexConsumerProvider vertices, int light, LlamaEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, CallbackInfo ci) {
        ItemStack stack = ((LlamaEntityAccess) entity).getSaddleItem();
        if (stack.getItem() == MarblesBlocks.POLLEN_GRACED_CARPET.asItem()) {
            LlamaDecorFeatureRenderer $this = LlamaDecorFeatureRenderer.class.cast(this);

            $this.getContextModel().copyStateTo(this.model);
            this.model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

            this.model.render(matrices, vertices.getBuffer(RenderLayer.getEntityCutoutNoCull(Util.POLLEN_GRACED_CARPET_LLAMA_DECOR_TEXTURE)), light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

            ci.cancel();
        }
    }
}
