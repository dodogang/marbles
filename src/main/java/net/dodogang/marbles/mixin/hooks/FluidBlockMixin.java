package net.dodogang.marbles.mixin.hooks;

import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.tag.MarblesBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidBlock.class)
public class FluidBlockMixin {
    @Shadow @Final protected FlowableFluid fluid;

    @Inject(method = "receiveNeighborFluids", at = @At("HEAD"), cancellable = true)
    private void receiveNeighborFluids(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (this.fluid.isIn(FluidTags.LAVA)) {
            Direction[] directions = Direction.values();

            for (Direction direction : directions) {
                if (direction != Direction.DOWN) {
                    BlockPos blockPos = pos.offset(direction);
                    if (world.getFluidState(blockPos).isIn(FluidTags.WATER)
                            && world.getFluidState(pos).isStill()
                            && world.getBlockState(pos.down()).isIn(MarblesBlockTags.RAW_TRAVERTINE)
                    ) {
                        world.setBlockState(pos, MarblesBlocks.TRAVERTINE_OBSIDIAN.getDefaultState());
                        world.syncWorldEvent(1501, pos, 0); // FluidBlock#playExtinguishSound
                        cir.setReturnValue(false);
                    }
                }
            }
        }
    }
}
