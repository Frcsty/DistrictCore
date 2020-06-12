package com.github.frcsty.districtcore.plugins.tools;

import com.github.frcsty.districtcore.CorePlugin;
import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.tools.commands.*;
import com.github.frcsty.districtcore.plugins.tools.listener.BlockDamageListener;
import com.github.frcsty.districtcore.plugins.tools.listener.ItemUseListener;
import com.github.frcsty.districtcore.plugins.tools.util.ActionBarAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;

public class ToolsPlugin implements CorePlugin {

    private static Economy economy;
    private final DistrictCore core;
    private final ActionBarAPI actionBarAPI;

    public ToolsPlugin(final DistrictCore core) {
        this.core = core;

        this.actionBarAPI = new ActionBarAPI(core);
    }

    public static Economy getEconomy() {
        return economy;
    }

    public void onEnable() {
        final Plugin shopGuiPlusPlugin = getServer().getPluginManager().getPlugin("ShopGUIPlus");
        if (shopGuiPlusPlugin != null && !shopGuiPlusPlugin.isEnabled()) {
            core.getLogger().log(Level.WARNING, "Failed to initialize ShopGUIPlus API! Disabling plugin.");
            core.getServer().getPluginManager().disablePlugin(core);
            return;
        }
        final RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider == null) {
            core.getLogger().log(Level.WARNING, "Failed to register Vault Economy Provider! Disabling plugin.");
            getServer().getPluginManager().disablePlugin(core);
            return;
        }
        economy = economyProvider.getProvider();

        core.addCommands(new TrayPickaxe(core), new TrenchPickaxe(core)
                , new SandWand(core), new LightningWand(core), new CraftWand(core)
                , new SellWand(core));

        core.addListeners(new BlockDamageListener(), new ItemUseListener(core, this));
        actionBarAPI.enable();
    }

    public final ActionBarAPI getActionBarAPI() {
        return this.actionBarAPI;
    }
}
