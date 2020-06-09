package com.github.frcsty.districtcore.tokens.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.tokens.TokensPlugin;
import com.github.frcsty.districtcore.util.Color;
import com.github.frcsty.districtcore.util.Replace;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
@Command("tokens")
public class TokensGiveCommand extends CommandBase {

    private final TokensPlugin plugin;
    private final DistrictCore core;

    public TokensGiveCommand(final TokensPlugin plugin, final DistrictCore core) {
        this.plugin = plugin;
        this.core = core;
    }

    @SubCommand("give")
    @Permission("tokens.give")
    public void tokensGiveCommand(final CommandSender sender, final String target, final Long input) {
        final FileConfiguration config = core.getConfig();
        final Player player = Bukkit.getPlayer(target);
        if (player == null) {
            sender.sendMessage(Color.colorize(config.getString("messages.invalid-player")));
            return;
        }

        plugin.getTokenManager().addTokens(player, input);
        sender.sendMessage(Color.colorize(Replace.replaceString(config.getString("messages.gave-tokens")
                , "{player}", player.getName()
                , "{tokens}", String.valueOf(input))));
    }
}
