package com.github.frcsty.districtcore.creepereggs.util;

import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

public class Velocity {

    public static boolean isStill(final Item drop) {
        final Vector vector = drop.getVelocity();
        return vector.getX() == 0 || vector.getY() == 0 || vector.getZ() == 0;
    }

}
