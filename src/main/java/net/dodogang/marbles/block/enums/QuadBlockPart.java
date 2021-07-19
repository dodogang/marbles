package net.dodogang.marbles.block.enums;

import net.minecraft.util.StringIdentifiable;

import java.util.Locale;

public enum QuadBlockPart implements StringIdentifiable {
    FIRST,
    SECOND,
    THIRD,
    FOURTH;

    public static final QuadBlockPart MOST;
    static {
        QuadBlockPart[] parts = QuadBlockPart.values();
        MOST = parts[parts.length - 1];
    }

    @Override
    public String asString() {
        return this.toString().toLowerCase(Locale.ROOT);
    }

    public boolean isLowerThan(QuadBlockPart part) {
        return this.ordinal() < part.ordinal();
    }

    public QuadBlockPart next() {
        QuadBlockPart[] parts = QuadBlockPart.values();
        QuadBlockPart next = parts[this.ordinal() + 1];
        return next == null ? parts[0] : next;
    }
}
