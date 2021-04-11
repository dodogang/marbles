package net.dodogang.marbles.mixin.hooks;

import com.mojang.datafixers.util.Pair;
import net.dodogang.marbles.net.MarblesNetwork;
import net.dodogang.marbles.util.SpotlightUtil;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(World.class)
public class WorldMixin {
    @Inject(
        method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;onBlockChanged(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;)V")
    )
    @SuppressWarnings("ConstantConditions")
    private void onSetBlockState(BlockPos pos, BlockState state, int flags, int maxDepth, CallbackInfoReturnable<Boolean> cb) {
        List<Pair<BlockPos, Integer>> updates = new ArrayList<>();
        SpotlightUtil.updateSpotlight(World.class.cast(this), pos, updates);
        SpotlightUtil.updateNearbySpotlights(World.class.cast(this), pos, updates);

        if (ServerWorld.class.isInstance(this)) {
            MarblesNetwork.sendSpotlightUpdate(ServerWorld.class.cast(this), updates);
        }
    }
}
