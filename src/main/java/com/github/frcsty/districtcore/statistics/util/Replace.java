package com.github.frcsty.districtcore.statistics.util;

import com.github.frcsty.districtcore.statistics.statistic.StatisticWrapper;

public class Replace {

    public static String replaceStatisticString(final String input, final StatisticWrapper wrapper, final String formatting) {
        final String placeholder = getStatisticPlaceholder(input);
        final String statistic = getStatisticString(input);

        if (!placeholder.contains(":")) {
            return input;
        }

        if (statistic.equalsIgnoreCase("stat.playOneMinute")) {
            return input.replace(placeholder, Time.getFormattedTime(formatting, wrapper.getStatistic(statistic)));
        }

        return input.replace(placeholder, String.valueOf(wrapper.getStatistic(statistic)));
    }

    private static String getStatisticString(final String input) {
        String output = getStatisticPlaceholder(input);

        if (!output.contains(":")) {
            return input;
        }

        output = output.replace("{", "");
        output = output.replace("}", "");

        return output.split(":")[1];
    }

    private static String getStatisticPlaceholder(final String input) {
        int rangeMin = 0;
        int rangeMax = 0;
        for (char c : input.toCharArray()) {
            if (String.valueOf(c).equals("{")) {
                rangeMin = input.indexOf(c);
            }
            if (String.valueOf(c).equals("}")) {
                rangeMax = input.indexOf(c);
            }
        }

        return input.substring(rangeMin, rangeMax + 1);
    }
}
