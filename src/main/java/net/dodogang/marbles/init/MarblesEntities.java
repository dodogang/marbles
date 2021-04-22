package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.MarblesClient;
import net.dodogang.marbles.entity.BouncerEntity;
import net.dodogang.marbles.entity.PinkSaltCubeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class MarblesEntities {
    public static final EntityType<BouncerEntity> BOUNCER = register(
        BouncerEntity.id,
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
        , createSpawnEggColors(0x555c50, 0x967543)
    );
    public static final EntityType<PinkSaltCubeEntity> PINK_SALT_CUBE = register(
        PinkSaltCubeEntity.id,
        FabricEntityTypeBuilder.createMob()
            .entityFactory(PinkSaltCubeEntity::new)
            .defaultAttributes(() -> MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0d)
                .add(EntityAttributes.GENERIC_ARMOR, 5.0d)
            )
            .dimensions(EntityDimensions.fixed(1.0f, 1.0f))
            .spawnGroup(SpawnGroup.MONSTER)
            .trackRangeBlocks(10)
            .fireImmune()
        , createSpawnEggColors(0x555c50, 0x967543)
    );

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> entityType, int[] spawnEggColors) {
        EntityType<T> builtEntityType = entityType.build();

        if (spawnEggColors != null) {
            Registry.register(Registry.ITEM, new Identifier(Marbles.MOD_ID, id + "_spawn_egg"), new SpawnEggItem(builtEntityType, spawnEggColors[0], spawnEggColors[1], new Item.Settings().maxCount(64).group(Marbles.ITEM_GROUP)));
        }

        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Marbles.MOD_ID, id), builtEntityType);
    }

    @Environment(EnvType.CLIENT)
    public static Identifier texture(String path) {
        return MarblesClient.texture("entity/" + path);
    }
    protected static int[] createSpawnEggColors(int primary, int secondary) {
        return new int[]{ primary, secondary };
    }
}
