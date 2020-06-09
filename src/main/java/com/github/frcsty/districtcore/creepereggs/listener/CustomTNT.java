package com.github.frcsty.districtcore.creepereggs.listener;

import com.github.frcsty.districtcore.DistrictCore;
import com.github.frcsty.districtcore.creepereggs.util.EntityHider;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

class CustomTNT {

    static void spawnCustomTNT(final DistrictCore core, final Location location, final String type, final float power) {
        final EntityHider hider = new EntityHider(core, EntityHider.Policy.BLACKLIST);
        final TNTPrimed primed = location.getWorld().spawn(location, TNTPrimed.class);
        primed.setMetadata("type", new FixedMetadataValue(core, type));
        primed.setYield(power);
        primed.setFuseTicks(0);

        core.getServer().getOnlinePlayers().forEach(plr -> hider.hideEntity(plr, primed));
    }

    static String getMetadataValue(final Entity block) {
        final List<MetadataValue> values = block.getMetadata("type");

        if (values.isEmpty()) {
            return null;
        }

        if (values.size() > 1) {
            return null;
        }

        return values.get(0).asString();
    }

}
