package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;
import net.minecraft.item.Items;

public class FastXP extends Module {
    public final IntSetting delay = new IntSetting("Delay ticks", 1, 0, 10, 1);
    private int timer;
    public FastXP(){super("FastXP","Player"); settings.add(delay);}
    public void tick(MinecraftClient c){
        if(c.player==null || c.interactionManager==null) return;
        if(timer>0){timer--;return;}
        if(!c.player.getMainHandStack().isOf(Items.EXPERIENCE_BOTTLE)) return;
        if(c.options.useKey.isPressed()){
            c.interactionManager.interactItem(c.player, Hand.MAIN_HAND);
            timer=delay.value;
        }
    }
}
