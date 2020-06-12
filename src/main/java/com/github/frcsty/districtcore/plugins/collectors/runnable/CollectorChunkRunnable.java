package com.github.frcsty.districtcore.plugins.collectors.runnable;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.collectors.CollectorsPlugin;
import com.github.frcsty.districtcore.plugins.collectors.collector.CollectorStorage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class CollectorChunkRunnable {

    private final DistrictCore core;
    private final CollectorsPlugin plugin;

    public CollectorChunkRunnable(final DistrictCore core, final CollectorsPlugin plugin) {
        this.core = core;
        this.plugin = plugin;
    }

    public void run() {
        final CollectorStorage storage = plugin.getCollectorStorage();
        new BukkitRunnable() {
            @Override
            public void run() {
                storage.getCollectorChunks().forEach((chunk, collectorChunk) -> {
                    for (Entity entity : chunk.getEntities()) {
                        if (!(entity instanceof Item)) {
                            continue;
                        }

                        final ItemStack item = ((Item) entity).getItemStack();

                        if (!collectorChunk.getValidMaterials().contains(item.getType())) {
                            continue;
                        }

                        collectorChunk.addMaterial(item.getType(), item.getAmount());
                        entity.remove();
                    }

                    storage.updateCollectorChunk(chunk, collectorChunk);
                });
            }
        }.runTaskTimer(core, 1, 5);
    }
}
