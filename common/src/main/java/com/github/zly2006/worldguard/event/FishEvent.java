package com.github.zly2006.worldguard.event;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class FishEvent extends Event {
    public FishEvent(ServerPlayerEntity player, BlockPos pos) {
        super(player, pos);
    }
}
