package com.github.frcsty.districtcore.creepereggs;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.creepereggs.command.GiveEggCommand;
import com.github.frcsty.districtcore.creepereggs.listener.BlockExplodeListener;
import com.github.frcsty.districtcore.creepereggs.listener.CeggThrowListener;
import com.github.frcsty.districtcore.creepereggs.object.ObjectStorage;
import de.dustplanet.util.SilkUtil;

import static org.bukkit.Bukkit.getServer;

public class CreeperEggsPlugin {

    private final DistrictCore core;
    private final ObjectStorage objectStorage = new ObjectStorage();
    private SilkUtil silkUtil;

    public CreeperEggsPlugin(final DistrictCore core) {
        this.core = core;
    }

    public void onEnable() {
        silkUtil = SilkUtil.hookIntoSilkSpanwers();
        core.addCommand(new GiveEggCommand(core, this));

        objectStorage.loadSpawnEggs(core);
        getServer().getPluginManager().registerEvents(new CeggThrowListener(core, this), core);
        getServer().getPluginManager().registerEvents(new BlockExplodeListener(this), core);
    }

    public void onDisable() {
        objectStorage.getSpawnEggsHashMap().clear();
    }

    public final ObjectStorage getObjectStorage() {
        return this.objectStorage;
    }

    public final SilkUtil getSilkUtil() {
        return this.silkUtil;
    }
}
