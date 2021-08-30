package net.dodogang.marbles.entity.ai.goal;

import com.mojang.datafixers.DataFixUtils;
import net.dodogang.marbles.entity.KoiFishEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.List;
import java.util.function.Predicate;

public class KoiFollowGroupLeaderGoal extends Goal {
    private static final int MIN_SEARCH_DELAY = 200;
    private final KoiFishEntity mob;
    private int moveDelay;
    private int checkSurroundingDelay;

    public KoiFollowGroupLeaderGoal(KoiFishEntity mob) {
        this.mob = mob;
        this.checkSurroundingDelay = this.getSurroundingSearchDelay(mob);
    }

    protected int getSurroundingSearchDelay(KoiFishEntity mob) {
        return MIN_SEARCH_DELAY + mob.getRandom().nextInt(MIN_SEARCH_DELAY) % 20;
    }

    @Override
    public boolean canStart() {
        if (this.mob.hasOtherFishInGroup()) {
            return false;
        } else if (this.mob.hasLeader()) {
            return true;
        } else if (this.checkSurroundingDelay > 0) {
            --this.checkSurroundingDelay;
            return false;
        } else {
            // update check delay
            this.checkSurroundingDelay = this.getSurroundingSearchDelay(this.mob);

            // list mobs to pull in
            Predicate<KoiFishEntity> predicate = (e) -> this.mob.getSize().ordinal() > e.getSize().ordinal() && (e.canHaveMoreFishInGroup() || !e.hasLeader());

            List<? extends KoiFishEntity> list = this.mob.world.getEntitiesByClass(this.mob.getClass(), this.mob.getBoundingBox().expand(8.0D, 8.0D, 8.0D), predicate);
            KoiFishEntity leader = DataFixUtils.orElse(list.stream().filter(KoiFishEntity::canHaveMoreFishInGroup).findAny(), this.mob);
            leader.pullInOtherFish(list.stream().filter((e) -> this.mob.getSize().ordinal() > e.getSize().ordinal() && !e.hasLeader()));

            return this.mob.hasLeader();
        }
    }

    @Override
    public boolean shouldContinue() {
        return this.mob.hasLeader() && this.mob.isCloseEnoughToLeader();
    }

    @Override
    public void start() {
        this.moveDelay = 0;
    }

    @Override
    public void stop() {
        this.mob.leaveGroup();
    }

    @Override
    public void tick() {
        if (--this.moveDelay <= 0) {
            this.moveDelay = 10;
            this.mob.moveTowardLeader();
        }
    }
}
