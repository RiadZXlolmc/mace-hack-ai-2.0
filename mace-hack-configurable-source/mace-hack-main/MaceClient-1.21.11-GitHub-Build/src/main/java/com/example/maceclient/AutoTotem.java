package com.example.maceclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class AutoTotem extends Module {

    public AutoTotem() {
        super("AutoTotem", "Automatically moves a Totem of Undying into the offhand", true);
    }

    @Override
    public void tick(MinecraftClient c) {
        if (c.player == null || c.interactionManager == null) {
            return;
        }

        // Already holding a totem in the offhand.
        if (c.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
            return;
        }

        // Only perform inventory clicks when the normal player inventory
        // screen handler is active.
        if (c.player.currentScreenHandler.syncId != 0) {
            return;
        }

        int totemSlot = -1;

        // Search the player's main inventory and hotbar.
        for (int i = 0; i < 36; i++) {
            if (c.player.getInventory().getStack(i).isOf(Items.TOTEM_OF_UNDYING)) {
                totemSlot = i;
                break;
            }
        }

        if (totemSlot == -1) {
            return;
        }

        // Convert PlayerInventory index to PlayerScreenHandler slot ID.
        int slotId;

        if (totemSlot < 9) {
            slotId = 36 + totemSlot;
        } else {
            slotId = totemSlot;
        }

        // Pick up the totem.
        c.interactionManager.clickSlot(
                0,
                slotId,
                0,
                SlotActionType.PICKUP,
                c.player
        );

        // Put the totem into the offhand slot.
        c.interactionManager.clickSlot(
                0,
                45,
                0,
                SlotActionType.PICKUP,
                c.player
        );

        // Put whatever was originally in the offhand back
        // into the original inventory slot.
        c.interactionManager.clickSlot(
                0,
                slotId,
                0,
                SlotActionType.PICKUP,
                c.player
        );
    }
}
