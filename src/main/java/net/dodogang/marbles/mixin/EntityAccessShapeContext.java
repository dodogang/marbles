package net.dodogang.marbles.mixin;

import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface EntityAccessShapeContext {
    @Nullable Entity marbles_getEntity();
}
