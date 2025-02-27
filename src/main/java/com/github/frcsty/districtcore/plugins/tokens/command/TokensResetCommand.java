package com.github.frcsty.districtcore.plugins.tokens.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.tokens.TokensPlugin;
import com.github.frcsty.districtcore.plugins.tokens.token.TokenManager;
import com.github.frcsty.districtcore.util.Color;
import com.github.frcsty.districtcore.util.Replace;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
@Command("tokens")
public class TokensResetCommand extends CommandBase {

    private final TokensPlugin plugin;
    private final DistrictCore core;

    public TokensResetCommand(final TokensPlugin plugin, final DistrictCore core) {
        this.plugin = plugin;
        this.core = core;
    }

    @SubCommand("reset")
    @Permission("district.tokens.command.reset")
    public void tokensResetCommand(final CommandSender sender, final String target) {
        final Player player = Bukkit.getPlayerExact(target);
        if (player == null) {
            sender.sendMessage(Color.colorize(core.getMessageLoader().getMessage("invalid-player")));
            return;
        }

        final TokenManager manager = plugin.getTokenManager();

        manager.resetTokens(player);
        sender.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("reset-tokens")
                , "{player}", player.getName())));
    }
}