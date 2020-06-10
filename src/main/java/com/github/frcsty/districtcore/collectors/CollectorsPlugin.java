package com.github.frcsty.districtcore.collectors;

import com.github.frcsty.districtcore.CorePlugin;
import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.collectors.collector.CollectorStorage;
import com.github.frcsty.districtcore.collectors.collector.FileManager;
import com.github.frcsty.districtcore.collectors.command.CollectorGiveCommand;
import com.github.frcsty.districtcore.collectors.listener.CollectorPlaceListener;
import com.github.frcsty.districtcore.collectors.listener.CollectorUseListener;
import com.github.frcsty.districtcore.collectors.listener.CollectorBreakListener;
import com.github.frcsty.districtcore.collectors.runnable.CollectorChunkRunnable;

import static org.bukkit.Bukkit.getServer;

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
        getServer().getPluginManager().registerEvents(new CollectorPlaceListener(core, this), core);
        getServer().getPluginManager().registerEvents(new CollectorUseListener(core, this), core);
        getServer().getPluginManager().registerEvents(new CollectorBreakListener(core), core);

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
