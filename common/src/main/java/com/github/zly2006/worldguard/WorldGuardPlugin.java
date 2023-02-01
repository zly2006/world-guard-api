package com.github.zly2006.worldguard;

import com.github.zly2006.worldguard.event.Event;

import java.util.ArrayList;
import java.util.List;

public abstract class WorldGuardPlugin {
    public abstract void init();

    public List<Class<Event>> registerEvents() {
        return new ArrayList<>();
    }
}
