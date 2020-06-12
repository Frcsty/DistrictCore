package com.github.frcsty.districtcore.plugins.tools.util;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCheck {

    public static boolean isPlayerValid(final String target, final CommandSender sender, final DistrictCore core) {
        final Player player = Bukkit.getPlayer(target);

        if (player == null || !player.isOnline()) {
            sender.sendMessage(Color.colorize(core.getMessageLoader().getMessage("invalid-player")));
            return false;
        }
        return true;
    }
}
