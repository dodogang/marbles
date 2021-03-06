package net.dodogang.marbles.state.property;

import net.dodogang.marbles.block.RopeBlock;
import net.dodogang.marbles.block.enums.OptionalDirection;
import net.dodogang.marbles.block.enums.OptionalHorizontalDirection;
import net.dodogang.marbles.block.enums.QuadBlockPart;
import net.dodogang.marbles.block.enums.RopePart;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.Direction;

public class MarblesProperties {
    /**
     * A property that specifies the vertical direction of a block.
     */
    public static final DirectionProperty VERTICAL_DIRECTION = DirectionProperty.of("vertical_direction", Direction.UP, Direction.DOWN);
    /**
     * A property that specifies the part of a quad block.
     */
    public static final EnumProperty<QuadBlockPart> QUAD_BLOCK_PART = EnumProperty.of("quad", QuadBlockPart.class);
    /**
     * A property that specifies the vertical direction of a block.
     */
    public static final IntProperty RETAINED_LIGHT = IntProperty.of("retained_light", 0, 15);
    /**
     * A property that defines how a block connects to something, or doesn't.
     */
    public static final EnumProperty<OptionalDirection> CONNECTION = EnumProperty.of("connection", OptionalDirection.class);
    /**
     * A property that defines how a block connects to something, or doesn't.
     */
    public static final EnumProperty<OptionalHorizontalDirection> HORIZONTAL_CONNECTION = EnumProperty.of("connection", OptionalHorizontalDirection.class);
    /**
     * A property that defines the part of a {@link RopeBlock}.
     */
    public static final EnumProperty<RopePart> ROPE_PART = EnumProperty.of("rope_part", RopePart.class);
}
