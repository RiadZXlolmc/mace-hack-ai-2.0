package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.HitResult;

public class ClickTP extends Module {
    public ClickTP(){super("ClickTP","Movement");}
    public void tick(MinecraftClient c){
        if(c.player!=null && c.crosshairTarget!=null && c.crosshairTarget.getType()==HitResult.Type.BLOCK && c.options.attackKey.isPressed()){
            var p=c.crosshairTarget.getPos(); c.player.setPosition(p.x,p.y+1,p.z);
        }
    }
}
