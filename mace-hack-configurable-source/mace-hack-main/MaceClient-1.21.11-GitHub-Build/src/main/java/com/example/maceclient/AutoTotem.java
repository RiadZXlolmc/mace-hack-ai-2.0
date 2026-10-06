package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class AutoTotem extends Module {

    public AutoTotem() {
        super("AutoTotem", "Automatically moves a Totem of Undying into the offhand");
    }

    @Override
    public void tick(MinecraftClient c) {
        if (c.player == null || c.interactionManager == null) {
            return;
        }

        if (c.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
            return;
        }

        if (c.player.currentScreenHandler.syncId != 0) {
            return;
        }

        int totemSlot = -1;

        for (int i = 0; i < 36; i++) {
            if (c.player.getInventory().getStack(i).isOf(Items.TOTEM_OF_UNDYING)) {
                totemSlot = i;
                break;
            }
        }

        if (totemSlot == -1) {
            return;
        }

        int slotId = totemSlot < 9 ? 36 + totemSlot : totemSlot;

        c.interactionManager.clickSlot(
                0,
                slotId,
                0,
                SlotActionType.PICKUP,
                c.player
        );

        c.interactionManager.clickSlot(
                0,
                45,
                0,
                SlotActionType.PICKUP,
                c.player
        );

        c.interactionManager.clickSlot(
                0,
                slotId,
                0,
                SlotActionType.PICKUP,
                c.player
        );
    }
}
