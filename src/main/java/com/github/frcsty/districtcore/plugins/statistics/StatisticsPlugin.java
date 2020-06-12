package com.github.frcsty.districtcore.plugins.statistics;

import com.github.frcsty.districtcore.CorePlugin;
import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.statistics.command.LeaderboardCommand;
import com.github.frcsty.districtcore.plugins.statistics.command.StatsCommand;
import com.github.frcsty.districtcore.plugins.statistics.statistic.StatisticStorage;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class StatisticsPlugin implements CorePlugin {

    private final DistrictCore core;
    private final StatisticStorage storage = new StatisticStorage();

    public StatisticsPlugin(final DistrictCore core) {
        this.core = core;
    }

    public void onEnable() {
        core.addCommands(new StatsCommand(core, this), new LeaderboardCommand(core, this));

        new BukkitRunnable() {
            @Override
            public void run() {
                storage.loadUsers(getPlugin(DistrictCore.class));
            }
        }.runTaskTimerAsynchronously(core, 10, 36000);
    }

    public StatisticStorage getStorage() {
        return this.storage;
    }
}
