package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MarblesSoundEvents {
    public static final SoundEvent BLOCK_PINK_SALT_BREAK = register("block.pink_salt.break");
    public static final SoundEvent BLOCK_PINK_SALT_STEP = register("block.pink_salt.step");
    public static final SoundEvent BLOCK_PINK_SALT_HIT = register("block.pink_salt.hit");
    public static final SoundEvent BLOCK_PINK_SALT_PLACE = register("block.pink_salt.place");
    public static final SoundEvent BLOCK_PINK_SALT_FALL = register("block.pink_salt.fall");

    public static final SoundEvent BLOCK_PINK_SALT_BRICKS_BREAK = register("block.pink_salt_bricks.break");
    public static final SoundEvent BLOCK_PINK_SALT_BRICKS_STEP = register("block.pink_salt_bricks.step");
    public static final SoundEvent BLOCK_PINK_SALT_BRICKS_HIT = register("block.pink_salt_bricks.hit");
    public static final SoundEvent BLOCK_PINK_SALT_BRICKS_PLACE = register("block.pink_salt_bricks.place");
    public static final SoundEvent BLOCK_PINK_SALT_BRICKS_FALL = register("block.pink_salt_bricks.fall");

    private static SoundEvent register(String name) {
        Identifier id = new Identifier(Marbles.MOD_ID, name);
        SoundEvent event = new SoundEvent(id);
        Registry.register(Registry.SOUND_EVENT, id, event);
        return event;
    }
}
