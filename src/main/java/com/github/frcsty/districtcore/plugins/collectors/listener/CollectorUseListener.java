package com.github.frcsty.districtcore.plugins.collectors.listener;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.collectors.CollectorsPlugin;
import com.github.frcsty.districtcore.plugins.collectors.collector.chunk.CollectorChunk;
import com.github.frcsty.districtcore.plugins.collectors.listener.menu.CollectorMenu;
import me.mattstudios.mfgui.gui.guis.Gui;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CollectorUseListener implements Listener {

    private final DistrictCore core;
    private final CollectorsPlugin plugin;

    public CollectorUseListener(final DistrictCore core, final CollectorsPlugin plugin) {
        this.core = core;
        this.plugin = plugin;
    }

    @EventHandler
    public void onCollectorUse(PlayerInteractEvent event) {
        final Block block = event.getClickedBlock();
        final Player player = event.getPlayer();
        final Action action = event.getAction();
        if (block == null) return;
        if (action != Action.RIGHT_CLICK_BLOCK) return;

        final CollectorChunk collectorChunk = plugin.getCollectorStorage().getCollectorChunk(player.getLocation().getChunk());
        if (collectorChunk == null) {
            return;
        }

        if (block.getType() != Material.BEACON) return;

        final Gui menu = CollectorMenu.getCollectorMenu(core, plugin, collectorChunk, block);
        if (menu == null) {
            event.setCancelled(true);
            return;
        }

        event.setCancelled(true);
        menu.open(player);
    }
}
