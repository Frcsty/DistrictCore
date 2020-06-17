package com.github.frcsty.districtcore.plugins.roam.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.dependency.DependencyUtil;
import com.github.frcsty.districtcore.plugins.roam.RoamPlugin;
import com.github.frcsty.districtcore.plugins.roam.object.RoamPlayer;
import com.github.frcsty.districtcore.util.Color;
import com.github.frcsty.districtcore.util.Replace;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.configuration.ConfigurationSection;
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
    @Permission("district.roam.command.roam")
    public void roamCommand(final Player player) {
        RoamPlayer roamPlayer = RoamPlugin.getRoamPlayers().get(player);

        if (roamPlayer == null) {
            roamPlayer = new RoamPlayer(player);

            roamPlayer.enableRoam(player);
            player.sendMessage(Color.colorize(core.getMessageLoader().getMessage("roam-enabled")));
            RoamPlugin.getRoamPlayers().put(player, roamPlayer);
            return;
        }

        if (!roamPlayer.getStatus()) {
            RoamPlugin.getRoamPlayers().put(player, roamPlayer);
            roamPlayer.enableRoam(player);
            player.sendMessage(Color.colorize(core.getMessageLoader().getMessage("roam-enabled")));
        } else {
            RoamPlugin.getRoamPlayers().remove(player);
            roamPlayer.disableRoam(player);
            player.sendMessage(Color.colorize(core.getMessageLoader().getMessage("roam-disabled")));
        }
    }

    private void startRunnable() {
        final ConfigurationSection roam = core.getSectionLoader().getSection("roam");
        final int radius = roam.getInt("radius");
        final int density = roam.getInt("density");
        final String effect = roam.getString("effect");
        final int amount = roam.getInt("amount");
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : RoamPlugin.getRoamPlayers().keySet()) {
                    if (player.getHealth() == 0) continue;

                    final RoamPlayer roamPlayer = RoamPlugin.getRoamPlayers().get(player);
                    final double distance = player.getLocation().distance(roamPlayer.getStartingLocation());

                    if (distance > radius) {
                        player.sendMessage(Color.colorize(core.getMessageLoader().getMessage("roam-cannot-exit-bounds")));
                        roamPlayer.teleportToRange(player);
                    } else {
                        roamPlayer.setValidLocation(player.getLocation());
                    }

                    final LivingEntity entity = roamPlayer.getLivingEntity();
                    DependencyUtil.getActionBarAPI().sendActionBar(player,
                            Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("roam-actionbar")
                                    , "{entity-health}", String.valueOf(entity.getHealth())
                                    , "{distance}", getFormattedDistance(distance))));
                }
            }
        }.runTaskTimer(core, 1L, 1L);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : RoamPlugin.getRoamPlayers().keySet()) {
                    if (player.getHealth() == 0) continue;

                    final RoamPlayer roamPlayer = RoamPlugin.getRoamPlayers().get(player);
                    roamPlayer.spawnParticleBorder(radius, density, effect, amount);
                }
            }
        }.runTaskTimer(core, 0, 10L);
    }

    private String getFormattedDistance(final double distance) {
        final NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(0);
        return format.format(distance);
    }
}
