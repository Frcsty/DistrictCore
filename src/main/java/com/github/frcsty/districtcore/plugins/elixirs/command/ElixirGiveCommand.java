package com.github.frcsty.districtcore.plugins.elixirs.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.elixirs.ElixirsPlugin;
import com.github.frcsty.districtcore.plugins.elixirs.elixir.Elixir;
import com.github.frcsty.districtcore.plugins.elixirs.elixir.ElixirBuilder;
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

@SuppressWarnings("unused")
@Command("elixir")
public class ElixirGiveCommand extends CommandBase {

    private final DistrictCore core;
    private final ElixirsPlugin plugin;

    public ElixirGiveCommand(final DistrictCore core, final ElixirsPlugin plugin) {
        this.plugin = plugin;
        this.core = core;
    }

    @SubCommand("give")
    @Permission("elixirs.commands.give")
    public void giveElixir(final CommandSender sender, final String t, final String e, final Integer amount) {
        final Player target = Bukkit.getPlayerExact(t);
        final String elixir = e.toLowerCase();

        if (target == null) {
            sender.sendMessage(Color.colorize(core.getMessageLoader().getMessage("invalid-player")));
            return;
        }

        if (!target.isOnline()) {
            sender.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("offline-player"), "{player}", target.getName())));
            return;
        }

        final Elixir elixirObject = plugin.getElixirStorage().getElixir(elixir);

        if (elixirObject == null) {
            sender.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("invalid-elixir"), "{elixir}", elixir)));
            return;
        }

        final ItemStack elixirItem = new ElixirBuilder(elixirObject.getMaterial(), elixirObject.getData(), elixirObject.getDisplay(), elixirObject.getLore(), elixirObject.getEffects(), amount, elixirObject.getSplash(), elixirObject.getType()).getItem();

        if (target.getInventory().firstEmpty() == -1) {
            sender.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("target-full-inventory"))));
            target.getLocation().getWorld().dropItem(target.getLocation(), elixirItem);
            return;
        }

        target.getInventory().addItem(elixirItem);
    }

}
