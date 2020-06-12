package com.github.frcsty.districtcore.plugins.collectors;

import com.github.frcsty.districtcore.CorePlugin;
import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.collectors.collector.CollectorStorage;
import com.github.frcsty.districtcore.plugins.collectors.collector.FileManager;
import com.github.frcsty.districtcore.plugins.collectors.command.CollectorGiveCommand;
import com.github.frcsty.districtcore.plugins.collectors.listener.CollectorBreakListener;
import com.github.frcsty.districtcore.plugins.collectors.listener.CollectorPlaceListener;
import com.github.frcsty.districtcore.plugins.collectors.listener.CollectorUseListener;
import com.github.frcsty.districtcore.plugins.collectors.runnable.CollectorChunkRunnable;

public final class CollectorsPlugin implements CorePlugin {

    private final DistrictCore core;
    private final CollectorStorage collectorStorage = new CollectorStorage();
    private final FileManager fileManager;

    public CollectorsPlugin(final DistrictCore core) {
        this.core = core;

        fileManager = new FileManager(core, this);
    }

    public void onEnable() {
        collectorStorage.load(core);

        core.addCommand(new CollectorGiveCommand(core, this));
        core.addListeners(new CollectorPlaceListener(core, this)
                , new CollectorUseListener(core, this)
                , new CollectorBreakListener(core, this));

        new CollectorChunkRunnable(core, this).run();

        fileManager.load();
    }

    public void onDisable() {
        fileManager.save();
    }

    public CollectorStorage getCollectorStorage() {
        return this.collectorStorage;
    }
}
