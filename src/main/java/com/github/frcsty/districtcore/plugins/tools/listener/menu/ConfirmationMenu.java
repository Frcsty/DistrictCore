package com.github.frcsty.districtcore.plugins.tools.listener.menu;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.tools.listener.RemovalQueue;
import com.github.frcsty.districtcore.plugins.tools.listener.WaterUpdates;
import com.github.frcsty.districtcore.util.Color;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ConfirmationMenu {

    public static Gui getMenu(final DistrictCore core, final Chunk chunk, final int delay) {
        final ConfigurationSection menu = core.getSectionLoader().getSection("chunk-confirmation-menu");
        final Gui gui = new Gui(core, menu.getInt("rows"), menu.getString("title"));

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        for (String itemString : menu.getConfigurationSection("items").getKeys(false)) {
            final ConfigurationSection itemSection = menu.getConfigurationSection("items." + itemString);

            final GuiItem item = new GuiItem(new ItemBuilder(new ItemStack(itemSection.getInt("material"), 1, (short) itemSection.getInt("data")))
                    .setName(Color.colorize(itemSection.getString("display")))
                    .setLore(Color.colorize(itemSection.getStringList("lore")))
                    .build(), event -> {
                final Player player = (Player) event.getWhoClicked();

                if (itemString.equalsIgnoreCase("confirm")) {
                    player.sendMessage(Color.colorize(core.getMessageLoader().getMessage("started-chunk-buster")));
                    WaterUpdates.addWaterChunk(chunk);
                    player.setItemInHand(new ItemStack(Material.AIR));
                    gui.close(player);
                    removeChunk(core, chunk, player.getWorld(), delay);
                } else if (itemString.equalsIgnoreCase("cancel")) {
                    gui.close(player);
                }
            });

            gui.setItem(itemSection.getInt("slot"), item);
        }

        return gui;
    }

    private static void removeChunk(final DistrictCore core, final Chunk chunk, final World world, final int delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                final RemovalQueue removalQueue = new RemovalQueue(delay, chunk);

                final int borderX = chunk.getX() << 4;
                final int borderZ = chunk.getZ() << 4;

                int starting = 255;
                boolean set = false;
                for (int y = 255; y > 1; y--) {
                    for (int x = borderX; x < borderX + 16; x++) {
                        for (int z = borderZ; z < borderZ + 16; z++) {
                            if (world.getBlockTypeIdAt(x, y, z) != 0) {
                                starting = y;
                                set = true;
                                break;
                            }
                        }
                        if (set) break;
                    }
                    if (set) break;
                    ;
                }
                for (int y = starting; y > 0; y--) {
                    spiralPattern(borderX, y, borderZ, removalQueue, world);
                }
                removalQueue.runTaskTimer(core, 1L, 1L);
            }
        }.runTaskAsynchronously(core);
    }

    private static void spiralPattern(int x, int y, int z, final RemovalQueue queue, final World world) {
        int minOffset = 0;
        int maxOffset = 16;
        // A 16x16 area can be spiraled around in 8 loops
        for (; minOffset < 8; minOffset++) {
            int xMin = x + minOffset;
            int xMax = x + maxOffset;
            int zMin = z + minOffset;
            int zMax = z + maxOffset;

            int newX = xMin;
            int newZ = zMin;
            for (newX = xMin; newX < xMax; newX++) {
                queue.addBlock(world.getBlockAt(newX, y, zMin));
            }
            newX--;
            for (newZ = zMin + 1; newZ < zMax; newZ++) {
                queue.addBlock(world.getBlockAt(xMax - 1, y, newZ));
            }
            newZ--;
            for (newX = xMax - 2; newX >= xMin; newX--) {
                queue.addBlock(world.getBlockAt(newX, y, zMax - 1));
            }
            newX--;
            for (newZ = zMax - 2; newZ > zMin; newZ--) {
                queue.addBlock(world.getBlockAt(xMin, y, newZ));
            }
            maxOffset--;
        }
    }
}
