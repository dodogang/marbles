package net.dodogang.marbles.entity;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.init.MarblesSoundEvents;
import net.dodogang.marbles.network.MarblesNetwork;
import net.dodogang.marbles.tag.MarblesEntityTypeTags;
import net.dodogang.marbles.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.Durations;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.IntRange;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BouncerEntity extends PathAwareEntity implements Angerable {
    public static final String id = "bouncer";

    public static final TrackedData<Integer> SIZE = DataTracker.registerData(BouncerEntity.class, TrackedDataHandlerRegistry.INTEGER);

    protected int cachedSize = 1;
    private int attackTicksLeft;
    private static final IntRange ANGER_TIME_RANGE = Durations.betweenSeconds(20, 39);
    private int angerTime;
    private UUID angryAt;

    public BouncerEntity(EntityType<? extends BouncerEntity> entityType, World world) {
        super(entityType, world);
        this.stepHeight = 1.0F;
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason reason, @Nullable EntityData data, @Nullable CompoundTag tag) {
        this.setSize((int) Util.getBiasedRandom(1.0d, 1.0d, 3.0d, world.getRandom()), true);
        return super.initialize(world, difficulty, reason, data, tag);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new BouncerSwimGoal(this));
        this.goalSelector.add(1, new BouncerMeleeAttackGoal(this, 1.0d, true));
        this.goalSelector.add(2, new WanderNearTargetGoal(this, 0.9d, 32.0F));
        this.goalSelector.add(4, new WanderAroundGoal(this, 0.6d));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));

        this.targetSelector.add(2, new RevengeGoal(this));
        this.targetSelector.add(3, new FollowTargetGoal<>(this, PlayerEntity.class, 10, true, false, entity -> this.shouldAngerAt(entity, false)));
        this.targetSelector.add(4, new FollowTargetGoal<>(this, MobEntity.class, 5, false, false, entity -> this.shouldAngerAt(entity, false)));
        this.targetSelector.add(5, new UniversalAngerGoal<>(this, false));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BouncerEntity.SIZE, 1);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (BouncerEntity.SIZE.equals(data)) {
            this.calculateDimensions();
            this.yaw = this.headYaw;
            this.bodyYaw = this.headYaw;
        }

        super.onTrackedDataSet(data);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        this.angerToTag(tag);

        tag.putInt("Size", this.getSize());
    }
    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.angerFromTag((ServerWorld)this.world, tag);

        int size = tag.getInt("Size");
        if (size < 0) {
            size = 0;
        }
        this.setSize(size, false);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if (this.attackTicksLeft > 0) {
            --this.attackTicksLeft;
        }

        if (Entity.squaredHorizontalLength(this.getVelocity()) > Double.MAX_VALUE && this.random.nextInt(5) == 0) {
            int x = MathHelper.floor(this.getX());
            int y = MathHelper.floor(this.getY() - 0.20000000298023224d);
            int z = MathHelper.floor(this.getZ());

            BlockState state = this.world.getBlockState(new BlockPos(x, y, z));
            if (!state.isAir()) {
                this.world.addParticle(
                    new BlockStateParticleEffect(ParticleTypes.BLOCK, state),
                    this.getX() + ((double)this.random.nextFloat() - 0.5d) * (double)this.getWidth(),
                    this.getY() + 0.1d,
                    this.getZ() + ((double)this.random.nextFloat() - 0.5d) * (double)this.getWidth(),
                    4.0d * ((double)this.random.nextFloat() - 0.5d),
                    0.5d,
                    ((double)this.random.nextFloat() - 0.5d) * 4.0d
                );
            }
        }

        if (!this.world.isClient) {
            this.tickAngerLogic((ServerWorld)this.world, true);
        }
    }

    public int getSize() {
        return this.dataTracker.get(SIZE);
    }
    @SuppressWarnings("ConstantConditions")
    protected void setSize(int size, boolean heal) {
        size = Math.max(1, size);

        this.dataTracker.set(SIZE, size);
        this.cachedSize = size;

        this.refreshPosition();
        this.calculateDimensions();

        this.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE).setBaseValue(24.0d);
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(10.0d + ((size * size) * 3.0f));
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(Math.min(0.45f, (2.1f / size) * 0.36f));
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(4.0f + (size * 1.3f));

        if (heal) {
            this.setHealth(this.getMaxHealth());
        }

        this.experiencePoints = size * 4;
    }

    @Override
    public boolean shouldAngerAt(LivingEntity entity) {
        return !(entity instanceof Monster)
            && !(entity instanceof TameableEntity)
            && !(entity instanceof WaterCreatureEntity)
            && entity.getType() != this.getType()
            && !MarblesEntityTypeTags.BOUNCER_IGNORED_ENTITIES.contains(entity.getType())
            && EntityPredicates.EXCEPT_CREATIVE_SPECTATOR_OR_PEACEFUL.test(entity);
    }
    public boolean shouldAngerAt(LivingEntity entity, boolean bypassRandom) {
        return (bypassRandom || this.random.nextFloat() <= (1.0f / this.getSize()) / 10) && this.shouldAngerAt(entity);
    }

    @Override
    protected void pushAway(Entity entity) {
        if (entity instanceof LivingEntity && (this.getAngryAt() == null || entity instanceof PlayerEntity) && this.shouldAngerAt((LivingEntity) entity, true)) {
            this.setTarget((LivingEntity)entity);
        }

        super.pushAway(entity);
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        int size = this.getSize();
        return super.getDimensions(pose).scaled(size * 0.57f, 0.35f + (size * 0.3f));
    }

    @Override
    public void calculateDimensions() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        super.calculateDimensions();
        this.updatePosition(x, y, z);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }

    @Override
    protected int getNextAirUnderwater(int air) {
        return air;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.choose(this.random));
    }

    @Override
    public int getAngerTime() {
        return this.angerTime;
    }
    @Override
    public void setAngerTime(int ticks) {
        this.angerTime = ticks;
    }

    @Override
    public UUID getAngryAt() {
        return this.angryAt;
    }
    @Override
    public void setAngryAt(@Nullable UUID uuid) {
        this.angryAt = uuid;
    }

    public float getAttackDamage() {
        return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (!(target instanceof PlayerEntity) || target.getVelocity().getY() >= 0.1d) {
            this.attackTicksLeft = 10;
            this.world.sendEntityStatus(this, (byte)4);

            this.tryThrowEntity(target, false);
        }

        return false;
    }
    public void tryAttackPlayerHasShield(PlayerEntity target) {
        target.disableShield(true);
        this.tryThrowEntity(target, true);

        if (!target.world.isClient && target instanceof ServerPlayerEntity) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(this.getEntityId());
            buf.writeUuid(target.getUuid());

            ServerPlayNetworking.send((ServerPlayerEntity) target, MarblesNetwork.BOUNCER_HIT_PLAYER_SHIELD_PACKET_ID, buf);
        }
    }

    public void tryThrowEntity(Entity target, boolean fromShield) {
        float attackDamage = this.getAttackDamage();
        float damage = attackDamage > 0 ? attackDamage / 6.0F + (float)this.random.nextInt((int) (attackDamage / 1.3f)) : attackDamage;

        boolean damageTarget = fromShield || target.damage(new EntityDamageSource(Marbles.MOD_ID + "." + BouncerEntity.id, this), target instanceof PlayerEntity ? damage / 2.3f : 0.1f);
        if (damageTarget) {
            int size = this.getSize();

            target.setVelocity(target.getVelocity().add(0.0d, 0.1d + ((damage * damage) * 0.02d), 0.0d));

            StatusEffectInstance slowFalling = new StatusEffectInstance(StatusEffects.SLOW_FALLING, (int) ((size * (target instanceof PlayerEntity ? 0.4f : size)) * 20), size);
            ((LivingEntity) target).addStatusEffect(slowFalling);

            this.dealDamage(this, target);
            this.playThrowSound();
        }
    }
    public void playThrowSound() {
        this.playSound(MarblesSoundEvents.ENTITY_BOUNCER_THROW, 1.0F, this.getPitch(1.0f));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 4) {
            this.attackTicksLeft = 10;
            this.playThrowSound();
        }

        super.handleStatus(status);
    }

    @Environment(EnvType.CLIENT)
    public int getAttackTicksLeft() {
        return this.attackTicksLeft;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MarblesSoundEvents.ENTITY_BOUNCER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MarblesSoundEvents.ENTITY_BOUNCER_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        UUID angryAt = this.getAngryAt();
        if (angryAt != null) {
            PlayerEntity player = this.world.getPlayerByUuid(angryAt);
            if (player != null && this.squaredDistanceTo(player) <= 12.0d && player.isOnGround()) {
                return MarblesSoundEvents.ENTITY_BOUNCER_WAITING;
            }
        }

        return MarblesSoundEvents.ENTITY_BOUNCER_AMBIENT;
    }

    @Override
    public float getPitch(float tickDelta) {
        return 1.6f / this.cachedSize;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(MarblesSoundEvents.ENTITY_BOUNCER_STEP, 0.2f, this.getPitch(1.0f));
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
    }

    @Override
    public boolean canSpawn(WorldView world) {
        BlockPos pos = this.getBlockPos();
        BlockPos posDown = pos.down();
        BlockState stateDown = world.getBlockState(posDown);
        if (!stateDown.hasSolidTopSurface(world, posDown, this)) {
            return false;
        } else {
            for(int i = 1; i < 3; ++i) {
                BlockPos iPos = pos.up(i);
                BlockState iState = world.getBlockState(iPos);
                if (!SpawnHelper.isClearForSpawn(world, iPos, iState, iState.getFluidState(), EntityType.IRON_GOLEM)) {
                    return false;
                }
            }

            return SpawnHelper.isClearForSpawn(world, pos, world.getBlockState(pos), Fluids.EMPTY.getDefaultState(), EntityType.IRON_GOLEM) && world.intersectsEntities(this);
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Vec3d method_29919() {
        return new Vec3d(0.0d, 0.875F * this.getStandingEyeHeight(), this.getWidth() * 0.4F);
    }

    public static class BouncerSwimGoal extends SwimGoal {
        private final BouncerEntity mob;

        public BouncerSwimGoal(BouncerEntity mob) {
            super(mob);
            this.mob = mob;
        }

        @Override
        public boolean canStart() {
            return this.mob.getSize() <= 1 && super.canStart();
        }
    }
    public static class BouncerMeleeAttackGoal extends MeleeAttackGoal {
        public BouncerMeleeAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
        }

        @Override
        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            double distance = this.mob.getWidth() * 1.5f;
            return Math.max(Math.min(24.0d, distance * distance), 4.0f);
        }

        @Override
        protected void method_28346() {
            this.field_24667 = 5;
        }
    }
}
