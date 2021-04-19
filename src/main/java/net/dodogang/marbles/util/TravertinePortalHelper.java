package net.dodogang.marbles.util;

import net.dodogang.marbles.init.MarblesBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.PortalUtil;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

@SuppressWarnings("FieldMayBeFinal")
public class TravertinePortalHelper {
    public static final int MIN_WIDTH = 2;
    public static final int MIN_HEIGHT = 3;
    public static final int MAX_WIDTH = 21;
    public static final int MAX_HEIGHT = 21;

    private static final AbstractBlock.ContextPredicate FRAME = (state, world, pos) -> state.isOf(MarblesBlocks.TRAVERTINE_OBSIDIAN);
    private static final AbstractBlock.ContextPredicate PORTAL = (state, world, pos) -> state.isOf(MarblesBlocks.TRAVERTINE_PORTAL);
    private static final AbstractBlock.ContextPredicate REPLACEABLE = (state, world, pos) -> {
        if (PORTAL.test(state, world, pos)) return true;
        return state.isOf(Blocks.FIRE) || state.isAir();
    };

    private final WorldAccess world;
    private final Direction.Axis axis;
    private final Direction direction;
    private int foundPortalBlocks;
    private BlockPos origin;
    private int height;
    private int width;

    public static Optional<TravertinePortalHelper> tryFindPortal(WorldAccess worldAccess, BlockPos blockPos, Direction.Axis axis) {
        return tryFindPortal(worldAccess, blockPos, portalHelper -> portalHelper.isValid() && portalHelper.foundPortalBlocks == 0, axis);
    }

    public static Optional<TravertinePortalHelper> tryFindPortal(WorldAccess world, BlockPos pos, Predicate<TravertinePortalHelper> predicate, Direction.Axis axis) {
        Optional<TravertinePortalHelper> optional = Optional.of(new TravertinePortalHelper(world, pos, axis)).filter(predicate);
        if (optional.isPresent()) {
            return optional;
        } else {
            Direction.Axis oppositeAxis = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
            return Optional.of(new TravertinePortalHelper(world, pos, oppositeAxis)).filter(predicate);
        }
    }

    public TravertinePortalHelper(WorldAccess world, BlockPos blockPos, Direction.Axis axis) {
        this.world = world;
        this.axis = axis;
        this.direction = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        this.origin = findLowerCorner(blockPos);

        if (origin == null) {
            origin = blockPos;
            width = 1;
            height = 1;
        } else {
            width = findWidth();
            if (width > 0) {
                height = findHeight();
            }
        }

    }

    @Nullable
    private BlockPos findLowerCorner(BlockPos source) {
        int yLimit = Math.max(0, source.getY() - MAX_HEIGHT);

        BlockPos down = source.down();
        while (source.getY() > yLimit && canPortalReplace(world.getBlockState(down), world, down)) {
            source = down;
            down = source.down();
        }

        Direction invDirection = direction.getOpposite();

        int frameDist = findDistanceToCorner(source, invDirection) - 1;
        return frameDist < 0 ? null : source.offset(invDirection, frameDist);
    }

    private int findWidth() {
        int width = findDistanceToCorner(origin, direction);
        return width >= MIN_WIDTH && width <= MAX_WIDTH ? width : 0;
    }

    private int findDistanceToCorner(BlockPos source, Direction dir) {
        BlockPos.Mutable mpos = new BlockPos.Mutable();

        for (int dist = 0; dist <= MAX_WIDTH; dist++) {
            mpos.set(source).move(dir, dist);

            BlockState state = world.getBlockState(mpos);
            if (!canPortalReplace(state, world, mpos)) {
                if (FRAME.test(state, world, mpos)) {
                    return dist;
                }
                break;
            }

            BlockState stateBelow = world.getBlockState(mpos.move(Direction.DOWN));
            if (!FRAME.test(stateBelow, world, mpos)) {
                break;
            }
        }

        return 0;
    }

    private int findHeight() {
        BlockPos.Mutable mpos = new BlockPos.Mutable();
        int height = computeHeight(mpos);
        return height >= MIN_HEIGHT && height <= MAX_HEIGHT && checkUpperEdge(mpos, height) ? height : 0;
    }

    private boolean checkUpperEdge(BlockPos.Mutable mpos, int i) {
        for (int x = 0; x < width; x++) {
            mpos.set(origin).move(Direction.UP, i).move(direction, x);

            if (!FRAME.test(world.getBlockState(mpos), world, mpos))
                return false;
        }

        return true;
    }

    private int computeHeight(BlockPos.Mutable mpos) {
        for (int height = 0; height < MAX_HEIGHT; height++) {
            mpos.set(origin).move(Direction.UP, height).move(direction, -1);
            if (!FRAME.test(world.getBlockState(mpos), world, mpos))
                return height;

            mpos.set(origin).move(Direction.UP, height).move(direction, width);
            if (!FRAME.test(world.getBlockState(mpos), world, mpos))
                return height;

            for (int x = 0; x < width; x++) {
                mpos.set(origin).move(Direction.UP, height).move(direction, x);
                BlockState state = world.getBlockState(mpos);

                if (!canPortalReplace(state, world, mpos))
                    return height;

                if (PORTAL.test(state, world, mpos))
                    foundPortalBlocks++;
            }
        }

        return MAX_HEIGHT;
    }

    private static boolean canPortalReplace(BlockState state, BlockView world, BlockPos pos) {
        return REPLACEABLE.test(state, world, pos);
    }

    public boolean isValid() {
        return origin != null && width >= MIN_WIDTH && width <= MAX_WIDTH && height >= MIN_HEIGHT && height <= MAX_HEIGHT;
    }

    public void createPortal() {
        assert origin != null;

        BlockState portal = MarblesBlocks.TRAVERTINE_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, axis);
        BlockPos.iterate(origin, origin.offset(Direction.UP, height - 1).offset(direction, width - 1))
                .forEach(pos -> world.setBlockState(pos, portal, 18));
    }

    public boolean wasAlreadyValid() {
        return isValid() && foundPortalBlocks == width * height;
    }

    public static Vec3d computeInterpolator(PortalUtil.Rectangle arg, Direction.Axis axis, Vec3d vec3d, EntityDimensions entityDimensions) {
        double d = (double) arg.width - (double) entityDimensions.width;
        double e = (double) arg.height - (double) entityDimensions.height;
        BlockPos blockPos = arg.lowerLeft;
        double h;
        if (d > 0.0D) {
            float f = (float) blockPos.getComponentAlongAxis(axis) + entityDimensions.width / 2.0F;
            h = MathHelper.clamp(MathHelper.getLerpProgress(vec3d.getComponentAlongAxis(axis) - (double) f, 0.0D, d), 0.0D, 1.0D);
        } else {
            h = 0.5D;
        }

        double j;
        Direction.Axis axis3;
        if (e > 0.0D) {
            axis3 = Direction.Axis.Y;
            j = MathHelper.clamp(MathHelper.getLerpProgress(vec3d.getComponentAlongAxis(axis3) - (double) blockPos.getComponentAlongAxis(axis3), 0.0D, e), 0.0D, 1.0D);
        } else {
            j = 0.0D;
        }

        axis3 = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        double k = vec3d.getComponentAlongAxis(axis3) - ((double) blockPos.getComponentAlongAxis(axis3) + 0.5D);
        return new Vec3d(h, j, k);
    }

    public static TeleportTarget computeTeleportTarget(ServerWorld world, PortalUtil.Rectangle portal, Direction.Axis axis, Vec3d posInterpolator, EntityDimensions size, Vec3d velocity, float f, float g) {
        BlockPos portalPos = portal.lowerLeft;
        BlockState portalBlock = world.getBlockState(portalPos);
        Direction.Axis portalAxis = portalBlock.get(Properties.HORIZONTAL_AXIS);

        double width = portal.width;
        double height = portal.height;
        int rotationDelta = axis == portalAxis ? 0 : 90;

        Vec3d portalOffset = axis == portalAxis
                             ? velocity
                             : new Vec3d(velocity.z, velocity.y, -velocity.x);

        double tangentOffset = size.width / 2 + (width - size.width) * posInterpolator.getX();
        double heightOffset = (height - size.height) * posInterpolator.getY();
        double normalOffset = 0.5 + posInterpolator.getZ();

        boolean xAxis = portalAxis == Direction.Axis.X;

        Vec3d location = new Vec3d(
            portalPos.getX() + (xAxis ? tangentOffset : normalOffset),
            portalPos.getY() + heightOffset,
            portalPos.getZ() + (xAxis ? normalOffset : tangentOffset)
        );
        return new TeleportTarget(location, portalOffset, f + (float) rotationDelta, g);
    }
}
