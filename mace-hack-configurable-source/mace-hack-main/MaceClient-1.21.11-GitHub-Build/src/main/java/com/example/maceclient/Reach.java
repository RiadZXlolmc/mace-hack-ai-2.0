package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class Reach extends Module {
    public final DoubleSetting distance = new DoubleSetting("Distance", 6.0, 3.0, 12.0, 0.5);
    private boolean attacking;
    public Reach(){ super("Reach","Combat"); settings.add(distance); }

    public void tick(MinecraftClient c){
        if(c.player==null || c.world==null || c.interactionManager==null) return;
        if(!c.options.attackKey.isPressed() || attacking) return;
        attacking=true;
        try {
            Vec3d start=c.player.getCameraPosVec(1.0f);
            Vec3d dir=c.player.getRotationVec(1.0f);
            Vec3d end=start.add(dir.multiply(distance.value));
            EntityHitResult hit=findEntity(c,start,end);
            if(hit!=null && hit.getEntity()!=c.player)
                c.interactionManager.attackEntity(c.player, hit.getEntity());
        } finally { attacking=false; }
    }

    private EntityHitResult findEntity(MinecraftClient c, Vec3d start, Vec3d end){
        Entity best=null; double bestD=distance.value*distance.value;
        Box search=c.player.getBoundingBox().stretch(end.subtract(start)).expand(1.0);
        for(Entity e:c.world.getOtherEntities(c.player,search)){
            if(!e.isAttackable()) continue;
            var hit=e.getBoundingBox().expand(0.1).raycast(start,end);
            if(hit.isPresent()){
                double d=start.squaredDistanceTo(hit.get());
                if(d<bestD){bestD=d;best=e;}
            }
        }
        return best==null?null:new EntityHitResult(best, start.add(end.subtract(start).normalize().multiply(Math.sqrt(bestD))));
    }
}
