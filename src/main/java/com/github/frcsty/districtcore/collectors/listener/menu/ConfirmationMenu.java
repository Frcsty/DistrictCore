package com.github.frcsty.districtcore.collectors.listener.menu;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.collectors.CollectorsPlugin;
import com.github.frcsty.districtcore.collectors.collector.object.CollectorBuilder;
import com.github.frcsty.districtcore.util.Color;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

class ConfirmationMenu {

    static Gui getConfirmationMenu(final DistrictCore core, final CollectorsPlugin plugin, final Block block) {
        final ConfigurationSection section = core.getConfig().getConfigurationSection("collector.confirmation-menu");
        final Gui gui = new Gui(core, section.getInt("rows"), Color.colorize(section.getString("title")));

        gui.setDefaultClickAction(event -> event.setCancelled(true));

        for (String itemPath : section.getConfigurationSection("items").getKeys(false)) {
            final ConfigurationSection itemSection = section.getConfigurationSection("items." + itemPath);
            final GuiItem item = new GuiItem(new ItemBuilder(new ItemStack(itemSection.getInt("material"), 1, (short) itemSection.getInt("data")))
                    .setName(Color.colorize(itemSection.getString("display")))
                    .setLore(Color.colorize(itemSection.getStringList("lore")))
                    .build(), event -> {
                final Player player = (Player) event.getWhoClicked();

                if (itemPath.equalsIgnoreCase("confirm")) {
                    player.sendMessage("picked up collector");
                    plugin.getCollectorStorage().removeCollectorChunk(player.getLocation().getChunk());
                    block.setType(Material.AIR);
                    player.getInventory().addItem(CollectorBuilder.getItem(plugin.getCollectorStorage(), 1));
                }
                gui.close(player);
            });

            gui.setItem(itemSection.getInt("slot"), item);
        }
        return gui;
    }
}
