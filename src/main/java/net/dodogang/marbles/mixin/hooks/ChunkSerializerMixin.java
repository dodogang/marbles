package net.dodogang.marbles.mixin.hooks;

import net.dodogang.marbles.world.chunk.MarblesChunkSection;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.ChunkTickScheduler;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.*;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@SuppressWarnings("UnnecessaryLocalVariable")
@Mixin(ChunkSerializer.class)
public class ChunkSerializerMixin {
    @Inject(
        method = "deserialize",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/ChunkSection;calculateCounts()V"),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void onDeserializeChunkSection(
        // Thank you mixin, very funny
        ServerWorld world,
        StructureManager structureManager,
        PointOfInterestStorage poiStorage,
        ChunkPos pos,
        NbtCompound tag,
        CallbackInfoReturnable<ProtoChunk> cir,

        ChunkGenerator chunkGenerator,
        BiomeSource biomeSource,
        NbtCompound NbtCompound,
        BiomeArray biomeArray,
        UpgradeData upgradeData,
        ChunkTickScheduler<Block> chunkTickScheduler,
        ChunkTickScheduler<Fluid> chunkTickScheduler2,
        boolean bl,
        NbtList NbtList,
        int i,
        ChunkSection[] chunkSections,
        boolean bl2,
        ChunkManager chunkManager,
        LightingProvider lightingProvider,
        int j,
        NbtCompound NbtCompound2,
        int k,
        ChunkSection chunkSection
    ) {
        // Reassignment of locals for clarity of this method
        ChunkSection section = chunkSection;
        NbtCompound sectionTag = NbtCompound2;

        if (sectionTag.contains("Marbles", 10)) {
            ((MarblesChunkSection) section).read(sectionTag.getCompound("Marbles"));
        }
    }

    @Inject(
        method = "serialize",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/chunk/PalettedContainer;write(Lnet/minecraft/nbt/NbtCompound;Ljava/lang/String;Ljava/lang/String;)V",
            shift = At.Shift.AFTER
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void onSerializeChunkSection(
        ServerWorld world,
        Chunk chunk,
        CallbackInfoReturnable<NbtCompound> cir,

        ChunkPos chunkPos,
        NbtCompound NbtCompound,
        NbtCompound NbtCompound2,
        ChunkSection[] chunkSections,
        NbtList NbtList,
        LightingProvider lightingProvider,
        boolean bl,
        int i,
        int j,
        ChunkSection chunkSection,
        ChunkNibbleArray chunkNibbleArray,
        ChunkNibbleArray chunkNibbleArray2,
        NbtCompound NbtCompound3
    ) {
        NbtCompound sectionTag = NbtCompound3;
        NbtCompound marblesTag = new NbtCompound();
        ChunkSection section = chunkSection;

        ((MarblesChunkSection) section).write(marblesTag);
        sectionTag.put("Marbles", marblesTag);
    }
}
