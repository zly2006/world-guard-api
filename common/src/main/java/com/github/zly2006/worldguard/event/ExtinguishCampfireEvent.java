package com.github.zly2006.worldguard.event;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ExtinguishCampfireEvent extends Event {
    public ServerWorld world;

    public ExtinguishCampfireEvent(@Nullable ServerPlayerEntity player, @Nullable BlockPos pos, ServerWorld world) {
        super(player, pos);
        this.world = world;
    }
}
