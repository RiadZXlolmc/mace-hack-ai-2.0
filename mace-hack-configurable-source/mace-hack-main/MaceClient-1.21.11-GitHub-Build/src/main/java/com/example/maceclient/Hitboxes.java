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
        super("Hitboxes", "Expands player target boxes");
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

    public static boolean isActive() {
        return activeExpand > 0.0;
    }

    public static Box getExpandedTargetBox(PlayerEntity target) {
        double amount = activeExpand;
        return target.getBoundingBox().expand(amount, amount, amount);
    }

    public Entity findTarget(MinecraftClient c, double range) {
        if (c.player == null || c.world == null) {
            return null;
        }

        Entity best = null;
        double bestDistance = range * range;

        Box searchBox = c.player.getBoundingBox().expand(range);

        for (Entity entity : c.world.getOtherEntities(c.player, searchBox)) {
            if (!(entity instanceof PlayerEntity)) {
                continue;
            }

            double distance = c.player.squaredDistanceTo(entity);

            if (distance < bestDistance) {
                best = entity;
                bestDistance = distance;
            }
        }

        return best;
    }
}
