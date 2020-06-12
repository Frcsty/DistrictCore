package com.github.frcsty.districtcore.plugins.creepereggs.util;

import com.github.frcsty.districtcore.plugins.creepereggs.object.SpawnEgg;

public class Replace {

    public static String getFormattedType(final SpawnEgg.Type type)
    {
        switch (type)
        {
            case CHARGED:
                return "Charged";
            case THROWABLE_CHARGED:
                return "Charged Throwable";
            case THROWABLE:
                return "Throwable";
            default:
                return "Invalid Type";
        }
    }
}
