package net.dodogang.marbles.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class SnowUnderTreesFeature extends Feature<DefaultFeatureConfig> {
    private static final BlockState SNOW = Blocks.SNOW.getDefaultState();

    public SnowUnderTreesFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> ctx) {
        boolean generated = false;
        BlockPos origin = ctx.getOrigin();
        StructureWorldAccess world = ctx.getWorld();
        Random random = ctx.getRandom();
        BlockPos.Mutable mpos = new BlockPos.Mutable();

        for (int ix = 0; ix < 16; ix++) {
            for (int iz = 0; iz < 16; iz++) {
                int x = origin.getX() + ix;
                int z = origin.getZ() + iz;

                mpos.set(x, world.getTopY(Heightmap.Type.MOTION_BLOCKING, x, z) - 1, z);

                if (world.getBlockState(mpos).isIn(BlockTags.LEAVES) && world.getBiome(mpos).isCold(mpos)) {
                    BlockState previousState = world.getBlockState(mpos);
                    mpos.move(Direction.DOWN);

                    int ignoreLeavesTopY = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z) - 1;
                    while (mpos.getY() >= ignoreLeavesTopY) {
                        BlockState istate = world.getBlockState(mpos);
                        if (previousState.getMaterial().isReplaceable()) {
                            boolean canSnowy = istate.contains(Properties.SNOWY);
                            if (istate.isIn(BlockTags.LEAVES) || canSnowy) {
                                generated = true;

                                world.setBlockState(mpos.up(), SNOW.with(Properties.LAYERS, canSnowy ? Math.max(2, random.nextInt(4)) : 1), Block.NOTIFY_LISTENERS);

                                if (canSnowy) {
                                    world.setBlockState(mpos, istate.with(Properties.SNOWY, true), Block.NOTIFY_LISTENERS);
                                }
                            }
                        }

                        previousState = istate;
                        mpos.move(Direction.DOWN);
                    }
                }
            }
        }

        return generated;
    }
}
