package com.github.frcsty.districtcore.patches.mechanics;

import com.github.frcsty.districtcore.DistrictCore;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class Entities implements Listener {

    private final DistrictCore core;

    public Entities(final DistrictCore core) {
        this.core = core;
    }

    /*
    @EventHandler
    public void onEntityMount(PlayerInteractAtEntityEvent event) {
        final ConfigurationSection entityMechanics = core.getSectionLoader().getSection("patches").getConfigurationSection("entity-mechanics");

        for (String type : entityMechanics.getStringList("disable-use")) {
            if (event.getRightClicked().getType().name().equalsIgnoreCase(type)) {
                event.setCancelled(true);
            }
        }
    }

    /*
    public void registerMountPacket(final ProtocolManager manager) {
        final ConfigurationSection entityMechanics = core.getSectionLoader().getSection("patches").getConfigurationSection("entity-mechanics");

        manager.addPacketListener(
                new PacketAdapter(core, ListenerPriority.NORMAL, PacketType.Play.Server.ATTACH_ENTITY) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        final Player player = event.getPlayer();
                        final PacketContainer container = event.getPacket();
                        final Integer id = container.getIntegers().read(2);
                        Entity entity = null;

                        for (Entity e : player.getNearbyEntities(10, 10, 10)) {
                            if (e.getEntityId() == id) {
                                entity = e;
                                break;
                            }
                        }
                        if (entity == null) {
                            return;
                        }
                        for (String disabled : entityMechanics.getStringList("disable-use")) {
                            if (entity.getType().name().equalsIgnoreCase(disabled)) {
                                event.setCancelled(true);
                                break;
                            }
                        }
                    }
                }
        );
    }
    */

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        final ConfigurationSection entityMechanics = core.getSectionLoader().getSection("patches").getConfigurationSection("entity-mechanics");
        final Entity entity = event.getRightClicked();
        final Player player = event.getPlayer();
        if (entity == null) return;

        final Location location = player.getLocation();
        for (String type : entityMechanics.getStringList("disable-use")) {
            if (entity.getType().name().equalsIgnoreCase(type)) {
                player.teleport(location);
                event.setCancelled(true);
            }
        }
    }

}
