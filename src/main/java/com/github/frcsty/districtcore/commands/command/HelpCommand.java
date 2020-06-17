package com.github.frcsty.districtcore.commands.command;

import me.clip.placeholderapi.util.jsonmessage.JSONMessage;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Command("help")
public class HelpCommand extends CommandBase {

    @Default
    @Permission("district.core.command.help")
    public void helpCommand(final Player player, final String plugin) {
        getPluginMessage(plugin).forEach(line -> line.send(player));
    }

    private List<JSONMessage> getPluginMessage(final String plugin) {
        final List<JSONMessage> messages = new ArrayList<>();

        switch (plugin) {
            case "tools":
                messages.add(JSONMessage.create("&8&l> &fTools Commands"));
                messages.add(JSONMessage.create(""));
                messages.add(getToolsMessage("chunkbuster", "Chunk Buster"));
                messages.add(getToolsMessage("craftwand", "Craft Wand"));
                messages.add(getToolsMessage("lightningwand", "Lightning Wand"));
                messages.add(getToolsMessage("sandwand", "Sand Wand"));
                messages.add(getToolsMessage("sellwand", "Sell Wand"));
                messages.add(getToolsMessage("traypick", "Tray Pickaxe"));
                messages.add(getToolsMessage("trenchpick", "Trench Pickaxe"));
                messages.add(JSONMessage.create(""));
                break;
            case "tokens":
                break;
            case "statistics":
                break;
            case "roam":
                break;
            case "pouches":
                break;
            case "elixirs":
                break;
            case "ceggs":
                break;
            case "collectors":
                break;
        }

        return messages;
    }

    private JSONMessage getToolsMessage(final String perm, final String display) {
        return JSONMessage.create(" &8• &b/" + perm + "  <p> <a>")
                .tooltip(" \n" +
                        " &8• &7Gives the specified user a " + display + "\n" +
                        " &8• &7Arguments&8: &f<player>&7, &f<amount>\n" +
                        " \n" +
                        " &8• &7Permission&8: \n" +
                        " &f&ndistrict.tools.command." + perm +
                        "\n");
    }

    private JSONMessage getTokensMessage(final String perm, final String display) {
        return null;
    }
}
