package com.github.frcsty.districtcore.commands.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.util.Color;
import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;

@Command("website")
@Alias("forums")
public class WebsiteCommand extends CommandBase {

    private final DistrictCore core;

    public WebsiteCommand(final DistrictCore core) {
        this.core = core;
    }

    @Default
    @Permission("district.core.command.website")
    public void websiteCommand(final CommandSender sender) {
        core.getMessageLoader().getListMessage("website-message").forEach(line -> sender.sendMessage(Color.colorize(line)));
    }
}
