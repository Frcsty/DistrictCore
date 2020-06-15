package com.github.frcsty.districtcore.plugins.roam.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.dependency.DependencyUtil;
import com.github.frcsty.districtcore.plugins.roam.RoamPlugin;
import com.github.frcsty.districtcore.plugins.roam.object.RoamPlayer;
import com.github.frcsty.districtcore.util.Color;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;
import java.util.Locale;

@Command("roam")
public class RoamCommand extends CommandBase {

    private final DistrictCore core;

    public RoamCommand(final DistrictCore core) {
        this.core = core;

        startRunnable();
    }

    @Default
    @Permission("roam.command")
    public void roamCommand(final Player player) {
        RoamPlayer roamPlayer = RoamPlugin.getRoamPlayers().get(player);

        if (roamPlayer == null) {
            roamPlayer = new RoamPlayer(player);

            roamPlayer.enableRoam(player);
            RoamPlugin.getRoamPlayers().put(player, roamPlayer);
            return;
        }

        if (!roamPlayer.getStatus()) {
            RoamPlugin.getRoamPlayers().put(player, roamPlayer);
            roamPlayer.enableRoam(player);
        } else {
            RoamPlugin.getRoamPlayers().remove(player);
            roamPlayer.disableRoam(player);
        }
    }

    private void startRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : RoamPlugin.getRoamPlayers().keySet()) {
                    if (player.getHealth() == 0) continue;

                    final RoamPlayer roamPlayer = RoamPlugin.getRoamPlayers().get(player);
                    final double distance = player.getLocation().distance(roamPlayer.getStartingLocation());

                    if (distance > 128) {
                        player.sendMessage(Color.colorize("&7&oYou cannot exit roam bounds!"));
                        roamPlayer.teleportToRange(player);
                    } else {
                        roamPlayer.setValidLocation(player.getLocation());
                    }

                    final LivingEntity entity = roamPlayer.getLivingEntity();
                    DependencyUtil.getActionBarAPI().sendActionBar(player, Color.colorize("&eEntity Health&8: &c" + entity.getHealth() + "&7/&c" + entity.getMaxHealth() + " &8&l| &eDistance&8: &c" + getFormattedDistance(distance) + "m"));
                }
            }
        }.runTaskTimer(core, 1L, 1L);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : RoamPlugin.getRoamPlayers().keySet()) {
                    if (player.getHealth() == 0) continue;

                    final RoamPlayer roamPlayer = RoamPlugin.getRoamPlayers().get(player);
                    roamPlayer.spawnParticleBorder(16);
                }
            }
        }.runTaskTimer(core, 0, 20L);
    }

    private String getFormattedDistance(final double distance) {
        final NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(0);
        return format.format(distance);
    }
}
