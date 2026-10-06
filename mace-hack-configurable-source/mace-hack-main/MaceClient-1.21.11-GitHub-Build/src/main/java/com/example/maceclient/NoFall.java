package com.example.maceclient;

import net.minecraft.client.MinecraftClient;

public class NoFall extends Module {
    public NoFall(){super("NoFall","Movement");}
    public void tick(MinecraftClient c){ if(c.player!=null) c.player.fallDistance=0; }
}
