package com.github.frcsty.districtcore.plugins.collectors.collector;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.collectors.CollectorsPlugin;
import com.github.frcsty.districtcore.plugins.collectors.collector.chunk.CollectorChunk;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

public class FileManager {

    private final DistrictCore core;
    private final CollectorsPlugin plugin;

    public FileManager(final DistrictCore core, final CollectorsPlugin plugin) {
        this.core = core;
        this.plugin = plugin;
    }

    public void save() {
        final Map<Chunk, CollectorChunk> collectorChunks = plugin.getCollectorStorage().getCollectorChunks();

        for (Chunk chunk : collectorChunks.keySet()) {
            final String collector = chunk.getX() + ";" + chunk.getZ() + ".yml";
            final CollectorChunk collectorChunk = collectorChunks.get(chunk);
            final File file = new File(core.getDataFolder() + "/collectors", collector);

            try {
                file.createNewFile();
            } catch (IOException ex) {
                core.getLogger().log(Level.WARNING, "An error occurred while trying to create file: '" + collector + "'!");
            }

            final FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
            fileConfig.set("world", collectorChunk.getWorld());
            for (Material material : collectorChunk.getContents().keySet()) {
                final int amount = collectorChunk.getContents().getOrDefault(material, 0);

                fileConfig.set("materials." + material.name(), amount);
            }
            try {
                fileConfig.save(file);
            } catch (IOException ex) {
                core.getLogger().log(Level.WARNING, "An error occurred while trying to save file: '" + collector + "'!");
            }
        }
    }

    public void load() {
        final File dir = new File(core.getDataFolder() + "/collectors");
        if (!dir.exists()) {
            return;
        }
        if (dir.length() == 0) {
            return;
        }
        final File[] files = dir.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            final FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
            final String[] coords = file.getName().substring(0, file.getName().lastIndexOf(".")).split(";");

            final Chunk chunk = Bukkit.getWorld(fileConfig.getString("world")).getChunkAt(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]));
            final CollectorChunk collectorChunk = new CollectorChunk(core.getConfig().getStringList("collector.material-items"));

            for (String mat : fileConfig.getConfigurationSection("materials").getKeys(false)) {
                final Material material = Material.getMaterial(mat);
                final int amount = fileConfig.getInt("materials." + mat);

                collectorChunk.addMaterial(material, amount);
            }

            plugin.getCollectorStorage().updateCollectorChunk(chunk, collectorChunk);
            file.delete();
        }
    }

}
