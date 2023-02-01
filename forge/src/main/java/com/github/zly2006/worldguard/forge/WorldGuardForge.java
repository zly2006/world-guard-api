package com.github.zly2006.worldguard.forge;

import dev.architectury.platform.forge.EventBuses;
import com.github.zly2006.worldguard.WorldGuard;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WorldGuard.MOD_ID)
public class WorldGuardForge {
    public WorldGuardForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(WorldGuard.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
            WorldGuard.init();
    }
}