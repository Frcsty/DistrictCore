package com.github.frcsty.districtcore.statistics.util;

@SuppressWarnings("unused")
public class Time {

    private final static long ONE_MILLISECOND = 1;
    private final static long MILLISECONDS_IN_A_SECOND = 1000;
    private final static long ONE_SECOND = 1000;
    private final static long SECONDS_IN_A_MINUTE = 60;
    private final static long ONE_MINUTE = ONE_SECOND * 60;
    private final static long MINUTES_IN_AN_HOUR = 60;
    private final static long ONE_HOUR = ONE_MINUTE * 60;
    private final static long HOURS_IN_A_DAY = 24;
    private final static long ONE_DAY = ONE_HOUR * 24;
    private final static long DAYS_IN_A_YEAR = 365;

    public static String getFormattedTime(final String formatting, Number number) {
        String res = "";

        if (number != null) {
            long duration = number.longValue() * 60;

            int milliseconds = (int) ((duration /= ONE_MILLISECOND) % MILLISECONDS_IN_A_SECOND);
            int seconds = (int) ((duration /= ONE_SECOND) % SECONDS_IN_A_MINUTE);
            int minutes = (int) ((duration /= SECONDS_IN_A_MINUTE) % MINUTES_IN_AN_HOUR);
            int hours = (int) ((duration /= MINUTES_IN_AN_HOUR) % HOURS_IN_A_DAY);
            int days = (int) ((duration /= HOURS_IN_A_DAY) % DAYS_IN_A_YEAR);
            int years = (int) (duration / DAYS_IN_A_YEAR);
            if (years == 0) {
                res = String.format(formatting, days, hours, minutes, seconds);
            }
        }
        return res;

    }
}
