package com.github.frcsty.districtcore.plugins.collectors.collector.object;

import java.util.List;

public final class Collector {

    private int material;
    private int data;
    private String display;
    private List<String> lore;

    public Collector(final int material, final int data, final String display, final List<String> lore) {
        this.material = material;
        this.data = data;
        this.display = display;
        this.lore = lore;
    }

    public int getMaterial() {
        return this.material;
    }

    public int getData() {
        return this.data;
    }

    public String getDisplay() {
        return this.display;
    }

    public List<String> getLore() {
        return this.lore;
    }

}
