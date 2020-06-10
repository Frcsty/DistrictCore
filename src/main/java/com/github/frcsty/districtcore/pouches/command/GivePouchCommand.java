package com.github.frcsty.districtcore.pouches.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.pouches.PouchesPlugin;
import com.github.frcsty.districtcore.pouches.object.PouchStorage;
import com.github.frcsty.districtcore.util.Color;
import com.github.frcsty.districtcore.util.Replace;
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static com.github.frcsty.districtcore.pouches.object.PouchBuilder.getItem;

@SuppressWarnings("unused")
@Command("pouch")
public class GivePouchCommand extends CommandBase {

    private final PouchesPlugin plugin;
    private final DistrictCore core;

    public GivePouchCommand(final PouchesPlugin plugin, final DistrictCore core) {
        this.plugin = plugin;
        this.core = core;
    }

    @Default
    @Permission("pouches.commands.reload")
    public void reloadPlugin(final CommandSender sender) {
        final long startTime = System.currentTimeMillis();
        final PouchStorage pouchStorage = plugin.getPouchStorage();

        pouchStorage.getPouchHashMap().clear();
        new BukkitRunnable() {
            @Override
            public void run() {
                core.reloadConfig();
                pouchStorage.loadPouches(core);
            }
        }.runTaskAsynchronously(core);

        final String estimatedTime = String.valueOf(System.currentTimeMillis() - startTime);
        sender.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("reload-plugin"), "{time}", estimatedTime)));
    }

    @SubCommand("give")
    @Permission("pouches.commands.give")
    public void givePouch(final CommandSender sender, @Completion("#pouches") final String pouch, final String player, @Completion("#range:64") final Integer amount) {
        final Player target = Bukkit.getPlayerExact(player);
        final ItemStack item = getItem(plugin.getPouchStorage(), pouch, amount);

        if (item == null) {
            sender.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("invalid-pouch"), "{type}", pouch)));
            return;
        }

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

        target.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("target-receive-pouch"), "{amount}", String.valueOf(amount), "{type}", pouch)));
    }
}
