package com.github.zly2006.worldguard.event;

import net.minecraft.entity.ItemEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class PickupItemEvent extends Event {
    public ItemEntity itemEntity;

    public PickupItemEvent(@Nullable ServerPlayerEntity player, BlockPos pos, ItemEntity itemEntity) {
        super(player, pos);
        this.itemEntity = itemEntity;
    }
}
