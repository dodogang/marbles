package net.dodogang.marbles.mixin.hooks;

import net.minecraft.entity.ai.goal.EatGrassGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EatGrassGoal.class)
public interface EatGrassGoalAccessor {
    @Accessor("timer")
    int getTimer();
    @Accessor("timer")
    void setTimer(int timer);
}
