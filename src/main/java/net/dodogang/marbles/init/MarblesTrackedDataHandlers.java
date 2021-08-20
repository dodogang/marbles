package net.dodogang.marbles.init;

import net.dodogang.marbles.entity.enums.KoiSize;
import net.dodogang.marbles.mixin.entity.TrackedDataHandlerRegistryAccessor;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.HashMap;

public class MarblesTrackedDataHandlers {
    private static final HashMap<Integer, TrackedDataHandler<?>> HANDLERS = new HashMap<>();
    private static int previousId = 74674708;

    // ---

    public static final TrackedDataHandler<KoiSize> KOI_SIZE = register(
        new TrackedDataHandler<>() {
            @Override
            public void write(PacketByteBuf buf, KoiSize size) {
                buf.writeString(size.asString());
            }

            @Override
            public KoiSize read(PacketByteBuf buf) {
                return KoiSize.valueOf(buf.readString());
            }

            @Override
            public KoiSize copy(KoiSize size) {
                return size;
            }
        }
    );

    // ---

    static {
        HANDLERS.forEach((id, trackedDataHandler) -> TrackedDataHandlerRegistryAccessor.getDataHandlers().put(trackedDataHandler, id));
    }

    public static <T> TrackedDataHandler<T> register(TrackedDataHandler<T> trackedDataHandler) {
        HANDLERS.put(previousId++, trackedDataHandler);
        return trackedDataHandler;
    }
}
