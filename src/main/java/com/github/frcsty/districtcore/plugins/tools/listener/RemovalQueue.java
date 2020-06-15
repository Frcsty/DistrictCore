package com.github.frcsty.districtcore.plugins.tools.listener;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;

public class RemovalQueue extends BukkitRunnable {

    private final LinkedList<Block> blocks = new LinkedList<>();
    private final int amount;
    private final Chunk chunk;

    public RemovalQueue(final int amount, final Chunk chunk) {
        this.amount = amount;
        this.chunk = chunk;
    }

    @Override
    public void run() {
        for (int count = 0; count < amount; count++) {
            if (blocks.isEmpty()) break;
            final Block block = blocks.getFirst();
            if (block.getType() != Material.AIR) block.setType(Material.AIR);
            else count--;
            blocks.removeFirst();
        }

        if (blocks.isEmpty()) {
            cancel();
            WaterUpdates.removeWaterChunk(chunk);
        }
    }

    public void addBlock(final Block block) {
        this.blocks.add(block);
    }
}
