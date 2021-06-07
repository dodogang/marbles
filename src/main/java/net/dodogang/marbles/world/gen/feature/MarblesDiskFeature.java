package net.dodogang.marbles.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DiskFeature;
import net.minecraft.world.gen.feature.DiskFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class MarblesDiskFeature extends Feature<DiskFeatureConfig> {
    public MarblesDiskFeature(Codec<DiskFeatureConfig> codec) {
        super(codec);
    }

    /**
     * Copy of {@link DiskFeature#generate}, checking {@link SandBlock}
     * instead of {@link FallingBlock} for sandstone replacement.
     */
    @Override
    public boolean generate(FeatureContext<DiskFeatureConfig> ctx) {
        DiskFeatureConfig config = ctx.getConfig();
        BlockPos blockPos = ctx.getOrigin();
        StructureWorldAccess structureWorldAccess = ctx.getWorld();
        boolean generated = false;
        int y = blockPos.getY();
        int min = y - config.halfHeight - 1;
        int max = y + config.halfHeight;
        boolean stateIsSand = config.state.getBlock() instanceof SandBlock;
        int radius = config.radius.get(ctx.getRandom());

        for(int ix = blockPos.getX() - radius; ix <= blockPos.getX() + radius; ++ix) {
            for(int iz = blockPos.getZ() - radius; iz <= blockPos.getZ() + radius; ++iz) {
                int minX = ix - blockPos.getX();
                int minZ = iz - blockPos.getZ();
                if (minX * minX + minZ * minZ <= radius * radius) {
                    boolean bl3 = false;

                    for(int iy = max; iy >= min; --iy) {
                        BlockPos ipos = new BlockPos(ix, iy, iz);
                        BlockState state = structureWorldAccess.getBlockState(ipos);
                        Block block = state.getBlock();
                        boolean bl4 = false;
                        if (iy > min) {

                            for (BlockState blockState2 : config.targets) {
                                if (blockState2.isOf(block)) {
                                    structureWorldAccess.setBlockState(ipos, config.state, Block.NOTIFY_LISTENERS);
                                    this.method_37256(structureWorldAccess, ipos);
                                    generated = true;
                                    bl4 = true;
                                    break;
                                }
                            }
                        }

                        if (stateIsSand && bl3 && state.isAir()) {
                            BlockState blockState3 = config.state.isOf(Blocks.RED_SAND) ? Blocks.RED_SANDSTONE.getDefaultState() : Blocks.SANDSTONE.getDefaultState();
                            structureWorldAccess.setBlockState(new BlockPos(ix, iy + 1, iz), blockState3, Block.NOTIFY_LISTENERS);
                        }

                        bl3 = bl4;
                    }
                }
            }
        }

        return generated;
    }
}
