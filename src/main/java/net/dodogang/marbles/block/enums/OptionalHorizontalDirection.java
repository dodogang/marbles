package net.dodogang.marbles.block.enums;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;

@SuppressWarnings("unused")
public enum OptionalHorizontalDirection implements StringIdentifiable {
    NONE("none", null),
    NORTH("north", Direction.NORTH),
    EAST("east", Direction.EAST),
    SOUTH("south", Direction.SOUTH),
    WEST("west", Direction.WEST);

    private final String id;
    private final Direction direction;

    OptionalHorizontalDirection(String id, Direction direction) {
        this.id = id;
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isPresent() {
        return direction != null;
    }

    @Override
    public String asString() {
        return id;
    }

    public static OptionalHorizontalDirection fromDirection(Direction direction) {
        for (OptionalHorizontalDirection d : values()) {
            if (d.getDirection() == direction) return d;
        }
        return NONE;
    }
}
