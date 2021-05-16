package net.dodogang.marbles.datagen.tags.factory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;

@SuppressWarnings({"UnusedReturnValue","unused"})
public class TagFactory<T> {
    private boolean replace;
    private final Function<T, Identifier> idGetter;
    private final Set<String> entries = new LinkedHashSet<>();

    public TagFactory(Function<T, Identifier> idGetter) {
        this.idGetter = idGetter;
    }

    public TagFactory<T> replace(boolean replace) {
        this.replace = replace;
        return this;
    }

    public TagFactory<T> add(T object) {
        entries.add(idGetter.apply(object) + "");
        return this;
    }

    public TagFactory<T> add(Tag<T> object) {
        if (!(object instanceof Tag.Identified)) {
            throw new RuntimeException("Cannot identify tag " + object);
        }
        return add((Tag.Identified<T>) object);
    }

    public TagFactory<T> add(Tag.Identified<T> object) {
        entries.add("#" + object.getId());
        return this;
    }

    public TagFactory<T> add(String id) {
        entries.add(id);
        return this;
    }

    public void copyTo(TagFactory<?> factory) {
        entries.forEach(factory::add);
    }

    public boolean isReplace() {
        return replace;
    }

    public Set<String> getEntries() {
        return entries;
    }

    public JsonObject createJson() {
        JsonObject root = new JsonObject();
        root.addProperty("replace", replace);
        JsonArray values = new JsonArray();
        entries.forEach(values::add);
        root.add("values", values);
        return root;
    }
}
