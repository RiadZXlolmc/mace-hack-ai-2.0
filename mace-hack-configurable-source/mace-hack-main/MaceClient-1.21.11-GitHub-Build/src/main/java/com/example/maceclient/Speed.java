package com.example.maceclient;

import net.minecraft.client.MinecraftClient;

public class Speed extends Module {
    public final DoubleSetting multiplier = new DoubleSetting("Multiplier", 1.35, 1.05, 3.00, 0.05);
    public Speed(){ super("Speed","Movement"); settings.add(multiplier); }
    public void tick(MinecraftClient c){
        if(c.player==null || !c.player.isOnGround()) return;
        var v=c.player.getVelocity();
        if(c.player.forwardSpeed!=0 || c.player.sidewaysSpeed!=0)
            c.player.setVelocity(v.x*multiplier.value,v.y,v.z*multiplier.value);
    }
}
