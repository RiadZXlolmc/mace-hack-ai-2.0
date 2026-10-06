package com.example.maceclient;

import com.example.maceclient.mixin.MinecraftClientAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class FastXP extends Module {

    public final IntSetting delay = new IntSetting("Delay ticks", 1, 0, 10, 1);

    private int timer = 0;

    public FastXP() {
        super("FastXP", "Throws experience bottles faster");
        settings.add(delay);
    }

    @Override
    public void tick(MinecraftClient c) {
        if (c.player == null || c.interactionManager == null) {
            return;
        }

        if (!c.player.getMainHandStack().isOf(Items.EXPERIENCE_BOTTLE)) {
            return;
        }

        if (!c.options.useKey.isPressed()) {
            return;
        }

        if (timer > 0) {
            timer--;
            return;
        }

        c.interactionManager.interactItem(c.player, Hand.MAIN_HAND);

        MinecraftClientAccessor accessor = (MinecraftClientAccessor) c;
        accessor.maceclient$setItemUseCooldown(0);

        timer = delay.value;
    }
}
