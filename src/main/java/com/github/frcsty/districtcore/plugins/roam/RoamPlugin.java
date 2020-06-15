package com.github.frcsty.districtcore.plugins.roam;

import com.github.frcsty.districtcore.CorePlugin;
import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.roam.command.RoamCommand;
import com.github.frcsty.districtcore.plugins.roam.listener.PlayerListener;
import com.github.frcsty.districtcore.plugins.roam.object.RoamPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class RoamPlugin implements CorePlugin {

    private final DistrictCore core;
    private static final Map<Player, RoamPlayer> roamPlayers = new HashMap<>();

    public RoamPlugin(final DistrictCore core) {
        this.core = core;
    }

    public void onEnable() {
        core.addCommand(new RoamCommand(core));

        core.addListener(new PlayerListener(core));
    }

    public void onDisable() {
        for (Player player : getRoamPlayers().keySet()) {
            final RoamPlayer roamPlayer = getRoamPlayers().get(player);

            roamPlayer.disableRoam(player);
            getRoamPlayers().remove(player);
        }
    }

    public static Map<Player, RoamPlayer> getRoamPlayers() { return roamPlayers; }
}
