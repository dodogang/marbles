package net.dodogang.marbles.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class StateProvidedChanceDiskFeature extends Feature<StateProvidedChanceDiskFeatureConfig> {
    public StateProvidedChanceDiskFeature(Codec<StateProvidedChanceDiskFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator generator, Random random, BlockPos pos, StateProvidedChanceDiskFeatureConfig config) {
        boolean generated = false;
        if (random.nextFloat() <= config.chance) {
            int radius = config.radius.getValue(random);

            for (int x = pos.getX() - radius; x <= pos.getX() + radius; ++x) {
                for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; ++z) {
                    int dx = x - pos.getX();
                    int dz = z - pos.getZ();
                    if (dx * dx + dz * dz <= radius * radius) {
                        for (int y = pos.getY() - config.halfHeight; y <= pos.getY() + config.halfHeight; ++y) {
                            BlockPos ipos = new BlockPos(x, y, z);
                            Block block = world.getBlockState(ipos)
                                               .getBlock();
                            for (BlockState blockState : config.targets) {
                                if (blockState.isOf(block)) {
                                    world.setBlockState(ipos, config.stateProvider.getBlockState(random, pos), 2);
                                    generated = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return generated;
    }
}
