package com.github.zly2006.worldguard.event;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public abstract class Event {
    boolean cancelled = false;
    boolean cancellable = true;
    final private @Nullable ServerPlayerEntity player;

    final private @Nullable BlockPos pos;

    protected Event(@Nullable ServerPlayerEntity player, @Nullable BlockPos pos) {
        this.player = player;
        this.pos = pos;
    }

    public void setCancelled(boolean cancelled) {
        if (cancellable) {
            this.cancelled = cancelled;
        }
        else {
            throw new UnsupportedOperationException("This event is not cancellable");
        }
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean isCancellable() {
        return cancellable;
    }

    public @Nullable ServerPlayerEntity getPlayer() {
        return player;
    }

    public @Nullable BlockPos getPos() {
        return pos;
    }
}
