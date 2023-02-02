package com.github.zly2006.worldguard.event;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public class PlayerUseEntityEvent extends Event {
    public Hand hand;
    public Entity entity;

    public PlayerUseEntityEvent(ServerPlayerEntity player, BlockPos pos, Hand hand, Entity entity) {
        super(player, pos);
        this.hand = hand;
        this.entity = entity;
    }
}
