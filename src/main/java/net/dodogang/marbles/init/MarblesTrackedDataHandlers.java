package net.dodogang.marbles.init;

import net.dodogang.marbles.entity.data.EnumTrackedDataHandler;
import net.dodogang.marbles.entity.enums.KoiSize;
import net.dodogang.marbles.entity.enums.KoiVariant;
import net.dodogang.marbles.mixin.entity.TrackedDataHandlerRegistryAccessor;
import net.minecraft.entity.data.TrackedDataHandler;

import java.util.ArrayList;

public class MarblesTrackedDataHandlers {
    private static final ArrayList<TrackedDataHandler<?>> HANDLERS = new ArrayList<>();

    // ---

    public static final TrackedDataHandler<KoiSize> KOI_SIZE = register(new EnumTrackedDataHandler<>(KoiSize.class));
    public static final TrackedDataHandler<KoiVariant> KOI_VARIANT = register(new EnumTrackedDataHandler<>(KoiVariant.class));

    // ---

    static {
        HANDLERS.forEach(TrackedDataHandlerRegistryAccessor.getDataHandlers()::add);
    }

    public static <T> TrackedDataHandler<T> register(TrackedDataHandler<T> handler) {
        HANDLERS.add(handler);
        return handler;
    }
}
