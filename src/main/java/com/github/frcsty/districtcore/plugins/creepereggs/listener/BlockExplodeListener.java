package com.github.frcsty.districtcore.plugins.creepereggs.listener;

import com.github.frcsty.districtcore.plugins.creepereggs.CreeperEggsPlugin;
import com.github.frcsty.districtcore.plugins.creepereggs.object.SpawnEgg;
import de.dustplanet.util.SilkUtil;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.SplittableRandom;

public class BlockExplodeListener implements Listener {

    private final CreeperEggsPlugin plugin;
    private final SplittableRandom random;

    public BlockExplodeListener(final CreeperEggsPlugin plugin) {
        this.plugin = plugin;
        this.random = new SplittableRandom();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockExplode(EntityExplodeEvent event) {
        final Entity block = event.getEntity();

        if (block == null) {
            return;
        }
        final String type = CustomTNT.getMetadataValue(block);

        if (type == null) {
            return;
        }

        final SilkUtil util = plugin.getSilkUtil();
        final boolean spawner = event.blockList().stream().anyMatch(m -> m.getType() == util.nmsProvider.getSpawnerMaterial());

        if (!spawner) {
            return;
        }
        event.setCancelled(true);
        final SpawnEgg spawnEgg = plugin.getObjectStorage().getSpawnEgg(type);
        final int chance = spawnEgg.getPercent();

        for (Block block1 : event.blockList()) {
            if (block1.getType() == util.nmsProvider.getSpawnerMaterial()) {
                final int rnd = random.nextInt(100);
                final String entityID = util.getSpawnerEntityID(block1);

                if (rnd < chance) {
                    final World world = block1.getWorld();
                    world.dropItemNaturally(block.getLocation(), util.newSpawnerItem(entityID, util.getCustomSpawnerName(entityID), 1, false));
                }
            }
            block1.setType(Material.AIR);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        final ItemStack item = event.getItem();
        final Block block = event.getClickedBlock();

        final String type = ItemNBT.getNBTTag(item, "type");
        if (block == null) {
            return;
        }

        if (block.getType() == Material.MOB_SPAWNER && type != null && type.length() != 0) {
            event.setCancelled(true);
        }
    }

}
