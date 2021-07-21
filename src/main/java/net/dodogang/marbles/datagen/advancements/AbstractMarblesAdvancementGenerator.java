package net.dodogang.marbles.datagen.advancements;

import net.dodogang.marbles.Marbles;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.item.ItemConvertible;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractMarblesAdvancementGenerator implements Consumer<Consumer<Advancement>> {
    protected final String categoryId;

    private Consumer<Advancement> consumer;

    public AbstractMarblesAdvancementGenerator(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void accept(Consumer<Advancement> consumer) {
        this.consumer = consumer;
        this.createAdvancements();
    }

    protected abstract void createAdvancements();

    protected Advancement createRoot(ItemConvertible icon, Map<String, CriterionConditions> criteria) {
        String id = "root";

        Advancement.Task task = Advancement.Task.create()
                                                .display(
                                                    icon,
                                                    new TranslatableText(String.format("advancements.%s.%s.%s.title", Marbles.MOD_ID, this.categoryId, id)),
                                                    new TranslatableText(String.format("advancements.%s.%s.%s.description", Marbles.MOD_ID, this.categoryId, id)),
                                                    new Identifier(Marbles.MOD_ID, String.format("textures/gui/advancements/backgrounds/%s.png", this.categoryId)),
                                                    AdvancementFrame.TASK, false, false, false
                                                );

        criteria.forEach(task::criterion);
        return task.build(consumer, id(id));
    }

    protected Advancement create(String id, ItemConvertible icon, @Nullable Advancement parent, AdvancementFrame frame, boolean hidden, Map<String, CriterionConditions> criteria) {
        Advancement.Task task = Advancement.Task.create()
                                                .parent(parent)
                                                .display(
                                                    icon,
                                                    new TranslatableText(String.format("advancements.%s.%s.%s.title", Marbles.MOD_ID, this.categoryId, id)),
                                                    new TranslatableText(String.format("advancements.%s.%s.%s.description", Marbles.MOD_ID, this.categoryId, id)),
                                                    null,
                                                    frame, true, true, hidden
                                                );

        criteria.forEach(task::criterion);
        return task.build(consumer, id(id));
    }
    protected Advancement create(String id, ItemConvertible icon, Advancement parent, AdvancementFrame frame, Map<String, CriterionConditions> criteria) {
        return this.create(id, icon, parent, frame, false, criteria);
    }
    protected Advancement create(String id, ItemConvertible icon, Advancement parent, boolean hidden, Map<String, CriterionConditions> criteria) {
        return this.create(id, icon, parent, AdvancementFrame.TASK, hidden, criteria);
    }
    protected Advancement create(String id, ItemConvertible icon, Advancement parent, Map<String, CriterionConditions> criteria) {
        return this.create(id, icon, parent, AdvancementFrame.TASK, false, criteria);
    }

    protected String id(String id) {
        return new Identifier(Marbles.MOD_ID, this.categoryId + "/" + id).toString();
    }
}
