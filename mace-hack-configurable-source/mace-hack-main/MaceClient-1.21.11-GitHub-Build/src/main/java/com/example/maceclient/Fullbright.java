package com.example.maceclient;

import net.minecraft.client.MinecraftClient;

public class Fullbright extends Module {
    private Double oldGamma;
    public Fullbright(){super("Fullbright","Render");}
    public void onEnable(MinecraftClient c){
        oldGamma=c.options.getGamma().getValue();
        c.options.getGamma().setValue(16.0);
    }
    public void tick(MinecraftClient c){ if(c.options.getGamma().getValue()<15.9) c.options.getGamma().setValue(16.0); }
    public void onDisable(MinecraftClient c){
        if(oldGamma!=null) c.options.getGamma().setValue(oldGamma);
    }
}
