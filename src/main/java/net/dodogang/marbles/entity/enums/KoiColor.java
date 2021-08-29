package net.dodogang.marbles.entity.enums;

import java.util.Locale;

public enum KoiColor {
    YELLOW,
    WHITE,
    BLACK,
    PINK,
    ORANGE;

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
