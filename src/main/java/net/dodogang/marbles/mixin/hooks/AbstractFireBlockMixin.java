package net.dodogang.marbles.mixin.hooks;

import net.dodogang.marbles.util.ImmersivePortalsCompatHelper;
import net.dodogang.marbles.util.TravertinePortalHelper;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(AbstractFireBlock.class)
public class AbstractFireBlockMixin {
    @Inject(
        method = "onBlockAdded",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/dimension/AreaHelper;method_30485(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction$Axis;)Ljava/util/Optional;"
        ),
        cancellable = true
    )
    private void onOnBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo cb) {
        String ipNetherPortalMode = ImmersivePortalsCompatHelper.getNetherPortalMode();
        if (ipNetherPortalMode.isEmpty() || ipNetherPortalMode.equals("vanilla")) {
            Optional<TravertinePortalHelper> optional = TravertinePortalHelper.tryFindPortal(world, pos, Direction.Axis.X);
            if (optional.isPresent()) {
                optional.get().createPortal();
                cb.cancel();
            }
        }
    }
}
