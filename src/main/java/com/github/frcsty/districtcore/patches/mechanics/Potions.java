package com.github.frcsty.districtcore.patches.mechanics;

import com.github.frcsty.districtcore.DistrictCore;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Potions implements Listener {

    private final DistrictCore core;

    public Potions(final DistrictCore core) {
        this.core = core;
    }

    @EventHandler
    public void onPotionUse(PlayerItemConsumeEvent event) {
        final ItemStack item = event.getItem();
        final Player player = event.getPlayer();
        if (item == null) return;
        if (item.getType() != Material.POTION) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                player.setItemInHand(new ItemStack(Material.AIR));
            }
        }.runTaskLater(core, 1);
    }

    @EventHandler
    public void onBrewingStandUse(PlayerInteractEvent event) {
        final Block block = event.getClickedBlock();
        if (block == null) return;
        if (block.getType() != Material.BREWING_STAND) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        event.setCancelled(true);
    }
}
