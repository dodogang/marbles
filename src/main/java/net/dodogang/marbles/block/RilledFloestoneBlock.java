package net.dodogang.marbles.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RilledFloestoneBlock extends Block {
    public static final int BREAK_WITHOUT_SILK_TOUCH_EVENT = 927465188;

    public RilledFloestoneBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);

        if (!world.isClient && !(EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, player.getStackInHand(Hand.MAIN_HAND)) > 0)) {
            world.syncGlobalEvent(RilledFloestoneBlock.BREAK_WITHOUT_SILK_TOUCH_EVENT, pos, 0);
        }
    }
}
