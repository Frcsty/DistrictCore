package com.github.frcsty.districtcore.elixirs;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.elixirs.command.ElixirGiveCommand;
import com.github.frcsty.districtcore.elixirs.elixir.ElixirStorage;
import me.mattstudios.mf.base.CommandManager;

public class ElixirsPlugin {

    private final DistrictCore core;

    public ElixirsPlugin(final DistrictCore core) {
        this.core = core;
    }

    private final ElixirStorage elixirStorage = new ElixirStorage();

    public void onEnable() {
        core.saveDefaultConfig();

        final CommandManager commandManager = new CommandManager(core);
        commandManager.register(new ElixirGiveCommand(core, this));

        elixirStorage.loadElixirs(core);
    }

    public void onDisable() {
        core.reloadConfig();

        elixirStorage.getElixirHashMap().clear();
    }

    public ElixirStorage getElixirStorage() {
        return this.elixirStorage;
    }
}
