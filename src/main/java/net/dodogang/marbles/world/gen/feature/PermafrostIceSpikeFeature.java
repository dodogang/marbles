package net.dodogang.marbles.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;
import java.util.Random;

public class PermafrostIceSpikeFeature extends Feature<PermafrostIceSpikeFeatureConfig> {
    public PermafrostIceSpikeFeature(Codec<PermafrostIceSpikeFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<PermafrostIceSpikeFeatureConfig> ctx) {
        PermafrostIceSpikeFeatureConfig cfg = ctx.getConfig();

        List<BlockState> targets = cfg.targets();
        BlockStateProvider stateProvider = cfg.stateProvider();

        BlockPos pos = ctx.getOrigin();
        BlockPos.Mutable mpos = pos.mutableCopy();
        Random random = ctx.getRandom();

        StructureWorldAccess world = ctx.getWorld();
        while (world.isAir(mpos) && mpos.getY() > world.getBottomY() + 2) {
            mpos = mpos.move(Direction.DOWN);
        }

        if (!targets.contains(world.getBlockState(mpos))) {
            return false;
        } else {
            mpos = mpos.move(Direction.UP, random.nextInt(4));
            int i = random.nextInt(4) + 7;
            int j = i / 4 + random.nextInt(2);
            if (j > 1 && random.nextInt(60) == 0) {
                mpos = mpos.move(Direction.UP, 10 + random.nextInt(30));
            }

            int iy;
            int z;
            for(iy = 0; iy < i; ++iy) {
                float f = (1.0F - (float)iy / (float)i) * (float)j;
                z = MathHelper.ceil(f);

                for(int ix = -z; ix <= z; ++ix) {
                    float g = (float)MathHelper.abs(ix) - 0.25F;

                    for(int iz = -z; iz <= z; ++iz) {
                        float h = (float)MathHelper.abs(iz) - 0.25F;
                        if ((ix == 0 && iz == 0 || !(g * g + h * h > f * f)) && (ix != -z && ix != z && iz != -z && iz != z || !(random.nextFloat() > 0.75F))) {
                            BlockState istate = world.getBlockState(mpos.add(ix, iy, iz));
                            if (istate.isAir() || isSoil(istate) || targets.contains(istate)) {
                                BlockPos ipos = mpos.add(ix, iy, iz);
                                this.setBlockState(world, mpos.add(ix, iy, iz), stateProvider.getBlockState(random, ipos));
                            }

                            if (iy != 0 && z > 1) {
                                istate = world.getBlockState(mpos.add(ix, -iy, iz));
                                if (istate.isAir() || isSoil(istate) || targets.contains(istate)) {
                                    BlockPos ipos = mpos.add(ix, -iy, iz);
                                    this.setBlockState(world, ipos, stateProvider.getBlockState(random, ipos));
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

            for(int ix = -iy; ix <= iy; ++ix) {
                for(z = -iy; z <= iy; ++z) {
                    BlockPos ipos = mpos.add(ix, -1, z);
                    int r = 50;
                    if (Math.abs(ix) == 1 && Math.abs(z) == 1) {
                        r = random.nextInt(5);
                    }

                    int maxY = (int) (world.getBottomY() + (world.getLogicalHeight() * 0.05f));
                    while(ipos.getY() > maxY) {
                        BlockState istate = world.getBlockState(ipos);
                        if (!istate.isAir() && !isSoil(istate) && !targets.contains(istate)) {
                            break;
                        }

                        this.setBlockState(world, ipos, stateProvider.getBlockState(random, ipos));
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
