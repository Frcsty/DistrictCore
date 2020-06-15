package com.github.frcsty.districtcore.plugins.tools.listener;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.ArrayList;
import java.util.List;

public class WaterUpdates implements Listener {

    private static List<Chunk> chunks = new ArrayList<>();

    @EventHandler
    public void onWaterFlow(BlockFromToEvent event) {
        final Block block = event.getBlock();
        final Chunk chunk = block.getChunk();
        final Chunk newChunk = event.getToBlock().getChunk();

        if (!chunks.contains(chunk) && !chunks.contains(newChunk)) {
            return;
        }

        final Material material = block.getType();
        if (material == Material.WATER || material == Material.valueOf("STATIONARY_WATER") || material == Material.LAVA || material == Material.valueOf("STATIONARY_LAVA")) {
            event.setCancelled(true);
        }
    }

    public static void addWaterChunk(final Chunk chunk) {
        chunks.add(chunk);
    }

    static void removeWaterChunk(final Chunk chunk) {
        chunks.remove(chunk);
    }
}
