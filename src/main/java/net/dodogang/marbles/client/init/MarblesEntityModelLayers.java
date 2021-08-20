package net.dodogang.marbles.client.init;

import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.mixin.client.entity.EntityModelLayersInvoker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MarblesEntityModelLayers {
    public static final EntityModelLayer BOUNCER = registerMain("bouncer");
    public static final EntityModelLayer POLLEN_GRACED_SHEEP = registerMain("pollen_graced_sheep");

    public static final EntityModelLayer KOI_SMALL = registerMain("koi_small");
    public static final EntityModelLayer KOI_LARGE = registerMain("koi_large");
    public static final EntityModelLayer KOI_THICC = registerMain("koi_thicc");
    public static final EntityModelLayer KOI_DUMMY_THICC = registerMain("koi_dummy_thicc");

    private static EntityModelLayer registerMain(String id) {
        return EntityModelLayersInvoker.invoke_register(new Identifier(Marbles.MOD_ID, id).toString(), "main");
    }
}
