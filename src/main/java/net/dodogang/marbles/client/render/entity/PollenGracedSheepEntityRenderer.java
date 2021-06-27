package net.dodogang.marbles.client.render.entity;

import net.dodogang.marbles.client.init.MarblesEntityModelLayers;
import net.dodogang.marbles.client.model.entity.PollenGracedSheepEntityModel;
import net.dodogang.marbles.entity.PollenGracedSheepEntity;
import net.dodogang.marbles.init.MarblesEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PollenGracedSheepEntityRenderer extends MobEntityRenderer<PollenGracedSheepEntity, PollenGracedSheepEntityModel<PollenGracedSheepEntity>> {
    private static final Identifier TEXTURE = MarblesEntities.texture("pollen_graced_sheep/pollen_graced_sheep");

    public PollenGracedSheepEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PollenGracedSheepEntityModel<>(ctx.getPart(MarblesEntityModelLayers.POLLEN_GRACED_SHEEP)), 0.7F);
    }

    @Override
    public Identifier getTexture(PollenGracedSheepEntity sheepEntity) {
        return TEXTURE;
    }
}
