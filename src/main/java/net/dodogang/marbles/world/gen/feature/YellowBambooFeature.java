package net.dodogang.marbles.world.gen.feature;

import com.mojang.serialization.Codec;
import net.dodogang.marbles.init.MarblesBlocks;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BambooLeaves;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class YellowBambooFeature extends Feature<ProbabilityConfig> {
    public static final String id = "yellow_bamboo";

    private static final BlockState BAMBOO = MarblesBlocks.YELLOW_BAMBOO.getDefaultState().with(BambooBlock.AGE, 1).with(BambooBlock.LEAVES, BambooLeaves.NONE).with(BambooBlock.STAGE, 0);
    private static final BlockState BAMBOO_TOP_1 = BAMBOO.with(BambooBlock.LEAVES, BambooLeaves.LARGE).with(BambooBlock.STAGE, 1);
    private static final BlockState BAMBOO_TOP_2 = BAMBOO.with(BambooBlock.LEAVES, BambooLeaves.LARGE);
    private static final BlockState BAMBOO_TOP_3 = BAMBOO.with(BambooBlock.LEAVES, BambooLeaves.SMALL);

    public YellowBambooFeature(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator generator, Random random, BlockPos pos, ProbabilityConfig config) {
        int i = 0;
        BlockPos.Mutable bambooPos = pos.mutableCopy();
        BlockPos.Mutable podzolPos = pos.mutableCopy();
        if (world.isAir(bambooPos)) {
            if (Blocks.BAMBOO.getDefaultState().canPlaceAt(world, bambooPos)) {
                int height;
                if (random.nextFloat() < config.probability) {
                    height = random.nextInt(4) + 1;

                    for (int x = pos.getX() - height; x <= pos.getX() + height; ++x) {
                        for (int z = pos.getZ() - height; z <= pos.getZ() + height; ++z) {
                            int n = x - pos.getX();
                            int o = z - pos.getZ();
                            if (n * n + o * o <= height * height) {
                                podzolPos.set(x, world.getTopY(Heightmap.Type.WORLD_SURFACE, x, z) - 1, z);
                                if (isSoil(world.getBlockState(podzolPos).getBlock())) {
                                    world.setBlockState(podzolPos, Blocks.PODZOL.getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }

                int maxHeight = random.nextInt(12) + 5;
                for (height = 0; height < maxHeight && world.isAir(bambooPos); ++height) {
                    world.setBlockState(bambooPos, BAMBOO, 2);
                    bambooPos.move(Direction.UP, 1);
                }

                if (bambooPos.getY() - pos.getY() >= 3) {
                    world.setBlockState(bambooPos, BAMBOO_TOP_1, 2);
                    world.setBlockState(bambooPos.move(Direction.DOWN, 1), BAMBOO_TOP_2, 2);
                    world.setBlockState(bambooPos.move(Direction.DOWN, 1), BAMBOO_TOP_3, 2);
                }
            }

            i++;
        }

        return i > 0;
    }
}
