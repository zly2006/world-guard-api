package com.github.zly2006.worldguard.fabric;

import com.github.zly2006.worldguard.WorldGuard;
import net.fabricmc.api.ModInitializer;

public class WorldGuardFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        WorldGuard.init();
    }
}