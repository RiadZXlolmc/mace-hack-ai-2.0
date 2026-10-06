package com.example.maceclient;

import net.minecraft.client.MinecraftClient;

public class XRay extends Module {
    public XRay(){super("XRay","Render");}
    public void onEnable(MinecraftClient c){ if(c.worldRenderer!=null) c.worldRenderer.reload(); }
    public void onDisable(MinecraftClient c){ if(c.worldRenderer!=null) c.worldRenderer.reload(); }
}
