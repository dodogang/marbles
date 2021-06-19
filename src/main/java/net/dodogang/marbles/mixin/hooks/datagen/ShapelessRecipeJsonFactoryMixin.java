package net.dodogang.marbles.mixin.hooks.datagen;

import net.dodogang.marbles.Marbles;
import net.minecraft.advancement.Advancement;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ShapelessRecipeJsonFactory.class)
public class ShapelessRecipeJsonFactoryMixin {
    @Shadow @Final private Advancement.Task builder;

    @Inject(method = "offerTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/Advancement$Task;parent(Lnet/minecraft/util/Identifier;)Lnet/minecraft/advancement/Advancement$Task;", shift = At.Shift.AFTER, ordinal = 0))
    private void fixParent(Consumer<RecipeJsonProvider> exporter, Identifier recipeId, CallbackInfo ci) {
        if (recipeId.getNamespace().equals(Marbles.MOD_ID)) {
            this.builder.parent(new Identifier(Marbles.MOD_ID, "recipes/root"));
        }
    }
}
