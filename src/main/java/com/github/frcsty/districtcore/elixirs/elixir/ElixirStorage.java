package com.github.frcsty.districtcore.elixirs.elixir;

import com.github.frcsty.districtcore.DistrictCore;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElixirStorage {

    private final Map<String, Elixir> elixirHashMap = new HashMap<>();

    public final Map<String, Elixir> getElixirHashMap() {
        return this.elixirHashMap;
    }

    private void setElixir(final String name, final Elixir elixir) {
        elixirHashMap.put(name, elixir);
    }

    public final Elixir getElixir(final String name) {
        return elixirHashMap.get(name);
    }

    public final void loadElixirs(final DistrictCore core) {
        final ConfigurationSection elixirs = core.getConfig().getConfigurationSection("elixirs");

        if (elixirs == null) {
            return;
        }

        for (String e : elixirs.getKeys(false)) {
            final ConfigurationSection elixir = core.getConfig().getConfigurationSection("elixirs." + e);
            final String display = elixir.getString("display");
            final List<String> lore = elixir.getStringList("lore");
            final Material material = Material.matchMaterial(elixir.getString("material"));
            final int data = elixir.getInt("data");
            final boolean splash = elixir.getBoolean("splash");
            final String type = elixir.getString("type");
            final List<PotionEffect> effects = new ArrayList<>();

            final ConfigurationSection effectsSection = elixir.getConfigurationSection("effects");
            for (String effect : effectsSection.getKeys(false)) {
                final PotionEffectType potionEffectType = PotionEffectType.getByName(effect.toUpperCase());
                final int duration = effectsSection.getInt(effect + ".duration");
                final int amplifier = effectsSection.getInt(effect + ".amplifier");
                final PotionEffect potionEffect = new PotionEffect(potionEffectType, duration * 20, amplifier, false, true);

                effects.add(potionEffect);
            }

            final Elixir object = new Elixir(e, material, data, display, lore, effects, splash, type);
            setElixir(e, object);
        }
    }

}
