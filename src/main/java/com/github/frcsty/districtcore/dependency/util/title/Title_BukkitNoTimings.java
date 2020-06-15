package com.github.frcsty.districtcore.dependency.util.title;

import org.bukkit.entity.Player;

public class Title_BukkitNoTimings implements Title {

    @Override
    public void sendTitle(final Player player, final String message, final String submessage) {
        player.sendTitle(message, submessage);
    }
}
