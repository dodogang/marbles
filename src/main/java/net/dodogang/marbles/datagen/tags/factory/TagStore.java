package net.dodogang.marbles.datagen.tags.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataProvider;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class TagStore<T> {
    private static final Gson GSON = new GsonBuilder()
                                         .setPrettyPrinting()
                                         .disableHtmlEscaping()
                                         .create();

    private final Registry<T> registry;
    private final Path rootPath;
    private final String tagType;
    private final Map<Identifier, TagFactory<T>> tagFactoryMap = new LinkedHashMap<>();

    public TagStore(Registry<T> registry, Path rootPath, String tagType) {
        this.registry = registry;
        this.rootPath = rootPath;
        this.tagType = tagType;
    }

    public TagFactory<T> factory(Tag<T> tag) {
        if (!(tag instanceof Tag.Identified)) {
            throw new RuntimeException("Cannot identify tag " + tag);
        }
        return factory((Tag.Identified<T>) tag);
    }

    public TagFactory<T> factory(Tag.Identified<T> tag) {
        return tagFactoryMap.computeIfAbsent(tag.getId(), t -> new TagFactory<>(registry::getId));
    }

    public TagFactory<T> factory(Identifier id) {
        return tagFactoryMap.computeIfAbsent(id, t -> new TagFactory<>(registry::getId));
    }

    public TagFactory<T> factory(String id) {
        return tagFactoryMap.computeIfAbsent(new Identifier(id), t -> new TagFactory<>(registry::getId));
    }

    public void write(DataCache cache) throws IOException {
        for (Map.Entry<Identifier, TagFactory<T>> entry : tagFactoryMap.entrySet()) {
            Path outPath = rootPath.resolve("data/" + entry.getKey().getNamespace() + "/tags/" + tagType + "/" + entry.getKey().getPath() + ".json");
            DataProvider.writeToPath(GSON, cache, entry.getValue().createJson(), outPath);
        }
    }
}
