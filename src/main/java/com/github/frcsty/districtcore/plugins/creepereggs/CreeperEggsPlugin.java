package com.github.frcsty.districtcore.plugins.creepereggs;

import com.github.frcsty.districtcore.CorePlugin;
import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.creepereggs.command.GiveEggCommand;
import com.github.frcsty.districtcore.plugins.creepereggs.listener.BlockExplodeListener;
import com.github.frcsty.districtcore.plugins.creepereggs.listener.CeggThrowListener;
import com.github.frcsty.districtcore.plugins.creepereggs.object.ObjectStorage;

public class CreeperEggsPlugin implements CorePlugin {

    private final DistrictCore core;
    private final ObjectStorage objectStorage = new ObjectStorage();

    public CreeperEggsPlugin(final DistrictCore core) {
        this.core = core;
    }

    public void onEnable() {
        core.addCommand(new GiveEggCommand(core, this));

        objectStorage.loadSpawnEggs(core);
        core.addListeners(new CeggThrowListener(core, this), new BlockExplodeListener(this));
    }

    public void onDisable() {
        objectStorage.getSpawnEggsHashMap().clear();
    }

    public final ObjectStorage getObjectStorage() {
        return this.objectStorage;
    }
}
