package com.github.frcsty.districtcore.tools.commands;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.tools.util.PlayerCheck;
import com.github.frcsty.districtcore.tools.util.ToolBuilder;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("unused")
@Command("traypick")
public class TrayPickaxe extends CommandBase {

    private final DistrictCore core;
    private final FileConfiguration config;

    public TrayPickaxe(final DistrictCore core) {
        this.core = core;
        this.config = core.getConfig();
    }

    @Default
    @Permission("tools.traypick")
    public void trayPickCommand(final CommandSender sender, final String target, final Integer amount) {
        if (!PlayerCheck.isPlayerValid(target, sender, config)) {
            return;
        }

        ItemStack item = new ToolBuilder(core, "tray", amount).getItem();
        final ItemMeta meta = item.getItemMeta();

        meta.spigot().setUnbreakable(true);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 10);

        item = ItemNBT.setNBTTag(item, "range", "11x1x11");

        Bukkit.getPlayerExact(target).getInventory().addItem(item);
    }

}
