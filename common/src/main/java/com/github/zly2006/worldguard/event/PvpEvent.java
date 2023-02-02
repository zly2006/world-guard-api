package com.github.zly2006.worldguard.event;

import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class PvpEvent extends Event {
    public ServerPlayerEntity target;

    public PvpEvent(@Nullable ServerPlayerEntity player, ServerPlayerEntity target) {
        super(player, null);
        this.target = target;
    }
}
