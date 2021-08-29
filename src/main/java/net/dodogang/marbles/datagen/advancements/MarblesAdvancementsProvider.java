package net.dodogang.marbles.datagen.advancements;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.advancement.Advancement;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

public class MarblesAdvancementsProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator root;
    private final ImmutableList<Consumer<Consumer<Advancement>>> tabGenerators = ImmutableList.of(new MarblesStoryAdvancementGenerator());

    public MarblesAdvancementsProvider(DataGenerator root) {
        this.root = root;
    }

    @Override
    public void run(DataCache cache) {
        Path path = this.root.getOutput();
        Set<Identifier> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (advancement) -> {
            if (!set.add(advancement.getId())) {
                throw new IllegalStateException("Duplicate advancement " + advancement.getId());
            } else {
                Path output = getOutput(path, advancement);

                try {
                    DataProvider.writeToPath(GSON, cache, advancement.createTask().toJson(), output);
                } catch (IOException exc) {
                    LOGGER.error("Couldn't save advancement {}", output, exc);
                }

            }
        };

        for (Consumer<Consumer<Advancement>> generator : this.tabGenerators) {
            generator.accept(consumer);
        }
    }

    private static Path getOutput(Path rootOutput, Advancement advancement) {
        Identifier advancementId = advancement.getId();
        return rootOutput.resolve("data/" + advancementId.getNamespace() + "/advancements/" + advancementId.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "MarblesAdvancements";
    }
}
