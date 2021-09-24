package net.dodogang.marbles.entity.ai.goal;


import net.dodogang.marbles.entity.PinkSaltCubeEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Difficulty;

public class SaltCubeSearchForPlayerGoal extends ActiveTargetGoal<PlayerEntity> {
    public SaltCubeSearchForPlayerGoal(PinkSaltCubeEntity mob) {
        super(mob, PlayerEntity.class, true);
    }

    @Override
    public boolean canStart() {
        return this.mob.world.getDifficulty() != Difficulty.PEACEFUL && super.canStart();
    }

    @Override
    protected Box getSearchBox(double distance) {
        Direction direction = ((PinkSaltCubeEntity)this.mob).getAttachedFace();
        if (direction.getAxis() == Direction.Axis.X) {
            return this.mob.getBoundingBox().expand(4.0D, distance, distance);
        } else {
            return direction.getAxis() == Direction.Axis.Z ? this.mob.getBoundingBox().expand(distance, distance, 4.0D) : this.mob.getBoundingBox().expand(distance, 4.0D, distance);
        }
    }
}
