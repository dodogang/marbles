package net.dodogang.marbles.mixin.entity;

import net.minecraft.block.BlockState;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;
import java.util.function.Predicate;

@SuppressWarnings("unused")
@Mixin(PointOfInterestType.class)
public interface PointOfInterestTypeInvoker {
    @Invoker(value = "register", remap = false)
    static PointOfInterestType register(String id, Set<BlockState> workStationStates, int ticketCount, int searchDistance) {
        throw new AssertionError();
    }

    @Invoker(value = "register", remap = false)
    static PointOfInterestType register(String id, Set<BlockState> workStationStates, int ticketCount, Predicate<PointOfInterestType> completionCondition, int searchDistance) {
        throw new AssertionError();
    }
}
