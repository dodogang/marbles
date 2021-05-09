package net.dodogang.marbles.entity;

import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.init.MarblesEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class PollenGracedSheepEntity extends SheepEntity {
    public static final String id = "pollen_graced_sheep";

    public PollenGracedSheepEntity(EntityType<? extends SheepEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Identifier getLootTableId() {
        return this.getType().getLootTableId();
    }

    @Override
    public DyeColor getColor() {
        return DyeColor.YELLOW;
    }
    @Override
    public void setColor(DyeColor color) {}

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.remove("Color");
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        tag.remove("Color");
        super.readCustomDataFromTag(tag);
    }

    @Override
    public SheepEntity createChild(ServerWorld world, PassiveEntity other) {
        return MarblesEntities.POLLEN_GRACED_SHEEP.create(world);
    }

    @Override
    public void sheared(SoundCategory category) {
        this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, category, 1.0F, 1.0F);
        this.setSheared(true);
        int count = 1 + this.random.nextInt(3);

        for(int j = 0; j < count; ++j) {
            ItemEntity itemEntity = this.dropItem(MarblesBlocks.POLLEN_GRACED_WOOL, 1);
            if (itemEntity != null) {
                itemEntity.setVelocity(itemEntity.getVelocity().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
            }
        }
    }
}
