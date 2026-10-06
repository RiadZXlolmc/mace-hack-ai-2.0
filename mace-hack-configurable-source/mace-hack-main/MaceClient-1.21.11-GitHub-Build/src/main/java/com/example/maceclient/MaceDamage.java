package com.example.maceclient;

import net.minecraft.client.MinecraftClient;

public class MaceDamage extends Module {
    public final DoubleSetting virtualHeight = new DoubleSetting("Virtual fall", 100.0, 3.0, 1000.0, 10.0);
    public MaceDamage(){ super("MaceDamage","Combat"); settings.add(virtualHeight); }
    public void tick(MinecraftClient c){
        if(c.player==null) return;
        if(!c.player.getMainHandStack().getItem().toString().toLowerCase().contains("mace")) return;
        // This only changes the client-side fall-distance value. A server may ignore it.
        if(c.player.fallDistance > 2) c.player.fallDistance=(float)Math.max(c.player.fallDistance, virtualHeight.value);
    }
}
