package com.github.frcsty.districtcore.patches.mechanics;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.util.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import java.util.Collection;

public class Piston implements Listener {

    private final DistrictCore core;

    public Piston(final DistrictCore core) {
        this.core = core;
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        final ConfigurationSection piston = core.getSectionLoader().getSection("patches").getConfigurationSection("disabled-piston-mechanics");

        event.getBlocks().forEach(block -> {
            if (piston.getStringList("materials").contains(block.getType().name())) {
                final Player player = getPlayer(block.getWorld().getNearbyEntities(block.getLocation(), 5, 5, 5));
                event.setCancelled(true);
                if (player != null) {
                    player.sendMessage(Color.colorize(core.getMessageLoader().getMessage("disabled-piston-mechanic")));
                }
            }
        });
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        final ConfigurationSection piston = core.getSectionLoader().getSection("patches").getConfigurationSection("disabled-piston-mechanics");

        event.getBlocks().forEach(block -> {
            if (piston.getStringList("materials").contains(block.getType().name())) {
                final Player player = getPlayer(block.getWorld().getNearbyEntities(block.getLocation(), 5, 5, 5));
                event.setCancelled(true);
                if (player != null) {
                    player.sendMessage(Color.colorize(core.getMessageLoader().getMessage("disabled-piston-mechanic")));
                }
            }
        });
    }

    private Player getPlayer(final Collection<Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof Player) return (Player) entity;
        }
        return null;
    }
}
