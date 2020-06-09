package com.github.frcsty.districtcore.creepereggs.listener;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.creepereggs.CreeperEggsPlugin;
import com.github.frcsty.districtcore.creepereggs.object.SpawnEgg;
import com.github.frcsty.districtcore.creepereggs.util.Velocity;
import com.github.frcsty.districtcore.util.Color;
import com.github.frcsty.districtcore.util.Replace;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class CeggThrowListener implements Listener {

    private final DistrictCore core;
    private final CreeperEggsPlugin plugin;

    public CeggThrowListener(final DistrictCore core, final CreeperEggsPlugin plugin) {
        this.core = core;
        this.plugin = plugin;
    }

    @EventHandler
    public void onCeggThrow(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = event.getItem();
        final Action action = event.getAction();

        if (item == null) {
            return;
        }

        final String stringType = ItemNBT.getNBTTag(item, "type");

        if (stringType == null) {
            return;
        }

        if (stringType.length() == 0) {
            return;
        }

        if (action != Action.RIGHT_CLICK_BLOCK && action != Action.RIGHT_CLICK_AIR) {
            return;
        }

        boolean isValid = false;
        for (SpawnEgg.Type type : SpawnEgg.Type.values()) {
            if (type.name().equalsIgnoreCase(stringType)) {
                isValid = true;
                break;
            }
        }

        if (!isValid) {
            return;
        }

        final SpawnEgg.Type type = SpawnEgg.Type.valueOf(stringType);

        if (type == SpawnEgg.Type.CHARGED) {
            if (event.getClickedBlock() == null) {
                return;
            }

            final Location clickLocation = event.getClickedBlock().getLocation();

            if (clickLocation.getWorld() == null) {
                return;
            }

            clickLocation.setY(clickLocation.getY() + 1.1f);
            clickLocation.getWorld().spawnEntity(clickLocation, EntityType.CREEPER);
            clickLocation.getWorld().strikeLightning(clickLocation);
        } else {
            final Location location = player.getLocation();

            if (location.getWorld() == null) {
                return;
            }

            location.setY(location.getY() + 1.2f);
            final ItemStack clone = item.clone();
            clone.setAmount(1);

            final String value = ItemNBT.getNBTTag(clone, "value");

            if (value == null) {
                return;
            }

            final SpawnEgg egg = plugin.getObjectStorage().getSpawnEgg(value);

            if (egg == null) {
                return;
            }

            final Item drop = location.getWorld().dropItem(location, clone);
            drop.setVelocity(location.getDirection().multiply(1.1d));
            drop.setPickupDelay(Integer.MAX_VALUE);
            final float power = egg.getPower();

            switch (type) {
                case THROWABLE:
                case THROWABLE_CHARGED:
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            final Location dropLocation = drop.getLocation();

                            if (Velocity.isStill(drop)) {
                                CustomTNT.spawnCustomTNT(core, dropLocation, value, power);
                                drop.remove();
                                cancel();
                            }
                        }
                    }.runTaskTimer(core, 5, 1);
                    break;
            }
        }

        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            item.setType(Material.AIR);
        }

        player.sendMessage(Color.colorize(Replace.replaceString(core.getConfig().getString("messages.used-cegg"), "{type}", com.github.frcsty.districtcore.creepereggs.util.Replace.getFormattedType(type))));
        player.setItemInHand(item);
    }

    @EventHandler
    public void onStack(ItemMergeEvent event) {
        final Item item = event.getEntity();

        if (item == null) {
            return;
        }

        final String itemNbt = ItemNBT.getNBTTag(item.getItemStack(), "type");

        if (itemNbt == null) {
            return;
        }

        event.setCancelled(true);
    }

}
