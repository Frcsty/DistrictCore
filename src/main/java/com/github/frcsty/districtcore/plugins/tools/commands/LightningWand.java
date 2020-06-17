package com.github.frcsty.districtcore.plugins.tools.commands;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.tools.util.PlayerCheck;
import com.github.frcsty.districtcore.plugins.tools.util.ToolBuilder;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
@Command("lightningwand")
public class LightningWand extends CommandBase {

    private final DistrictCore core;

    public LightningWand(final DistrictCore core) {
        this.core = core;
    }

    @Default
    @Permission("district.tools.command.lightningwand")
    public void lightningWandCommand(final CommandSender sender, final String target, final Integer amount) {
        if (!PlayerCheck.isPlayerValid(target, sender, core)) {
            return;
        }

        ItemStack item = new ToolBuilder(core, "lightning", amount).getCooldownItem(false);

        item = ItemNBT.setNBTTag(item, "range", "0");

        Bukkit.getPlayerExact(target).getInventory().addItem(item);
    }
}
