package com.example.maceclient;

import java.util.*;
import net.minecraft.client.MinecraftClient;

public class ModuleManager {
    public final List<Module> modules = new ArrayList<>();

    public final ClickTP clickTP=new ClickTP();
    public final MaceDamage maceDamage=new MaceDamage();
    public final Fly fly=new Fly();
    public final BoatFly boatFly=new BoatFly();
    public final Tracker tracker=new Tracker();
    public final ESP esp=new ESP();
    public final Speed speed=new Speed();
    public final Reach reach=new Reach();

    public ModuleManager(){
        modules.add(clickTP);
        modules.add(maceDamage);
        modules.add(fly);
        modules.add(boatFly);
        modules.add(tracker);
        modules.add(esp);
        modules.add(speed);
        modules.add(reach);
        modules.add(new NoFall());
        modules.add(new Fullbright());
        modules.add(new XRay());
        modules.add(new AutoTotem());
        modules.add(new KillAura());
        modules.add(new FastXP());
        modules.add(new FastPlace());
        modules.add(new CrystalAura());
        modules.add(new AnchorAura());
        modules.add(new Hitboxes());
        modules.add(new MaceAura());
        modules.add(new SpawnerFinder());
    }
    public void tick(MinecraftClient c){ for(Module m:modules) if(m.enabled) m.tick(c); }
}
