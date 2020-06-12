package com.github.frcsty.districtcore.patches.mechanics;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.util.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Crafting implements Listener {

    private final DistrictCore core;

    public Crafting(final DistrictCore core) {
        this.core = core;
    }

    @EventHandler
    public void onItemCraft(CraftItemEvent event) {
        final ConfigurationSection recipes = core.getSectionLoader().getSection("patches").getConfigurationSection("disabled-recipes");
        final ItemStack result = event.getRecipe().getResult();

        if (recipes.getStringList("materials").contains(result.getType().name())) {
            final ItemMeta meta = result.getItemMeta();
            if (recipes.getString("display") != null) {
                meta.setDisplayName(Color.colorize(recipes.getString("display")));
            }
            if (!recipes.getStringList("lore").isEmpty()) {
                meta.setLore(Color.colorize(recipes.getStringList("lore")));
            }

            result.setItemMeta(meta);
            event.getInventory().setResult(result);
            event.setCancelled(true);
        }
    }
}
