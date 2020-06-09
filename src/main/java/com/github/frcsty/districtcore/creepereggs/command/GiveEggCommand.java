package com.github.frcsty.districtcore.creepereggs.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.creepereggs.CreeperEggsPlugin;
import com.github.frcsty.districtcore.creepereggs.object.ObjectStorage;
import com.github.frcsty.districtcore.creepereggs.object.SpawnEgg;
import com.github.frcsty.districtcore.creepereggs.object.SpawnEggBuilder;
import com.github.frcsty.districtcore.util.Color;
import com.github.frcsty.districtcore.util.Replace;
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("unused")
@Command("cegg")
public class GiveEggCommand extends CommandBase {

    private final DistrictCore core;
    private final CreeperEggsPlugin plugin;
    private final FileConfiguration config;

    public GiveEggCommand(final DistrictCore core, final CreeperEggsPlugin plugin) {
        this.core = core;
        this.plugin = plugin;
        this.config = core.getConfig();
    }

    @Default
    @Permission("ceggs.commands.reload")
    public void reloadPlugin(final CommandSender sender) {
        final long startTime = System.currentTimeMillis();
        final ObjectStorage objectStorage = plugin.getObjectStorage();

        objectStorage.getSpawnEggsHashMap().clear();
        new BukkitRunnable() {
            @Override
            public void run() {
                core.reloadConfig();
                objectStorage.loadSpawnEggs(core);
            }
        }.runTaskAsynchronously(core);

        final String estimatedTime = String.valueOf(System.currentTimeMillis() - startTime);
        sender.sendMessage(Color.colorize(Replace.replaceString(config.getString("messages.reload-plugin"), "{time}", estimatedTime)));
    }

    @SubCommand("give")
    @Permission("ceggs.commands.give")
    public void giveCegg(final CommandSender sender, @Completion("#players") String inputTarget, String inputCegg, final Integer amount) {
        final Player target = Bukkit.getPlayerExact(inputTarget);

        if (target == null) {
            sender.sendMessage(Color.colorize(config.getString("messages.invalid-target")));
            return;
        }

        if (!target.isOnline()) {
            sender.sendMessage(Color.colorize(Replace.replaceString(config.getString("messages.offline-target"), "{player}", target.getName())));
            return;
        }

        final SpawnEgg spawnEgg = plugin.getObjectStorage().getSpawnEgg(inputCegg.toLowerCase());

        if (spawnEgg == null) {
            sender.sendMessage(Color.colorize(Replace.replaceString(config.getString("messages.invalid-cegg"), "{type}", inputCegg)));
            return;
        }

        final ItemStack item = new SpawnEggBuilder(inputCegg.toLowerCase(), spawnEgg.getMaterial(), spawnEgg.getData(), spawnEgg.getDisplay(), spawnEgg.getLore(), amount, spawnEgg.isGlow(), spawnEgg.getType()).getItem();

        if (target.getInventory().firstEmpty() == -1) {
            sender.sendMessage(Color.colorize(Replace.replaceString(config.getString("messages.target-full-inventory"), "{player}", target.getName())));
            target.getWorld().dropItem(target.getLocation(), item);
            return;
        }

        target.getInventory().addItem(item);
    }

}
