package com.github.frcsty.districtcore.statistics.statistic;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatisticWrapper {

    private final UUID player;
    private final Map<String, Long> statistics = new HashMap<>();

    StatisticWrapper(final OfflinePlayer player) {
        this.player = player.getUniqueId();
    }

    public final void setStatistic(final String path, final Long value) {
        this.statistics.put(path, value);
    }

    public final long getStatistic(final String path) {
        return this.statistics.get(path) == null ? 0 : this.statistics.get(path);
    }

}
