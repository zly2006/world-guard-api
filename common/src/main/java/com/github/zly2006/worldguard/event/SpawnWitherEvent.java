package com.github.zly2006.worldguard.event;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class SpawnWitherEvent extends Event {
    public ServerWorld world;

    public SpawnWitherEvent(@Nullable BlockPos pos, ServerWorld world) {
        super(null, pos);
        this.world = world;
    }

    @Override
    /**
     * @return always null as this event is not caused by a player
     */
    public @Nullable ServerPlayerEntity getPlayer() {
        return null;
    }
}
