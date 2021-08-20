package net.dodogang.marbles.datagen.loottables;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.init.MarblesEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.FurnaceSmeltLootFunction;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.entity.EntityFlagsPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class MarblesEntityLootTables implements Consumer<BiConsumer<Identifier, LootTable.Builder>> {
    private static final NbtCompound NOT_SHEARED_NBT = new NbtCompound();
    static {
        NOT_SHEARED_NBT.putBoolean("Sheared", false);
    }

    private static final EntityPredicate.Builder NEEDS_ENTITY_ON_FIRE = EntityPredicate.Builder.create().flags(EntityFlagsPredicate.Builder.create().onFire(true).build());
    private static final EntityPropertiesLootCondition.Builder NOT_SHEARED = EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().nbt(new NbtPredicate(NOT_SHEARED_NBT)));
    private static final Set<EntityType<?>> ENTITY_TYPES_IN_MISC_GROUP_TO_CHECK = ImmutableSet.of(EntityType.PLAYER, EntityType.ARMOR_STAND, EntityType.IRON_GOLEM, EntityType.SNOW_GOLEM, EntityType.VILLAGER);

    private final Map<Identifier, LootTable.Builder> lootTables = Maps.newHashMap();


    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> biConsumer) {
        register(MarblesEntities.BOUNCER, LootTable.builder());

        register(
            MarblesEntities.POLLEN_GRACED_SHEEP,

            LootTable.builder().pool(
                LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0F)).with(
                        ItemEntry.builder(Items.MUTTON)
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F)))
                            .apply(FurnaceSmeltLootFunction.builder().conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, NEEDS_ENTITY_ON_FIRE)))
                            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0F, 1.0F)))
                    ).rolls(ConstantLootNumberProvider.create(1.0F)).with(
                        ItemEntry.builder(MarblesBlocks.POLLEN_GRACED_WOOL).conditionally(NOT_SHEARED)
                    )
            )
        );

        register(MarblesEntities.KOI, LootTable.builder());

        /*
         * GENERATION
         */

        Set<Identifier> set = Sets.newHashSet();
        Iterator<EntityType<?>> entityTypes = Registry.ENTITY_TYPE.iterator();

        EntityType<?> entityType;
        Identifier identifier;
        label32:
        do {
            while(entityTypes.hasNext()) {
                entityType = entityTypes.next();
                identifier = entityType.getLootTableId();
                if (identifier.getNamespace().equals(Marbles.MOD_ID)) {
                    if (!ENTITY_TYPES_IN_MISC_GROUP_TO_CHECK.contains(entityType) && entityType.getSpawnGroup() == SpawnGroup.MISC) {
                        continue label32;
                    }

                    if (identifier != LootTables.EMPTY && set.add(identifier)) {
                        LootTable.Builder builder = this.lootTables.remove(identifier);
                        if (builder == null) {
                            throw new IllegalStateException(String.format("Missing loottable '%s' for '%s'", identifier, Registry.ENTITY_TYPE.getId(entityType)));
                        }

                        biConsumer.accept(identifier, builder);
                    }
                }
            }

            this.lootTables.forEach(biConsumer);
            return;
        } while(identifier == LootTables.EMPTY || this.lootTables.remove(identifier) == null);

        throw new IllegalStateException(String.format("Weird loottable '%s' for '%s', not a LivingEntity so should not have loot", identifier, Registry.ENTITY_TYPE.getId(entityType)));
    }

    private void register(EntityType<?> entityType, LootTable.Builder lootTable) {
        this.register(entityType.getLootTableId(), lootTable);
    }
    private void register(Identifier entityId, LootTable.Builder lootTable) {
        this.lootTables.put(entityId, lootTable);
    }
}
