package com.example.maceclient;

import net.minecraft.client.MinecraftClient;

public class FastPlace extends Module {
    public final IntSetting delay = new IntSetting("Tick delay", 0, 0, 5, 1);
    public FastPlace(){super("FastPlace","Player"); settings.add(delay);}
    public void tick(MinecraftClient c){
        // MinecraftClient.itemUseCooldown is private in 1.21.11; changing it needs a mixin.
        // This module currently exposes the intended delay setting without unsafe reflection.
    }
}
