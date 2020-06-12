package com.github.frcsty.districtcore.plugins.collectors.listener;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.collectors.CollectorsPlugin;
import com.github.frcsty.districtcore.util.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;

public class CollectorBreakListener implements Listener {

    private final DistrictCore core;
    private final CollectorsPlugin plugin;

    public CollectorBreakListener(final DistrictCore core, final CollectorsPlugin plugin) {
        this.core = core;
        this.plugin = plugin;
    }

    @EventHandler
    public void onCollectorBreak(BlockBreakEvent event) {
        final Block block = event.getBlock();
        final Player player = event.getPlayer();
        if (block == null) return;
        if (block.getType() == Material.BEACON) {
            event.setCancelled(true);
            player.sendMessage(Color.colorize(core.getMessageLoader().getMessage("cannot-break-collector")));
        }
    }

    @EventHandler
    public void onCollectorExplode(BlockExplodeEvent event) {
        event.blockList().forEach(block -> {
            if (block.getType() == Material.BEACON) {
                event.blockList().remove(block);
                plugin.getCollectorStorage().removeCollectorChunk(block.getChunk());
            }
        });
    }
}
