package com.github.frcsty.districtcore.plugins.creepereggs.object;

import org.bukkit.Material;

import java.util.List;

@SuppressWarnings("unused")
public class SpawnEgg {

    private String name;
    private Material material;
    private int data;
    private String display;
    private List<String> lore;
    private boolean glow;
    private Type type;
    private float power;
    private int percent;

    SpawnEgg(final String name, final Material material, final int data, final String display, final List<String> lore, final boolean glow, final Type type, final float power, final int percent) {
        this.name = name;
        this.material = material;
        this.data = data;
        this.display = display;
        this.lore = lore;
        this.glow = glow;
        this.type = type;
        this.power = power;
        this.percent = percent;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(final String value) {
        this.name = value;
    }

    public final Material getMaterial() {
        return this.material;
    }

    public final void setMaterial(final Material value) {
        this.material = value;
    }

    public final int getData() {
        return this.data;
    }

    public final void setData(final int value) {
        this.data = value;
    }

    public final String getDisplay() {
        return this.display;
    }

    public final void setDisplay(final String value) {
        this.display = value;
    }

    public final List<String> getLore() {
        return this.lore;
    }

    public final void setLore(final List<String> values) {
        this.lore = values;
    }

    public final boolean isGlow() {
        return this.glow;
    }

    public final void setGlow(final boolean value) {
        this.glow = value;
    }

    public final Type getType() {
        return this.type;
    }

    public final void setType(final Type value) {
        this.type = value;
    }

    public final float getPower() {
        return this.power;
    }

    public final void setPower(final float value) {
        this.power = value;
    }

    public final int getPercent() {
        return this.percent;
    }

    public final void setPercent(final int value) {
        this.percent = value;
    }

    public enum Type {
        CHARGED,
        THROWABLE_CHARGED,
        THROWABLE,
        ;
    }

}
