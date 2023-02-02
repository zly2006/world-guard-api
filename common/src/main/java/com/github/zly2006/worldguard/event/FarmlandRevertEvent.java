package com.github.zly2006.worldguard.event;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class FarmlandRevertEvent extends Event {
    public ServerWorld world;

    public FarmlandRevertEvent(ServerPlayerEntity player, ServerWorld world, BlockPos pos) {
        super(player, pos);
        this.world = world;
    }
}
