package net.dodogang.marbles.mixin.hooks;

import net.dodogang.marbles.util.SpotlightUtil;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockView.class)
public interface BlockViewMixin {
    @Shadow
    BlockState getBlockState(BlockPos pos);

    /**
     * @reason Mixin doesn't allow injections in interfaces
     * @author Shadew
     */
    @Overwrite
    default int getLuminance(BlockPos pos) {
        int cur = getBlockState(pos).getLuminance();
        if (this instanceof Chunk) {
            Chunk c = (Chunk) this;
            int sld = SpotlightUtil.getSpotlightData(c, pos);
            for (Direction d : Direction.values()) {
                int sl = SpotlightUtil.getSpotlightValue(sld, d);
                int ll = sl == 0 ? 0 : Math.max(sl / 2, 2);
                if (ll > cur) cur = Math.min(15, ll);
            }
        } else if (this instanceof WorldView) {
            Chunk c = ((WorldView) this).getChunk(pos);
            int sld = SpotlightUtil.getSpotlightData(c, pos);
            for (Direction d : Direction.values()) {
                int sl = SpotlightUtil.getSpotlightValue(sld, d);
                int ll = sl == 0 ? 0 : Math.max(sl / 2, 2);
                if (ll > cur) cur = Math.min(15, ll);
            }
        }
        return cur;
    }
}
