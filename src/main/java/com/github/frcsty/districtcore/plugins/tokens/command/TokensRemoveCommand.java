package com.github.frcsty.districtcore.plugins.tokens.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.tokens.TokensPlugin;
import com.github.frcsty.districtcore.util.Color;
import com.github.frcsty.districtcore.util.Replace;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("tokens")
public class TokensRemoveCommand extends CommandBase {

    private final DistrictCore core;
    private final TokensPlugin plugin;

    public TokensRemoveCommand(final DistrictCore core, final TokensPlugin plugin) {
        this.core = core;
        this.plugin = plugin;
    }

    @SubCommand("remove")
    @Permission("tokens.remove")
    public void tokensRemoveCommand(final CommandSender sender, final String target, final Long amount) {
        final Player player = Bukkit.getPlayerExact(target);

        if (player == null) {
            sender.sendMessage(Color.colorize(core.getMessageLoader().getMessage("invalid-player")));
            return;
        }

        sender.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("removed-tokens")
                , "{player}", player.getName()
                , "{amount}", String.valueOf(amount))));
        plugin.getTokenManager().removeTokens(player, amount);
    }
}
