package com.example.maceclient.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {

    @Accessor("itemUseCooldown")
    int maceclient$getItemUseCooldown();

    @Accessor("itemUseCooldown")
    void maceclient$setItemUseCooldown(int cooldown);
}
