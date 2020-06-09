package com.github.frcsty.districtcore.creepereggs.object;

import com.github.frcsty.districtcore.util.Color;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@SuppressWarnings({"unused", "deprecation"})
public class SpawnEggBuilder {

    private String value;
    private Material material;
    private int data;
    private String display;
    private List<String> lore;
    private int amount;
    private boolean glow;
    private SpawnEgg.Type type;

    public SpawnEggBuilder(final String value, final Material material, final int data, final String display, final List<String> lore, final int amount, final boolean glow, final SpawnEgg.Type type) {
        this.value = value;
        this.material = material;
        this.data = data;
        this.display = display;
        this.lore = lore;
        this.amount = amount;
        this.glow = glow;
        this.type = type;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(material, amount, (short) 0, (byte) data);
        final ItemMeta meta = item.getItemMeta();

        if (display != null) {
            meta.setDisplayName(Color.colorize(display));
        }

        if (!lore.isEmpty()) {
            meta.setLore(Color.colorize(lore));
        }

        if (glow) {
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(meta);

        item = ItemNBT.setNBTTag(item, "type", type.name());
        item = ItemNBT.setNBTTag(item, "value", value);

        return item;
    }


}
