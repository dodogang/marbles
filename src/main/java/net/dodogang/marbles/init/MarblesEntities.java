package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.MarblesClient;
import net.dodogang.marbles.entity.BouncerEntity;
import net.dodogang.marbles.entity.KoiFishEntity;
import net.dodogang.marbles.entity.PollenGracedSheepEntity;
import net.dodogang.marbles.entity.ThrownRopeEntity;
import net.dodogang.marbles.item.MarblesItemGroup;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class MarblesEntities {
    public static final EntityType<BouncerEntity> BOUNCER = register(
        "bouncer",
        FabricEntityTypeBuilder.createMob()
            .entityFactory(BouncerEntity::new)
            .defaultAttributes(() -> MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.8D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0D)
            )
            .dimensions(EntityDimensions.changing(1.5F, 3.7F))
            .spawnableFarFromPlayer()
            .trackRangeBlocks(10)
            .spawnGroup(SpawnGroup.CREATURE)
        , new Pair<>(0x555C50, 0x967543)
    );

    public static final EntityType<PollenGracedSheepEntity> POLLEN_GRACED_SHEEP = register(
        "pollen_graced_sheep",
        FabricEntityTypeBuilder.createMob()
            .entityFactory(PollenGracedSheepEntity::new)
            .defaultAttributes(SheepEntity::createSheepAttributes)
            .dimensions(EntityDimensions.changing(0.9F, 1.3F))
            .trackRangeBlocks(10)
            .spawnGroup(SpawnGroup.CREATURE)
        , new Pair<>(0xFFFBF0, 0xEACA15)
    );

    public static final EntityType<KoiFishEntity> KOI = register(
        "koi",
        FabricEntityTypeBuilder.createMob()
            .entityFactory(KoiFishEntity::new)
            .spawnGroup(SpawnGroup.WATER_AMBIENT)
            .defaultAttributes(KoiFishEntity::createFishAttributes)
            .dimensions(EntityDimensions.changing(0.5F, 0.4F))
            .trackRangeChunks(4)
        , new Pair<>(0xFFFBF0, 0xB9CACF)
    );

    public static final EntityType<ThrownRopeEntity> THROWN_ROPE = register(
        "thrown_rope",
        FabricEntityTypeBuilder.<ThrownRopeEntity>create(SpawnGroup.MISC, ThrownRopeEntity::new)
            .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
            .trackRangeBlocks(4).trackedUpdateRate(10)
        , null
    );

    @SuppressWarnings("unchecked")
    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> entityType, Pair<Integer, Integer> spawnEggColors) {
        EntityType<T> builtEntityType = entityType.build();

        if (spawnEggColors != null) {
            Registry.register(
                Registry.ITEM, new Identifier(Marbles.MOD_ID, id + "_spawn_egg"),
                new SpawnEggItem(
                    (EntityType<? extends MobEntity>) builtEntityType,
                    spawnEggColors.getLeft(), spawnEggColors.getRight(),
                    new FabricItemSettings().maxCount(64).group(MarblesItemGroup.INSTANCE)
                )
            );
        }

        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Marbles.MOD_ID, id), builtEntityType);
    }

    @Environment(EnvType.CLIENT)
    public static Identifier texture(String path) {
        return MarblesClient.texture("entity/" + path);
    }
}
