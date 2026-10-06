package com.example.maceclient;

public abstract class Setting {
    public final String name;
    protected Setting(String name) { this.name = name; }
    public abstract String display();
    public abstract void cycle();
}
