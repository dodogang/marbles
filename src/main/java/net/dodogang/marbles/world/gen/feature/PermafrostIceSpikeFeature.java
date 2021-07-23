package net.dodogang.marbles.world.gen.feature;

import com.mojang.serialization.Codec;
import net.dodogang.marbles.init.MarblesBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.IceSpikeFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class PermafrostIceSpikeFeature extends IceSpikeFeature {
    public PermafrostIceSpikeFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> ctx) {
        BlockPos pos = ctx.getOrigin();
        BlockPos.Mutable mpos = pos.mutableCopy();
        Random random = ctx.getRandom();

        StructureWorldAccess world = ctx.getWorld();
        while (world.isAir(mpos) && mpos.getY() > world.getBottomY() + 2) {
            mpos = mpos.move(Direction.DOWN);
        }

        if (!world.getBlockState(mpos).isOf(MarblesBlocks.PERMAFROST)) {
            return false;
        } else {
            mpos = mpos.move(Direction.UP, random.nextInt(4));
            int i = random.nextInt(4) + 7;
            int j = i / 4 + random.nextInt(2);
            if (j > 1 && random.nextInt(60) == 0) {
                mpos = mpos.move(Direction.UP, 10 + random.nextInt(30));
            }

            int iy;
            int l;
            for(iy = 0; iy < i; ++iy) {
                float f = (1.0F - (float)iy / (float)i) * (float)j;
                l = MathHelper.ceil(f);

                for(int ix = -l; ix <= l; ++ix) {
                    float g = (float)MathHelper.abs(ix) - 0.25F;

                    for(int iz = -l; iz <= l; ++iz) {
                        float h = (float)MathHelper.abs(iz) - 0.25F;
                        if ((ix == 0 && iz == 0 || !(g * g + h * h > f * f)) && (ix != -l && ix != l && iz != -l && iz != l || !(random.nextFloat() > 0.75F))) {
                            BlockState istate = world.getBlockState(mpos.add(ix, iy, iz));
                            if (istate.isAir() || isSoil(istate) || istate.isOf(Blocks.SNOW_BLOCK) || istate.isOf(Blocks.ICE)) {
                                this.setBlockState(world, mpos.add(ix, iy, iz), Blocks.PACKED_ICE.getDefaultState());
                            }

                            if (iy != 0 && l > 1) {
                                istate = world.getBlockState(mpos.add(ix, -iy, iz));
                                if (istate.isAir() || isSoil(istate) || istate.isOf(Blocks.SNOW_BLOCK) || istate.isOf(Blocks.ICE)) {
                                    this.setBlockState(world, mpos.add(ix, -iy, iz), Blocks.PACKED_ICE.getDefaultState());
                                }
                            }
                        }
                    }
                }
            }

            iy = j - 1;
            if (iy > 1) {
                iy = 1;
            }

            for(int p = -iy; p <= iy; ++p) {
                for(l = -iy; l <= iy; ++l) {
                    BlockPos ipos = mpos.add(p, -1, l);
                    int r = 50;
                    if (Math.abs(p) == 1 && Math.abs(l) == 1) {
                        r = random.nextInt(5);
                    }

                    while(ipos.getY() > 50) {
                        BlockState istate = world.getBlockState(ipos);
                        if (!istate.isAir() && !isSoil(istate) && !istate.isOf(Blocks.SNOW_BLOCK) && !istate.isOf(Blocks.ICE) && !istate.isOf(Blocks.PACKED_ICE)) {
                            break;
                        }

                        this.setBlockState(world, ipos, Blocks.PACKED_ICE.getDefaultState());
                        ipos = ipos.down();
                        --r;
                        if (r <= 0) {
                            ipos = ipos.down(random.nextInt(5) + 1);
                            r = random.nextInt(5);
                        }
                    }
                }
            }

            return true;
        }
    }
}
