package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;

public class MaceAura extends Module {
    public final DoubleSetting range = new DoubleSetting("Range", 4.0, 2.0, 8.0, 0.5);
    public final DoubleSetting virtualFall = new DoubleSetting("Virtual fall", 1000.0, 3.0, 1000.0, 25.0);
    public final IntSetting delay = new IntSetting("Delay ticks", 8, 0, 20, 1);
    private int cooldown;
    public MaceAura(){super("MaceAura","Combat"); settings.add(range); settings.add(virtualFall); settings.add(delay);}
    public void tick(MinecraftClient c){
        if(c.player==null || c.world==null || c.interactionManager==null) return;
        if(!c.player.getMainHandStack().getItem().toString().toLowerCase().contains("mace")) return;
        if(cooldown>0){cooldown--;return;}
        LivingEntity best=null; double bestD=range.value*range.value;
        Box box=c.player.getBoundingBox().expand(range.value);
        for(Entity e:c.world.getOtherEntities(c.player,box)){
            if(!(e instanceof LivingEntity living) || !living.isAlive()) continue;
            double d=c.player.squaredDistanceTo(living);
            if(d<bestD){best=living;bestD=d;}
        }
        if(best!=null){
            double old=c.player.fallDistance;
            c.player.fallDistance=virtualFall.value>Float.MAX_VALUE?Float.MAX_VALUE:(float)virtualFall.value;
            c.interactionManager.attackEntity(c.player,best);
            c.player.fallDistance=old;
            cooldown=delay.value;
        }
    }
}
