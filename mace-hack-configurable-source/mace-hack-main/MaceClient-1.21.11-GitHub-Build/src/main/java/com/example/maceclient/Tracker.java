package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class Tracker extends Module {
    public final IntSetting delay=new IntSetting("Update ticks",20,5,100,5);
    private int timer;
    public Tracker(){super("Tracker","Render");settings.add(delay);}
    public void tick(MinecraftClient c){
        if(c.player==null||c.world==null)return;
        if(timer++<delay.value)return; timer=0;
        PlayerEntity best=null;double d=Double.MAX_VALUE;
        for(PlayerEntity p:c.world.getPlayers()) if(p!=c.player){
            double x=p.squaredDistanceTo(c.player);if(x<d){d=x;best=p;}
        }
        if(best!=null) MaceClient.msg("Nearest: "+best.getName().getString()+" ("+(int)Math.sqrt(d)+"m)");
    }
}
