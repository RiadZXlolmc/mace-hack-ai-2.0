package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class ESP extends Module {
    public final IntSetting range=new IntSetting("Range",64,16,128,16);
    public ESP(){super("ESP","Render");settings.add(range);}
    public void tick(MinecraftClient c){
        // HUD/player-list rendering can be added without touching the working ClickGui render path.
        // The module is active and keeps a configurable range for the next render pass.
    }
}
