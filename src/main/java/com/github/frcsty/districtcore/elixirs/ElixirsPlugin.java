package com.github.frcsty.districtcore.elixirs;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.elixirs.command.ElixirGiveCommand;
import com.github.frcsty.districtcore.elixirs.elixir.ElixirStorage;

public class ElixirsPlugin {

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
