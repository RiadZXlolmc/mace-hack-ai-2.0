package com.example.maceclient;

import com.example.maceclient.mixin.MinecraftClientAccessor;
import net.minecraft.client.MinecraftClient;

public class FastPlace extends Module {

    public final IntSetting delay = new IntSetting("Tick delay", 0, 0, 5, 1);

    public FastPlace() {
        super("FastPlace", "Reduces the item placement cooldown", true);
        settings.add(delay);
    }

    @Override
    public void tick(MinecraftClient c) {
        if (c.player == null) {
            return;
        }

        MinecraftClientAccessor accessor = (MinecraftClientAccessor) c;

        int currentCooldown = accessor.maceclient$getItemUseCooldown();

        if (currentCooldown > delay.value) {
            accessor.maceclient$setItemUseCooldown(delay.value);
        }
    }
}
