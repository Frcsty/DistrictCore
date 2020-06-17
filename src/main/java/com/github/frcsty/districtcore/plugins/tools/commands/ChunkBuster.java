package com.github.frcsty.districtcore.plugins.tools.commands;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.tools.util.PlayerCheck;
import com.github.frcsty.districtcore.plugins.tools.util.ToolBuilder;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

@Command("chunkbuster")
public class ChunkBuster extends CommandBase {

    private final DistrictCore core;

    public ChunkBuster(final DistrictCore core) {
        this.core = core;
    }

    @SubCommand("give")
    @Permission("district.tools.command.chunkbuster")
    public void chunkBusterCommand(final CommandSender sender, final String target, final Integer amount) {
        if (!PlayerCheck.isPlayerValid(target, sender, core)) {
            return;
        }

        ItemStack item = new ToolBuilder(core, "buster").getItem(true);
        final ItemMeta meta = item.getItemMeta();

        meta.spigot().setUnbreakable(true);
        item.setItemMeta(meta);

        item = ItemNBT.setNBTTag(item, "range", "0");
        final Player player = Bukkit.getPlayerExact(target);
        for (int i = 1; i < amount + 1; i++) {
            item = ItemNBT.setNBTTag(item, "uuid", UUID.randomUUID().toString());

            player.getInventory().addItem(item);
        }
    }
}
