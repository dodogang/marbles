package net.dodogang.marbles.datagen.models.modelgen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public record ParticleOnlyModelGen(String particles) implements ModelGen {
    @Override
    public JsonElement makeJson(Identifier name) {
        JsonObject root = new JsonObject();
        JsonObject textures = new JsonObject();
        textures.addProperty("particle", particles);
        root.add("textures", textures);
        return root;
    }

    public static ParticleOnlyModelGen particles(String particles) {
        return new ParticleOnlyModelGen(particles);
    }

    public static ParticleOnlyModelGen particles(Identifier particles) {
        return new ParticleOnlyModelGen(particles.toString());
    }
}
