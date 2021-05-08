package net.dodogang.marbles.block;

import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.mixin.EntityAccessShapeContext;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

@SuppressWarnings("deprecation")
public class PinkSaltStackBlock extends AbstractLightRetainingBlock {
    public static final String id = "pink_salt_stack";

    private static final VoxelShape SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 14.0D, 12.0D);

    public PinkSaltStackBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        Entity entity = ((EntityAccessShapeContext) ctx).marbles_getEntity();
        return entity instanceof FallingBlockEntity
            ? VoxelShapes.empty()
            : super.getCollisionShape(state, world, pos, ctx);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        return SHAPE.offset(vec3d.x, 0.0D, vec3d.z);
    }

    @Override
    public AbstractBlock.OffsetType getOffsetType() {
        return AbstractBlock.OffsetType.XZ;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos down = pos.down();
        return Block.sideCoversSmallSquare(world, down, Direction.UP) || world.getBlockState(down).isOf(MarblesBlocks.PINK_SALT_SPIRE);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext ctx) {
        return ctx.getPlayer() == null || super.canReplace(state, ctx);
    }

    @Override
    protected double getHorizontalParticleOffsetRange() {
        return 0.4D;
    }
    @Override
    protected double getVerticalParticleOffset() {
        return 0.7D;
    }
}
