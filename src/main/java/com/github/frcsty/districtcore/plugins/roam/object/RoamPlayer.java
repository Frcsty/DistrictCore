package com.github.frcsty.districtcore.plugins.roam.object;

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
        entity = player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
        manageEntity(player);
        status = true;
        player.setGameMode(GameMode.SPECTATOR);
    }

    public void disableRoam(final Player player) {
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

    public void spawnParticleBorder(final int offset, final int density, final String effect, final int amount) {
        double increment = 2 * Math.PI / density;
        for (double phi = 0; phi < 2 * Math.PI; phi += increment) {
            double z = location.getBlockZ() + offset * Math.sin(phi);
            for (double theta = 0; theta < 2 * Math.PI; theta += increment) {
                double y = location.getBlockY() + offset * Math.cos(phi) * Math.sin(theta);
                double x = location.getBlockX() + offset * Math.cos(phi) * Math.cos(theta);
                player.playEffect(new Location(location.getWorld(), x, y, z), Effect.valueOf(effect), amount);
            }
        }
    }
}
