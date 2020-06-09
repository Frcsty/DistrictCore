package com.github.frcsty.districtcore.pouches.object;

import com.github.frcsty.districtcore.DistrictCore;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PouchStorage {

    private final Map<String, Pouch> pouchHashMap = new HashMap<>();

    private void setPouch(final String name, final Pouch pouch) {
        this.pouchHashMap.put(name, pouch);
    }

    public final Pouch getPouch(final String name) {
        return this.pouchHashMap.get(name);
    }

    public final Map<String, Pouch> getPouchHashMap() {
        return this.pouchHashMap;
    }

    public final void loadPouches(final DistrictCore core) {
        final ConfigurationSection pouchesSection = core.getConfig().getConfigurationSection("pouches");
        if (pouchesSection == null) {
            return;
        }

        for (String pouchString : pouchesSection.getKeys(false)) {
            final ConfigurationSection pouchItemSection = core.getConfig().getConfigurationSection("pouches." + pouchString + ".item");
            final ConfigurationSection pouchSection = core.getConfig().getConfigurationSection("pouches." + pouchString);
            if (pouchItemSection == null || pouchSection == null) {
                continue;
            }

            final int material = pouchItemSection.getInt("material");
            final int data = pouchItemSection.getInt("data");
            final String display = pouchItemSection.getString("display");
            final List<String> lore = pouchItemSection.getStringList("lore");
            final boolean glow = pouchItemSection.getBoolean("glow");

            final String range = pouchSection.getString("range");
            final List<String> actions = pouchSection.getStringList("open-actions");
            final String symbol = pouchSection.getString("eco-symbol");

            setPouch(pouchString, new Pouch(pouchString, material, data, display, lore, glow, range, actions, symbol));
        }
    }
}
