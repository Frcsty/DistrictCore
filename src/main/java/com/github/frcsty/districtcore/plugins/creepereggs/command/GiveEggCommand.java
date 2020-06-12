package com.github.frcsty.districtcore.plugins.creepereggs.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.creepereggs.CreeperEggsPlugin;
import com.github.frcsty.districtcore.plugins.creepereggs.object.SpawnEgg;
import com.github.frcsty.districtcore.plugins.creepereggs.object.SpawnEggBuilder;
import com.github.frcsty.districtcore.util.Color;
import com.github.frcsty.districtcore.util.Replace;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Completion;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
@Command("cegg")
public class GiveEggCommand extends CommandBase {

    private final DistrictCore core;
    private final CreeperEggsPlugin plugin;


    public GiveEggCommand(final DistrictCore core, final CreeperEggsPlugin plugin) {
        this.core = core;
        this.plugin = plugin;
    }

    @SubCommand("give")
    @Permission("ceggs.commands.give")
    public void giveCegg(final CommandSender sender, @Completion("#players") String inputTarget, String inputCegg, final Integer amount) {
        final Player target = Bukkit.getPlayerExact(inputTarget);

        if (target == null) {
            sender.sendMessage(Color.colorize(core.getMessageLoader().getMessage("invalid-target")));
            return;
        }

        if (!target.isOnline()) {
            sender.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("offline-target"), "{player}", target.getName())));
            return;
        }

        final SpawnEgg spawnEgg = plugin.getObjectStorage().getSpawnEgg(inputCegg.toLowerCase());

        if (spawnEgg == null) {
            sender.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("invalid-cegg"), "{type}", inputCegg)));
            return;
        }

        final ItemStack item = new SpawnEggBuilder(inputCegg.toLowerCase(), spawnEgg.getMaterial(), spawnEgg.getData(), spawnEgg.getDisplay(), spawnEgg.getLore(), amount, spawnEgg.isGlow(), spawnEgg.getType()).getItem();

        if (target.getInventory().firstEmpty() == -1) {
            sender.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("target-full-inventory"), "{player}", target.getName())));
            target.getWorld().dropItem(target.getLocation(), item);
            return;
        }

        target.getInventory().addItem(item);
    }

}
