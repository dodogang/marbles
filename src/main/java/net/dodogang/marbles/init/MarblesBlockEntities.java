package net.dodogang.marbles.init;

import com.mojang.datafixers.types.Type;
import net.dodogang.marbles.Marbles;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class MarblesBlockEntities {
    private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder<T> builder) {
        Identifier identifier = new Identifier(Marbles.MOD_ID, id);

        Type<?> type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, identifier.toString());
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, identifier, builder.build(type));
    }
}
