package com.github.frcsty.districtcore.plugins.creepereggs.object;

import com.github.frcsty.districtcore.DistrictCore;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectStorage {

    private final Map<String, SpawnEgg> ceggsHashMap = new HashMap<>();

    public final Map<String, SpawnEgg> getSpawnEggsHashMap() {
        return this.ceggsHashMap;
    }

    private void setSpawnEgg(final String name, final SpawnEgg elixir) {
        ceggsHashMap.put(name, elixir);
    }

    public final SpawnEgg getSpawnEgg(final String name) {
        return ceggsHashMap.get(name);
    }

    public final void loadSpawnEggs(final DistrictCore core) {
        final ConfigurationSection spawnEggs = core.getSectionLoader().getSection("ceggs");

        if (spawnEggs == null) {
            return;
        }

        for (String cegg : spawnEggs.getKeys(false)) {
            final ConfigurationSection ceggSection = spawnEggs.getConfigurationSection(cegg);
            final String display = ceggSection.getString("display");
            final List<String> lore = ceggSection.getStringList("lore");
            final Material material = Material.matchMaterial(ceggSection.getString("material"));
            final int data = ceggSection.getInt("data");
            final boolean glow = ceggSection.getBoolean("glow");
            final SpawnEgg.Type type = SpawnEgg.Type.valueOf(ceggSection.getString("type").toUpperCase());
            float power = 0f;
            if (ceggSection.getString("power") != null) {
                power = Float.valueOf(ceggSection.getString("power"));
            }
            final int percent = ceggSection.getInt("drop-percentage");

            final SpawnEgg object = new SpawnEgg(cegg, material, data, display, lore, glow, type, power, percent);
            setSpawnEgg(cegg, object);
        }
    }

}
