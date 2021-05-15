package net.dodogang.marbles.mixin.hooks.client;

import net.dodogang.marbles.tag.MarblesBlockTags;
import net.dodogang.marbles.util.ModLoaded;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(BiomeColors.class)
public class BiomeColorsMixin {
    @Inject(method = "getGrassColor", at = @At("HEAD"), cancellable = true)
    private static void onGetGrassColor(BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> cb) {
        if (!ModLoaded.SODIUM) {
            BlockState state = world.getBlockState(pos);
            if (!state.isSideSolidFullSquare(world, pos, Direction.DOWN)) {
                if (world.getBlockState(pos.down()).isIn(MarblesBlockTags.GRISP_COLOR_SOURCE)) {
                    cb.setReturnValue(0x74C05A);
                    cb.cancel();
                }
            }
        }
    }
}
