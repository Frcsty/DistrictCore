package com.github.frcsty.districtcore.plugins.statistics.statistic;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatisticWrapper {

    private final UUID player;
    private final Map<String, Double> statistics = new HashMap<>();

    StatisticWrapper(final OfflinePlayer player) {
        this.player = player.getUniqueId();
    }

    public final void setStatistic(final String path, final Double value) {
        this.statistics.put(path, value);
    }

    public final double getStatistic(final String path) {
        return this.statistics.get(path) == null ? 0 : this.statistics.get(path);
    }

}
