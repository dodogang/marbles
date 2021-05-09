package net.dodogang.marbles.client.render.entity;

import net.dodogang.marbles.client.model.entity.PollenGracedSheepEntityModel;
import net.dodogang.marbles.entity.PollenGracedSheepEntity;
import net.dodogang.marbles.init.MarblesEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class PollenGracedSheepEntityRenderer extends MobEntityRenderer<PollenGracedSheepEntity, PollenGracedSheepEntityModel<PollenGracedSheepEntity>> {
    private static final Identifier TEXTURE = MarblesEntities.texture(PollenGracedSheepEntity.id + "/" + PollenGracedSheepEntity.id);

    @SuppressWarnings("unused")
    public PollenGracedSheepEntityRenderer(EntityRenderDispatcher dispatcher, @Nullable EntityRendererRegistry.Context ctx) {
        super(dispatcher, new PollenGracedSheepEntityModel<>(), 0.7F);
    }

    @Override
    public Identifier getTexture(PollenGracedSheepEntity sheepEntity) {
        return TEXTURE;
    }
}
