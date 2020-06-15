package com.github.frcsty.districtcore.plugins.tokens.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.tokens.TokensPlugin;
import com.github.frcsty.districtcore.plugins.tokens.command.menu.ShopMenu;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import me.mattstudios.mfgui.gui.guis.Gui;
import org.bukkit.entity.Player;

@Command("token")
public class TokenShopCommand extends CommandBase {

    private final DistrictCore core;
    private final TokensPlugin plugin;

    public TokenShopCommand(final DistrictCore core, final TokensPlugin plugin) {
        this.core = core;
        this.plugin = plugin;
    }

    @SubCommand("shop")
    @Permission("tokens.shop")
    public void onShopCommand(final Player player) {
        final Gui menu = ShopMenu.getShopMenu(core, plugin);

        if (menu == null) {
            return;
        }

        menu.open(player);
    }

}
