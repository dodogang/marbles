package net.dodogang.marbles.init;

import com.google.common.collect.ImmutableSet;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.mixin.entity.PointOfInterestTypeInvoker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Set;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class MarblesPointOfInterestTypes {
    public static final PointOfInterestType TRAVERTINE_NETHER_PORTAL = register("travertine_nether_portal", getAllStatesOf(MarblesBlocks.TRAVERTINE_NETHER_PORTAL), 0, 1);


    private static PointOfInterestType register(String id, Set<BlockState> workStationStates, int ticketCount, int searchDistance) {
        return PointOfInterestTypeInvoker.register(new Identifier(Marbles.MOD_ID, id).toString(), workStationStates, ticketCount, searchDistance);
    }

    private static PointOfInterestType register(String id, Set<BlockState> workStationStates, int ticketCount, Predicate<PointOfInterestType> completionCondition, int searchDistance) {
        return PointOfInterestTypeInvoker.register(new Identifier(Marbles.MOD_ID, id).toString(), workStationStates, ticketCount, completionCondition, searchDistance);
    }

    private static Set<BlockState> getAllStatesOf(Block block) {
        return ImmutableSet.copyOf(block.getStateManager().getStates());
    }
}
