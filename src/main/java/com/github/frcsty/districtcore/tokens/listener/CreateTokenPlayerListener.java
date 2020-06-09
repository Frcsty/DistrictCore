package com.github.frcsty.districtcore.tokens.listener;

import com.github.frcsty.districtcore.tokens.TokensPlugin;
import com.github.frcsty.districtcore.tokens.token.TokenManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CreateTokenPlayerListener implements Listener {

    private final TokensPlugin plugin;

    public CreateTokenPlayerListener(final TokensPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event) {
        final TokenManager manager = plugin.getTokenManager();
        final Player player = event.getPlayer();

        manager.createTokenPlayer(player);
    }
}
