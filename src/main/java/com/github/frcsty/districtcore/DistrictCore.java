package com.github.frcsty.districtcore;

import com.github.frcsty.districtcore.collectors.CollectorsPlugin;
import com.github.frcsty.districtcore.creepereggs.CreeperEggsPlugin;
import com.github.frcsty.districtcore.elixirs.ElixirsPlugin;
import com.github.frcsty.districtcore.messages.MessageLoader;
import com.github.frcsty.districtcore.pouches.PouchesPlugin;
import com.github.frcsty.districtcore.statistics.StatisticsPlugin;
import com.github.frcsty.districtcore.tokens.TokensPlugin;
import com.github.frcsty.districtcore.tools.ToolsPlugin;
import me.mattstudios.mf.base.CommandBase;
import me.mattstudios.mf.base.CommandManager;
import me.mattstudios.mf.base.CompletionHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DistrictCore extends JavaPlugin {

    private final List<CommandBase> commands = new ArrayList<>();
    private final List<CorePlugin> plugins = Arrays.asList(new TokensPlugin(this), new ToolsPlugin(this), new CreeperEggsPlugin(this)
            , new StatisticsPlugin(this), new ElixirsPlugin(this), new PouchesPlugin(this), new CollectorsPlugin(this));
    private final MessageLoader messageLoader = new MessageLoader();

    private CommandManager commandManager;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        commandManager = new CommandManager(this);
        messageLoader.load(this);

        plugins.forEach(CorePlugin::onEnable);
        commands.forEach(commandManager::register);
    }

    @Override
    public void onDisable() {
        this.reloadConfig();

        plugins.forEach(CorePlugin::onDisable);
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

    public MessageLoader getMessageLoader() {
        return this.messageLoader;
    }

}
