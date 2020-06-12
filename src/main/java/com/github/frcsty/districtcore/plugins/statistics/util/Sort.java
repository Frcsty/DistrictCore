package com.github.frcsty.districtcore.plugins.statistics.util;

import com.github.frcsty.districtcore.plugins.statistics.statistic.StatisticStorage;
import com.github.frcsty.districtcore.plugins.statistics.statistic.StatisticWrapper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.*;
import java.util.stream.Collectors;

public class Sort {

    public static Map<UUID, Long> getSortedMap(final StatisticStorage storage, final String path) {
        final Map<UUID, Long> map = new HashMap<>();

        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            final StatisticWrapper wrapper = storage.getUserStatisticWrapper(player);

            map.put(player.getUniqueId(), wrapper.getStatistic(path));
        }

        return map.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new))
                ;
    }
}
