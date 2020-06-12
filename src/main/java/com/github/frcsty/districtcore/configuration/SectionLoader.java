package com.github.frcsty.districtcore.configuration;

import com.github.frcsty.districtcore.DistrictCore;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public final class SectionLoader {

    private final Map<String, ConfigurationSection> sections = new HashMap<>();

    public void load(final DistrictCore core) {
        final FileConfiguration config = core.getConfig();

        for (String sectionString : config.getDefaultSection().getKeys(false)) {
            if (sectionString.equalsIgnoreCase("messages")) continue;

            sections.put(sectionString, config.getConfigurationSection(sectionString));
        }
    }

    public ConfigurationSection getSection(final String key) {
        return sections.get(key);
    }
}
