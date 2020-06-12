package com.github.frcsty.districtcore.plugins.pouches.util.parser;

import org.bukkit.Color;

import java.util.regex.Pattern;

public class FireworkParser {

    private static final Pattern PARAM_SPLITTER = Pattern.compile(" ");
    private static final Pattern COLOR_SPLITTER = Pattern.compile(",");

    private final String[] params;
    private final String[] colors;

    public FireworkParser(final String data) {
        this.params = PARAM_SPLITTER.split(data);

        this.colors = COLOR_SPLITTER.split(params[2]);
    }

    public int getAmount() {
        return Integer.parseInt(params[0]);
    }

    public int getPower() {
        return Integer.parseInt(params[1]);
    }

    public Color getColor() {
        return org.bukkit.Color.fromBGR(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));
    }

    public boolean getFlicker() {
        return Boolean.valueOf(params[3]);
    }
}
