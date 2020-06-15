package com.github.frcsty.districtcore.plugins.tokens.command.menu;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.dependency.DependencyUtil;
import com.github.frcsty.districtcore.dependency.util.lib.ActionManager;
import com.github.frcsty.districtcore.plugins.tokens.TokensPlugin;
import com.github.frcsty.districtcore.util.Color;
import com.github.frcsty.districtcore.util.Replace;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopMenu {

    public static Gui getShopMenu(final DistrictCore core, final TokensPlugin plugin) {
        final ConfigurationSection shop = core.getSectionLoader().getSection("token-shop");
        final ActionManager manager = DependencyUtil.getActionManager();
        final Gui gui = new Gui(core, shop.getInt("rows"), Color.colorize(shop.getString("title")));
        gui.setDefaultClickAction(event -> event.setCancelled(true));

        for (String itemString : shop.getConfigurationSection("items").getKeys(false)) {
            final ConfigurationSection itemSection = shop.getConfigurationSection("items." + itemString);

            final GuiItem item = new GuiItem(new ItemBuilder(new ItemStack(itemSection.getInt("material"), 1, (short) itemSection.getInt("data")))
                    .setName(Color.colorize(itemSection.getString("display")))
                    .setLore(Color.colorize(itemSection.getStringList("lore")))
                    .build(), event -> {
                final Player player = (Player) event.getWhoClicked();
                final long balance = plugin.getTokenManager().getTokens(player);
                final long cost = itemSection.getLong("cost");

                if (balance < cost) {
                    player.sendMessage(Color.colorize(core.getMessageLoader().getMessage("not-enough-tokens")));
                    return;
                }

                manager.execute(player, Replace.replaceList(itemSection.getStringList("commands")
                        , "{player}", player.getName()));
            });

            gui.setItem(itemSection.getInt("slot"), item);
        }

        return gui;
    }
}
