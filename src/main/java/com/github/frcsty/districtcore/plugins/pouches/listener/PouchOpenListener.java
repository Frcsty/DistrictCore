package com.github.frcsty.districtcore.plugins.pouches.listener;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.pouches.PouchesPlugin;
import com.github.frcsty.districtcore.plugins.pouches.object.Pouch;
import com.github.frcsty.districtcore.util.Color;
import com.github.frcsty.districtcore.util.Replace;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

public class PouchOpenListener implements Listener {

    private final PouchesPlugin plugin;
    private final DistrictCore core;

    public PouchOpenListener(final DistrictCore core, final PouchesPlugin plugin) {
        this.plugin = plugin;
        this.core = core;
    }

    @EventHandler
    public void onPouchOpen(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = event.getItem();
        final Action action = event.getAction();

        if (item == null) {
            return;
        }

        if (action != Action.RIGHT_CLICK_AIR) {
            return;
        }

        final String pouchType = ItemNBT.getNBTTag(item, "pouch");

        if (pouchType.length() == 0) {
            return;
        }

        final Pouch pouch = plugin.getPouchStorage().getPouch(pouchType);

        if (pouch == null) {
            return;
        }

        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            item.setType(Material.AIR);
        }

        player.setItemInHand(item);

        usePouch(player, pouch);
    }

    @EventHandler
    public void onPouchPlace(BlockPlaceEvent event) {
        final ItemStack item = event.getItemInHand();
        final Block block = event.getBlockPlaced();
        if (item == null || block == null) {
            return;
        }

        final String pouchType = ItemNBT.getNBTTag(item, "pouch");
        if (pouchType.length() == 0) {
            return;
        }

        event.setCancelled(true);
    }

    private void usePouch(final Player player, final Pouch pouch) {
        final String[] range = pouch.getRange().split(";");
        long random = ThreadLocalRandom.current().nextLong(Long.valueOf(range[0]), Long.valueOf(range[1]));
        final ConfigurationSection title = core.getSectionLoader().getSection("title");
        final ConfigurationSection sound = core.getSectionLoader().getSection("sound");
        player.playSound(player.getLocation(), Sound.valueOf(sound.getString("opensound")), 3, 1);
        new BukkitRunnable() {
            final String prefixColour = Color.colorize(title.getString("prefix-colour"));
            final String suffixColour = Color.colorize(title.getString("suffix-colour"));
            final String revealColour = Color.colorize(title.getString("reveal-colour"));
            final String obfuscateColour = Color.colorize(title.getString("obfuscate-colour"));
            final String obfuscateDigitChar = title.getString("obfuscate-digit-char", "#");
            final String obfuscateDelimiterChar = ",";
            int position = 0;
            boolean delimiter = title.getBoolean("format.enabled", false);
            boolean revealComma = title.getBoolean("format.reveal-comma", false);
            String number = (delimiter ? (new DecimalFormat("#,###").format(random)) : String.valueOf(random));
            boolean reversePouchReveal = title.getBoolean("reverse-pouch-reveal");

            @Override
            public void run() {
                if (player.isOnline()) {
                    player.playSound(player.getLocation(), Sound.valueOf(sound.getString("revealsound")), 3, 1);
                    StringBuilder viewedTitle = new StringBuilder();
                    for (int i = 0; i < position; i++) {
                        if (reversePouchReveal) {
                            viewedTitle.insert(0, number.charAt(number.length() - i - 1)).insert(0, revealColour);
                        } else {
                            viewedTitle.append(revealColour).append(number.charAt(i));
                        }
                        if ((i == (position - 1)) && (position != number.length())
                                && (reversePouchReveal
                                ? (revealComma && (number.charAt(number.length() - i - 1)) == ',')
                                : (revealComma && (number.charAt(i + 1)) == ','))) {
                            position++;
                        }
                    }
                    for (int i = position; i < number.length(); i++) {
                        if (reversePouchReveal) {
                            char at = number.charAt(number.length() - i - 1);
                            if (at == ',') {
                                if (revealComma) {
                                    viewedTitle.insert(0, at).insert(0, revealColour);
                                } else
                                    viewedTitle.insert(0, obfuscateDelimiterChar).insert(0, ChatColor.MAGIC).insert(0, obfuscateColour);
                            } else
                                viewedTitle.insert(0, obfuscateDigitChar).insert(0, ChatColor.MAGIC).insert(0, obfuscateColour);
                        } else {
                            char at = number.charAt(i);
                            if (at == ',') {
                                if (revealComma) viewedTitle.append(revealColour).append(at);
                                else
                                    viewedTitle.append(obfuscateColour).append(ChatColor.MAGIC).append(obfuscateDelimiterChar);
                            } else
                                viewedTitle.append(obfuscateColour).append(ChatColor.MAGIC).append(obfuscateDigitChar);
                        }
                    }
                    plugin.getTitle().sendTitle(player, prefixColour + pouch.getSymbol() + viewedTitle.toString() + suffixColour, Color.colorize(title.getString("subtitle")));
                } else {
                    position = number.length();
                }
                if (position == number.length()) {
                    this.cancel();
                    if (player.isOnline()) {
                        player.playSound(player.getLocation(), Sound.valueOf(sound.getString("endsound")), 3, 1);
                        plugin.getActionManager().execute(player, Replace.replaceList(pouch.getActions(), "{amount}", String.valueOf(random), "{player}", player.getName()));
                    }
                    return;
                }
                position++;
            }
        }.runTaskTimer(core, 10, title.getInt("speed-in-tick"));
    }
}
