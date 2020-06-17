package com.github.frcsty.districtcore.commands.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.util.Color;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;

@Command("store")
public class StoreCommand extends CommandBase {

    private final DistrictCore core;

    public StoreCommand(final DistrictCore core) {
        this.core = core;
    }

    @Default
    @Permission("district.core.command.store")
    public void storeCommand(final CommandSender sender) {
        core.getMessageLoader().getListMessage("store-message").forEach(line -> sender.sendMessage(Color.colorize(line)));
    }
}
