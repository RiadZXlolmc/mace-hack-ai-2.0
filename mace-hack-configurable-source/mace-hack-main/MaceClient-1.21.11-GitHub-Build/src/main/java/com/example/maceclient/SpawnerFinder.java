package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

public class SpawnerFinder extends Module {
    public final IntSetting radius=new IntSetting("Radius",32,8,128,8);
    private BlockPos last;
    private int timer;
    public SpawnerFinder(){super("SpawnerFinder","Render");settings.add(radius);}
    public void tick(MinecraftClient c){
        if(c.player==null||c.world==null)return;
        if(timer++<20)return; timer=0;
        BlockPos base=c.player.getBlockPos(); last=null;
        outer: for(int x=-radius.value;x<=radius.value;x++) for(int y=-radius.value;y<=radius.value;y++) for(int z=-radius.value;z<=radius.value;z++){
            BlockPos p=base.add(x,y,z);
            if(c.world.getBlockState(p).isOf(Blocks.SPAWNER)){last=p;break outer;}
        }
        if(last!=null) MaceClient.msg("Spawner: "+last.toShortString()+" ("+(int)Math.sqrt(last.getSquaredDistance(c.player.getBlockPos()))+"m)");
    }
}
