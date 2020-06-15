package com.github.frcsty.districtcore.plugins.tokens;

import com.github.frcsty.districtcore.CorePlugin;
import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.tokens.command.*;
import com.github.frcsty.districtcore.plugins.tokens.listener.CreateTokenPlayerListener;
import com.github.frcsty.districtcore.plugins.tokens.placeholder.Placeholders;
import com.github.frcsty.districtcore.plugins.tokens.token.FileManager;
import com.github.frcsty.districtcore.plugins.tokens.token.TokenManager;
import com.github.frcsty.districtcore.plugins.tokens.token.TokenStorage;
import org.bukkit.plugin.ServicePriority;

public class TokensPlugin implements CorePlugin {

    private final DistrictCore core;
    private FileManager fileManager;
    private TokenStorage tokenStorage;
    private TokenManager tokenManager;

    public TokensPlugin(final DistrictCore core) {
        this.core = core;
    }

    public void onEnable() {
        fileManager = new FileManager(core);
        tokenStorage = new TokenStorage();

        fileManager.createTokenFile();
        tokenStorage.load(fileManager);
        this.tokenManager = new TokenManager(tokenStorage);

        core.addCommands(new TokensCommand(this, core), new TokensGiveCommand(this, core)
                , new TokensResetCommand(this, core), new TokensPayCommand(this, core)
                , new TokenShopCommand(core, this), new TokensRemoveCommand(core, this));
        new Placeholders(tokenManager).register();

        core.addListener(new CreateTokenPlayerListener(this));

        core.getServer().getPluginManager().registerEvents(new CreateTokenPlayerListener(this), core);
        core.getServer().getServicesManager().register(TokenManager.class, tokenManager, core, ServicePriority.Normal);
    }

    public void onDisable() {
        tokenStorage.save(fileManager);
        fileManager.saveTokenFile();

        core.getServer().getServicesManager().unregisterAll(core);
    }

    public TokenManager getTokenManager() {
        return this.tokenManager;
    }
}
