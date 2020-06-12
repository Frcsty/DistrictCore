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
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
@Command("tokens")
public class TokensPayCommand extends CommandBase {

    private final TokensPlugin plugin;
    private final DistrictCore core;

    public TokensPayCommand(final TokensPlugin plugin, final DistrictCore core) {
        this.plugin = plugin;
        this.core = core;
    }

    @SubCommand("pay")
    @Permission("tokens.pay")
    public void tokensPayCommand(final Player player, final String targetString, final Long tokens) {
        final Player target = Bukkit.getPlayerExact(targetString);

        if (target == null) {
            player.sendMessage(Color.colorize(core.getMessageLoader().getMessage("invalid-player")));
            return;
        }

        if (player.equals(target)) {
            player.sendMessage(Color.colorize(core.getMessageLoader().getMessage("cannot-pay-self")));
            return;
        }
        final TokenManager manager = plugin.getTokenManager();

        if (manager.getTokens(player) < tokens) {
            player.sendMessage(Color.colorize(core.getMessageLoader().getMessage("not-enough-tokens")));
            return;
        }

        manager.removeTokens(player, tokens);
        manager.addTokens(target, tokens);
        player.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("paid-tokens")
                , "{player}", target.getName()
                , "{tokens}", String.valueOf(tokens))));
        target.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("received-tokens")
                , "{player}", player.getName()
                , "{tokens}", String.valueOf(tokens))));
    }
}