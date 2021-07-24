package net.dodogang.marbles.mixin.client.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
@Mixin(EntityModelLayers.class)
public interface EntityModelLayersInvoker {
    @Invoker("register")
    static EntityModelLayer register(String id, String layer) {
        throw new AssertionError();
    }
}
