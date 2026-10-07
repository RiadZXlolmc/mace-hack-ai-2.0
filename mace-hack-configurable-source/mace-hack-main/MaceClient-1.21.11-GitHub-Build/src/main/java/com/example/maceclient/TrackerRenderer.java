package com.example.maceclient;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public final class TrackerRenderer {

    private TrackerRenderer() {
    }

    public static void init() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            MinecraftClient client = MinecraftClient.getInstance();

            if (client.player == null || client.world == null) {
                return;
            }

            Tracker tracker = MaceClient.getModuleManager().tracker;

            if (!tracker.enabled) {
                return;
            }

            MatrixStack matrices = context.matrixStack();
            Vec3d camera = context.camera().getPos();

            VertexConsumer vertices =
                    context.consumers().getBuffer(RenderLayer.getLines());

            matrices.push();
            matrices.translate(-camera.x, -camera.y, -camera.z);

            for (PlayerEntity player : client.world.getPlayers()) {
                if (player == client.player) {
                    continue;
                }

                Vec3d start = client.player.getCameraPosVec(1.0f);
                Vec3d end = player.getPos().add(0.0, player.getHeight() * 0.5, 0.0);

                vertices.vertex(
                        matrices.peek().getPositionMatrix(),
                        (float) start.x,
                        (float) start.y,
                        (float) start.z
                ).color(1.0f, 0.0f, 0.0f, 1.0f);

                vertices.vertex(
                        matrices.peek().getPositionMatrix(),
                        (float) end.x,
                        (float) end.y,
                        (float) end.z
                ).color(1.0f, 0.0f, 0.0f, 1.0f);
            }

            matrices.pop();
        });
    }
}
