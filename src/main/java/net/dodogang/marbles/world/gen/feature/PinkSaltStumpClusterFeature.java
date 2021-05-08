package net.dodogang.marbles.world.gen.feature;

import com.mojang.serialization.Codec;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.state.property.MarblesProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class PinkSaltStumpClusterFeature extends Feature<DefaultFeatureConfig> {
    public static final String id = "pink_salt_stump_cluster";

    private static final BlockState SALT_STUMP = MarblesBlocks.PINK_SALT_STUMP.getDefaultState();
    private static final BlockState SALT_STACK = MarblesBlocks.PINK_SALT_STACK.getDefaultState();

    public PinkSaltStumpClusterFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        int placedBlocks = 0;

        BlockPos.Mutable mpos = new BlockPos.Mutable();
        mpos.set(pos);

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        for (int proj = 0; proj < 40; proj++) {
            int y1 = y - proj;
            int y2 = y + proj;

            mpos.set(x, y1, z);
            if (canBuildAt(world, mpos)) {
                y = y1;
                break;
            }

            mpos.set(x, y2, z);
            if (canBuildAt(world, mpos)) {
                y = y2;
                break;
            }
        }

        int retLight = random.nextInt(7) + 5;

        if (!projectState(world, x, y, z, SALT_STACK.with(MarblesProperties.RETAINED_LIGHT, retLight), mpos)) {
            return false;
        }


        for (int ox = -5; ox <= 5; ox++) {
            for (int oz = -5; oz <= 5; oz++) {
                int r = ox * ox + oz * oz;

                if (random.nextInt(4 + r / 3) == 0) {
                    if (projectState(world, ox + x, y, oz + z, SALT_STUMP.with(MarblesProperties.RETAINED_LIGHT, retLight), mpos)) {
                        placedBlocks++;
                    }
                }
            }
        }


        return placedBlocks > 0;
    }

    private boolean projectState(StructureWorldAccess world, int x, int y, int z, BlockState state, BlockPos.Mutable mpos) {
        for (int proj = 0; proj < 10; proj++) {
            int y1 = y - proj;
            int y2 = y + proj;

            mpos.set(x, y1, z);
            if (canBuildAt(world, mpos)) {
                mpos.set(x, y1, z);
                BlockState waterlogged = state.with(Properties.WATERLOGGED, world.getFluidState(mpos).getFluid() == Fluids.WATER);
                world.setBlockState(mpos, waterlogged, 2);
                return true;
            }

            mpos.set(x, y2, z);
            if (canBuildAt(world, mpos)) {
                mpos.set(x, y2, z);
                BlockState waterlogged = state.with(Properties.WATERLOGGED, world.getFluidState(mpos).getFluid() == Fluids.WATER);
                world.setBlockState(mpos, waterlogged, 2);
                return true;
            }
        }

        return false;
    }

    private boolean canBuildAt(StructureWorldAccess world, BlockPos.Mutable pos) {
        return isStateReplaceable(world.getBlockState(pos)) && (
            world.getBlockState(pos.down()).isOf(MarblesBlocks.PINK_SALT) ||
                world.getBlockState(pos.down()).isOf(MarblesBlocks.CRUMBLED_PINK_SALT) ||
                world.getBlockState(pos.down()).isOf(Blocks.GRANITE)
        );
    }

    private static boolean isStateReplaceable(BlockState state) {
        return state.isOf(MarblesBlocks.PINK_SALT_CAVE_AIR)/* || state.getFluidState().getFluid() == Fluids.WATER && state.isOf(Blocks.WATER)*/;
    }
}
