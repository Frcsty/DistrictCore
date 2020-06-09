package com.github.frcsty.districtcore.elixirs.elixir;

import com.github.frcsty.districtcore.util.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.util.List;

public class ElixirBuilder {

    private Material material;
    private int data;
    private String display;
    private List<String> lore;
    private List<PotionEffect> effects;
    private int amount;
    private boolean splash;
    private String type;

    public ElixirBuilder(final Material material, final int data, final String display, final List<String> lore, final List<PotionEffect> effects, final int amount, final boolean splash, final String type) {
        this.material = material;
        this.data = data;
        this.display = display;
        this.lore = lore;
        this.effects = effects;
        this.amount = amount;
        this.splash = splash;
        this.type = type;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(material, amount, (short) 0, (byte) data);
        final ItemMeta meta = item.getItemMeta();
        final PotionMeta potionMeta = (PotionMeta) meta;

        if (display != null) {
            potionMeta.setDisplayName(Color.colorize(display));
        }

        if (!lore.isEmpty()) {
            potionMeta.setLore(Color.colorize(lore));
        }

        if (!effects.isEmpty()) {
            for (PotionEffect effect : effects) {
                potionMeta.addCustomEffect(effect, true);
            }
        }

        item.setItemMeta(meta);

        Potion potion = new Potion(1);

        potion.setType(PotionType.valueOf(type));
        potion.setSplash(splash);

        potion.apply(item);

        return item;
    }

}
