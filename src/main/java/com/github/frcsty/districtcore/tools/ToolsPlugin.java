package com.github.frcsty.districtcore.tools;

import com.github.frcsty.districtcore.CorePlugin;
import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.tools.commands.*;
import com.github.frcsty.districtcore.tools.listener.BlockDamageListener;
import com.github.frcsty.districtcore.tools.listener.ItemUseListener;
import com.github.frcsty.districtcore.tools.util.ActionBarAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;

public class ToolsPlugin implements CorePlugin {

    private final DistrictCore core;
    private final ActionBarAPI actionBarAPI;
    private static Economy economy;

    public ToolsPlugin(final DistrictCore core) {
        this.core = core;

        this.actionBarAPI = new ActionBarAPI(core);
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

        registerEvents();
        actionBarAPI.enable();
    }

    public void onDisable() {

    }

    private void registerEvents() {
        final List<Listener> listeners = Arrays.asList(new BlockDamageListener(this), new ItemUseListener(core, this));

        listeners.forEach(listener -> getServer().getPluginManager().registerEvents(listener, core));
    }

    public static Economy getEconomy() {
        return economy;
    }

    public final ActionBarAPI getActionBarAPI() {
        return this.actionBarAPI;
    }
}
