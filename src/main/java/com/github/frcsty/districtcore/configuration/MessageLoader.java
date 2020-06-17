package com.github.frcsty.districtcore.configuration;

import com.github.frcsty.districtcore.DistrictCore;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MessageLoader {

    private final Map<String, String> messages = new HashMap<>();
    private final Map<String, List<String>> lists = new HashMap<>();

    public void load(final DistrictCore core) {
        final ConfigurationSection messageSection = core.getConfig().getConfigurationSection("messages");
        final ConfigurationSection coreMessageSection = core.getConfig().getConfigurationSection("core-commands");

        for (String key : messageSection.getKeys(false)) {
            messages.put(key, messageSection.getString(key));
        }

        for (String key : coreMessageSection.getKeys(false)) {
            lists.put(key, coreMessageSection.getStringList(key));
        }
    }

    public String getMessage(final String key) {
        return messages.get(key) == null ? key : messages.get(key);
    }

    public List<String> getListMessage(final String key) { return lists.get(key).isEmpty() ? Collections.singletonList(key) : lists.get(key); }
}
