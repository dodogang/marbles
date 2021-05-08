package net.dodogang.marbles.util;

import com.mojang.datafixers.util.Pair;
import net.dodogang.marbles.block.SpotlightBlock;
import net.dodogang.marbles.network.MarblesNetwork;
import net.dodogang.marbles.world.chunk.MarblesChunkSection;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;

import java.util.List;

public class SpotlightUtil {
    public static final int SPOTLIGHT_BUFFER_SIZE = 16 * 16 * 16;

    public static int getSpotlightValue(int packed, Direction dir) {
        int shift = dir.getId() * 5;
        return packed >>> shift & 0b11111;
    }

    public static int setSpotlightValue(int packed, Direction dir, int newVal) {
        int shift = dir.getId() * 5;
        int none = packed & ~(0b11111 << shift);
        return none | newVal << shift;
    }

    public static boolean hasSpotlightValue(int packed) {
        return (packed & 0b11111_11111_11111_11111_11111_11111) != 0;
    }

    public static int getSpotlightData(World world, BlockPos pos) {
        if (World.isOutOfBuildLimitVertically(pos)) return 0;
        Chunk chunk = world.getChunk(pos.getX() >> 4, pos.getZ() >> 4, ChunkStatus.EMPTY, false);
        return getSpotlightData(chunk, pos);
    }

    public static int getSpotlightData(Chunk chunk, BlockPos pos) {
        if (World.isOutOfBuildLimitVertically(pos)) return 0;
        if (chunk == null) return 0;

        ChunkSection[] sections = chunk.getSectionArray();
        for (ChunkSection section : sections) {
            if (section != null) {
                int y = pos.getY() - section.getYOffset();
                if (y >= 0 && y < 16) {
                    int x = pos.getX() & 15;
                    int z = pos.getZ() & 15;
                    return ((MarblesChunkSection) section).getSpotlightData(x, y, z);
                }
            }
        }

        return 0;
    }

    @SuppressWarnings("ConstantConditions")
    public static void setSpotlightData(World world, BlockPos pos, int val, boolean sync) {
        if (World.isOutOfBuildLimitVertically(pos)) return;
        Chunk chunk = world.getChunk(pos.getX() >> 4, pos.getZ() >> 4, ChunkStatus.EMPTY, true);
        if (chunk == null) return;


        boolean set = false;
        ChunkSection[] sections = chunk.getSectionArray();
        for (ChunkSection section : sections) {
            if (section != null) {
                int y = pos.getY() - section.getYOffset();
                if (y >= 0 && y < 16) {
                    int x = pos.getX() & 15;
                    int z = pos.getZ() & 15;
                    ((MarblesChunkSection) section).setSpotlightData(x, y, z, val);
                    set = true;
                    break;
                }
            }
        }

        if (!set) {
            ChunkSection section = new ChunkSection(pos.getY() >> 4 << 4);
            sections[pos.getY() >> 4] = section;

            int x = pos.getX() & 15;
            int y = pos.getY() & 15;
            int z = pos.getZ() & 15;
            ((MarblesChunkSection) section).setSpotlightData(x, y, z, val);
        }

        if (world instanceof ServerWorld && sync) {
            MarblesNetwork.sendSpotlightUpdate((ServerWorld) world, pos, val);
        }

        world.getLightingProvider().checkBlock(pos);
    }

    public static void updateSpotlight(World world, BlockPos pos, List<Pair<BlockPos, Integer>> updates) {
        if (World.isOutOfBuildLimitVertically(pos)) return;

        int currentLight = getSpotlightData(world, pos);
        int newLight = currentLight;

        BlockState state = world.getBlockState(pos);

        int opacity = state.getOpacity(world, pos) * 2;

        for (Direction dir : Direction.values()) {
            BlockPos near = pos.offset(dir);
            BlockState nearState = world.getBlockState(near);

            int lightFromThisDirection = getSpotlightValue(getSpotlightData(world, near), dir.getOpposite());

            boolean nearIsSpotlight = false;
            if (nearState.getBlock() instanceof SpotlightBlock && nearState.get(SpotlightBlock.FACING) == dir.getOpposite()) {
                lightFromThisDirection = Math.max(lightFromThisDirection, 32);
                nearIsSpotlight = true;
            }

            lightFromThisDirection -= Math.max(opacity, 1);
            if (state.isSideSolidFullSquare(world, pos, dir) && state.isOpaque() || nearState.isSideSolidFullSquare(world, near, dir.getOpposite()) && nearState.isOpaque() && !nearIsSpotlight) {
                lightFromThisDirection = 0;
            }
            newLight = setSpotlightValue(newLight, dir.getOpposite(), Math.max(lightFromThisDirection, 0));
        }

        if (currentLight != newLight) {
            setSpotlightData(world, pos, newLight, false);
            updates.add(Pair.of(pos, newLight));
            updateNearbySpotlights(world, pos, updates);
        }
    }

    public static void updateNearbySpotlights(World world, BlockPos pos, List<Pair<BlockPos, Integer>> updates) {
        for (Direction dir : Direction.values()) {
            updateSpotlight(world, pos.offset(dir), updates);
        }
    }
}
