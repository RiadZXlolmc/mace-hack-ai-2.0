package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;

public class AutoTotem extends Module {
    public AutoTotem(){super("AutoTotem","Combat");}
    public void tick(MinecraftClient c){
        if(c.player==null) return;
        if(c.player.getOffHandStack().isEmpty()){
            for(int i=0;i<c.player.getInventory().size();i++){
                if(c.player.getInventory().getStack(i).isOf(Items.TOTEM_OF_UNDYING)){
                    MaceClient.msg("Totem found in slot "+i+" (inventory swap requires screen-handler sync).");
                    break;
                }
            }
        }
    }
}
