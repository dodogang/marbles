package net.dodogang.marbles.entity.enums;

import net.minecraft.util.StringIdentifiable;

public enum KoiVariant implements StringIdentifiable {
    YELLOW,
    WHITE,
    BLACK,
    PINK,
    ORANGE;

    @Override
    public String asString() {
        return this.toString();
    }
}
