package net.dodogang.marbles.mixin.hooks;

import net.dodogang.marbles.init.MarblesBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CropBlock.class)
public class CropBlockMixin {
    @ModifyVariable(
        method = "getAvailableMoisture",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;north()Lnet/minecraft/util/math/BlockPos;", ordinal = 0),
        ordinal = 0 // modifies 'f'
    )
    private static float onAvailableMoisture(float totalMoisture, Block block, BlockView world, BlockPos pos) {
        BlockPos blockPos = pos.down();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                float localMoisture = 0;
                BlockState state = world.getBlockState(blockPos.add(x, 0, y));
                if (state.isOf(MarblesBlocks.GRISP_FARMLAND)) {
                    localMoisture = 1;
                    if (state.get(FarmlandBlock.MOISTURE) > 0) {
                        localMoisture = 3;
                    }
                }

                if (x != 0 || y != 0) {
                    localMoisture /= 4;
                }

                totalMoisture += localMoisture;
            }
        }

        return totalMoisture;
    }
}
