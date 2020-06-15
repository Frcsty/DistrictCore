package com.github.frcsty.districtcore.plugins.statistics.placeholder;

import com.github.frcsty.districtcore.plugins.statistics.StatisticsPlugin;
import com.github.frcsty.districtcore.plugins.statistics.statistic.StatisticWrapper;
import com.github.frcsty.districtcore.util.Replace;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class Placeholders extends PlaceholderExpansion {

    private final StatisticsPlugin plugin;

    public Placeholders(final StatisticsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        final String[] parameters = params.split(":");

        if (parameters[0].equalsIgnoreCase("stat")) {
            final String statistic = getStatistic(parameters[1]);

            final StatisticWrapper wrapper = plugin.getStorage().getUserStatisticWrapper(player);
            return String.valueOf(wrapper.getStatistic(statistic));
        }
        return null;
    }

    private String getStatistic(final String input) {
        return Replace.replaceString(input, "{", "", "}", "");
    }

    @Override
    public String getIdentifier() {
        return "statistics";
    }

    @Override
    public String getAuthor() {
        return "Frcsty";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

}
