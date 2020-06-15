package com.github.frcsty.districtcore.plugins.tools;

import com.github.frcsty.districtcore.CorePlugin;
import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.plugins.tools.commands.*;
import com.github.frcsty.districtcore.plugins.tools.listener.BlockDamageListener;
import com.github.frcsty.districtcore.plugins.tools.listener.ItemUseListener;
import com.github.frcsty.districtcore.plugins.tools.listener.WaterUpdates;

public class ToolsPlugin implements CorePlugin {

    private final DistrictCore core;

    public ToolsPlugin(final DistrictCore core) {
        this.core = core;
    }

    public void onEnable() {
        core.addCommands(new TrayPickaxe(core), new TrenchPickaxe(core)
                , new SandWand(core), new LightningWand(core), new CraftWand(core)
                , new SellWand(core), new ChunkBuster(core));

        core.addListeners(new BlockDamageListener(), new ItemUseListener(core)
                , new WaterUpdates());
    }
}
