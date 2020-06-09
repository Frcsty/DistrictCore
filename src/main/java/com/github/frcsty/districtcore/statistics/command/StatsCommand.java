package com.github.frcsty.districtcore.statistics.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.statistics.StatisticsPlugin;
import com.github.frcsty.districtcore.statistics.statistic.StatisticStorage;
import com.github.frcsty.districtcore.statistics.statistic.StatisticWrapper;
import com.github.frcsty.districtcore.util.Color;
import com.github.frcsty.districtcore.util.Replace;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
@Command("stats")
public class StatsCommand extends CommandBase {

    private final FileConfiguration config;
    private final StatisticStorage storage;

    public StatsCommand(final DistrictCore core, final StatisticsPlugin plugin) {
        this.config = core.getConfig();
        this.storage = plugin.getStorage();
    }

    @Default
    @Permission("statistics.check")
    public void statisticsCommand(final CommandSender sender, final String[] args) {

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Color.colorize(config.getString("messages.cannot-stats-console")));
                return;
            }

            final Player player = (Player) sender;
            sendMessage(player, player);
            return;
        }

        final OfflinePlayer target = args.length == 1 ? Bukkit.getOfflinePlayer(args[0]) : null;

        if (target == null) {
            sender.sendMessage(Color.colorize(config.getString("messages.invalid-stats-player")));
            return;
        }

        sendMessage(target, sender);
    }

    private void sendMessage(final OfflinePlayer target, final CommandSender sender) {
        final StatisticWrapper wrapper = storage.getUserStatisticWrapper(target);
        config.getStringList("stats").forEach(line -> {
            final String message = Color.colorize(com.github.frcsty.districtcore.statistics.util.Replace.replaceStatisticString(line, wrapper, config.getString("time")));
            sender.sendMessage(Replace.replaceString(message, "{player}", target.getName()));
        });
    }

}
