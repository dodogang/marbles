package net.dodogang.marbles.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;

public class MarblesMaterial {
    public static final Material PINK_SALT_ROCK = new FabricMaterialBuilder(MaterialColor.ORANGE)
                                                       .lightPassesThrough()
                                                       .destroyedByPiston()
                                                       .build();
}
