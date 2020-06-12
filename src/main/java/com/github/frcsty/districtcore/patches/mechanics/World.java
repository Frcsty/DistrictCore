package com.github.frcsty.districtcore.patches.mechanics;

import com.github.frcsty.districtcore.DistrictCore;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class World implements Listener {

    private final DistrictCore core;

    public World(final DistrictCore core) {
        this.core = core;
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        final ConfigurationSection worldMechanics = core.getSectionLoader().getSection("patches").getConfigurationSection("world-mechanics");

        worldMechanics.getStringList("fixed-weather").forEach(worldString -> {
            final org.bukkit.World world = core.getServer().getWorld(worldString);

            if (world.equals(event.getWorld())) event.setCancelled(event.toWeatherState());
        });

    }
}
