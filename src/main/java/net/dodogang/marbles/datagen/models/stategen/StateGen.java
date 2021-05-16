package net.dodogang.marbles.datagen.models.stategen;

import com.google.gson.JsonElement;
import net.dodogang.marbles.datagen.models.modelgen.ModelGen;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;


@SuppressWarnings("unused")
public interface StateGen {
    JsonElement makeJson(Identifier id, Block block);
    void getModels(BiConsumer<String, ModelGen> consumer);
}
