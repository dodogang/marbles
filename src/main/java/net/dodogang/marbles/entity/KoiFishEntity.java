package net.dodogang.marbles.entity;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.entity.enums.KoiSize;
import net.dodogang.marbles.entity.enums.KoiVariant;
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
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class KoiFishEntity extends SchoolingFishEntity {
    public static final TrackedData<KoiSize> SIZE = DataTracker.registerData(KoiFishEntity.class, MarblesTrackedDataHandlers.KOI_SIZE);

    public static final TrackedData<KoiVariant> VAR_BASE = DataTracker.registerData(KoiFishEntity.class, MarblesTrackedDataHandlers.KOI_VARIANT);
    public static final TrackedData<KoiVariant> VAR_SPOTS = DataTracker.registerData(KoiFishEntity.class, MarblesTrackedDataHandlers.KOI_VARIANT);
    public static final TrackedData<KoiVariant> VAR_FINS = DataTracker.registerData(KoiFishEntity.class, MarblesTrackedDataHandlers.KOI_VARIANT);

    public KoiFishEntity(EntityType<? extends SchoolingFishEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound nbt) {
        if (spawnReason == SpawnReason.NATURAL || spawnReason == SpawnReason.CHUNK_GENERATION || spawnReason == SpawnReason.SPAWN_EGG) {
            this.age = random.nextInt(this.getAgeToTransform(KoiSize.values().length - 1));
            this.setSize(this.getSizeForAge(this.age));

            KoiVariant[] variants = KoiVariant.values();
            Random random = world.getRandom();
            this.setVariantBase(variants[random.nextInt(variants.length)]);
            this.setVariantSpots(variants[random.nextInt(variants.length)]);
            this.setVariantFins(variants[random.nextInt(variants.length)]);

            // Marbles.log(this.getSizeForAge(this.age) + " " + " " + this.getAgeToTransform(this.getSize().ordinal()) + " " + this.age + " " + (this.getAgeToTransform(this.getSize().ordinal()) <= this.age));
        }

        return super.initialize(world, difficulty, spawnReason, entityData, nbt);
    }

    // ---

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SIZE, KoiSize.SMALL);

        this.dataTracker.startTracking(VAR_BASE, KoiVariant.WHITE);
        this.dataTracker.startTracking(VAR_SPOTS, KoiVariant.WHITE);
        this.dataTracker.startTracking(VAR_FINS, KoiVariant.WHITE);
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

    public KoiVariant getVariantBase() {
        return this.dataTracker.get(VAR_BASE);
    }
    public void setVariantBase(KoiVariant variant) {
        this.dataTracker.set(VAR_BASE, variant);
    }

    public KoiVariant getVariantSpots() {
        return this.dataTracker.get(VAR_SPOTS);
    }
    public void setVariantSpots(KoiVariant variant) {
        this.dataTracker.set(VAR_SPOTS, variant);
    }

    public KoiVariant getVariantFins() {
        return this.dataTracker.get(VAR_FINS);
    }
    public void setVariantFins(KoiVariant variant) {
        this.dataTracker.set(VAR_FINS, variant);
    }

    // ---

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        try {
            this.setSize(KoiSize.valueOf(nbt.getString("Size")));

            this.setVariantBase(KoiVariant.valueOf(nbt.getString("VariantBase")));
            this.setVariantSpots(KoiVariant.valueOf(nbt.getString("VariantSpots")));
            this.setVariantFins(KoiVariant.valueOf(nbt.getString("VariantFins")));
        } catch (Exception e) {
            logParseIssue(e);
        }

        this.age = nbt.getInt("Age");
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putString("Size", this.getSize().asString());

        nbt.putString("VariantBase", this.getVariantBase().asString());
        nbt.putString("VariantSpots", this.getVariantSpots().asString());
        nbt.putString("VariantFins", this.getVariantFins().asString());

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
        if (stack.getItem() instanceof SpawnEggItem spawnEgg && player.world instanceof ServerWorld serverWorld) {
            if (spawnEgg.isOfSameEntityType(stack.getOrCreateNbt(), this.getType()) && this.getSize() != KoiSize.SMALL) {
                this.world.spawnEntity(this.createChild(serverWorld, null));

                if (!player.isCreative()) {
                    stack.decrement(1);
                }

                return ActionResult.SUCCESS;
            }
        }

        return super.interactMob(player, hand);
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(MarblesItems.KOI_BUCKET);
    }

    @SuppressWarnings({ "ConstantConditions", "unused" })
    public KoiFishEntity createChild(ServerWorld world, @Nullable KoiFishEntity other) {
        KoiFishEntity fish = MarblesEntities.KOI.create(this.world);

        fish.setVariantBase(this.getVariantBase());
        fish.setVariantSpots(this.getVariantSpots());
        fish.setVariantFins(this.getVariantFins());

        fish.age = 0;
        fish.setSize(KoiSize.SMALL);

        fish.refreshPositionAndAngles(this.getBlockPos(), 0.0f, 0.0f);

        return fish;
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

        nbt.putString("VariantBase", this.getVariantBase().asString());
        nbt.putString("VariantSpots", this.getVariantSpots().asString());
        nbt.putString("VariantFins", this.getVariantFins().asString());

        nbt.putInt("Age", this.age);
        nbt.putLong("WorldTimeAtBucketEntry", this.world.getTime());
    }
    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        super.copyDataFromNbt(nbt);

        try {
            this.setSize(KoiSize.valueOf(nbt.getString("Size")));

            this.setVariantBase(KoiVariant.valueOf(nbt.getString("VariantBase")));
            this.setVariantSpots(KoiVariant.valueOf(nbt.getString("VariantSpots")));
            this.setVariantFins(KoiVariant.valueOf(nbt.getString("VariantFins")));
        } catch (Exception e) {
            logParseIssue(e);
        }

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

    // ---

    public static String getNameForVariantType(TrackedData<KoiVariant> variantType) {
        if (variantType == VAR_SPOTS) {
            return "spots";
        } else if (variantType == VAR_FINS) {
            return "fins";
        }

        return "";
    }
    public void logParseIssue(Throwable throwable) {
        Marbles.log(Level.ERROR, String.format("Error parsing koi entity - (%s) '%s' %s", this.getBlockPos().toShortString(), this.getUuidAsString(), throwable.getMessage()));
    }
}
