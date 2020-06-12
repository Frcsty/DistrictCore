package com.github.frcsty.districtcore;

public interface CorePlugin {

    void onEnable();
    default void onDisable() {}

}
