package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

public class BoatFly extends Module {
    public final DoubleSetting verticalSpeed = new DoubleSetting("Vertical", 0.50, 0.10, 2.00, 0.10);
    public BoatFly(){ super("BoatFly","Movement"); settings.add(verticalSpeed); }
    public void tick(MinecraftClient c){
        if(c.player==null || !c.player.hasVehicle()) return;
        Entity vehicle=c.player.getVehicle();
        vehicle.setNoGravity(true);
        double y=0;
        if(c.options.jumpKey.isPressed()) y += verticalSpeed.value;
        if(c.options.sneakKey.isPressed()) y -= verticalSpeed.value;
        var v=vehicle.getVelocity();
        vehicle.setVelocity(v.x, y, v.z);
    }
    public void onDisable(MinecraftClient c){
        if(c.player!=null && c.player.hasVehicle()) c.player.getVehicle().setNoGravity(false);
    }
}
