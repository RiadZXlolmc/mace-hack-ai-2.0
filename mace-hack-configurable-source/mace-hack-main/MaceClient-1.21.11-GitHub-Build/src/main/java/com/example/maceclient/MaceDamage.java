package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Box;

public class MaceDamage extends Module {

    public final DoubleSetting triggerFall =
            new DoubleSetting("Trigger fall", 5.0, 1.0, 20.0, 1.0);

    public final DoubleSetting virtualFall =
            new DoubleSetting("Virtual fall", 200.0, 5.0, 1000.0, 5.0);

    public final DoubleSetting range =
            new DoubleSetting("Attack range", 4.0, 2.0, 6.0, 0.5);

    private boolean spoofed = false;

    public MaceDamage() {
        super("MaceDamage", "Turns a real fall into a larger server-side fall");
        settings.add(triggerFall);
        settings.add(virtualFall);
        settings.add(range);
    }

    @Override
    public void onDisable(MinecraftClient c) {
        spoofed = false;
    }

    @Override
    public void tick(MinecraftClient c) {
        ClientPlayerEntity player = c.player;

        if (player == null || c.world == null || c.interactionManager == null) {
            return;
        }

        // Must be holding a mace.
        if (!player.getMainHandStack().isOf(Items.MACE)) {
            spoofed = false;
            return;
        }

        // We only want this while actually falling.
        if (player.isOnGround() || player.getVelocity().y >= 0.0) {
            spoofed = false;
            return;
        }

        // Don't activate until the configured real fall distance.
        if (player.fallDistance < triggerFall.value) {
            return;
        }

        /*
         * Send an airborne movement packet with a much larger Y displacement.
         *
         * The server receives movement packets rather than reading our local
         * fallDistance field, so this is the part intended to make the server
         * see the larger fall.
         */
        if (!spoofed) {
            double virtualY = player.getY() - virtualFall.value;

            c.getNetworkHandler().sendPacket(
                    new PlayerMoveC2SPacket.PositionAndOnGround(
                            player.getX(),
                            virtualY,
                            player.getZ(),
                            false,
                            false
                    )
            );

            spoofed = true;
        }

        // Wait for the normal attack cooldown.
        if (player.getAttackCooldownProgress(0.0f) < 1.0f) {
            return;
        }

        LivingEntity target = findTarget(c, range.value);

        if (target != null) {
            c.interactionManager.attackEntity(player, target);

            // Reset so the next real fall can trigger it again.
            spoofed = false;
        }
    }

    private LivingEntity findTarget(MinecraftClient c, double attackRange) {
        ClientPlayerEntity player = c.player;

        Box searchBox = player.getBoundingBox().expand(attackRange);

        LivingEntity best = null;
        double bestDistance = attackRange * attackRange;

        for (Entity entity : c.world.getOtherEntities(player, searchBox)) {

            if (!(entity instanceof LivingEntity living)) {
                continue;
            }

            if (!living.isAlive()) {
                continue;
            }

            if (living instanceof PlayerEntity && living == player) {
                continue;
            }

            double distance = player.squaredDistanceTo(living);

            if (distance < bestDistance) {
                best = living;
                bestDistance = distance;
            }
        }

        return best;
    }
}
