package net.dodogang.marbles.entity;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.entity.enums.KoiSize;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.init.MarblesEntities;
import net.dodogang.marbles.init.MarblesItems;
import net.dodogang.marbles.init.MarblesTrackedDataHandlers;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class KoiFishEntity extends SchoolingFishEntity {
    public static final TrackedData<KoiSize> SIZE = DataTracker.registerData(KoiFishEntity.class, MarblesTrackedDataHandlers.KOI_SIZE);

    public KoiFishEntity(EntityType<? extends SchoolingFishEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound nbt) {
        if (spawnReason == SpawnReason.NATURAL || spawnReason == SpawnReason.CHUNK_GENERATION || spawnReason == SpawnReason.SPAWN_EGG) {
            this.age = random.nextInt(this.getAgeToTransform(KoiSize.values().length - 1));
            this.setSize(this.getSizeForAge(this.age));

            // Marbles.log(this.getSizeForAge(this.age) + " " + " " + this.getAgeToTransform(this.getSize().ordinal()) + " " + this.age + " " + (this.getAgeToTransform(this.getSize().ordinal()) <= this.age));
        }

        return super.initialize(world, difficulty, spawnReason, entityData, nbt);
    }

    // ---

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SIZE, KoiSize.SMALL);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (SIZE.equals(data)) {
            this.calculateDimensions();
            this.setYaw(this.headYaw);
            this.bodyYaw = this.headYaw;
            if (this.isTouchingWater() && this.random.nextInt(20) == 0) {
                this.onSwimmingStart();
            }
        }

        super.onTrackedDataSet(data);
    }

    public KoiSize getSize() {
        return this.dataTracker.get(SIZE);
    }
    public void setSize(KoiSize size) {
        this.dataTracker.set(SIZE, size);

        this.refreshPosition();
        this.calculateDimensions();
    }

    // ---

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        try {
            this.setSize(KoiSize.valueOf(nbt.getString("Size")));
        } catch (Exception ignored) {}

        this.age = nbt.getInt("Age");
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putString("Size", this.getSize().asString());
        nbt.putInt("Age", this.age);
    }

    // ---

    @Override
    protected void mobTick() {
        if (this.world instanceof ServerWorld serverWorld) {
            int nextSize = this.getSize().ordinal() + 1;
            KoiSize[] koiSizes = KoiSize.values();
            if (nextSize < koiSizes.length) {
                int ageToTransform = this.getAgeToTransform(nextSize);
                if (this.age >= ageToTransform) {
                    serverWorld.spawnParticles(ParticleTypes.BUBBLE, this.getX(), this.getY(), this.getZ(), nextSize * 7, 0.2d, 0.2d, 0.2d, 1.0d);
                    this.setSize(koiSizes[nextSize]);
                }
            }

            /*if (this.getVelocity().length() > 0.02d) {
                serverWorld.spawnParticles(ParticleTypes.BUBBLE, this.getX(), this.getY() + (this.getDimensions(this.getPose()).height / 2), this.getZ(), 1, 0.0d, 0.0d, 0.0d, 0.0d);
            }*/
        }

        super.mobTick();
    }

    public int getAgeToTransform(int size) {
        return size * ((30 * 60) * 20);
    }
    public KoiSize getSizeForAge(int age) {
        try {
            return KoiSize.values()[(Math.round((float) age / ((30 * 60) * 20)))];
        } catch (ArrayIndexOutOfBoundsException ignored) {}

        return KoiSize.SMALL;
    }

    // ---

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() instanceof SpawnEggItem spawnEgg) {
            if (spawnEgg.isOfSameEntityType(stack.getOrCreateNbt(), this.getType())) {
                KoiFishEntity fish = MarblesEntities.KOI.create(this.world);
                if (fish != null) {
                    fish.refreshPositionAndAngles(this.getBlockPos(), 0.0f, 0.0f);
                    this.world.spawnEntity(fish);

                    if (!player.isCreative()) {
                        stack.decrement(1);
                    }

                    return ActionResult.SUCCESS;
                }
            }
        }

        return super.interactMob(player, hand);
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(MarblesItems.KOI_BUCKET);
    }

    // ---

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_TROPICAL_FISH_HURT;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }

    // ---

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return super.getDimensions(pose).scaled(1 + (this.getSize().ordinal() * 0.5f));
    }

    @Override
    public void calculateDimensions() {
        double x = MathHelper.floor(this.getX()) + 0.5d;
        double y = MathHelper.floor(this.getY());
        double z = MathHelper.floor(this.getZ()) + 0.5d;

        super.calculateDimensions();

        this.setPosition(x, y, z);
    }

    // ---

    @Override
    public void copyDataToStack(ItemStack stack) {
        super.copyDataToStack(stack);

        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putString("Size", this.getSize().asString());

        nbt.putInt("Age", this.age);
        nbt.putLong("WorldTimeAtBucketEntry", this.world.getTime());
    }
    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        super.copyDataFromNbt(nbt);

        try {
            this.setSize(KoiSize.valueOf(nbt.getString("Size")));
        } catch (Exception ignored) {}

        int ageToAdd = nbt.contains("WorldTimeAtBucketEntry")
            ? (int) (this.world.getTime() - nbt.getInt("WorldTimeAtBucketEntry"))
            : 0;
        this.age = nbt.getInt("Age") + ageToAdd;
    }

    // ---

    // = getMaxGroupSize
    @Override
    public int getLimitPerChunk() {
        return 12;
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        BlockState state = world.getBlockState(pos.down());
        return state.isOf(MarblesBlocks.ASPEN_SEAGRASS) || state.isOf(MarblesBlocks.TALL_ASPEN_SEAGRASS) ? 10.0F : world.getBrightness(pos) - 0.5F;
    }
}
