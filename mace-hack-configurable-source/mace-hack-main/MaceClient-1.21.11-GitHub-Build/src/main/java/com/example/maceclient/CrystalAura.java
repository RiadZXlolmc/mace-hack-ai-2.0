package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.math.Box;

public class CrystalAura extends Module {
    public final DoubleSetting range=new DoubleSetting("Range",5.0,2.0,8.0,0.5);
    public final IntSetting delay=new IntSetting("Delay ticks",6,0,20,1);
    private int cooldown;
    public CrystalAura(){super("CrystalAura","Combat");settings.add(range);settings.add(delay);}
    public void tick(MinecraftClient c){
        if(c.player==null||c.world==null||c.interactionManager==null)return;
        if(cooldown>0){cooldown--;return;}
        EndCrystalEntity best=null;double bd=range.value*range.value;
        Box box=c.player.getBoundingBox().expand(range.value);
        for(Entity e:c.world.getOtherEntities(c.player,box)) if(e instanceof EndCrystalEntity crystal){
            double d=c.player.squaredDistanceTo(crystal);if(d<bd){bd=d;best=crystal;}
        }
        if(best!=null){c.interactionManager.attackEntity(c.player,best);cooldown=delay.value;}
    }
}
