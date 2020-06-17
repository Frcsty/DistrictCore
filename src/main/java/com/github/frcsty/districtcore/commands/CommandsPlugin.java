package com.github.frcsty.districtcore.commands;

import com.github.frcsty.districtcore.CorePlugin;
import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.commands.command.*;

public class CommandsPlugin implements CorePlugin {

    private final DistrictCore core;

    public CommandsPlugin(final DistrictCore core) {
        this.core = core;
    }

    public void onEnable() {
        core.addCommands(new ReloadCommand(core), new DiscordCommand(core)
                , new StoreCommand(core), new WebsiteCommand(core)
                , new HelpCommand());
    }
}
