package com.github.frcsty.districtcore.patches.mechanics;

import com.github.frcsty.districtcore.DistrictCore;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class Entities implements Listener {

    private final DistrictCore core;

    public Entities(final DistrictCore core) {
        this.core = core;
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        final ConfigurationSection entityMechanics = core.getSectionLoader().getSection("patches").getConfigurationSection("entity-mechanics");
        final Entity entity = event.getRightClicked();
        final Player player = event.getPlayer();
        if (entity == null) return;

        final Location location = player.getLocation();
        for (String type : entityMechanics.getStringList("disable-use")) {
            if (entity.getType().name().equalsIgnoreCase(type)) {
                player.teleport(location);
                event.setCancelled(true);
            }
        }
    }

}
