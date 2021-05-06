package net.dodogang.marbles.handler;

import net.dodogang.marbles.init.MarblesBlocks;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class ToolClickHandlers {
    private static final Map<Block, BlockState> FLATTENABLE = Util.make(new HashMap<>(), map -> {
        map.put(MarblesBlocks.GRISP_DIRT, MarblesBlocks.GRISP_DIRT_PATH.getDefaultState());
        map.put(MarblesBlocks.GRISP_GRASS_BLOCK, MarblesBlocks.GRISP_GRASS_PATH.getDefaultState());
        map.put(MarblesBlocks.GRISP_PODZOL, MarblesBlocks.GRISP_PODZOL_PATH.getDefaultState());
        map.put(MarblesBlocks.GRISP_MYCELIUM, MarblesBlocks.GRISP_MYCELIUM_PATH.getDefaultState());
    });
    private static final Map<Block, BlockState> TILLABLE = Util.make(new HashMap<>(), map -> {
        map.put(MarblesBlocks.GRISP_DIRT, MarblesBlocks.GRISP_FARMLAND.getDefaultState());
        map.put(MarblesBlocks.GRISP_GRASS_BLOCK, MarblesBlocks.GRISP_FARMLAND.getDefaultState());
        map.put(MarblesBlocks.GRISP_PODZOL, MarblesBlocks.GRISP_FARMLAND.getDefaultState());
        map.put(MarblesBlocks.GRISP_MYCELIUM, MarblesBlocks.GRISP_FARMLAND.getDefaultState());
    });

    static {
        UseBlockCallback.EVENT.register(ToolClickHandlers::onItemUse);
    }

    private static ActionResult onItemUse(PlayerEntity player, World world, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        Item item = stack.getItem();
        BlockPos pos = hit.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (player.isSpectator())
            return ActionResult.PASS;

        if (item.isIn(FabricToolTags.SHOVELS) && hit.getSide() == Direction.UP) {
            BlockState newState = FLATTENABLE.get(state.getBlock());
            if (newState != null && world.getBlockState(pos.up()).isAir()) {
                playFlattenSound(world, pos, player);

                if (!world.isClient) {
                    world.setBlockState(pos, newState, 11);
                    if (!player.isCreative()) {
                        stack.damage(1, player, p -> p.sendToolBreakStatus(hand));
                    }
                }
                return ActionResult.success(world.isClient);
            }
        }
        if (item.isIn(FabricToolTags.HOES) && hit.getSide() == Direction.UP) {
            BlockState newState = TILLABLE.get(state.getBlock());
            if (newState != null && world.getBlockState(pos.up()).isAir()) {
                playTillSound(world, pos, player);

                if (!world.isClient) {
                    world.setBlockState(pos, newState, 11);
                    if (!player.isCreative()) {
                        stack.damage(1, player, p -> p.sendToolBreakStatus(hand));
                    }
                }
                return ActionResult.success(world.isClient);
            }
        }

        return ActionResult.PASS;
    }

    private static void playFlattenSound(World world, BlockPos pos, PlayerEntity player) {
        world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1, 1);
    }
    private static void playTillSound(World world, BlockPos pos, PlayerEntity player) {
        world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1, 1);
    }
}
