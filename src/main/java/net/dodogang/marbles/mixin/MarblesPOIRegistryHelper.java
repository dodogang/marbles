package net.dodogang.marbles.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Set;
import java.util.function.Predicate;

public interface MarblesPOIRegistryHelper {
    PointOfInterestType marbles_register(String id, Set<BlockState> workStationStates, int ticketCount, int searchDistance);
    PointOfInterestType marbles_register(String id, Set<BlockState> workStationStates, int ticketCount, Predicate<PointOfInterestType> completionCondition, int searchDistance);
}
