package com.github.frcsty.districtcore.collectors.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.collectors.CollectorsPlugin;
import com.github.frcsty.districtcore.collectors.collector.object.CollectorBuilder;
import com.github.frcsty.districtcore.util.Color;
import com.github.frcsty.districtcore.util.Replace;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Command("collector")
public class CollectorGiveCommand extends CommandBase {

    private final DistrictCore core;
    private final CollectorsPlugin plugin;

    public CollectorGiveCommand(final DistrictCore core, final CollectorsPlugin plugin) {
        this.core = core;
        this.plugin = plugin;
    }

    @SubCommand("give")
    @Permission("collectors.give")
    public void giveCollectorCommand(final CommandSender sender, final String player, final Integer amount) {
        final Player target = Bukkit.getPlayerExact(player);
        final ItemStack item = CollectorBuilder.getItem(plugin.getCollectorStorage(), amount);

        if (target == null) {
            sender.sendMessage(Color.colorize(core.getMessageLoader().getMessage("invalid-target")));
            return;
        }

        if (!target.isOnline()) {
            sender.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("offline-target"), "{player}", target.getName())));
            return;
        }

        if (target.getInventory().firstEmpty() == -1) {
            target.sendMessage(Color.colorize(core.getMessageLoader().getMessage("target-full-inventory")));
            target.getWorld().dropItem(target.getLocation(), item);
        } else {
            target.getInventory().addItem(item);
        }

        target.sendMessage(Replace.replaceString(Color.colorize(core.getMessageLoader().getMessage("given-collector-item")), "{amount}", String.valueOf(amount)));
    }
}
