package net.dodogang.marbles.mixin.world;

import net.dodogang.marbles.util.SpotlightUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Mixin({WorldChunk.class, ProtoChunk.class})
public abstract class ProtoWorldChunkMixin {
    @Inject(method = "getLightSourcesStream", at = @At("RETURN"), cancellable = true)
    private void onGetLightSourcesStream(CallbackInfoReturnable<Stream<BlockPos>> cb) {
        ChunkPos pos = ((Chunk) this).getPos();
        cb.setReturnValue(Stream.concat(
            cb.getReturnValue(),
            StreamSupport.stream(BlockPos.iterate(
                pos.getStartX(), 0, pos.getStartZ(),
                pos.getEndX(), 255, pos.getEndZ()
            ).spliterator(), false).filter(
                blockPos -> SpotlightUtil.hasSpotlightValue(SpotlightUtil.getSpotlightData((Chunk) this, blockPos))
            )
        ));
    }
}
