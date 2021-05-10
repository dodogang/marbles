package net.dodogang.marbles.entity;

import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.init.MarblesEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class PollenGracedSheepEntity extends SheepEntity {
    public static final String id = "pollen_graced_sheep";

    public PollenGracedSheepEntity(EntityType<? extends SheepEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.eatGrassGoal = new EatGrispGrassGoal(this);

        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25D));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(3, new TemptGoal(this, 1.1D, Ingredient.ofItems(Items.WHEAT), false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.add(5, this.eatGrassGoal);
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
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

    static class EatGrispGrassGoal extends EatGrassGoal {
        private static final Predicate<BlockState> GRASS_PREDICATE = state -> state.isOf(Blocks.GRASS);
        private static final Predicate<BlockState> GRASS_BLOCK_PREDICATE = state -> state.isOf(Blocks.GRASS_BLOCK) || state.isOf(MarblesBlocks.GRISP_GRASS_BLOCK) || state.isOf(MarblesBlocks.GRISP_PODZOL);

        private final MobEntity mob;
        private final World world;

        public EatGrispGrassGoal(MobEntity mob) {
            super(mob);
            this.mob = mob;
            this.world = mob.world;
        }

        @Override
        public boolean canStart() {
            if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 50 : 1000) != 0) {
                return false;
            } else {
                return GRASS_BLOCK_PREDICATE.test(this.world.getBlockState(this.mob.getBlockPos().down()));
            }
        }

        @Override
        public void tick() {
            this.timer = Math.max(0, this.timer - 1);
            if (this.timer == 4) {
                BlockPos pos = this.mob.getBlockPos();
                if (GRASS_PREDICATE.test(this.world.getBlockState(pos))) {
                    if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                        this.world.breakBlock(pos, false);
                    }

                    this.mob.onEatingGrass();
                } else {
                    BlockPos posDown = pos.down();
                    BlockState state = this.world.getBlockState(posDown);
                    if (GRASS_BLOCK_PREDICATE.test(state)) {
                        if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                            Block dirt = this.world.getBlockState(posDown).isOf(Blocks.GRASS_BLOCK)
                                ? Blocks.DIRT
                                : MarblesBlocks.GRISP_DIRT;

                            this.world.syncWorldEvent(2001, posDown, Block.getRawIdFromState(state));
                            this.world.setBlockState(posDown, dirt.getDefaultState(), 2);
                        }

                        this.mob.onEatingGrass();
                    }
                }
            }
        }
    }
}
