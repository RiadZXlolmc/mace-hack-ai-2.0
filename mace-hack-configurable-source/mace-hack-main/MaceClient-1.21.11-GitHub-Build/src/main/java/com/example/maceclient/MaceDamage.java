package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class MaceDamage extends Module {

    public final DoubleSetting triggerFall =
            new DoubleSetting("Trigger fall", 5.0, 1.0, 20.0, 1.0);

    public final DoubleSetting virtualFall =
            new DoubleSetting("Virtual fall", 200.0, 5.0, 1000.0, 5.0);

    private boolean spoofed = false;

    public MaceDamage() {
        super("MaceDamage", "Makes a real fall appear much larger to the server");
        settings.add(triggerFall);
        settings.add(virtualFall);
    }

    @Override
    public void onDisable(MinecraftClient c) {
        spoofed = false;
    }

    @Override
    public void tick(MinecraftClient c) {
        ClientPlayerEntity player = c.player;

        if (player == null || c.getNetworkHandler() == null) {
            return;
        }

        // Only activate while holding a mace.
        if (!player.getMainHandStack().isOf(Items.MACE)) {
            spoofed = false;
            return;
        }

        // Must actually be falling.
        if (player.isOnGround() || player.getVelocity().y >= 0.0) {
            spoofed = false;
            return;
        }

        // Wait until the configured real fall distance is reached.
        if (player.fallDistance < triggerFall.value) {
            return;
        }

        // Only send the spoof once per fall.
        if (spoofed) {
            return;
        }

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
}
