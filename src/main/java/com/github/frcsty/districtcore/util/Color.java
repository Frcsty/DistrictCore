package com.github.frcsty.districtcore.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public final class Color {

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> colorize(final List<String> message) {
        return message.stream().map(Color::colorize).collect(Collectors.toList());
    }

    public static List<String> placeholdify(final List<String> input, final Player player) {
        return input.stream().map(i -> PlaceholderAPI.setPlaceholders(player, i)).collect(Collectors.toList());
    }

    public static String placeholdify(final String input, final Player player) {
        return PlaceholderAPI.setPlaceholders(player, input);
    }

}
