package com.github.frcsty.districtcore.tokens;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.tokens.command.TokensCommand;
import com.github.frcsty.districtcore.tokens.command.TokensGiveCommand;
import com.github.frcsty.districtcore.tokens.command.TokensPayCommand;
import com.github.frcsty.districtcore.tokens.command.TokensResetCommand;
import com.github.frcsty.districtcore.tokens.listener.CreateTokenPlayerListener;
import com.github.frcsty.districtcore.tokens.placeholder.Placeholders;
import com.github.frcsty.districtcore.tokens.token.FileManager;
import com.github.frcsty.districtcore.tokens.token.TokenManager;
import com.github.frcsty.districtcore.tokens.token.TokenStorage;
import org.bukkit.plugin.ServicePriority;

public class TokensPlugin {

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
                , new TokensResetCommand(this, core), new TokensPayCommand(this, core));
        new Placeholders(tokenManager).register();

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
