package net.dodogang.marbles.client.init;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.entity.BouncerEntity;
import net.dodogang.marbles.entity.PollenGracedSheepEntity;
import net.dodogang.marbles.mixin.hooks.client.EntityModelLayersInvoker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MarblesEntityModelLayers {
    public static final EntityModelLayer BOUNCER = registerMain(BouncerEntity.id);
    public static final EntityModelLayer POLLEN_GRACED_SHEEP = registerMain(PollenGracedSheepEntity.id);

    private static EntityModelLayer registerMain(String id) {
        return EntityModelLayersInvoker.register(new Identifier(Marbles.MOD_ID, id).toString(), "main");
    }
}
