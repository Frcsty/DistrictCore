package com.github.frcsty.districtcore.collectors.collector.object;

import com.github.frcsty.districtcore.collectors.collector.CollectorStorage;
import com.github.frcsty.districtcore.collectors.collector.object.Collector;
import com.github.frcsty.districtcore.util.Color;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CollectorBuilder {

    public static ItemStack getItem(final CollectorStorage storage, final int amount) {
        final Collector collector = storage.getCollectorItem();

        if (collector == null) {
            return null;
        }

        ItemStack item = new ItemStack(collector.getMaterial(), amount, (short) collector.getData());
        final ItemMeta meta = item.getItemMeta();

        if (collector.getDisplay() != null) {
            meta.setDisplayName(Color.colorize(collector.getDisplay()));
        }
        if (!collector.getLore().isEmpty()) {
            meta.setLore(Color.colorize(collector.getLore()));
        }

        item.setItemMeta(meta);
        item = ItemNBT.setNBTTag(item, "collector", "collector");

        return item;
    }
}
