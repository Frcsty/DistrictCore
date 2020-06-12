package com.github.frcsty.districtcore.patches.mechanics;

import com.github.frcsty.districtcore.DistrictCore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Arrays;
import java.util.List;

public class Sponges implements Listener {

    private final DistrictCore core;

    public Sponges(final DistrictCore core) {
        this.core = core;
    }

    @EventHandler
    public void onSpongePlace(BlockPlaceEvent event) {
        final Block block = event.getBlockPlaced();
        if (block.getType() != Material.SPONGE) {
            return;
        }

        final ConfigurationSection sponges = core.getSectionLoader().getSection("patches").getConfigurationSection("sponge-mechanics");

        final int rangeX = sponges.getInt("radiusX");
        final int rangeY = sponges.getInt("radiusY");
        final int rangeZ = sponges.getInt("radiusZ");

        final Location location = block.getLocation();
        final World world = block.getWorld();
        final List<BlockFace> faces = Arrays.asList(BlockFace.DOWN, BlockFace.UP, BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH);
        for (int x = location.getBlockX() - rangeX; x < location.getBlockX() + rangeX; x++) {
            if (x < location.getBlockX() - rangeX || x > location.getBlockX() + (rangeX - 1)) continue;

            for (int y = (rangeY != 1 ? location.getBlockY() - rangeY : location.getBlockY()); y < (rangeY != 1 ? location.getBlockY() + rangeY : location.getBlockY() + 1); y++) {
                if (y < location.getBlockY() - rangeY || y > location.getBlockY() + (rangeY - 1)) continue;

                for (int z = block.getLocation().getBlockZ() - rangeZ; z < block.getLocation().getBlockZ() + rangeZ; z++) {
                    if (z < location.getBlockZ() - rangeZ || z > location.getBlockZ() + (rangeZ - 1)) continue;
                    final Block blockX = world.getBlockAt(x, y, z);
                    if (!blockX.isLiquid()) continue;

                    blockX.setType(Material.GLASS);
                }
            }
        }
        /*
        for (int x = block.getLocation().getBlockX() - rangeX; x < block.getLocation().getBlockX() + rangeX; x++) {
            for (int y = (rangeY != 1 ? block.getLocation().getBlockY() - rangeY : block.getLocation().getBlockY()); y < (rangeY != 1 ? block.getLocation().getBlockY() + rangeY : block.getLocation().getBlockY() + 1); y++) {
                for (int z = block.getLocation().getBlockZ() - rangeZ; z < block.getLocation().getBlockZ() + rangeZ; z++) {
                    final Block broken = world.getBlockAt(new Location(world, x, y, z));
                    if (!broken.isLiquid()) continue;

                    for (BlockFace face : faces) {
                        final Block relative = world.getBlockAt(new Location(world, x + face.getModX(), y + face.getModY(), z + face.getModZ()));

                        relative.setType(Material.GLASS);
                        //if (!relative.isLiquid()) continue;
                        //relative.getState().update(true, false);
                    }
                }
            }
        }
        block.setType(Material.SPONGE);
        */
    }
}
