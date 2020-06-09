package com.github.frcsty.districtcore.statistics.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.statistics.StatisticsPlugin;
import com.github.frcsty.districtcore.util.Color;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

@Command("districtstatistics")
public class ReloadCommand extends CommandBase {

    private final DistrictCore core;
    private final StatisticsPlugin plugin;

    public ReloadCommand(final DistrictCore core, final StatisticsPlugin plugin) {
        this.core = core;
        this.plugin = plugin;
    }

    @SubCommand("reload")
    @Permission("statistics.reload")
    public void reloadCommand(final CommandSender sender) {
        new BukkitRunnable() {
            @Override
            public void run() {
                core.reloadConfig();
                plugin.getStorage().loadUsers(core);
            }
        }.runTaskAsynchronously(core);
        sender.sendMessage(Color.colorize(core.getConfig().getString("messages.reloaded-config")));
    }
}