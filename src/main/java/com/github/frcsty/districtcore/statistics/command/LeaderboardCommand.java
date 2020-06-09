package com.github.frcsty.districtcore.statistics.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.statistics.StatisticsPlugin;
import com.github.frcsty.districtcore.statistics.command.menu.SelectorMenu;
import com.github.frcsty.districtcore.util.Color;
import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import me.mattstudios.mfgui.gui.guis.Gui;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
@Command("leaderboard")
@Alias("lb")
public class LeaderboardCommand extends CommandBase {

    private final DistrictCore core;
    private final StatisticsPlugin plugin;

    public LeaderboardCommand(final DistrictCore core, final StatisticsPlugin plugin) {
        this.core = core;
        this.plugin = plugin;
    }

    @Default
    @Permission("statistics.leaderboard")
    public void leaderboardCommand(final Player player) {
        final Gui menu = SelectorMenu.getSelectorMenu(core, plugin);

        if (menu == null) {
            player.sendMessage(Color.colorize(core.getConfig().getString("messages.gui-error-occurred")));
            return;
        }

        menu.open(player);
    }

}
