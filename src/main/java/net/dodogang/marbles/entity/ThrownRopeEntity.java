package net.dodogang.marbles.entity;

import net.dodogang.marbles.block.RopeBlock;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.init.MarblesEntities;
import net.dodogang.marbles.init.MarblesParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ThrownRopeEntity extends ThrownItemEntity {
    public static final String id = "thrown_rope";

    private static final TrackedData<Integer> COUNT = DataTracker.registerData(ThrownRopeEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @SuppressWarnings("unchecked")
    public ThrownRopeEntity(EntityType<? extends Entity> entityType, World world) {
        super((EntityType<? extends ThrownItemEntity>) entityType, world);
    }
    public ThrownRopeEntity(World world, LivingEntity owner) {
        super(MarblesEntities.THROWN_ROPE, owner, world);
    }
    public ThrownRopeEntity(World world, double x, double y, double z) {
        super(MarblesEntities.THROWN_ROPE, x, y, z, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COUNT, 1);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putInt("Count", this.getCount());
    }
    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.setCount(tag.getInt("Count"));
    }

    public int getCount() {
        return this.dataTracker.get(COUNT);
    }
    public void setCount(int count) {
        this.dataTracker.set(COUNT, count);
    }

    @Override
    protected Item getDefaultItem() {
        return MarblesBlocks.ROPE.asItem();
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack stack = this.getItem();
        return stack.isEmpty() ? MarblesParticles.ITEM_ROPE : new ItemStackParticleEffect(ParticleTypes.ITEM, stack);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();
            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @Override
    protected void onBlockHit(BlockHitResult hit) {
        super.onBlockHit(hit);
        this.dropRope(
            hit.isInsideBlock() || hit.getSide() != Direction.DOWN
                ? this.getCount()
                : this.deployRope(hit.getBlockPos().down())
        );
    }

    private int deployRope(BlockPos root) {
        int i = this.getCount();

        BlockPos.Mutable pos = root.mutableCopy();
        BlockPos.Mutable posDown = root.down().mutableCopy();
        while (this.world.getBlockState(posDown).getMaterial().isReplaceable() && i-- > 0) {
            this.world.setBlockState(pos, MarblesBlocks.ROPE.getDefaultState().with(RopeBlock.WATERLOGGED, this.world.getFluidState(pos).isIn(FluidTags.WATER)));

            pos.move(Direction.DOWN);
            posDown.move(Direction.DOWN);
        }

        this.playSound(SoundEvents.BLOCK_WOOL_PLACE, 1.0f, 1.0f);

        return i;
    }

    @Override
    protected void onEntityHit(EntityHitResult hit) {
        super.onEntityHit(hit);

        Entity entity = hit.getEntity();
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 0.0f);
        this.dropRope();
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte)3);
            this.remove();
        }
    }

    public void dropRope(int count) {
        Entity owner = this.getOwner();
        if (!(owner instanceof PlayerEntity) || !((PlayerEntity) owner).isCreative()) {
            this.dropStack(new ItemStack(this.getDefaultItem(), count));
        }
    }
    public void dropRope() {
        this.dropRope(this.getCount());
        this.remove();
    }
}
