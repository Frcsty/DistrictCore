package com.github.frcsty.districtcore.plugins.tools.listener;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.dependency.DependencyUtil;
import com.github.frcsty.districtcore.dependency.util.ActionBarAPI;
import com.github.frcsty.districtcore.plugins.tools.listener.menu.ConfirmationMenu;
import com.github.frcsty.districtcore.plugins.tools.util.Condense;
import com.github.frcsty.districtcore.plugins.tools.util.ToolBuilder;
import com.github.frcsty.districtcore.util.Color;
import com.github.frcsty.districtcore.util.Replace;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import me.mattstudios.mfgui.gui.guis.Gui;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ItemUseListener implements Listener {

    private final DistrictCore core;

    public ItemUseListener(final DistrictCore core) {
        this.core = core;
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemUser(PlayerInteractEvent event) {
        final ItemStack item = event.getItem();
        final Action action = event.getAction();
        final Player player = event.getPlayer();
        final Block block = event.getClickedBlock();
        final String data = ItemNBT.getNBTTag(item, "tool");
        final World world = player.getWorld();
        final ActionBarAPI actionBar = DependencyUtil.getActionBarAPI();
        if (item == null) return;
        if (data.length() == 0) return;
        if (data.equalsIgnoreCase("tray") || data.equalsIgnoreCase("trench")) return;
        if (action != Action.RIGHT_CLICK_BLOCK && action != Action.RIGHT_CLICK_AIR) return;
        if (data.equalsIgnoreCase("sand")) {
            final String delay = ItemNBT.getNBTTag(item, "delay");
            final List<Material> materials = Arrays.asList(Material.SAND, Material.GRAVEL);
            int sY = 256;

            if (block.getLocation() == null) {
                return;
            }

            for (int i = block.getLocation().getBlockY(); i < 256; i++) {
                if (!materials.contains(world.getBlockAt(new Location(world, block.getLocation().getBlockX(), i, block.getLocation().getBlockZ())).getType())) {
                    sY = i;
                    break;
                }
            }

            final int startingY = sY;
            new BukkitRunnable() {
                int y = startingY - 1;

                @Override
                public void run() {
                    final Block broken = world.getBlockAt(new Location(world, block.getLocation().getBlockX(), y, block.getLocation().getBlockZ()));
                    if (!materials.contains(broken.getType()) || y < 2) {
                        cancel();
                        return;
                    }
                    broken.setType(Material.AIR);
                    y--;
                }
            }.runTaskTimer(core, 0, Integer.valueOf(delay));
        }
        if (data.equalsIgnoreCase("lightning")) {
            final boolean cooldown = Boolean.valueOf(ItemNBT.getNBTTag(item, "cooldown"));

            if (cooldown) {
                return;
            }

            final int delay = Integer.valueOf(ItemNBT.getNBTTag(item, "delay"));
            final Set<Material> transparent = new TreeSet<>();

            final int slot = player.getInventory().getHeldItemSlot();
            transparent.add(Material.AIR);
            new BukkitRunnable() {
                int i = delay;

                @Override
                public void run() {
                    if (i == delay) {
                        world.strikeLightning(player.getTargetBlock(transparent, 100).getLocation());
                        player.getInventory().setItemInHand(new ToolBuilder(core, "lightning", 1).getCooldownItem(true));
                        actionBar.sendActionBar(player, Color.colorize(core.getMessageLoader().getMessage("struck-lightning")));
                    }
                    if (i > 0 && i < delay) {
                        actionBar.sendActionBar(player, Replace.replaceString(Color.colorize(core.getMessageLoader().getMessage("lightning-wand-cooldown")), "{time}", String.valueOf(i)));
                    }
                    if (i == 0) {
                        player.getInventory().setItem(slot, new ToolBuilder(core, "lightning", 1).getCooldownItem(false));
                        cancel();
                    }
                    i--;
                }
            }.runTaskTimer(core, 0, delay * 20);
        }
        if (data.equalsIgnoreCase("craft")) {
            if (block.getType() != Material.CHEST && block.getType() != Material.TRAPPED_CHEST) {
                return;
            }

            final Condense condense = new Condense();
            condense.run(block);

            actionBar.sendActionBar(player, Color.colorize(core.getMessageLoader().getMessage("condensed-chest-contents")));
            event.setCancelled(true);
        }
        if (data.equalsIgnoreCase("sell")) {
            final double multiplier = Double.valueOf(ItemNBT.getNBTTag(item, "multiplier"));

            if (block.getType() != Material.CHEST && block.getType() != Material.TRAPPED_CHEST) {
                return;
            }

            final Map<Integer, ItemStack> inventory = new HashMap<>();
            final Inventory chest = ((InventoryHolder) block.getState()).getInventory();
            for (int i = 0; i < chest.getSize(); i++) {
                final ItemStack itemStack = chest.getItem(i);

                inventory.put(i, itemStack);
            }

            double amount = 0;
            for (Integer slot : inventory.keySet()) {
                final ItemStack itemStack = inventory.get(slot);

                if (itemStack == null || itemStack.getType() == Material.AIR) {
                    continue;
                }

                final double a = ShopGuiPlusApi.getItemStackPriceSell(itemStack);
                if (!String.valueOf(a).contains("-")) {
                    amount += a;
                    chest.setItem(slot, null);
                }
            }

            DependencyUtil.getEconomy().depositPlayer(player, amount * multiplier);
            actionBar.sendActionBar(player, Replace.replaceString(Color.colorize(core.getMessageLoader().getMessage("sold-chest-contents")), "{amount}", String.valueOf(amount * multiplier), "{multi}", String.valueOf(multiplier)));
            event.setCancelled(true);
        }
        if (data.equalsIgnoreCase("buster")) {
            final int delay = Integer.valueOf(ItemNBT.getNBTTag(item, "delay"));

            if (block.getLocation() == null) return;
            final Chunk chunk = block.getChunk();

            final Gui menu = ConfirmationMenu.getMenu(core, chunk, delay);

            event.setCancelled(true);
            menu.open(player);
        }
    }
}
