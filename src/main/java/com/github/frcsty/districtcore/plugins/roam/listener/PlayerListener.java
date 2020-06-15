package com.github.frcsty.districtcore.plugins.roam.listener;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.roam.RoamPlugin;
import com.github.frcsty.districtcore.plugins.roam.object.RoamPlayer;
import com.github.frcsty.districtcore.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final DistrictCore core;

    public PlayerListener(final DistrictCore core) {
        this.core = core;
    }

    @EventHandler
    public void preCommand(PlayerCommandPreprocessEvent event) {
        if (RoamPlugin.getRoamPlayers().get(event.getPlayer()) == null) {
            return;
        }

        if (!event.getMessage().startsWith("/")) {
            return;
        }

        String command = event.getMessage().replace("/", "");
        if (!command.equalsIgnoreCase("roam")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Color.colorize("&7&oCommands are disabled while in roam!"));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        final RoamPlayer player = RoamPlugin.getRoamPlayers().get(event.getPlayer());

        if (player != null) {
            player.disableRoam(event.getPlayer());
            RoamPlugin.getRoamPlayers().remove(event.getPlayer());
        }
    }

    @EventHandler
    public void onVillagerDeath(EntityDeathEvent event) {
        final LivingEntity entity = event.getEntity();

        if (entity.getName() == null) {
            return;
        }

        final String name = ChatColor.stripColor(entity.getName());
        final Player player = Bukkit.getPlayerExact(name);
        if (player == null) {
            return;
        }

        final RoamPlayer roamPlayer = RoamPlugin.getRoamPlayers().get(player);
        if (roamPlayer != null) {
            roamPlayer.disableRoam(player);
            player.sendMessage(Color.colorize("&7&oYour entity has been killed while in roam!"));
            RoamPlugin.getRoamPlayers().remove(player);
            player.setHealth(0);
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        final RoamPlayer player = RoamPlugin.getRoamPlayers().get(event.getPlayer());

        if (player != null) {
            player.disableRoam(event.getPlayer());
            RoamPlugin.getRoamPlayers().remove(event.getPlayer());
        }
    }
}
