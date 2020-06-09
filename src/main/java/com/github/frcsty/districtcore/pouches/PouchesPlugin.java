package com.github.frcsty.districtcore.pouches;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.pouches.command.GivePouchCommand;
import com.github.frcsty.districtcore.pouches.listener.PouchOpenListener;
import com.github.frcsty.districtcore.pouches.object.PouchStorage;
import com.github.frcsty.districtcore.pouches.util.Actions;
import com.github.frcsty.districtcore.pouches.util.lib.ActionManager;
import com.github.frcsty.districtcore.pouches.util.title.Title;
import com.github.frcsty.districtcore.pouches.util.title.Title_BukkitNoTimings;

import java.util.ArrayList;

import static org.bukkit.Bukkit.getServer;

public class PouchesPlugin {
    private final DistrictCore core;
    private final PouchStorage pouchStorage = new PouchStorage();
    private final Actions actions = new Actions();
    private final Title title = new Title_BukkitNoTimings();
    private ActionManager actionManager;

    public PouchesPlugin(final DistrictCore core) {
        this.core = core;
    }

    public void onEnable() {
        actionManager = new ActionManager(core);

        core.getCompletionHandler().register("#pouches", input -> new ArrayList<>(core.getConfig().getConfigurationSection("pouches").getKeys(false)));
        core.addCommand(new GivePouchCommand(this, core));
        actionManager.loadDefaults();
        actions.loadCustomActions(actionManager);

        getServer().getPluginManager().registerEvents(new PouchOpenListener(core, this), core);
        pouchStorage.loadPouches(core);
    }

    public final PouchStorage getPouchStorage() {
        return this.pouchStorage;
    }

    public final ActionManager getActionManager() {
        return this.actionManager;
    }

    public final Title getTitle() {
        return this.title;
    }
}
