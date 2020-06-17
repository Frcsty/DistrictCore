package com.github.frcsty.districtcore.plugins.tokens.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.tokens.TokensPlugin;
import com.github.frcsty.districtcore.plugins.tokens.token.TokenManager;
import com.github.frcsty.districtcore.util.Color;
import com.github.frcsty.districtcore.util.Replace;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
@Command("tokens")
public class TokensCommand extends CommandBase {

    private final TokensPlugin plugin;
    private final DistrictCore core;

    public TokensCommand(final TokensPlugin plugin, final DistrictCore core) {
        this.plugin = plugin;
        this.core = core;
    }

    @Default
    @Permission("district.tokens.command.balance")
    public void onTokensCommand(final CommandSender sender, final String[] args) {
        final TokenManager manager = plugin.getTokenManager();
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                return;
            }

            final Player player = (Player) sender;
            player.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("tokens-check-self")
                    , "{tokens}", String.valueOf(manager.getTokens(player)))));
            return;
        }

        final Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            sender.sendMessage(Color.colorize(core.getMessageLoader().getMessage("invalid-player")));
            return;
        }

        sender.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("tokens-check-other")
                , "{tokens}", String.valueOf(manager.getTokens(target))
                , "{player}", target.getName())));
    }
}
