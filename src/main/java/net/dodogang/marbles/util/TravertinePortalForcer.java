package net.dodogang.marbles.util;

import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.init.MarblesPointOfInterestTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.PortalUtil;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;

import java.util.Comparator;
import java.util.Optional;

public class TravertinePortalForcer {
    public static Optional<PortalUtil.Rectangle> findPortal(ServerWorld world, BlockPos source, boolean isNether) {
        PointOfInterestStorage pois = world.getPointOfInterestStorage();
        int searchRange = isNether ? 16 : 128;
        pois.preloadChunks(world, source, searchRange);

        Optional<PointOfInterest> nearestPortal = pois.getInSquare(
            type -> type == MarblesPointOfInterestTypes.TRAVERTINE_PORTAL,
            source,
            searchRange,
            PointOfInterestStorage.OccupationStatus.ANY
        ).sorted(
            Comparator.<PointOfInterest>comparingDouble(
                poi -> poi.getPos().getSquaredDistance(source)
            ).thenComparingInt(
                poi -> poi.getPos().getY()
            )
        ).filter(
            poi -> world.getBlockState(poi.getPos()).isOf(MarblesBlocks.TRAVERTINE_PORTAL)
        ).findFirst();

        return nearestPortal.map(poi -> {
            BlockPos pos = poi.getPos();
            world.getChunkManager().addTicket(ChunkTicketType.PORTAL, new ChunkPos(pos), 3, pos);
            BlockState portalState = world.getBlockState(pos);
            return PortalUtil.getLargestRectangle(
                pos,
                portalState.get(Properties.HORIZONTAL_AXIS),
                TravertinePortalHelper.MAX_WIDTH,
                Direction.Axis.Y,
                TravertinePortalHelper.MAX_HEIGHT,
                p -> world.getBlockState(p) == portalState
            );
        });
    }

    public static Optional<PortalUtil.Rectangle> createNewPortal(ServerWorld world, BlockPos pos, Direction.Axis axis) {
        Direction tangent = Direction.get(Direction.AxisDirection.POSITIVE, axis);

        double optimalDsq = -1;
        BlockPos portalPos = null;

        double unoptimalDsq = -1;
        BlockPos unoptimalPortalPos = null;

        WorldBorder worldBorder = world.getWorldBorder();
        int dimHeight = world.getDimensionHeight() - 1;

        BlockPos.Mutable mpos = pos.mutableCopy();

        for (BlockPos.Mutable ipos : BlockPos.method_30512(pos, 16, Direction.EAST, Direction.SOUTH)) {
            int portalY = Math.min(dimHeight, world.getTopY(Heightmap.Type.MOTION_BLOCKING, ipos.getX(), ipos.getZ()));

            if (!worldBorder.contains(ipos))
                continue;

            if (!worldBorder.contains(ipos.move(tangent, 1)))
                continue;

            ipos.move(tangent.getOpposite(), 1);

            for (int hgtI = portalY; hgtI >= 0; hgtI--) {
                ipos.setY(hgtI);
                if (world.isAir(ipos)) {
                    int m = hgtI;
                    while (hgtI > 0 && world.isAir(ipos.move(Direction.DOWN))) {
                        hgtI--;
                    }

                    if (hgtI + 4 <= dimHeight) {
                        int n = m - hgtI;
                        if (n <= 0 || n >= 3) {
                            ipos.setY(hgtI);
                            if (checkPortalSpaceLayer(world, ipos, mpos, tangent, 0)) {

                                double dsq = pos.getSquaredDistance(ipos);
                                if (checkPortalSpaceLayer(world, ipos, mpos, tangent, -1) && checkPortalSpaceLayer(world, ipos, mpos, tangent, 1) && (optimalDsq == -1 || optimalDsq > dsq)) {
                                    optimalDsq = dsq;
                                    portalPos = ipos.toImmutable();
                                }

                                if (optimalDsq == -1 && (unoptimalDsq == -1 || unoptimalDsq > dsq)) {
                                    unoptimalDsq = dsq;
                                    unoptimalPortalPos = ipos.toImmutable();
                                }
                            }
                        }
                    }
                }
            }
        }

        if (optimalDsq == -1 && unoptimalDsq != -1) {
            // Couldn't find optimal conditions, try unoptimal conditions
            portalPos = unoptimalPortalPos;
            optimalDsq = unoptimalDsq;
        }

        if (optimalDsq == -1) {
            // Couldn't find nearby solid surface
            portalPos = new BlockPos(
                pos.getX(),
                MathHelper.clamp(pos.getY(), 70, world.getDimensionHeight() - 10),
                pos.getZ()
            ).toImmutable();

            Direction normal = tangent.rotateYClockwise();
            if (!worldBorder.contains(portalPos)) {
                return Optional.empty();
            }

            // Portal floating or submerged, create space with platform
            for (int nrmI = -1; nrmI < 2; nrmI++) {
                for (int tanI = 0; tanI < 2; tanI++) {
                    for (int hgtI = -1; hgtI < 3; hgtI++) {
                        BlockState blockState = hgtI < 0 ? MarblesBlocks.TRAVERTINE_OBSIDIAN.getDefaultState()
                                                         : Blocks.AIR.getDefaultState();

                        mpos.set(
                            portalPos,
                            tanI * tangent.getOffsetX() + nrmI * normal.getOffsetX(),
                            hgtI,
                            tanI * tangent.getOffsetZ() + nrmI * normal.getOffsetZ()
                        );
                        world.setBlockState(mpos, blockState);
                    }
                }
            }
        }

        // Create portal frame
        for (int tanI = -1; tanI < 3; tanI++) {
            for (int hgtI = -1; hgtI < 4; hgtI++) {
                if (tanI == -1 || tanI == 2 || hgtI == -1 || hgtI == 3) {
                    mpos.set(
                        portalPos,
                        tanI * tangent.getOffsetX(),
                        hgtI,
                        tanI * tangent.getOffsetZ()
                    );
                    world.setBlockState(mpos, MarblesBlocks.TRAVERTINE_OBSIDIAN.getDefaultState(), 3);
                }
            }
        }

        // Create portal
        BlockState portal = MarblesBlocks.TRAVERTINE_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, axis);
        for (int tanI = 0; tanI < 2; tanI++) {
            for (int hgtI = 0; hgtI < 3; hgtI++) {
                mpos.set(portalPos, tanI * tangent.getOffsetX(), hgtI, tanI * tangent.getOffsetZ());
                world.setBlockState(mpos, portal, 18);
            }
        }

        return Optional.of(new PortalUtil.Rectangle(portalPos.toImmutable(), 2, 3));
    }

    private static boolean checkPortalSpaceLayer(ServerWorld world, BlockPos pos, BlockPos.Mutable mutable, Direction tangent, int nrmOff) {
        Direction normal = tangent.rotateYClockwise();

        for (int tanI = -1; tanI < 3; tanI++) {
            for (int hgtI = -1; hgtI < 4; hgtI++) {
                mutable.set(
                    pos,
                    tangent.getOffsetX() * tanI + normal.getOffsetX() * nrmOff,
                    hgtI,
                    tangent.getOffsetZ() * tanI + normal.getOffsetZ() * nrmOff
                );
                if (hgtI < 0 && !world.getBlockState(mutable).getMaterial().isSolid()) {
                    return false;
                }

                if (hgtI >= 0 && !world.isAir(mutable)) {
                    return false;
                }
            }
        }

        return true;
    }
}
