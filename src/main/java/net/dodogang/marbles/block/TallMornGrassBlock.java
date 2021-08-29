package net.dodogang.marbles.block;

import net.dodogang.marbles.tag.MarblesBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class TallMornGrassBlock extends TallPlantBlock {
    public TallMornGrassBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return super.canPlantOnTop(floor, world, pos) || floor.isIn(MarblesBlockTags.MORN_GRASS_SUPPORTERS);
    }
}
