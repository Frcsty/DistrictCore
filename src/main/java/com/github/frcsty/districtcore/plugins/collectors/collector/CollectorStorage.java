package com.github.frcsty.districtcore.plugins.collectors.collector;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.collectors.collector.chunk.CollectorChunk;
import com.github.frcsty.districtcore.plugins.collectors.collector.object.Collector;
import org.bukkit.Chunk;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CollectorStorage {

    private final Map<Chunk, CollectorChunk> collectorChunkMap = new HashMap<>();
    private Collector collectorItem;

    public void load(final DistrictCore core) {
        final ConfigurationSection collector = core.getSectionLoader().getSection("collector");

        final int material = collector.getInt("material");
        final int data = collector.getInt("data");
        final String display = collector.getString("display");
        final List<String> lore = collector.getStringList("lore");

        collectorItem = new Collector(material, data, display, lore);
    }

    public Collector getCollectorItem() {
        return this.collectorItem;
    }

    public CollectorChunk getCollectorChunk(final Chunk chunk) {
        return this.collectorChunkMap.get(chunk);
    }

    public Map<Chunk, CollectorChunk> getCollectorChunks() { return this.collectorChunkMap; }

    public void setCollectorChunk(final Chunk chunk, final List<String> components) {
        this.collectorChunkMap.put(chunk, new CollectorChunk(components));
    }

    public void updateCollectorChunk(final Chunk chunk, final CollectorChunk collectorChunk) {
        this.collectorChunkMap.put(chunk, collectorChunk);
    }

    public void removeCollectorChunk(final Chunk chunk) {
        this.collectorChunkMap.remove(chunk);
    }
}
