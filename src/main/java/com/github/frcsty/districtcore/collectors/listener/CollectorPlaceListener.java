package com.github.frcsty.districtcore.collectors.listener;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.collectors.CollectorsPlugin;
import com.github.frcsty.districtcore.collectors.collector.chunk.CollectorChunk;
import com.github.frcsty.districtcore.util.Color;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class CollectorPlaceListener implements Listener {

    private final CollectorsPlugin plugin;
    private final DistrictCore core;

    public CollectorPlaceListener(final DistrictCore core, final CollectorsPlugin plugin) {
        this.core = core;
        this.plugin = plugin;
    }

    @EventHandler
    public void onCollectorPlace(BlockPlaceEvent event) {
        final ItemStack item = event.getItemInHand();
        final Player player = event.getPlayer();
        if (item == null) return;
        if (item.getType() != Material.BEACON) return;

        final String nbt = ItemNBT.getNBTTag(item, "collector");
        if (item.getType() == Material.BEACON && nbt.length() == 0) {
            event.setCancelled(true);
            player.sendMessage(Color.colorize(core.getMessageLoader().getMessage("beacons-are-disabled")));
            return;
        }

        if (plugin.getCollectorStorage().getCollectorChunk(player.getLocation().getChunk()) != null) {
            event.setCancelled(true);
            player.sendMessage(Color.colorize(core.getMessageLoader().getMessage("already-contains-collector")));
            return;
        }

        player.sendMessage(Color.colorize(core.getMessageLoader().getMessage("placed-collector")));
        final CollectorChunk collectorChunk = new CollectorChunk(core.getConfig().getStringList("collector.material-items"));
        collectorChunk.setWorld(player.getWorld().getName());

        plugin.getCollectorStorage().updateCollectorChunk(player.getLocation().getChunk(), collectorChunk);
    }
}
