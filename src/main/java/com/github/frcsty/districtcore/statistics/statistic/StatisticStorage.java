package com.github.frcsty.districtcore.statistics.statistic;

import com.github.frcsty.districtcore.DistrictCore;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class StatisticStorage {

    private final Map<UUID, StatisticWrapper> statisticWrapperMap = new HashMap<>();

    public final void loadUsers(final DistrictCore core) {
        new BukkitRunnable() {
            @Override
            public void run() {
                final long startTime = System.currentTimeMillis();

                final File statsPath = new File(Bukkit.getServer().getWorlds().get(0).getWorldFolder(), "stats");
                int amount = 0;
                for (OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
                    final File playerStatistics = new File(statsPath, player.getUniqueId().toString() + ".json");
                    if (!playerStatistics.exists()) {
                        continue;
                    }
                    final StatisticWrapper wrapper = new StatisticWrapper(player);

                    try {
                        final JSONParser parser = new JSONParser();
                        final JSONObject object = (JSONObject) parser.parse(new FileReader(playerStatistics));

                        for (String value : getPaths(core)) {
                            if (object.get(value) == null) {
                                wrapper.setStatistic(value, (long) 0);
                                continue;
                            }
                            wrapper.setStatistic(value, (long) object.get(value));
                        }
                    } catch (ParseException | IOException ex) {
                        Bukkit.getLogger().log(Level.WARNING, "Failed to retrieve statistics file for user: " + player.getName());
                    }

                    setWrapperUser(player, wrapper);
                    amount++;
                }

                Bukkit.getLogger().log(Level.INFO, "[DistrictStatistic] Loaded statistics in: " + (System.currentTimeMillis() - startTime) + "ms. (Users: " + amount + ")");
            }
        }.runTaskAsynchronously(core);
    }

    private List<String> getPaths(final DistrictCore core) {
        /*
        final List<String> statisticPaths = new ArrayList<>();
        final List<String> miscellaneous = Arrays.asList("stat.walkOneCm", "stat.leaveGame", "stat.jump", "stat.swimOneCm"
                , "stat.fallOneCm", "stat.crouchOneCm", "stat.sprintOneCm", "stat.damageTaken"
                , "stat.flyOneCm", "stat.deaths", "stat.drop", "stat.playOneMinute", "stat.damageDealt"
                , "stat.timeSinceDeath", "stat.playerKills");
        for (EntityType entityType : EntityType.values()) {
            statisticPaths.add("stat.killEntity." + entityType.name());
        }
        for (Material material : Material.values()) {
            statisticPaths.add("stat.useItem.minecraft." + material.name().toLowerCase());
        }
        statisticPaths.addAll(miscellaneous);
        */

        return core.getConfig().getStringList("statistics");
    }

    public final void setWrapperUser(final OfflinePlayer player, final StatisticWrapper wrapper) {
        this.statisticWrapperMap.put(player.getUniqueId(), wrapper);
    }

    public final Map<UUID, StatisticWrapper> getStatisticWrapperMap() {
        return this.statisticWrapperMap;
    }

    public final StatisticWrapper getUserStatisticWrapper(final OfflinePlayer player) {
        return this.statisticWrapperMap.get(player.getUniqueId());
    }
}
