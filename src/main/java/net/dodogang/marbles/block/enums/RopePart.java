package net.dodogang.marbles.block.enums;

import net.minecraft.util.StringIdentifiable;

import java.util.Locale;

public enum RopePart implements StringIdentifiable {
    START,
    MIDDLE,
    END;

    @Override
    public String asString() {
        return this.toString().toLowerCase(Locale.ROOT);
    }
}
