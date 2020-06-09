package com.github.frcsty.districtcore.statistics.util;

import org.bukkit.Statistic;

public class Statistics {

    public static Statistic getStatistic(final String path) {
        String statistic = path.split(".")[1];
        final char[] chars = statistic.toCharArray();

        for (char c : chars) {
            if (Character.isUpperCase(c)) {
                statistic = statistic.replace(String.valueOf(statistic.charAt(statistic.indexOf(c) - 1)), "_");
            }
        }

        statistic = statistic.toUpperCase();
        return Statistic.valueOf(statistic);
    }
}
