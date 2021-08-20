package net.dodogang.marbles.entity.enums;

import net.minecraft.util.StringIdentifiable;

public enum KoiSize implements StringIdentifiable {
    SMALL,
    LARGE,
    THICC,
    DUMMY_THICC;

    @Override
    public String asString() {
        return this.toString();
    }
}
