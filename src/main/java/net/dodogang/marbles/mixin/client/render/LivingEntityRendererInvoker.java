package net.dodogang.marbles.mixin.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Environment(EnvType.CLIENT)
@Mixin(LivingEntityRenderer.class)
public interface LivingEntityRendererInvoker<T extends LivingEntity, M extends EntityModel<T>> {
    @Invoker("isVisible")
    boolean invoke_isVisible(T entity);

    @Invoker("getRenderLayer")
    @Nullable
    RenderLayer invoke_getRenderLayer(T entity, boolean showBody, boolean translucent, boolean showOutline);
}
