package net.dodogang.marbles.entity.ai.goal;

import net.dodogang.marbles.entity.PinkSaltCubeEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.Monster;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

public class SaltCubeSearchForTargetGoal extends ActiveTargetGoal<LivingEntity> {
    public SaltCubeSearchForTargetGoal(PinkSaltCubeEntity shulker) {
        super(shulker, LivingEntity.class, 10, true, false, (entity) -> entity instanceof Monster);
    }

    @Override
    public boolean canStart() {
        return this.mob.getScoreboardTeam() != null && super.canStart();
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
