package net.dodogang.marbles.mixin.hooks;

import net.dodogang.marbles.mixin.MarblesPOIRegistryHelper;
import net.minecraft.block.BlockState;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;
import java.util.function.Predicate;

@SuppressWarnings("unused")
@Mixin(PointOfInterestType.class)
public abstract class PointOfInterestTypeMixin implements MarblesPOIRegistryHelper {
    @Shadow
    private static PointOfInterestType register(String id, Set<BlockState> workStationStates, int ticketCount, int searchDistance) {
        throw new AssertionError();
    }

    @Shadow
    private static PointOfInterestType register(String id, Set<BlockState> workStationStates, int ticketCount, Predicate<PointOfInterestType> completionCondition, int searchDistance) {
        throw new AssertionError();
    }

    @Override
    public PointOfInterestType marbles_register(String id, Set<BlockState> workStationStates, int ticketCount, int searchDistance) {
        return register(id, workStationStates, ticketCount, searchDistance);
    }

    @Override
    public PointOfInterestType marbles_register(String id, Set<BlockState> workStationStates, int ticketCount, Predicate<PointOfInterestType> completionCondition, int searchDistance) {
        return register(id, workStationStates, ticketCount, completionCondition, searchDistance);
    }
}
