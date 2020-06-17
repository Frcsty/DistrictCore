package com.github.frcsty.districtcore.commands.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.util.Color;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;

@Command("discord")
public class DiscordCommand extends CommandBase {

    private final DistrictCore core;
    public DiscordCommand(final DistrictCore core) {
        this.core = core;
    }

    @Default
    @Permission("district.core.command.discord")
    public void discordCommand(final CommandSender sender) {
        core.getMessageLoader().getListMessage("discord-message").forEach(line -> sender.sendMessage(Color.colorize(line)));
    }

}
