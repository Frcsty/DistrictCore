package com.github.frcsty.districtcore.commands;

import com.github.frcsty.districtcore.CorePlugin;
import com.github.frcsty.districtcore.DistrictCore;

public class CommandsPlugin implements CorePlugin {

    private final DistrictCore core;

    public CommandsPlugin(final DistrictCore core) {
        this.core = core;
    }

    public void onEnable() {
        core.addCommand(new ReloadCommand(core));
    }
}
