package com.github.frcsty.districtcore.plugins.collectors.listener.menu;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.dependency.DependencyUtil;
import com.github.frcsty.districtcore.plugins.collectors.CollectorsPlugin;
import com.github.frcsty.districtcore.plugins.collectors.collector.chunk.CollectorChunk;
import com.github.frcsty.districtcore.util.Color;
import com.github.frcsty.districtcore.util.Replace;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public final class CollectorMenu {

    public static Gui getCollectorMenu(final DistrictCore core, final CollectorsPlugin plugin, final CollectorChunk collectorChunk, final Block block) {
        final ConfigurationSection collector = core.getSectionLoader().getSection("collector");
        final ConfigurationSection menu = collector.getConfigurationSection("menu");
        final Gui gui = new Gui(core, menu.getInt("rows"), Color.colorize(menu.getString("title")));

        gui.setDefaultClickAction(event -> event.setCancelled(true));

        int position = 0;
        for (Integer slot : menu.getIntegerList("item-slots")) {
            final String[] components = collector.getStringList("material-items").get(position).split(";");
            final int amount = collectorChunk.getContents().getOrDefault(Material.getMaterial(Integer.parseInt(components[0])), 0);

            final String display = menu.getString("item.display");
            final List<String> lore = menu.getStringList("item.lore");

            final GuiItem item = new GuiItem(new ItemBuilder(new ItemStack(Integer.valueOf(components[0])))
                    .setName(Color.colorize(Replace.replaceString(display
                            , "{item-color}", components[1]
                            , "{material}", getFormattedMaterial(Integer.valueOf(components[0])))))
                    .setLore(Color.colorize(Replace.replaceList(lore
                            , "{item-color}", components[1]
                            , "{item-secondary-color}", components[2]
                            , "{dropped-entity}", components[3]
                            , "{amount-stored}", String.valueOf(amount))))
                    .build(), event -> {
                final Player player = (Player) event.getWhoClicked();
                final double price = ShopGuiPlusApi.getItemStackPriceSell(new ItemStack(Integer.parseInt(components[0]), amount));

                DependencyUtil.getEconomy().depositPlayer(player, price);
                player.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("sold-collector-items")
                        , "{amount}", String.valueOf(amount)
                        , "{material}", getFormattedMaterial(Integer.parseInt(components[0]))
                        , "{price}", String.valueOf(price))));
                collectorChunk.clearMaterial(Integer.parseInt(components[0]));
                plugin.getCollectorStorage().updateCollectorChunk(player.getLocation().getChunk(), collectorChunk);
                gui.close(player);
            });

            gui.setItem(slot, item);
            position++;
        }

        final ConfigurationSection pickUpSection = menu.getConfigurationSection("pick-up");
        final GuiItem pickUpItem = new GuiItem(new ItemBuilder(new ItemStack(pickUpSection.getInt("material"), 1, (short) pickUpSection.getInt("data")))
                .setName(Color.colorize(pickUpSection.getString("display")))
                .setLore(Color.colorize(pickUpSection.getStringList("lore")))
                .build(), event -> {
            final Player player = (Player) event.getWhoClicked();
            final Gui confirmation = ConfirmationMenu.getConfirmationMenu(core, plugin, block);

            gui.close(player);
            new BukkitRunnable() {
                @Override
                public void run() {
                    confirmation.open(player);
                }
            }.runTaskLater(core, 2);
        });

        gui.setItem(pickUpSection.getInt("slot"), pickUpItem);
        return gui;
    }

    private static String getFormattedMaterial(final int material) {
        switch (material) {
            case 388:
                return "Emerald";
            case 264:
                return "Diamond";
            case 265:
                return "Iron Ingot";
            case 46:
                return "TNT";
            case 369:
                return "Blaze Rod";
            case 368:
                return "Ender Pearl";
            case 287:
                return "String";
            case 367:
                return "Rotten Flesh";
            case 352:
                return "Bone";
            default:
                return "Invalid Material";
        }
    }

}
