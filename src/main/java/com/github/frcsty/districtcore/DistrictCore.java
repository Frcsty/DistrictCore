package com.github.frcsty.districtcore;

import com.github.frcsty.districtcore.commands.CommandsPlugin;
import com.github.frcsty.districtcore.configuration.MessageLoader;
import com.github.frcsty.districtcore.configuration.SectionLoader;
import com.github.frcsty.districtcore.plugins.collectors.CollectorsPlugin;
import com.github.frcsty.districtcore.plugins.creepereggs.CreeperEggsPlugin;
import com.github.frcsty.districtcore.plugins.elixirs.ElixirsPlugin;
import com.github.frcsty.districtcore.plugins.pouches.PouchesPlugin;
import com.github.frcsty.districtcore.plugins.statistics.StatisticsPlugin;
import com.github.frcsty.districtcore.plugins.tokens.TokensPlugin;
import com.github.frcsty.districtcore.plugins.tools.ToolsPlugin;
import me.mattstudios.mf.base.CommandBase;
import me.mattstudios.mf.base.CommandManager;
import me.mattstudios.mf.base.CompletionHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DistrictCore extends JavaPlugin {

    private final List<CommandBase> commands = new ArrayList<>();
    private final List<Listener> listeners = new ArrayList<>();
    private final List<CorePlugin> plugins = Arrays.asList(new TokensPlugin(this), new ToolsPlugin(this), new CreeperEggsPlugin(this)
            , new StatisticsPlugin(this), new ElixirsPlugin(this), new PouchesPlugin(this), new CollectorsPlugin(this)
            /*, new PatchesPlugin(this) */, new CommandsPlugin(this));

    private final MessageLoader messageLoader = new MessageLoader();
    private final SectionLoader sectionLoader = new SectionLoader();

    private CommandManager commandManager;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        commandManager = new CommandManager(this);

        messageLoader.load(this);
        sectionLoader.load(this);

        plugins.forEach(CorePlugin::onEnable);
        commands.forEach(commandManager::register);
        listeners.forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
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
        Arrays.stream(commands).forEach(this::addCommand);
    }

    public void addListener(final Listener listener) {
        this.listeners.add(listener);
    }

    public void addListeners(final Listener... listeners) {
        Arrays.stream(listeners).forEach(this::addListener);
    }

    public CompletionHandler getCompletionHandler() {
        return this.commandManager.getCompletionHandler();
    }

    public MessageLoader getMessageLoader() {
        return this.messageLoader;
    }

    public SectionLoader getSectionLoader() {
        return this.sectionLoader;
    }

}
