package net.dodogang.marbles.mixin.hooks;

import net.dodogang.marbles.block.RopeBlock;
import net.dodogang.marbles.entity.ThrownRopeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings({ "EmptyMethod", "UnusedReturnValue", "unused" })
@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
    @Shadow private static void putProjectile(ItemStack crossbow, ItemStack projectile) {}
    @Shadow
    private static boolean loadProjectile(LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean simulated, boolean creative) {
        return false;
    }
    @Shadow
    public static List<ItemStack> getProjectiles(ItemStack crossbow) {
        return new ArrayList<>();
    }
    @Shadow
    public static boolean isCharged(ItemStack stack) {
        return false;
    }

    @Environment(EnvType.CLIENT)
    @Inject(method = "appendTooltip", at = @At("HEAD"), cancellable = true)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        List<ItemStack> list = getProjectiles(stack);
        if (isCharged(stack) && !list.isEmpty()) {
            ItemStack projectile = list.get(0);
            Item item = projectile.getItem();
            if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof RopeBlock) {
                item.appendTooltip(projectile, world, tooltip, context);
                tooltip.add(new TranslatableText(item.getTranslationKey() + ".crossbow", projectile.getCount()).formatted(Formatting.GRAY));

                ci.cancel();
            }
        }
    }

    @Inject(method = "shoot", at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;isClient:Z", shift = At.Shift.AFTER), cancellable = true)
    private static void shootRope(World world, LivingEntity shooter, Hand hand, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated, CallbackInfo ci) {
        Item item = projectile.getItem();
        if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof RopeBlock) {
            ThrownRopeEntity thrown = new ThrownRopeEntity(world, shooter);
            thrown.setCount(projectile.getCount());

            if (shooter instanceof CrossbowUser) {
                CrossbowUser crossbowUser = (CrossbowUser)shooter;
                crossbowUser.shoot(crossbowUser.getTarget(), crossbow, thrown, simulated);
            } else {
                Vec3d oppositeRotationVec = shooter.getOppositeRotationVector(1.0F);
                Quaternion oppositeRotation = new Quaternion(new Vector3f(oppositeRotationVec), simulated, true);
                Vec3d rotationVec = shooter.getRotationVec(1.0F);
                Vector3f rotation = new Vector3f(rotationVec);
                rotation.rotate(oppositeRotation);
                thrown.setVelocity(rotation.getX(), rotation.getY(), rotation.getZ(), speed, divergence);
            }

            crossbow.damage(1, shooter, (entity) -> entity.sendToolBreakStatus(hand));

            world.spawnEntity(thrown);
            world.playSoundFromEntity(null, shooter, SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, soundPitch);

            ci.cancel();
        }
    }

    @Inject(method = "getHeldProjectiles", at = @At("RETURN"), cancellable = true)
    public void getRopeProjectiles(CallbackInfoReturnable<Predicate<ItemStack>> cir) {
        cir.setReturnValue(
            cir.getReturnValue().or(stack -> {
                Item item = stack.getItem();
                return item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof RopeBlock;
            })
        );
    }

    @Inject(method = "loadProjectile", at = @At("HEAD"), cancellable = true)
    private static void loadProjectile(LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean simulated, boolean creative, CallbackInfoReturnable<Boolean> cir) {
        Item item = projectile.getItem();
        if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof RopeBlock) {
            if (!creative && !simulated && shooter instanceof PlayerEntity) {
                shooter.setStackInHand(shooter.getActiveHand() == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND, ItemStack.EMPTY);
            }

            putProjectile(crossbow, projectile);
            cir.setReturnValue(true);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Inject(method = "loadProjectiles", at = @At("HEAD"), cancellable = true)
    private static void loadProjectiles(LivingEntity shooter, ItemStack crossbow, CallbackInfoReturnable<Boolean> cir) {
        if (shooter instanceof PlayerEntity) {
            Item item = shooter.getStackInHand(shooter.getActiveHand() == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND).getItem();
            if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof RopeBlock) {
                loadProjectile(shooter, crossbow, shooter.getStackInHand(shooter.getActiveHand() == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND), false, ((PlayerEntity) shooter).isCreative());

                cir.setReturnValue(true);
            }
        }
    }
}
