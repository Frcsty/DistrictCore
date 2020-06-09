package com.github.frcsty.districtcore.tokens.token;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class TokenStorage {

    private final Map<UUID, TokenPlayer> users = new HashMap<>();

    public final void load(final FileManager manager) {
        final ConfigurationSection section = manager.getTokenConfig().getConfigurationSection("users");
        int amount = 0;

        for (String entry : section.getKeys(false)) {
            final UUID uuid = UUID.fromString(entry);
            final long value = manager.getTokenConfig().getLong("users." + entry);
            final OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

            users.put(uuid, new TokenPlayer(player, value));
            amount++;
        }

        manager.saveTokenFile();
        Bukkit.getLogger().log(Level.INFO, "[DistrictTokens] Loaded " + amount + " player(s).");
    }

    public final void save(final FileManager manager) {
        final FileConfiguration config = manager.getTokenConfig();
        int amount = 0;

        // users.uuid:tokens
        for (UUID uuid : users.keySet()) {
            config.set("users." + uuid.toString(), users.get(uuid).getTokens());
            amount++;
        }

        manager.saveTokenFile();
        Bukkit.getLogger().log(Level.INFO, "[DistrictTokens] Saved " + amount + " player(s).");
    }

    final void setTokenPlayer(final UUID uuid, final TokenPlayer tokenPlayer) {
        users.put(uuid, tokenPlayer);
    }

    final TokenPlayer getTokenPlayer(final UUID uuid) {
        return users.get(uuid);
    }

    final void createTokenPlayer(final Player player) {
        users.put(player.getUniqueId(), new TokenPlayer(player, 0));
    }
}
