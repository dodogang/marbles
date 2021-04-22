package net.dodogang.marbles.entity;

import net.dodogang.marbles.init.MarblesSoundEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class PinkSaltCubeEntity extends GolemEntity implements Monster {
    public static final String id = "pink_salt_cube";

    public static final int MAX_ROLL_PROGRESS = 3;

    protected static final TrackedData<Integer> ROLL_PROGRESS = DataTracker.registerData(PinkSaltCubeEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Direction> ROLL_DIRECTION = DataTracker.registerData(PinkSaltCubeEntity.class, TrackedDataHandlerRegistry.FACING);
    protected static final TrackedData<Direction> ATTACHED_FACE = DataTracker.registerData(PinkSaltCubeEntity.class, TrackedDataHandlerRegistry.FACING);
    protected static final TrackedData<Optional<BlockPos>> ATTACHED_POS = DataTracker.registerData(PinkSaltCubeEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS);
    private BlockPos prevAttachedPos = null;

    public PinkSaltCubeEntity(EntityType<? extends PinkSaltCubeEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason reason, @Nullable EntityData data, @Nullable CompoundTag tag) {
        if (tag != null && !tag.contains("RollDirection")) {
            Direction[] directions = Direction.values();
            this.setRollDirection(Direction.byId(directions[world.getRandom().nextInt(directions.length - 1)].getHorizontal()));
        }

        return super.initialize(world, difficulty, reason, data, tag);
    }

    @Override
    protected void initGoals() {
        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(2, new PinkSaltCubeEntity.SearchForPlayerGoal(this));
        this.targetSelector.add(3, new PinkSaltCubeEntity.SearchForTargetGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ROLL_PROGRESS, 0);
        this.dataTracker.startTracking(ROLL_DIRECTION, Direction.NORTH);
        this.dataTracker.startTracking(ATTACHED_FACE, Direction.DOWN);
        this.dataTracker.startTracking(ATTACHED_POS, Optional.empty());
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (ATTACHED_POS.equals(data) && this.world.isClient && !this.hasVehicle()) {
            BlockPos blockPos = this.getAttachedPos();
            if (blockPos != null) {
                if (this.prevAttachedPos == null) {
                    this.prevAttachedPos = blockPos;
                }

                this.resetPosition((double)blockPos.getX() + 0.5D, blockPos.getY(), (double)blockPos.getZ() + 0.5D);
            }
        }

        super.onTrackedDataSet(data);
    }

    @Override
    protected boolean canClimb() {
        return false;
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);

        this.dataTracker.set(ROLL_PROGRESS, tag.getInt("RollProgress"));
        this.dataTracker.set(ROLL_DIRECTION, Direction.valueOf(tag.getString("RollDirection")));
        this.dataTracker.set(ATTACHED_FACE, Direction.valueOf(tag.getString("AttachedFace")));

        if (tag.contains("AttachedPos")) {
            this.dataTracker.set(ATTACHED_POS, Optional.of(NbtHelper.toBlockPos(tag.getCompound("AttachedPos"))));
        } else {
            this.dataTracker.set(ATTACHED_POS, Optional.empty());
        }
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);

        tag.putInt("RollProgress", this.dataTracker.get(ROLL_PROGRESS));
        tag.putString("RollDirection", this.dataTracker.get(ROLL_DIRECTION).toString().toUpperCase(Locale.ROOT));
        tag.putString("AttachedFace", this.dataTracker.get(ATTACHED_FACE).toString().toUpperCase(Locale.ROOT));

        BlockPos attachedPos = this.getAttachedPos();
        if (attachedPos != null) {
            tag.put("AttachedPos", NbtHelper.fromBlockPos(attachedPos));
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.canRoll() && this.isRolling()) {
            int rollProgress = this.getRollProgress();

            if (rollProgress >= PinkSaltCubeEntity.MAX_ROLL_PROGRESS) {
                this.roll();
            } else {
                this.setRollProgress(rollProgress + 1);
            }
        } else {
            this.setRollProgress(0);
        }

        this.yaw = 90 * Math.round(this.yaw / 90);

        BlockPos attachedPos = this.dataTracker.get(ATTACHED_POS).orElse(null);
        if (attachedPos == null && !this.world.isClient) {
            attachedPos = this.getBlockPos();
            this.dataTracker.set(ATTACHED_POS, Optional.of(attachedPos));
        }

        float yaw;
        if (this.hasVehicle()) {
            attachedPos = null;
            Entity vehicle = this.getVehicle();
            if (vehicle != null) {
                yaw = this.getVehicle().yaw;
                this.yaw = yaw;
                this.bodyYaw = yaw;
                this.prevBodyYaw = yaw;
            }
        } else if (!this.world.isClient) {
            if (attachedPos != null) {
                BlockState attachedState = this.world.getBlockState(attachedPos);
                Direction face;
                if (!attachedState.isAir() && !attachedState.getCollisionShape(this.world, attachedPos).isEmpty()) {
                    if (attachedState.isOf(Blocks.MOVING_PISTON)) {
                        face = attachedState.get(PistonBlock.FACING);
                        if (this.world.isAir(attachedPos.offset(face))) {
                            attachedPos = attachedPos.offset(face);
                            this.dataTracker.set(ATTACHED_POS, Optional.of(attachedPos));
                        } else {
                            this.tryStartRoll();
                        }
                    } else if (attachedState.isOf(Blocks.PISTON_HEAD)) {
                        face = attachedState.get(PistonHeadBlock.FACING);
                        if (this.world.isAir(attachedPos.offset(face))) {
                            attachedPos = attachedPos.offset(face);
                            this.dataTracker.set(ATTACHED_POS, Optional.of(attachedPos));
                        } else {
                            this.tryStartRoll();
                        }
                    } else {
                        this.tryStartRoll();
                    }
                }

                face = this.getAttachedFace();
                if (!this.canStay(attachedPos, face)) {
                    Direction direction4 = this.findAttachedFace(attachedPos);
                    if (direction4 != null) {
                        this.dataTracker.set(ATTACHED_FACE, direction4);
                    } else {
                        this.tryStartRoll();
                    }
                }
            }
        }

        if (attachedPos != null) {
            if (this.world.isClient) {
                if (this.prevAttachedPos != null) {
                    this.prevAttachedPos = attachedPos;
                }
            }

            this.resetPosition((double)attachedPos.getX() + 0.5D, attachedPos.getY(), (double)attachedPos.getZ() + 0.5D);
            double offset = 0.5D - (double)MathHelper.sin((0.5F) * 3.1415927F) * 0.5D;
            double offsetDelta = 0.5D - (double)MathHelper.sin((0.5F) * 3.1415927F) * 0.5D;
            Direction oppAttachedFace = this.getAttachedFace().getOpposite();
            this.setBoundingBox(
                new Box(
                    this.getX() - 0.5D, this.getY(), this.getZ() - 0.5D,
                    this.getX() + 0.5D, this.getY() + 1.0D, this.getZ() + 0.5D
                ).stretch((double)oppAttachedFace.getOffsetX() * offset, (double)oppAttachedFace.getOffsetY() * offset, (double)oppAttachedFace.getOffsetZ() * offset)
            );
            double offsetDiff = offset - offsetDelta;
            if (offsetDiff > 0.0D) {
                List<Entity> list = this.world.getOtherEntities(this, this.getBoundingBox());
                if (!list.isEmpty()) {
                    for (Entity entity : list) {
                        if (!(entity instanceof PinkSaltCubeEntity) && !entity.noClip) {
                            entity.move(MovementType.SHULKER, new Vec3d(offsetDiff * (double) oppAttachedFace.getOffsetX(), offsetDiff * (double) oppAttachedFace.getOffsetY(), offsetDiff * (double) oppAttachedFace.getOffsetZ()));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        this.setVelocity(Vec3d.ZERO);
        if (!this.isAiDisabled()) {
            this.prevBodyYaw = 0.0F;
            this.bodyYaw = 0.0F;
        }
    }

    public void roll() {
        this.setRollProgress(0);

        BlockPos pos = this.getBlockPos().offset(this.getRollDirection());
        this.setPos(pos.getX() + 0.5d, pos.getY(), pos.getZ() + 0.5d);

        this.dataTracker.set(ATTACHED_FACE, this.findAttachedFace(pos));
        this.dataTracker.set(ATTACHED_POS, Optional.of(pos));
        this.playSound(SoundEvents.ENTITY_SHULKER_TELEPORT, 1.0F, 1.0F);
        this.setTarget(null);
    }
    public void tryStartRoll() {
        if (this.canRoll() && !this.isRolling()) {
            this.setRollProgress(1);
        }
    }
    public boolean canRoll() {
        if (!this.isAiDisabled() && this.isAlive()) {
            BlockPos pos = this.getBlockPos();
            BlockPos rollPos = pos.offset(this.getRollDirection());
            if (rollPos.getY() > 0
                && ( this.world.isAir(rollPos)
                  || this.world.getBlockState(rollPos).getCollisionShape(this.world, rollPos).isEmpty() )
                && this.world.getWorldBorder().contains(rollPos)
                && this.world.isSpaceEmpty(this, new Box(rollPos))
            ) {
                Direction rollDirection = this.getRollDirection();
                return this.canStay(pos, rollDirection) && this.findAttachedFace(pos.offset(rollDirection)) != null;
            }
        }

        return false;
    }
    private boolean canStay(BlockPos pos, Direction direction) {
        return this.world.isSpaceEmpty(this, this.getBoundingBox());
    }

    @Override
    public void move(MovementType type, Vec3d movement) {
        if (type == MovementType.SHULKER_BOX) {
            this.tryStartRoll();
        } else {
            super.move(type, movement);
        }

    }

    @Override
    public void updatePosition(double x, double y, double z) {
        super.updatePosition(x, y, z);

        if (this.dataTracker != null && this.age != 0) {
            Optional<BlockPos> attachedPos = this.dataTracker.get(ATTACHED_POS);
            Optional<BlockPos> updatedPos = Optional.of(new BlockPos(x, y, z));
            if (!updatedPos.equals(attachedPos)) {
                this.dataTracker.set(ATTACHED_POS, updatedPos);
                this.velocityDirty = true;
            }
        }
    }

    @Nullable
    protected Direction findAttachedFace(BlockPos pos) {
        Direction[] directions = Direction.values();
        for (Direction direction : directions) {
            if (this.canStay(pos, direction)) {
                return direction;
            }
        }

        return null;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
        this.bodyTrackingIncrements = 0;
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return MarblesSoundEvents.BLOCK_PINK_SALT_STEP;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return MarblesSoundEvents.BLOCK_PINK_SALT_BREAK;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MarblesSoundEvents.BLOCK_PINK_SALT_HIT;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        Entity entity = source.getSource();
        if (!(entity instanceof PersistentProjectileEntity)) {
            if (super.damage(source, amount)) {
                if (/*(double) this.getHealth() < (double) this.getMaxHealth() * 0.5D && this.random.nextInt(4) == 0*/ true) {
                    this.tryStartRoll();
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isCollidable() {
        return this.isAlive();
    }

    public Direction getRollDirection() {
        return this.dataTracker.get(ROLL_DIRECTION);
    }
    public void setRollDirection(Direction rollDirection) {
        this.dataTracker.set(ROLL_DIRECTION, rollDirection);
    }

    public Direction getAttachedFace() {
        return this.dataTracker.get(ATTACHED_FACE);
    }

    @Nullable
    public BlockPos getAttachedPos() {
        return this.dataTracker.get(ATTACHED_POS).orElse(null);
    }
    public void setAttachedPos(@Nullable BlockPos pos) {
        this.dataTracker.set(ATTACHED_POS, Optional.ofNullable(pos));
    }

    public void setRollProgress(int rollProgress) {
        this.dataTracker.set(ROLL_PROGRESS, rollProgress);
    }
    public int getRollProgress() {
        return this.dataTracker.get(ROLL_PROGRESS);
    }
    public boolean isRolling() {
        return this.getRollProgress() > 0;
    }

    @Environment(EnvType.CLIENT)
    public BlockPos getPrevAttachedPos() {
        return this.prevAttachedPos;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.5F;
    }

    @Override
    public int getLookPitchSpeed() {
        return 180;
    }

    @Override
    public int getBodyYawSpeed() {
        return 180;
    }

    @Override
    public void pushAwayFrom(Entity entity) {}

    @Override
    public float getTargetingMargin() {
        return 0.0F;
    }

    static class SearchForTargetGoal extends FollowTargetGoal<LivingEntity> {
        public SearchForTargetGoal(PinkSaltCubeEntity saltCube) {
            super(saltCube, LivingEntity.class, 10, true, false, (entity) -> entity instanceof Monster);
        }

        @Override
        public boolean canStart() {
            return this.mob.getScoreboardTeam() != null && super.canStart();
        }

        @Override
        protected Box getSearchBox(double distance) {
            Direction direction = ((PinkSaltCubeEntity)this.mob).getAttachedFace();
            if (direction.getAxis() == Direction.Axis.X) {
                return this.mob.getBoundingBox().expand(4.0D, distance, distance);
            } else {
                return direction.getAxis() == Direction.Axis.Z ? this.mob.getBoundingBox().expand(distance, distance, 4.0D) : this.mob.getBoundingBox().expand(distance, 4.0D, distance);
            }
        }
    }

    class SearchForPlayerGoal extends FollowTargetGoal<PlayerEntity> {
        public SearchForPlayerGoal(PinkSaltCubeEntity saltCube) {
            super(saltCube, PlayerEntity.class, true);
        }

        @Override
        public boolean canStart() {
            return PinkSaltCubeEntity.this.world.getDifficulty() != Difficulty.PEACEFUL && super.canStart();
        }

        @Override
        protected Box getSearchBox(double distance) {
            Direction direction = ((PinkSaltCubeEntity)this.mob).getAttachedFace();
            if (direction.getAxis() == Direction.Axis.X) {
                return this.mob.getBoundingBox().expand(4.0D, distance, distance);
            } else {
                return direction.getAxis() == Direction.Axis.Z ? this.mob.getBoundingBox().expand(distance, distance, 4.0D) : this.mob.getBoundingBox().expand(distance, 4.0D, distance);
            }
        }
    }
}
