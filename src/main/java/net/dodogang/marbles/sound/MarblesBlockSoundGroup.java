package net.dodogang.marbles.sound;

import net.dodogang.marbles.init.MarblesSoundEvents;
import net.minecraft.sound.BlockSoundGroup;

public class MarblesBlockSoundGroup {
    public static final BlockSoundGroup ICE_BRICKS = new BlockSoundGroup(
        1.0F, 1.0F,

        MarblesSoundEvents.BLOCK_ICE_BRICKS_BREAK,
        MarblesSoundEvents.BLOCK_ICE_BRICKS_STEP,
        MarblesSoundEvents.BLOCK_ICE_BRICKS_PLACE,
        MarblesSoundEvents.BLOCK_ICE_BRICKS_HIT,
        MarblesSoundEvents.BLOCK_ICE_BRICKS_FALL
    );
    public static final BlockSoundGroup SLUSH = new BlockSoundGroup(
        1.0F, 1.0F,

        MarblesSoundEvents.BLOCK_SLUSH_BREAK,
        MarblesSoundEvents.BLOCK_SLUSH_STEP,
        MarblesSoundEvents.BLOCK_SLUSH_PLACE,
        MarblesSoundEvents.BLOCK_SLUSH_HIT,
        MarblesSoundEvents.BLOCK_SLUSH_FALL
    );
    public static final BlockSoundGroup FLOESTONE = new BlockSoundGroup(
        1.0F, 1.0F,

        MarblesSoundEvents.BLOCK_FLOESTONE_BREAK,
        MarblesSoundEvents.BLOCK_FLOESTONE_STEP,
        MarblesSoundEvents.BLOCK_FLOESTONE_PLACE,
        MarblesSoundEvents.BLOCK_FLOESTONE_HIT,
        MarblesSoundEvents.BLOCK_FLOESTONE_FALL
    );
    public static final BlockSoundGroup RILLED_FLOESTONE = new BlockSoundGroup(
        1.0F, 1.0F,

        MarblesSoundEvents.BLOCK_RILLED_FLOESTONE_BREAK,
        MarblesSoundEvents.BLOCK_RILLED_FLOESTONE_STEP,
        MarblesSoundEvents.BLOCK_RILLED_FLOESTONE_PLACE,
        MarblesSoundEvents.BLOCK_RILLED_FLOESTONE_HIT,
        MarblesSoundEvents.BLOCK_RILLED_FLOESTONE_FALL
    );
}
