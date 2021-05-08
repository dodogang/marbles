package net.dodogang.marbles.world.gen.feature;

import com.mojang.serialization.Codec;
import net.dodogang.marbles.init.MarblesBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class PinkSaltSpikesFeature extends Feature<DefaultFeatureConfig> {
    public static final String id = "pink_salt_spikes";

    public PinkSaltSpikesFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGen, Random rng, BlockPos pos, DefaultFeatureConfig cfg) {
        BlockState state = MarblesBlocks.PINK_SALT_SPIKES.getDefaultState();

        int i = 0;
        BlockPos.Mutable mpos = new BlockPos.Mutable();

        for (int j = 0; j < 64; j++) {
            mpos.set(
                pos,
                rng.nextInt(8) - rng.nextInt(8),
                rng.nextInt(4) - rng.nextInt(4),
                rng.nextInt(8) - rng.nextInt(8)
            );
            BlockState medium = world.getBlockState(mpos);
            mpos.move(Direction.DOWN);
            BlockState support = world.getBlockState(mpos);
            mpos.move(Direction.UP);
            if (canPlaceIn(medium) && state.canPlaceAt(world, mpos) && canPlaceUpon(support)) {
                BlockState place = state;
                if (medium.getFluidState().getFluid() == Fluids.WATER)
                    place = place.with(Properties.WATERLOGGED, true);
                world.setBlockState(mpos, place, 2);
                i++;
            }
        }

        return i > 0;
    }

    private static boolean canPlaceIn(BlockState state) {
        return state.isOf(MarblesBlocks.PINK_SALT_CAVE_AIR) || state.getFluidState().getFluid() == Fluids.WATER && state.isOf(Blocks.WATER);
    }

    private static boolean canPlaceUpon(BlockState state) {
        return state.isOf(MarblesBlocks.PINK_SALT)
                   || state.isOf(MarblesBlocks.CRUMBLED_PINK_SALT)
                   || state.isOf(Blocks.GRANITE);
    }
}
