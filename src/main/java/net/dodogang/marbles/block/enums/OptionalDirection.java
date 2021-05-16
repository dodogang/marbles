package net.dodogang.marbles.block.enums;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;

@SuppressWarnings("unused")
public enum OptionalDirection implements StringIdentifiable {
    NONE("none", null),
    UP("up", Direction.UP),
    DOWN("down", Direction.DOWN),
    NORTH("north", Direction.NORTH),
    EAST("east", Direction.EAST),
    SOUTH("south", Direction.SOUTH),
    WEST("west", Direction.WEST);

    private final String id;
    private final Direction direction;

    OptionalDirection(String id, Direction direction) {
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

    public static OptionalDirection fromDirection(Direction direction) {
        for (OptionalDirection d : values()) {
            if (d.getDirection() == direction) return d;
        }
        return NONE;
    }
}
