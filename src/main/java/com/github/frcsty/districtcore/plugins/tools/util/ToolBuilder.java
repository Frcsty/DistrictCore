package com.github.frcsty.districtcore.plugins.tools.util;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.util.Color;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings({"unused", "deprecation"})
public class ToolBuilder {

    private final ConfigurationSection tools;
    private final String tool;
    private final int amount;

    public ToolBuilder(final DistrictCore core, final String tool, final int amount) {
        this.tools = core.getSectionLoader().getSection("tools");
        this.tool = tool;
        this.amount = amount;
    }

    public ToolBuilder(final DistrictCore core, final String tool) {
        this.tools = core.getSectionLoader().getSection("tools");
        this.tool = tool;
        this.amount = 1;
    }

    public final ItemStack getItem() {
        ItemStack item = new ItemBuilder(new ItemStack(tools.getInt(tool + ".material"), amount
                , (short) tools.getInt(tool + ".data")))
                .setName(Color.colorize(tools.getString(tool + ".display")))
                .setLore(Color.colorize(tools.getStringList(tool + ".lore")))
                .build();

        final ItemMeta meta = item.getItemMeta();

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        item = ItemNBT.setNBTTag(item, "tool", tool);

        return item;
    }

    public final ItemStack getItem(final double multiplier) {
        ItemStack item = new ItemBuilder(new ItemStack(tools.getInt(tool + "." + multiplier + ".material"), amount
                , (short) tools.getInt(tool + "." + multiplier + ".data")))
                .setName(Color.colorize(tools.getString(tool + "." + multiplier + ".display")))
                .setLore(Color.colorize(tools.getStringList(tool + "." + multiplier + ".lore")))
                .build();

        final ItemMeta meta = item.getItemMeta();

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        item = ItemNBT.setNBTTag(item, "tool", tool);

        return item;
    }

    public final ItemStack getItem(final boolean delay) {
        ItemStack item = getItem();

        item = ItemNBT.setNBTTag(item, "delay", tools.getString(tool + ".delay"));

        return item;
    }

    public final ItemStack getCooldownItem(final boolean cooldown) {
        ItemStack item = getItem(true);

        item = ItemNBT.setNBTTag(item, "cooldown", String.valueOf(cooldown));

        return item;
    }
}
