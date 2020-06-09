package com.github.frcsty.districtcore.tools.util;

import com.github.frcsty.districtcore.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PlayerCheck {

    public static boolean isPlayerValid(final String target, final CommandSender sender, final FileConfiguration config) {
        final Player player = Bukkit.getPlayer(target);

        if (player == null || !player.isOnline()) {
            sender.sendMessage(Color.colorize(config.getString("messages.invalid-player")));
            return false;
        }
        return true;
    }
}
