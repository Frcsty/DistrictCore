package com.github.frcsty.districtcore.patches;

import com.github.frcsty.districtcore.CorePlugin;
import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.patches.mechanics.Entities;
import org.bukkit.event.Listener;

import java.util.Collections;
import java.util.List;

public class PatchesPlugin implements CorePlugin {

    private final DistrictCore core;
    private final List<Listener> listeners;

    public PatchesPlugin(final DistrictCore core) {
        this.core = core;

        listeners = Collections.singletonList(new Entities(core));
        // listeners = Arrays.asList(new Crafting(core), new Piston(core), new Sponges(core), new Potions(core), new World(core), new Entities(core), new Portal());
    }

    public void onEnable() {
        registerListeners();

    }

    private void registerListeners() {
        for (Listener listener : listeners) {
            core.getServer().getPluginManager().registerEvents(listener, core);
        }
    }
}
