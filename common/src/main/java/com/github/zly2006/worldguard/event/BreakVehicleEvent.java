package com.github.zly2006.worldguard.event;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class BreakVehicleEvent extends Event {
    public Entity vehicle;

    public BreakVehicleEvent(@Nullable ServerPlayerEntity player, BlockPos pos, Entity vehicle) {
        super(player, pos);
        this.vehicle = vehicle;
    }
}
