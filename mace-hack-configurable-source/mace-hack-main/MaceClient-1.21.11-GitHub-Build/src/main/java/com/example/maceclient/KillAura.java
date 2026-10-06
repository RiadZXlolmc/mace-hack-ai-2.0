package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;

public class KillAura extends Module {
    public final DoubleSetting range = new DoubleSetting("Range", 4.0, 2.0, 8.0, 0.5);
    public final BooleanSetting playersOnly = new BooleanSetting("Players only", false);
    private int cooldown;
    public KillAura(){super("KillAura","Combat"); settings.add(range); settings.add(playersOnly);}
    public void tick(MinecraftClient c){
        if(c.player==null || c.world==null || c.interactionManager==null) return;
        if(cooldown>0){cooldown--;return;}
        LivingEntity best=null; double bestD=range.value*range.value;
        Box box=c.player.getBoundingBox().expand(range.value);
        for(Entity e:c.world.getOtherEntities(c.player,box)){
            if(!(e instanceof LivingEntity living) || !living.isAlive()) continue;
            if(playersOnly.value && !(living instanceof net.minecraft.entity.player.PlayerEntity)) continue;
            double d=c.player.squaredDistanceTo(living);
            if(d<bestD){best=living;bestD=d;}
        }
        if(best!=null){
            c.interactionManager.attackEntity(c.player,best);
            cooldown=8;
        }
    }
}
