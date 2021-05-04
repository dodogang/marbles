package net.dodogang.marbles.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Direction;

import java.util.Random;

public class Util {
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

    /**
     * @see <a href="https://stackoverflow.com/questions/17936619/efficient-bounded-biased-random-number-generator">Stack Overflow</a>
     */
    public static double getBiasedRandom(double bias, double min, double max, Random random) {
        double rndBiased;
        double centered_depth_perc = 0.3d;
        double centered_depth_abs = (max - min) * centered_depth_perc;
        double center = (min + max) / 2;

        double rndCentered = center  + random.nextGaussian() * centered_depth_abs; // generate centered random number.

        if (rndCentered >= center) {
            rndBiased = (rndCentered - center) * (max - bias) + bias;
        }
        else {
            rndBiased = bias - (center - rndCentered) * (bias - min);
        }

        // the following two tests will be as more important as centered_depth_perc
        // get bigger.
        if (rndBiased > max)
            rndBiased = max;

        if (rndBiased < min)
            rndBiased = min;

        return rndBiased;
    }
}
