package com.example.maceclient.mixin;

import com.example.maceclient.Hitboxes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(
            method = "findCrosshairTarget",
            at = @At("RETURN"),
            cancellable = true
    )
    private void maceclient$expandPlayerTargets(
            Entity camera,
            double blockInteractionRange,
            double entityInteractionRange,
            float tickDelta,
            CallbackInfoReturnable<HitResult> cir
    ) {
        if (!Hitboxes.isActive()) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || client.world == null) {
            return;
        }

        Vec3d start = camera.getCameraPosVec(tickDelta);
        Vec3d direction = camera.getRotationVec(tickDelta);
        Vec3d end = start.add(direction.multiply(entityInteractionRange));

        Box searchBox = camera.getBoundingBox()
                .stretch(direction.multiply(entityInteractionRange))
                .expand(Hitboxes.activeExpand);

        Entity bestEntity = null;
        Vec3d bestHit = null;
        double bestDistance = Double.MAX_VALUE;

        for (Entity entity : client.world.getOtherEntities(camera, searchBox)) {

            if (!(entity instanceof PlayerEntity player)) {
                continue;
            }

            if (player == client.player) {
                continue;
            }

            Box expandedBox = Hitboxes.getExpandedTargetBox(player);

            Optional<Vec3d> hit = expandedBox.raycast(start, end);

            if (hit.isEmpty()) {
                continue;
            }

            Vec3d hitPos = hit.get();
            double distance = start.squaredDistanceTo(hitPos);

            if (distance < bestDistance) {
                bestEntity = player;
                bestHit = hitPos;
                bestDistance = distance;
            }
        }

        if (bestEntity == null || bestHit == null) {
            return;
        }

        HitResult original = cir.getReturnValue();

        if (original != null
                && original.getType() != HitResult.Type.MISS
                && start.squaredDistanceTo(original.getPos()) < bestDistance) {
            return;
        }

        cir.setReturnValue(new EntityHitResult(bestEntity, bestHit));
    }
}
