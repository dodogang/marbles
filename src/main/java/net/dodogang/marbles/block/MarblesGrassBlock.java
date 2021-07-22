package net.dodogang.marbles.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowerFeature;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class MarblesGrassBlock extends MarblesSpreadableBlock implements Fertilizable {
   public MarblesGrassBlock(Supplier<Block> hostBlock, AbstractBlock.Settings settings) {
      super(hostBlock, settings);
   }

   @Override
   public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
      return world.getBlockState(pos.up()).isAir();
   }

   @Override
   public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
      return true;
   }

   @SuppressWarnings({"rawtypes", "unchecked"})
   @Override
   public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
      BlockPos start = pos.up();
      BlockState grassState = this.getDefaultState();

      iterator:
      for (int i = 0; i < 128; i++) {
         BlockPos growPos = start;

         for (int j = 0; j < i / 16; j++) {
            growPos = growPos.add(
                random.nextInt(3) - 1,
                (random.nextInt(3) - 1) * random.nextInt(3) / 2,
                random.nextInt(3) - 1
            );

            if (!world.getBlockState(growPos.down()).isOf(this) || world.getBlockState(growPos).isFullCube(world, growPos)) {
               continue iterator;
            }
         }

         BlockState currentState = world.getBlockState(growPos);
         if (currentState.isOf(grassState.getBlock()) && random.nextInt(10) == 0) {
            ((Fertilizable) grassState.getBlock()).grow(world, random, growPos, currentState);
         }

         if (currentState.isAir()) {
            BlockState placeState;
            if (random.nextInt(8) == 0) {
               List<ConfiguredFeature<?, ?>> list = world.getBiome(growPos).getGenerationSettings().getFlowerFeatures();
               if (list.isEmpty()) {
                  continue;
               }

               ConfiguredFeature<?, ?> configuredFeature = list.get(0);
               FlowerFeature flowerFeature = (FlowerFeature) configuredFeature.feature;
               placeState = flowerFeature.getFlowerState(random, growPos, configuredFeature.getConfig());
            } else {
               placeState = grassState;
            }

            if (placeState.canPlaceAt(world, growPos)) {
               world.setBlockState(growPos, placeState, 3);
            }
         }
      }
   }
}
