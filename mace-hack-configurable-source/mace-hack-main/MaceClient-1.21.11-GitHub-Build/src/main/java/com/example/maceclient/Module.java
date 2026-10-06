package com.example.maceclient;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;

public abstract class Module {
    public final String name, category;
    public boolean enabled;
    protected final List<Setting> settings = new ArrayList<>();

    protected Module(String n, String c) { name = n; category = c; }
    public List<Setting> getSettings() { return settings; }

    public void toggle() {
        enabled = !enabled;
        if (enabled) onEnable(MinecraftClient.getInstance());
        else onDisable(MinecraftClient.getInstance());
        MaceClient.msg(name + " " + (enabled ? "ON" : "OFF"));
    }
    public void onEnable(MinecraftClient c) {}
    public void onDisable(MinecraftClient c) {}
    public void tick(MinecraftClient c) {}
}
