package net.dodogang.marbles.init;

import net.dodogang.marbles.Marbles;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MarblesSoundEvents {
    public static final SoundEvent ENTITY_BOUNCER_THROW = bouncer("throw");
    public static final SoundEvent ENTITY_BOUNCER_AMBIENT = bouncer("ambient");
    public static final SoundEvent ENTITY_BOUNCER_WAITING = bouncer("waiting");
    public static final SoundEvent ENTITY_BOUNCER_DEATH = bouncer("death");
    public static final SoundEvent ENTITY_BOUNCER_HURT = bouncer("hurt");
    public static final SoundEvent ENTITY_BOUNCER_STEP = bouncer("step");
    private static SoundEvent bouncer(String type) {
        return entity("bouncer", type);
    }

    public static final SoundEvent BLOCK_PINK_SALT_BREAK = pinkSalt("break");
    public static final SoundEvent BLOCK_PINK_SALT_STEP = pinkSalt("step");
    public static final SoundEvent BLOCK_PINK_SALT_HIT = pinkSalt("hit");
    public static final SoundEvent BLOCK_PINK_SALT_PLACE = pinkSalt("place");
    public static final SoundEvent BLOCK_PINK_SALT_FALL = pinkSalt("fall");
    private static SoundEvent pinkSalt(String type) {
        return block("pink_salt", type);
    }

    public static final SoundEvent BLOCK_PINK_SALT_BRICKS_BREAK = pinkSaltBricks("break");
    public static final SoundEvent BLOCK_PINK_SALT_BRICKS_STEP = pinkSaltBricks("step");
    public static final SoundEvent BLOCK_PINK_SALT_BRICKS_HIT = pinkSaltBricks("hit");
    public static final SoundEvent BLOCK_PINK_SALT_BRICKS_PLACE = pinkSaltBricks("place");
    public static final SoundEvent BLOCK_PINK_SALT_BRICKS_FALL = pinkSaltBricks("fall");
    private static SoundEvent pinkSaltBricks(String type) {
        return block("pink_salt_bricks", type);
    }

    private static SoundEvent block(String block, String type) {
        return register("block." + block + "." + type);
    }
    private static SoundEvent entity(String entity, String type) {
        return register("entity." + entity + "." + type);
    }

    private static SoundEvent register(String name) {
        Identifier id = new Identifier(Marbles.MOD_ID, name);
        SoundEvent event = new SoundEvent(id);
        Registry.register(Registry.SOUND_EVENT, id, event);
        return event;
    }
}
