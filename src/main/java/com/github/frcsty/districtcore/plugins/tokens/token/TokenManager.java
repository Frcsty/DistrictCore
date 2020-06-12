package com.github.frcsty.districtcore.plugins.tokens.token;

import org.bukkit.entity.Player;

public final class TokenManager {

    private final TokenStorage storage;

    public TokenManager(final TokenStorage storage) {
        this.storage = storage;
    }

    public void addTokens(final Player player, final long amount) {
        final TokenPlayer tokenPlayer = storage.getTokenPlayer(player.getUniqueId());

        tokenPlayer.addTokens(amount);
        storage.setTokenPlayer(player.getUniqueId(), tokenPlayer);
    }

    public long getTokens(final Player player) {
        return storage.getTokenPlayer(player.getUniqueId()).getTokens();
    }

    public void setTokens(final Player player, final long amount) {
        final TokenPlayer tokenPlayer = storage.getTokenPlayer(player.getUniqueId());

        tokenPlayer.setTokens(amount);
        storage.setTokenPlayer(player.getUniqueId(), tokenPlayer);
    }

    public void removeTokens(final Player player, final long amount) {
        final TokenPlayer tokenPlayer = storage.getTokenPlayer(player.getUniqueId());

        tokenPlayer.removeTokens(amount);
        storage.setTokenPlayer(player.getUniqueId(), tokenPlayer);
    }

    public void resetTokens(final Player player) {
        final TokenPlayer tokenPlayer = storage.getTokenPlayer(player.getUniqueId());

        tokenPlayer.resetTokens();
        storage.setTokenPlayer(player.getUniqueId(), tokenPlayer);
    }

    public void createTokenPlayer(final Player player) {
        if (storage.getTokenPlayer(player.getUniqueId()) == null) {
            storage.createTokenPlayer(player);
        }
    }
}
