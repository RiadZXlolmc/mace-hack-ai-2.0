package com.example.maceclient;

public class IntSetting extends Setting {
    public int value, min, max, step;
    public IntSetting(String name, int value, int min, int max, int step) {
        super(name); this.value = value; this.min = min; this.max = max; this.step = step;
    }
    public String display() { return name + ": " + value; }
    public void cycle() {
        value += step;
        if (value > max) value = min;
    }
}
