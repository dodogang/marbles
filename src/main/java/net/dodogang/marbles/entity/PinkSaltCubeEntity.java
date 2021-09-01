package net.dodogang.marbles.entity;

import net.dodogang.marbles.entity.ai.goal.SaltCubeSearchForPlayerGoal;
import net.dodogang.marbles.entity.ai.goal.SaltCubeSearchForTargetGoal;
import net.dodogang.marbles.init.MarblesSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.MobSpawnS2CPacket;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.*;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PinkSaltCubeEntity extends GolemEntity implements Monster {
    protected static final TrackedData<Direction> ATTACHED_FACE = DataTracker.registerData(PinkSaltCubeEntity.class, TrackedDataHandlerRegistry.FACING);

    public PinkSaltCubeEntity(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;

        this.moveControl = new CubeMoveControl(this);
        this.lookControl = new CubeLookControl(this);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound nbt) {
        this.setYaw(0.0F);
        this.headYaw = this.getYaw();
        this.resetPosition();

        return super.initialize(world, difficulty, spawnReason, entityData, nbt);
    }

    @Override
    protected void initGoals() {
        this.targetSelector.add(1, new RevengeGoal(this, this.getClass()).setGroupRevenge());
        this.targetSelector.add(2, new SaltCubeSearchForPlayerGoal(this));
        this.targetSelector.add(3, new SaltCubeSearchForTargetGoal(this));
    }

    public static DefaultAttributeContainer.Builder createPinkSaltCubeAttributes() {
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0d);
    }

    @Override
    protected BodyControl createBodyControl() {
        return new CubeBodyControl(this);
    }

    // ---

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MarblesSoundEvents.BLOCK_PINK_SALT_BREAK;
    }


    // ---

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACHED_FACE, Direction.NORTH);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setAttachedFace(Direction.byId(nbt.getByte("AttachFace")));

    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putByte("AttachFace", (byte) this.getAttachedFace().getId());
    }

    @Override
    public void readFromPacket(MobSpawnS2CPacket packet) {
        super.readFromPacket(packet);
        this.bodyYaw = 0;
    }

    // ---

    public Direction getAttachedFace() {
        return this.dataTracker.get(ATTACHED_FACE);
    }
    private void setAttachedFace(Direction face) {
        this.dataTracker.set(ATTACHED_FACE, face);
    }

    // ---

    @Override
    public boolean isCollidable() {
        return this.isAlive();
    }

    @Override
    protected Entity.MoveEffect getMoveEffect() {
        return Entity.MoveEffect.NONE;
    }

    // ---

    @Override
    protected void mobTick() {
        super.mobTick();
    }


    // ---

    @Override
    public Vec3d getVelocity() {
        return Vec3d.ZERO;
    }

    @Override
    public void setVelocity(Vec3d velocity) {}

    @Override
    public void setVelocityClient(double x, double y, double z) {
        this.setVelocity(x, y, z);
    }

    @Override
    public void setPosition(double x, double y, double z) {
        BlockPos initialPos = this.getBlockPos();

        if (this.hasVehicle()) {
            super.setPosition(x, y, z);
        } else {
            super.setPosition((double)MathHelper.floor(x) + 0.5D, MathHelper.floor(y + 0.5D), (double)MathHelper.floor(z) + 0.5D);
        }

        if (this.age != 0) {
            BlockPos pos = this.getBlockPos();
            if (!pos.equals(initialPos)) {
                this.velocityDirty = true;
                if (this.world.isClient && !this.hasVehicle()) {
                    this.lastRenderX = this.getX();
                    this.lastRenderY = this.getY();
                    this.lastRenderZ = this.getZ();
                }
            }

        }
    }

    @Override
    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
        this.bodyTrackingIncrements = 0;
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }

    @Override
    public float getTargetingMargin() {
        return 0.0F;
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
    public void animateDamage() {}

    @Override
    public void pushAwayFrom(Entity entity) {}

    // ---

    public static class CubeMoveControl extends MoveControl {
        public CubeMoveControl(MobEntity entity) {
            super(entity);
        }
    }

    public static class CubeLookControl extends LookControl {
        public CubeLookControl(MobEntity entity) {
            super(entity);
        }

        @Override
        protected void method_36980() {}

        @Override
        protected Optional<Float> getTargetYaw() {
            /*Direction dir = PinkSaltCubeEntity.this.getAttachedFace().getOpposite();

            Vec3f southVector = PinkSaltCubeEntity.SOUTH_VECTOR.copy();
            southVector.rotate(dir.getRotationQuaternion());

            Vec3i dirVec = dir.getVector();
            Vec3f vec3f2 = new Vec3f((float)dirVec.getX(), (float)dirVec.getY(), (float)dirVec.getZ());
            vec3f2.cross(southVector);

            double lookX = this.lookX - this.entity.getX();
            double lookY = this.lookY - this.entity.getEyeY();
            double lookZ = this.lookZ - this.entity.getZ();
            Vec3f look = new Vec3f((float)lookX, (float)lookY, (float)lookZ);

            float y = vec3f2.dot(look);
            float x = southVector.dot(look);
            return !(Math.abs(y) > 1.0E-5F) && !(Math.abs(x) > 1.0E-5F) ? Optional.empty() : Optional.of((float)(MathHelper.atan2(-y, x) * 57.2957763671875D));*/

            return Optional.of(0.0F);
        }

        @Override
        protected Optional<Float> getTargetPitch() {
            return Optional.of(0.0f);
        }
    }

    public static class CubeBodyControl extends BodyControl {
        public CubeBodyControl(MobEntity entity) {
            super(entity);
        }

        @Override
        public void tick() {}
    }
}
