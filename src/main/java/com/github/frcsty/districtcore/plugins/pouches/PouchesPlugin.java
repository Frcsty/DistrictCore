package com.github.frcsty.districtcore.plugins.pouches;

import com.github.frcsty.districtcore.CorePlugin;
import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.pouches.command.GivePouchCommand;
import com.github.frcsty.districtcore.plugins.pouches.listener.PouchOpenListener;
import com.github.frcsty.districtcore.plugins.pouches.object.PouchStorage;

import java.util.ArrayList;

public class PouchesPlugin implements CorePlugin {
    private final DistrictCore core;
    private final PouchStorage pouchStorage = new PouchStorage();

    public PouchesPlugin(final DistrictCore core) {
        this.core = core;
    }

    public void onEnable() {

        core.getCompletionHandler().register("#pouches", input -> new ArrayList<>(core.getConfig().getConfigurationSection("pouches").getKeys(false)));
        core.addCommand(new GivePouchCommand(this, core));
        core.addListener(new PouchOpenListener(core, this));
        pouchStorage.loadPouches(core);
    }

    public final PouchStorage getPouchStorage() {
        return this.pouchStorage;
    }

}
