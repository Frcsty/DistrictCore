package com.github.frcsty.districtcore.plugins.pouches.object;

import java.util.List;

public final class Pouch {

    private String name;
    private int material;
    private int data;
    private String display;
    private List<String> lore;
    private boolean glow;
    private String range;
    private List<String> actions;
    private String symbol;

    public Pouch(final String name, final int material, final int data, final String display, final List<String> lore, final boolean glow, final String range, final List<String> actions, final String symbol) {
        this.name = name;
        this.material = material;
        this.display = display;
        this.lore = lore;
        this.glow = glow;
        this.range = range;
        this.actions = actions;
        this.symbol = symbol;
    }

    public final String getName() {
        return name;
    }

    public void setName(final String value) {
        this.name = value;
    }

    public int getMaterial() {
        return material;
    }

    public void setMaterial(final int value) {
        this.material = value;
    }

    public int getData() {
        return data;
    }

    public void setData(final int value) {
        this.data = value;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(final String value) {
        this.display = value;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(final List<String> values) {
        this.lore = values;
    }

    public boolean isGlow() {
        return this.glow;
    }

    public void setGlow(final boolean value) {
        this.glow = value;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(final String value) {
        this.symbol = value;
    }

    public String getRange() {
        return range;
    }

    public void setRange(final String value) {
        this.range = value;
    }

    public List<String> getActions() {
        return this.actions;
    }

    public void setActions(final List<String> values) {
        this.actions = values;
    }
}
