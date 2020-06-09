package com.github.frcsty.districtcore.tools.util;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.util.Color;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class ToolBuilder {

    private final FileConfiguration config;
    private final String tool;
    private final int amount;

    public ToolBuilder(final DistrictCore core, final String tool, final int amount) {
        this.tool = tool;
        this.config = core.getConfig();
        this.amount = amount;
    }

    public final ItemStack getItem() {
        ItemStack item = new ItemBuilder(new ItemStack(config.getInt("tools." + tool + ".material"), amount
                , (short) config.getInt("tools." + tool + ".data")))
                .setName(Color.colorize(config.getString("tools." + tool + ".display")))
                .setLore(Color.colorize(config.getStringList("tools." + tool + ".lore")))
                .build();

        item = ItemNBT.setNBTTag(item, "tool", tool);

        return item;
    }

    public final ItemStack getItem(final double multiplier) {
        ItemStack item = new ItemBuilder(new ItemStack(config.getInt("tools." + tool + "." + multiplier + ".material"), amount
                , (short) config.getInt("tools." + tool + "." + multiplier + ".data")))
                .setName(Color.colorize(config.getString("tools." + tool + "." + multiplier + ".display")))
                .setLore(Color.colorize(config.getStringList("tools." + tool + "." + multiplier + ".lore")))
                .build();

        item = ItemNBT.setNBTTag(item, "tool", tool);

        return item;
    }

    public final ItemStack getItem(final boolean delay) {
        ItemStack item = getItem();

        item = ItemNBT.setNBTTag(item, "delay", config.getString("tools." + tool + ".delay"));

        return item;
    }

    public final ItemStack getCooldownItem(final boolean cooldown) {
        ItemStack item = getItem(true);

        item = ItemNBT.setNBTTag(item, "cooldown", String.valueOf(cooldown));

        return item;
    }
}
