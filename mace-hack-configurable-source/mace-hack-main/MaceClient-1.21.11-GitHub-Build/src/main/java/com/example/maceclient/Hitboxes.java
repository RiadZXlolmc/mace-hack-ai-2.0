package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;

public class Hitboxes extends Module {

    public final DoubleSetting expand =
            new DoubleSetting("Expand", 0.20, 0.0, 1.0, 0.05);

    public Hitboxes() {
        super("Hitboxes", "Expands nearby entity hitboxes");
        settings.add(expand);
    }

    public Entity findTarget(MinecraftClient c, double range) {
        if (c.player == null || c.world == null) {
            return null;
        }

        Entity best = null;
        double bestD = range * range;

        Box box = c.player.getBoundingBox().expand(range);

        for (Entity e : c.world.getOtherEntities(c.player, box)) {
            if (!e.isAttackable()) {
                continue;
            }

            double d = c.player.squaredDistanceTo(e);

            if (d < bestD) {
                best = e;
                bestD = d;
            }
        }

        return best;
    }
}
