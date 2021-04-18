package net.dodogang.marbles.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Direction;

public class Utils {
    public static Direction getLookDirectionForAxis(Entity entity, Direction.Axis axis) {
        switch (axis) {
            case X:
                return Direction.EAST.method_30928(entity.getYaw(1.0F)) ? Direction.EAST : Direction.WEST;
            case Z:
                return Direction.SOUTH.method_30928(entity.getYaw(1.0F)) ? Direction.SOUTH : Direction.NORTH;
            case Y:
            default:
                return entity.getPitch(1.0F) < 0.0F ? Direction.UP : Direction.DOWN;
        }
    }

}
