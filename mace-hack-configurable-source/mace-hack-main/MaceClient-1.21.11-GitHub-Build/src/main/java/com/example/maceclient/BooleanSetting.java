package com.example.maceclient;

public class BooleanSetting extends Setting {
    public boolean value;
    public BooleanSetting(String name, boolean value) { super(name); this.value = value; }
    public String display() { return name + ": " + (value ? "ON" : "OFF"); }
    public void cycle() { value = !value; }
}
