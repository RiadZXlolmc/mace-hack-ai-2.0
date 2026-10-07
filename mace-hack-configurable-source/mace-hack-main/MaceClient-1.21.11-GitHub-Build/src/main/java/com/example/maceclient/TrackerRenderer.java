package com.example.maceclient;

import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
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

            if (matrices == null) {
                return;
            }

            VertexConsumerProvider consumers = context.consumers();

            if (consumers == null) {
                return;
            }

            VertexConsumer vertices =
                    consumers.getBuffer(RenderLayer.getLines());

            Vec3d camera = context.camera().getPos();

            matrices.push();
            matrices.translate(-camera.x, -camera.y, -camera.z);

            Vec3d start = client.player.getCameraPosVec(1.0f);

            for (PlayerEntity player : client.world.getPlayers()) {
                if (player == client.player) {
                    continue;
                }

                Vec3d end = new Vec3d(
                        player.getX(),
                        player.getY() + player.getHeight() * 0.5,
                        player.getZ()
                );

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
