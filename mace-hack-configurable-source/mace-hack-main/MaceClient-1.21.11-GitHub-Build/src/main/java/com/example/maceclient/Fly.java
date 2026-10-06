package com.example.maceclient;

import net.minecraft.client.MinecraftClient;

public class Fly extends Module {
    public final DoubleSetting speed = new DoubleSetting("Speed", 0.60, 0.10, 3.00, 0.10);
    public Fly(){ super("Fly","Movement"); settings.add(speed); }

    public void tick(MinecraftClient c){
        if(c.player==null) return;
        c.player.setNoGravity(true);
        double y = 0;
        if(c.options.jumpKey.isPressed()) y += speed.value;
        if(c.options.sneakKey.isPressed()) y -= speed.value;
        var v = c.player.getVelocity();
        c.player.setVelocity(v.x, y, v.z);
    }
    public void onDisable(MinecraftClient c){
        if(c.player!=null){
            c.player.setNoGravity(false);
            var v=c.player.getVelocity();
            c.player.setVelocity(v.x, 0, v.z);
        }
    }
}
