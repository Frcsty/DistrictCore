package com.github.frcsty.districtcore;

import com.github.frcsty.districtcore.creepereggs.CreeperEggsPlugin;
import com.github.frcsty.districtcore.elixirs.ElixirsPlugin;
import com.github.frcsty.districtcore.pouches.PouchesPlugin;
import com.github.frcsty.districtcore.statistics.StatisticsPlugin;
import com.github.frcsty.districtcore.tokens.TokensPlugin;
import com.github.frcsty.districtcore.tools.ToolsPlugin;
import me.mattstudios.mf.base.CommandBase;
import me.mattstudios.mf.base.CommandManager;
import me.mattstudios.mf.base.CompletionHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class DistrictCore extends JavaPlugin {

    private final List<CommandBase> commands = new ArrayList<>();

    private final TokensPlugin tokensPlugin = new TokensPlugin(this);
    private final ToolsPlugin toolsPlugin = new ToolsPlugin(this);
    private final CreeperEggsPlugin creeperEggsPlugin = new CreeperEggsPlugin(this);
    private final StatisticsPlugin statisticsPlugin = new StatisticsPlugin(this);
    private final ElixirsPlugin elixirsPlugin = new ElixirsPlugin(this);
    private final PouchesPlugin pouchesPlugin = new PouchesPlugin(this);

    private CommandManager commandManager;

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        commandManager = new CommandManager(this);

        tokensPlugin.onEnable();
        toolsPlugin.onEnable();
        creeperEggsPlugin.onEnable();
        statisticsPlugin.onEnable();
        elixirsPlugin.onEnable();
        pouchesPlugin.onEnable();

        commands.forEach(commandManager::register);
    }

    @Override
    public void onDisable() {
        this.reloadConfig();
        tokensPlugin.onDisable();
        creeperEggsPlugin.onDisable();
        elixirsPlugin.onDisable();
    }

    public void addCommand(final CommandBase command) {
        this.commands.add(command);
    }

    public void addCommands(final CommandBase... commands) {
        for (CommandBase command : commands) {
            addCommand(command);
        }
    }

    public CompletionHandler getCompletionHandler() {
        return this.commandManager.getCompletionHandler();
    }

}
