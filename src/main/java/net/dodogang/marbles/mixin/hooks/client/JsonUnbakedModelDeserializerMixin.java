package net.dodogang.marbles.mixin.hooks.client;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dodogang.marbles.Marbles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverride;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(JsonUnbakedModel.Deserializer.class)
public class JsonUnbakedModelDeserializerMixin {
    /**
     * @reason Injects a rope override for crossbows.
     */
    @Inject(method = "deserializeOverrides", at = @At("RETURN"), cancellable = true)
    private void addOverrides(JsonDeserializationContext context, JsonObject object, CallbackInfoReturnable<List<ModelOverride>> cir) {
        JsonElement textures = object.get("textures");
        if (textures != null && textures.isJsonObject()) {
            JsonElement layer0 = textures.getAsJsonObject().get("layer0");
            if (layer0 != null) {
                String textureId = layer0.getAsString();
                if (textureId.equals("item/crossbow_standby") || textureId.equals("minecraft:item/crossbow_standby")) {
                    List<ModelOverride> overrides = cir.getReturnValue();
                    overrides.add(new ModelOverride(new Identifier(Marbles.MOD_ID, "item/crossbow_rope"), ImmutableMap.of(new Identifier(Marbles.MOD_ID, "rope"), 1.0f)));

                    cir.setReturnValue(overrides);
                }
            }
        }
    }
}
