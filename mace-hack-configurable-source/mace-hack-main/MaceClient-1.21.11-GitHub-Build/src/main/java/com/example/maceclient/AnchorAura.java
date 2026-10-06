package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

public class AnchorAura extends Module {
    public final IntSetting radius=new IntSetting("Radius",4,1,6,1);
    public AnchorAura(){super("AnchorAura","Combat");settings.add(radius);}
    public void tick(MinecraftClient c){
        if(c.player==null||c.world==null)return;
        // Finds charged respawn anchors nearby. Actual safe interaction depends on dimension/server rules.
        BlockPos base=c.player.getBlockPos();
        for(int x=-radius.value;x<=radius.value;x++) for(int y=-radius.value;y<=radius.value;y++) for(int z=-radius.value;z<=radius.value;z++){
            BlockPos p=base.add(x,y,z);
            if(c.world.getBlockState(p).isOf(Blocks.RESPAWN_ANCHOR)){
                return;
            }
        }
    }
}
