package net.dodogang.marbles.client.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dodogang.marbles.block.SpotlightBlock;
import net.dodogang.marbles.util.SpotlightUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.world.World;
import net.shadew.debug.api.render.DebugView;

@Environment(EnvType.CLIENT)
public class SpotlightDebugView implements DebugView {
    public static boolean enabled = false;

    @Override
    public void clear() {

    }

    @Override
    @SuppressWarnings("deprecation")
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;

        BlockPos playerPos = client.player.getBlockPos();
        World world = client.player.world;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(0, 1, 0, 0.75f);
        RenderSystem.disableTexture();
        RenderSystem.lineWidth(1);
        VertexConsumer buff = vertexConsumers.getBuffer(RenderLayer.getLines());

        matrices.push();
        matrices.translate(-cameraX, -cameraY, -cameraZ);

        Matrix4f matrix = matrices.peek().getModel();
        for (BlockPos pos : BlockPos.iterate(playerPos.add(-8, -8, -8), playerPos.add(8, 8, 8))) {
            BlockState state = world.getBlockState(pos);

            if (state.getBlock() instanceof SpotlightBlock) {
                WorldRenderer.drawBox(
                    matrices, buff,
                    pos.getX(), pos.getY(), pos.getZ(),
                    pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1,
                    0, 1, 1, 1
                );

                Direction dir = state.get(SpotlightBlock.FACING);

                float x1 = pos.getX() + 0.5f;
                float y1 = pos.getY() + 0.5f;
                float z1 = pos.getZ() + 0.5f;
                float x2 = dir.getOffsetX() * 0.8f + x1;
                float y2 = dir.getOffsetY() * 0.8f + y1;
                float z2 = dir.getOffsetZ() * 0.8f + z1;

                buff.vertex(matrix, x1, y1, z1).color(0f, 1f, 1f, 1f).next();
                buff.vertex(matrix, x2, y2, z2).color(0f, 1f, 1f, 1f).next();
            }

            int spotlight = SpotlightUtil.getSpotlightData(world, pos);
            for (Direction direction : Direction.values()) {
                int level = SpotlightUtil.getSpotlightValue(spotlight, direction);

                if (level > 0) {
                    float color = level / 31f * 0.8f + 0.2f;

                    float x1 = pos.getX() + 0.5f;
                    float y1 = pos.getY() + 0.5f;
                    float z1 = pos.getZ() + 0.5f;
                    float x2 = direction.getOffsetX() * 0.4f + x1;
                    float y2 = direction.getOffsetY() * 0.4f + y1;
                    float z2 = direction.getOffsetZ() * 0.4f + z1;

                    buff.vertex(matrix, x1, y1, z1).color(color, color, 0f, 1f).next();
                    buff.vertex(matrix, x2, y2, z2).color(color, color, 0f, 1f).next();

                    DebugRenderer.drawString(
                        String.valueOf(level),
                        x2, y2, z2,
                        0xFFFFFF00
                    );
                }
            }
        }

        matrices.pop();

        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
