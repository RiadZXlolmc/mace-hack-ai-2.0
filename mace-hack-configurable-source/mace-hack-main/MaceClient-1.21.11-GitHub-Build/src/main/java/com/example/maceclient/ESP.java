package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class ESP extends Module {

    public final IntSetting range = new IntSetting("Range", 64, 16, 128, 16);

    public ESP() {
        super("ESP", "Highlights nearby players", true);
        settings.add(range);
    }

    @Override
    public void tick(MinecraftClient c) {
        if (c.player == null || c.world == null) {
            return;
        }

        double maxDistance = range.value;

        for (PlayerEntity player : c.world.getPlayers()) {

            // Don't highlight yourself.
            if (player == c.player) {
                continue;
            }

            double distance = c.player.distanceTo(player);

            // Highlight players inside the selected range.
            if (distance <= maxDistance) {
                player.setGlowing(true);
            } else {
                player.setGlowing(false);
            }
        }
    }
}
