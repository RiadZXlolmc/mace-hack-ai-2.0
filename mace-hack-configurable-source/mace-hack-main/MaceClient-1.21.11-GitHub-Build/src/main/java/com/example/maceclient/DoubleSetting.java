package com.example.maceclient;

public class DoubleSetting extends Setting {
    public double value, min, max, step;
    public DoubleSetting(String name, double value, double min, double max, double step) {
        super(name); this.value = value; this.min = min; this.max = max; this.step = step;
    }
    public String display() { return name + ": " + String.format(java.util.Locale.ROOT, "%.2f", value); }
    public void cycle() {
        value += step;
        if (value > max + 1e-9) value = min;
    }
}
