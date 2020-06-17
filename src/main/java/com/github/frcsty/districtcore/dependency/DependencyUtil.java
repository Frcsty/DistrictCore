package com.github.frcsty.districtcore.dependency;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.dependency.util.ActionBarAPI;
import com.github.frcsty.districtcore.dependency.util.Actions;
import com.github.frcsty.districtcore.dependency.util.lib.ActionManager;
import com.github.frcsty.districtcore.dependency.util.title.Title;
import com.github.frcsty.districtcore.dependency.util.title.Title_BukkitNoTimings;
import com.github.frcsty.districtcore.plugins.tokens.token.TokenManager;
import de.dustplanet.util.SilkUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;

public final class DependencyUtil {

    private static Economy economy;
    private static ActionBarAPI actionBarAPI;
    private static TokenManager tokenManager;
    private static Title title;
    private static ActionManager actionManager;
    private static SilkUtil silkUtil;

    private final DistrictCore core;
    private final Actions actions;

    public DependencyUtil(final DistrictCore core) {
        this.core = core;

        this.actions = new Actions();
        actionBarAPI = new ActionBarAPI(core);
    }

    public static Economy getEconomy() {
        return economy;
    }

    public static ActionBarAPI getActionBarAPI() {
        return actionBarAPI;
    }

    public static TokenManager getTokenManager() {
        return tokenManager;
    }

    public static Title getTitleManager() {
        return title;
    }

    public static ActionManager getActionManager() {
        return actionManager;
    }

    public static SilkUtil getSilkUtil() {
        return silkUtil;
    }

    public void onEnable() {
        final RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider == null) {
            core.getLogger().log(Level.WARNING, "Failed to register dependency: Economy.class!");
            getServer().getPluginManager().disablePlugin(core);
            return;
        }
        economy = economyProvider.getProvider();

        final Plugin shopGuiPlusPlugin = getServer().getPluginManager().getPlugin("ShopGUIPlus");
        if (shopGuiPlusPlugin != null && !shopGuiPlusPlugin.isEnabled()) {
            core.getLogger().log(Level.WARNING, "Failed to register dependency: ShopGUIPlusAPI.class!");
            core.getServer().getPluginManager().disablePlugin(core);
        }

        final RegisteredServiceProvider<TokenManager> tokenProvider = getServer().getServicesManager().getRegistration(TokenManager.class);
        if (tokenProvider == null) {
            core.getLogger().log(Level.WARNING, "Failed to register dependency: TokenManager.class!");
            getServer().getPluginManager().disablePlugin(core);
            return;
        }
        tokenManager = tokenProvider.getProvider();

        title = new Title_BukkitNoTimings();

        actionBarAPI.enable();
        actionManager = new ActionManager(core);
        actionManager.loadDefaults();
        actions.loadCustomActions(actionManager);

        silkUtil = SilkUtil.hookIntoSilkSpanwers();
    }
}
