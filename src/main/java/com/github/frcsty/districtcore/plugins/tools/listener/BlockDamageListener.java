package com.github.frcsty.districtcore.plugins.tools.listener;

import com.github.frcsty.districtcore.dependency.DependencyUtil;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockDamageListener implements Listener {

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        final ItemStack item = event.getItemInHand();
        final Player player = event.getPlayer();
        final World world = player.getWorld();
        final Block block = event.getBlock();
        final String data = ItemNBT.getNBTTag(item, "tool");

        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        if (data.length() == 0) {
            return;
        }

        if (data.equalsIgnoreCase("craft") || data.equalsIgnoreCase("sell") || data.equalsIgnoreCase("sand") || data.equalsIgnoreCase("lightning")) {
            return;
        }

        final String range = ItemNBT.getNBTTag(item, "range");

        if (range.length() == 0) {
            return;
        }

        final String[] ranges = range.split("x");
        final Map<Material, Integer> drops = new HashMap<>();

        final int rangeX = Integer.valueOf(ranges[0]) / 2;
        final int rangeY = Integer.valueOf(ranges[1]) != 1 ? Integer.valueOf(ranges[1]) / 2 : 1;
        final int rangeZ = Integer.valueOf(ranges[2]) / 2;

        if (data.equalsIgnoreCase("tray")) {
            for (int x = block.getLocation().getBlockX() - rangeX; x < block.getLocation().getBlockX() + rangeX; x++) {
                for (int y = (rangeY != 1 ? block.getLocation().getBlockY() - rangeY : block.getLocation().getBlockY()); y < (rangeY != 1 ? block.getLocation().getBlockY() + rangeY : block.getLocation().getBlockY() + 1); y++) {
                    for (int z = block.getLocation().getBlockZ() - rangeZ; z < block.getLocation().getBlockZ() + rangeZ; z++) {
                        final Block broken = world.getBlockAt(new Location(world, x, y, z));
                        final List<Material> materials = Arrays.asList(Material.DIRT, Material.NETHERRACK);

                        if (materials.contains(broken.getType()) && broken.getType() != Material.AIR) {
                            drops.put(broken.getType(), drops.getOrDefault(broken.getType(), 0) + 1);
                            broken.setType(Material.AIR);
                        }

                    }
                }
            }
        }
        if (data.equalsIgnoreCase("trench")) {
            for (int x = -rangeX; x < rangeX; x++) {
                for (int y = -rangeY; y <= rangeY; y++) {
                    for (int z = -rangeZ; z < rangeZ; z++) {
                        final int locX = block.getLocation().getBlockX() + x;
                        final int locY = block.getLocation().getBlockY() + y;
                        final int locZ = block.getLocation().getBlockZ() + z;

                        final Block broken = world.getBlockAt(new Location(world, locX, locY, locZ));
                        final List<Material> materials = Arrays.asList(Material.BEDROCK, Material.AIR);

                        if (!materials.contains(broken.getType())) {
                            drops.put(broken.getType(), drops.getOrDefault(broken.getType(), 0) + 1);
                            broken.setType(Material.AIR);

                        }
                    }
                }
            }
        }

        drops.forEach((key, value) -> DependencyUtil.getEconomy().depositPlayer(player, value * ShopGuiPlusApi.getItemStackPriceSell(new ItemStack(key))));
        event.setCancelled(true);
    }
}
