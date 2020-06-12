package com.github.frcsty.districtcore.plugins.pouches;

import com.github.frcsty.districtcore.CorePlugin;
import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.pouches.command.GivePouchCommand;
import com.github.frcsty.districtcore.plugins.pouches.listener.PouchOpenListener;
import com.github.frcsty.districtcore.plugins.pouches.object.PouchStorage;
import com.github.frcsty.districtcore.plugins.pouches.util.Actions;
import com.github.frcsty.districtcore.plugins.pouches.util.lib.ActionManager;
import com.github.frcsty.districtcore.plugins.pouches.util.title.Title;
import com.github.frcsty.districtcore.plugins.pouches.util.title.Title_BukkitNoTimings;

import java.util.ArrayList;

public class PouchesPlugin implements CorePlugin {
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
        core.addListener(new PouchOpenListener(core, this));
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
