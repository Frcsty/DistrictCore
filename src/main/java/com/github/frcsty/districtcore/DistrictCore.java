package com.github.frcsty.districtcore;

import com.github.frcsty.districtcore.creepereggs.CreeperEggsPlugin;
import com.github.frcsty.districtcore.elixirs.ElixirsPlugin;
import com.github.frcsty.districtcore.statistics.StatisticsPlugin;
import com.github.frcsty.districtcore.tokens.TokensPlugin;
import com.github.frcsty.districtcore.tools.ToolsPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class DistrictCore extends JavaPlugin {

    private final TokensPlugin tokensPlugin = new TokensPlugin(this);
    private final ToolsPlugin toolsPlugin = new ToolsPlugin(this);
    private final CreeperEggsPlugin creeperEggsPlugin = new CreeperEggsPlugin(this);
    private final StatisticsPlugin statisticsPlugin = new StatisticsPlugin(this);
    private final ElixirsPlugin elixirsPlugin = new ElixirsPlugin(this);

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        tokensPlugin.onEnable();
        toolsPlugin.onEnable();
        creeperEggsPlugin.onEnable();
        statisticsPlugin.onEnable();
        elixirsPlugin.onEnable();
    }

    @Override
    public void onDisable() {
        tokensPlugin.onDisable();
        toolsPlugin.onDisable();
        creeperEggsPlugin.onDisable();
        statisticsPlugin.onDisable();
        elixirsPlugin.onDisable();
    }

}
