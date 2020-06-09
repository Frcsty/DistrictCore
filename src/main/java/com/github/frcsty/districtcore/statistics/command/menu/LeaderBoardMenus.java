package com.github.frcsty.districtcore.statistics.command.menu;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.statistics.StatisticsPlugin;
import com.github.frcsty.districtcore.statistics.util.Sort;
import com.github.frcsty.districtcore.util.Color;
import com.github.frcsty.districtcore.util.Replace;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

class LeaderBoardMenus {

    static Gui getLeaderboardMenu(final DistrictCore core, final StatisticsPlugin plugin, @NotNull final String menu) {
        final ConfigurationSection section = core.getConfig().getConfigurationSection("menus." + menu);
        final Gui gui = new Gui(core, section.getInt("rows"), Color.colorize(section.getString("title")));
        final String path = section.getString("path");
        final ConfigurationSection leaderboard = section.getConfigurationSection("leaderboard");
        final ConfigurationSection noUser = section.getConfigurationSection("no-user");
        final ConfigurationSection items = section.getConfigurationSection("items");
        final List<Integer> slots = leaderboard.getIntegerList("slots");
        final Map<UUID, Long> statisticMap = Sort.getSortedMap(plugin.getStorage(), path);
        final Iterator<Map.Entry<UUID, Long>> iterator = statisticMap.entrySet().iterator();
        if (!iterator.hasNext()) {
            return null;
        }

        int place = 1;
        for (Integer slot : slots) {
            if (!iterator.hasNext()) {
                ItemStack item = new ItemBuilder(new ItemStack(noUser.getInt("material"), 1
                        , (short) noUser.getInt("data")))
                        .setName(Color.colorize(noUser.getString("display")))
                        .setLore(Color.colorize(noUser.getStringList("lore")))
                        .build();
                final SkullMeta meta = (SkullMeta) item.getItemMeta();
                meta.setOwner(noUser.getString("owner"));

                item.setItemMeta(meta);
                gui.setItem(slot, new GuiItem(item));
                continue;
            }
            final Map.Entry<UUID, Long> user = iterator.next();
            final OfflinePlayer player = Bukkit.getOfflinePlayer(user.getKey());
            ItemStack item = new ItemBuilder(new ItemStack(leaderboard.getInt("material"), 1
                    , (short) leaderboard.getInt("data")))
                    .setName(Color.colorize(Replace.replaceString(leaderboard.getString("display")
                            , "{position}", String.valueOf(place)
                            , "{player}", player.getName())))

                    .setLore(Color.colorize(Replace.replaceList(leaderboard.getStringList("lore")
                            , "{player}", player.getName()
                            , "{statistic-value}", String.valueOf(user.getValue())
                            , "{position}", String.valueOf(place))))
                    .build();

            final SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwner(Bukkit.getOfflinePlayer(user.getKey()).getName());

            item.setItemMeta(meta);
            gui.setItem(slot, new GuiItem(item));
        }

        for (String input : items.getKeys(false)) {
            final ConfigurationSection itemSection = items.getConfigurationSection(input);
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
