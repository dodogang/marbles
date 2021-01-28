package net.dodogang.marbles.state.property;

import net.dodogang.marbles.block.enums.SpirePart;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.Direction;

public class MarblesProperties {
    /*
     * A property that specifies the part of a spire block.
     */
    public static final EnumProperty<SpirePart> SPIRE_PART = EnumProperty.of("spire_part", SpirePart.class);
    /*
     * A property that specifies the vertical direction of a block.
     */
    public static final DirectionProperty VERTICAL_DIRECTION = DirectionProperty.of("vertical_direction", Direction.UP, Direction.DOWN);
    /*
     * A property that specifies the vertical direction of a block.
     */
    public static final IntProperty RETAINED_LIGHT = IntProperty.of("retained_light", 0, 15);
}
