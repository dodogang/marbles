package net.dodogang.marbles.state.property;

import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.Direction;

public class MarblesProperties {
    /*
     * A property that specifies the vertical direction of a block.
     */
    public static final DirectionProperty VERTICAL_DIRECTION = DirectionProperty.of("vertical_direction", Direction.UP, Direction.DOWN);
    /*
     * A property that specifies the vertical direction of a block.
     */
    public static final IntProperty RETAINED_LIGHT = IntProperty.of("retained_light", 0, 15);
}
