package com.github.frcsty.districtcore.commands.command;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.util.Color;
import com.github.frcsty.districtcore.util.Replace;
import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

@Command("districtcore")
@Alias("dcore")
public class ReloadCommand extends CommandBase {

    private final DistrictCore core;

    public ReloadCommand(final DistrictCore core) {
        this.core = core;
    }

    @SubCommand("reload")
    @Permission("district.core.command.reload")
    public void onCoreReload(final CommandSender sender) {
        final long start = System.currentTimeMillis();

        new BukkitRunnable() {
            @Override
            public void run() {
                core.reloadConfig();
                core.getMessageLoader().load(core);
                core.getSectionLoader().load(core);
            }
        }.runTaskAsynchronously(core);

        sender.sendMessage(Color.colorize(Replace.replaceString(core.getMessageLoader().getMessage("reload-core")
                , "{time}", String.valueOf(System.currentTimeMillis() - start))));
    }
}
