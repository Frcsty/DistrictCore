package com.github.frcsty.districtcore.plugins.statistics.command.menu;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.statistics.StatisticsPlugin;
import com.github.frcsty.districtcore.util.Color;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SelectorMenu {

    public static Gui getSelectorMenu(final DistrictCore core, final StatisticsPlugin plugin) {
        final ConfigurationSection selector = core.getSectionLoader().getSection("selector-menu");
        final Gui gui = new Gui(core, selector.getInt("rows"), Color.colorize(selector.getString("title")));

        gui.setDefaultClickAction(event -> event.setCancelled(true));

        for (String input : selector.getConfigurationSection("items").getKeys(false)) {
            final ConfigurationSection itemSection = selector.getConfigurationSection("items." + input);
            final String menu = itemSection.getString("menu");
            final GuiItem item = new GuiItem(
                    new ItemBuilder(new ItemStack(itemSection.getInt("material"), 1
                            , (short) itemSection.getInt("data")))
                            .setName(Color.colorize(itemSection.getString("display")))
                            .setLore(Color.colorize(itemSection.getStringList("lore")))
                            .build(), event -> {
                final Player viewer = (Player) event.getWhoClicked();
                final Gui newMenu = LeaderBoardMenus.getLeaderboardMenu(core, plugin, menu);

                viewer.closeInventory();
                if (newMenu == null) {
                    viewer.sendMessage(Color.colorize(core.getMessageLoader().getMessage("no-category-stats")));
                    return;
                }

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        newMenu.open(event.getWhoClicked());
                    }
                }.runTaskLater(core, 5);
            });

            gui.setItem(itemSection.getInt("slot"), item);
        }

        for (String input : selector.getConfigurationSection("background").getKeys(false)) {
            final ConfigurationSection itemSection = selector.getConfigurationSection("background." + input);
            final GuiItem item = new GuiItem(
                    new ItemBuilder(new ItemStack(itemSection.getInt("material"), 1
                            , (short) itemSection.getInt("data")))
                            .setName(itemSection.getString("display"))
                            .setLore(itemSection.getStringList("lore"))
                            .build());

            for (Integer slot : itemSection.getIntegerList("slots")) {
                gui.setItem(slot, item);
            }
        }

        return gui;
    }
}
