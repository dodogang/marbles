package net.dodogang.marbles.mixin.hooks;

import net.dodogang.marbles.mixin.EntityAccessShapeContext;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityShapeContext.class)
public class EntityShapeContextMixin implements EntityAccessShapeContext {
    private Entity marbles_entity;

    @Inject(method = "<init>(Lnet/minecraft/entity/Entity;)V", at = @At("RETURN"))
    private void grabEntity(Entity entity, CallbackInfo info) {
        this.marbles_entity = entity;
    }

    @Override
    @Nullable
    public Entity marbles_getEntity() {
        return marbles_entity;
    }
}
