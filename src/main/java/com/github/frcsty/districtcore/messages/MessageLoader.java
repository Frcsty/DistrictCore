package com.github.frcsty.districtcore.messages;

import com.github.frcsty.districtcore.DistrictCore;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public final class MessageLoader {

    private final Map<String, String> messages = new HashMap<>();

    public void load(final DistrictCore core) {
        final ConfigurationSection messageSection = core.getConfig().getConfigurationSection("messages");

        for (String key : messageSection.getKeys(false)) {
            messages.put(key, messageSection.getString(key));
        }
    }

    public String getMessage(final String key) {
        return messages.get(key);
    }
}
