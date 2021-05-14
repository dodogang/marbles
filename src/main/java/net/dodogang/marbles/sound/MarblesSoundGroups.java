package net.dodogang.marbles.sound;

import net.dodogang.marbles.init.MarblesSoundEvents;
import net.minecraft.sound.BlockSoundGroup;

public class MarblesSoundGroups {
    public static final BlockSoundGroup PINK_SALT = new BlockSoundGroup(
        1, 1,
        MarblesSoundEvents.BLOCK_PINK_SALT_BREAK,
        MarblesSoundEvents.BLOCK_PINK_SALT_STEP,
        MarblesSoundEvents.BLOCK_PINK_SALT_PLACE,
        MarblesSoundEvents.BLOCK_PINK_SALT_HIT,
        MarblesSoundEvents.BLOCK_PINK_SALT_FALL
    );
    public static final BlockSoundGroup PINK_SALT_BRICKS = new BlockSoundGroup(
        1, 1,
        MarblesSoundEvents.BLOCK_PINK_SALT_BRICKS_BREAK,
        MarblesSoundEvents.BLOCK_PINK_SALT_BRICKS_STEP,
        MarblesSoundEvents.BLOCK_PINK_SALT_BRICKS_PLACE,
        MarblesSoundEvents.BLOCK_PINK_SALT_BRICKS_HIT,
        MarblesSoundEvents.BLOCK_PINK_SALT_BRICKS_FALL
    );
}
