package com.github.zly2006.worldguard.event;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class BreakBlockEvent extends Event {
    public ServerWorld world;

    public BreakBlockEvent(@Nullable ServerPlayerEntity player, ServerWorld world, BlockPos pos) {
        super(player, pos);
        this.world = world;
    }
}
