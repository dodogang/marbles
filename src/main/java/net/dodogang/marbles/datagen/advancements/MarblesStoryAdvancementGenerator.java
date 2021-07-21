package net.dodogang.marbles.datagen.advancements;

/*import com.google.common.collect.ImmutableMap;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.item.MarblesItemGroup;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.predicate.entity.EntityPredicate;*/

public class MarblesStoryAdvancementGenerator extends AbstractMarblesAdvancementGenerator {
    public MarblesStoryAdvancementGenerator() {
        super("story");
    }

    @Override
    protected void createAdvancements() {
        // Advancement root = this.createRoot(MarblesItemGroup.ICON, ImmutableMap.of("base", new TickCriterion.Conditions(EntityPredicate.Extended.EMPTY)));
        // Advancement test = this.create("test", MarblesBlocks.YELLOW_SCAFFOLDING, root, ImmutableMap.of("scaffolding", InventoryChangedCriterion.Conditions.items(MarblesBlocks.YELLOW_SCAFFOLDING)));
    }
}
