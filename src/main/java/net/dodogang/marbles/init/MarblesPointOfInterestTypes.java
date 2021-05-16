package net.dodogang.marbles.init;

import com.google.common.collect.ImmutableSet;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.mixin.MarblesPOIRegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Set;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class MarblesPointOfInterestTypes {
    public static final PointOfInterestType TRAVERTINE_PORTAL = register("travertine_portal", getAllStatesOf(MarblesBlocks.TRAVERTINE_PORTAL), 0, 1);


    private static PointOfInterestType register(String id, Set<BlockState> workStationStates, int ticketCount, int searchDistance) {
        return ((MarblesPOIRegistryHelper) PointOfInterestType.UNEMPLOYED).marbles_register(Marbles.MOD_ID + ":" + id, workStationStates, ticketCount, searchDistance);
    }

    private static PointOfInterestType register(String id, Set<BlockState> workStationStates, int ticketCount, Predicate<PointOfInterestType> completionCondition, int searchDistance) {
        return ((MarblesPOIRegistryHelper) PointOfInterestType.UNEMPLOYED).marbles_register(Marbles.MOD_ID + ":" + id, workStationStates, ticketCount, completionCondition, searchDistance);
    }

    private static Set<BlockState> getAllStatesOf(Block block) {
        return ImmutableSet.copyOf(block.getStateManager().getStates());
    }
}
