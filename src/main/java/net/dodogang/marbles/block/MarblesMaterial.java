package net.dodogang.marbles.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;

public class MarblesMaterial {
    public static final Material PINK_SALT_ROCK = new FabricMaterialBuilder(MapColor.ORANGE)
                                                       .lightPassesThrough()
                                                       .destroyedByPiston()
                                                       .build();
}
