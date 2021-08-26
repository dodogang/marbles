package net.dodogang.marbles.entity.data;

import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.network.PacketByteBuf;

public record EnumTrackedDataHandler<T extends Enum<T>>(Class<T> clazz) implements TrackedDataHandler<T> {
    @Override
    public void write(PacketByteBuf buf, T variant) {
        buf.writeString(variant.name());
    }

    @Override
    public T read(PacketByteBuf buf) {
        return T.valueOf(this.clazz, buf.readString());
    }

    @Override
    public T copy(T variant) {
        return variant;
    }
}
