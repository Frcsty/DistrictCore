package com.github.frcsty.districtcore.plugins.elixirs;

import com.github.frcsty.districtcore.CorePlugin;
import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.elixirs.command.ElixirGiveCommand;
import com.github.frcsty.districtcore.plugins.elixirs.elixir.ElixirStorage;

public class ElixirsPlugin implements CorePlugin {

    private final DistrictCore core;
    private final ElixirStorage elixirStorage = new ElixirStorage();

    public ElixirsPlugin(final DistrictCore core) {
        this.core = core;
    }

    public void onEnable() {
        core.addCommand(new ElixirGiveCommand(core, this));
        elixirStorage.loadElixirs(core);
    }

    public void onDisable() {
        elixirStorage.getElixirHashMap().clear();
    }

    public ElixirStorage getElixirStorage() {
        return this.elixirStorage;
    }
}
