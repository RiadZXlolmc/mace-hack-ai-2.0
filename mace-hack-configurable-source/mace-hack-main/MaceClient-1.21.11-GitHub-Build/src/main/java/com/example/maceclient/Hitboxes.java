package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

public class Hitboxes extends Module {

    public static double activeExpand = 0.0;

    public final DoubleSetting expand =
            new DoubleSetting("Expand", 0.20, 0.0, 1.0, 0.05);

    public Hitboxes() {
        super("Hitboxes", "Expands player hitboxes");
        settings.add(expand);
    }

    @Override
    public void onEnable(MinecraftClient c) {
        activeExpand = expand.value;
    }

    @Override
    public void onDisable(MinecraftClient c) {
        activeExpand = 0.0;
    }

    @Override
    public void tick(MinecraftClient c) {
        activeExpand = expand.value;
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

    public static boolean isActive() {
        return activeExpand > 0.0;
    }

    public static Box expandBox(Entity entity, Box original) {
        if (!isActive()) {
            return original;
        }

        if (!(entity instanceof PlayerEntity)) {
            return original;
        }

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || entity == client.player) {
            return original;
        }

        double amount = activeExpand;

        return original.expand(amount, amount, amount);
    }
}
