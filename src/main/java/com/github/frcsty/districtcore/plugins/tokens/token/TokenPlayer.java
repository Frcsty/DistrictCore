package com.github.frcsty.districtcore.plugins.tokens.token;

import org.bukkit.OfflinePlayer;

class TokenPlayer {

    private OfflinePlayer player;
    private long tokens;

    TokenPlayer(final OfflinePlayer player, final long value) {
        this.player = player;
        this.tokens = value;
    }

    final void addTokens(final long tokens) {
        this.tokens += tokens;
    }

    final void removeTokens(final long tokens) {
        this.tokens -= tokens;
    }

    final long getTokens() {
        return this.tokens;
    }

    final void setTokens(final long tokens) {
        this.tokens = tokens;
    }

    final OfflinePlayer getPlayer() {
        return this.player;
    }

    final void resetTokens() {
        this.tokens = 0;
    }
}
