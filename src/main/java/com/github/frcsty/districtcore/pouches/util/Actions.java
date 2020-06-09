package com.github.frcsty.districtcore.pouches.util;

import com.github.frcsty.districtcore.pouches.util.lib.ActionManager;
import com.github.frcsty.districtcore.pouches.util.parser.FireworkParser;
import me.gabytm.util.actions.Action;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class Actions {

    public void loadCustomActions(final ActionManager actionManager) {
        actionManager.register(new Action() {
            @Override
            public String getID() {
                return "firework";
            }

            @Override
            public void run(Player player, String data) {
                final FireworkParser parser = new FireworkParser(data);

                spawnFirework(player.getLocation(), parser.getAmount(), parser.getPower(), parser.getColor(), parser.getFlicker());
            }
        }, false);

    }

    private void spawnFirework(final Location location, final int amount, final int power, final Color color, final boolean flicker) {
        final Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        final FireworkMeta fireworkMeta = firework.getFireworkMeta();

        fireworkMeta.setPower(power);
        fireworkMeta.addEffect(FireworkEffect.builder().withColor(color).flicker(flicker).build());

        firework.setFireworkMeta(fireworkMeta);
        firework.detonate();

        for (int i = 0; i < amount - 1; i++) {
            Firework duplicateFirework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
            duplicateFirework.setFireworkMeta(fireworkMeta);
        }
    }
}
