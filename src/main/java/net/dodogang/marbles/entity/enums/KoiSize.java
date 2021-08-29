package net.dodogang.marbles.entity.enums;

import java.util.Locale;

public enum KoiSize {
    SMALL,
    LARGE,
    THICC,
    DUMMY_THICC;

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
