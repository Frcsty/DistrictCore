package com.github.frcsty.districtcore.plugins.roam.object;

import com.github.frcsty.districtcore.util.Color;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.*;

public final class RoamPlayer {

    private final Location location;
    private final Player player;
    private Entity entity;
    private boolean status;
    private Location valid;

    public RoamPlayer(final Player player) {
        this.player = player;
        this.location = player.getLocation();
    }

    public void enableRoam(final Player player) {
        player.sendMessage(Color.colorize("&7&oRoam enabled"));

        entity = player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
        manageEntity(player);
        status = true;
        player.setGameMode(GameMode.SPECTATOR);
    }

    public void disableRoam(final Player player) {
        player.sendMessage(Color.colorize("&7&oRoam disabled"));

        removeEntity();
        status = false;
        player.teleport(location);
        player.setGameMode(GameMode.SURVIVAL);
    }

    public void teleportToRange(final Player player) {
        player.teleport(valid);
    }

    private void manageEntity(final Player player) {
        disableAI();
        entity.setCustomNameVisible(true);
        entity.setCustomName(player.getName());

        final Villager villager = (Villager) entity;
        villager.setCanPickupItems(false);
        villager.setTarget(null);
        villager.setRemoveWhenFarAway(false);
        villager.setProfession(Villager.Profession.LIBRARIAN);
    }

    private void disableAI() {
        final net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        nmsEntity.f(tag);
    }

    private void removeEntity() {
        entity.remove();
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setValidLocation(final Location location) {
        this.valid = location;
    }

    public Location getStartingLocation() {
        return this.location;
    }

    public LivingEntity getLivingEntity() {
        return (LivingEntity) this.entity;
    }

    public void spawnParticleBorder(final int offset) {
        int pointDensity = 40; // pointDensity being how many points you want to draw per section.
        double incr = 2 * Math.PI / pointDensity;
        for (double phi = 0; phi < 2 * Math.PI; phi += incr) {
            double z = offset * Math.sin(phi);
            for (double theta = 0; theta < 2 * Math.PI; theta += incr) {
                double y = offset * Math.cos(phi) * Math.sin(theta);
                double x = offset * Math.cos(phi) * Math.cos(theta);
                player.playEffect(new Location(location.getWorld(), x, y, z), Effect.HEART, 1);
            }
        }

        /*
        int borderXR = location.getBlockX() - offset;
        int borderZR = location.getBlockZ() - offset;
        int borderXL = location.getBlockX() + offset;
        int borderZL = location.getBlockZ() + offset;



        for (int x = borderXR + (offset * 2); x > borderXR; x--) {
            player.playEffect(new Location(location.getWorld(), x, y, borderZR), Effect.HEART, 1);
        }
        for (int z = borderZR + (offset * 2); z > borderZR; z--) {
            player.playEffect(new Location(location.getWorld(), borderXR, y, z), Effect.HEART, 1);
        }
        for (int x = borderXL - (offset * 2); x < borderXL; x++) {
            player.playEffect(new Location(location.getWorld(), x, y, borderZL), Effect.HEART, 1);
        }
        for (int z = borderZL - (offset * 2); z < borderZL; z++) {
            player.playEffect(new Location(location.getWorld(), borderXL, y, z), Effect.HEART, 1);
        }
        */
    }
}
