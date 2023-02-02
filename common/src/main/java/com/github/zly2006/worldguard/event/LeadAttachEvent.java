package com.github.zly2006.worldguard.event;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class LeadAttachEvent extends Event {
    public LeadAttachEvent(ServerPlayerEntity player, BlockPos pos) {
        super(player, pos);
    }
}
