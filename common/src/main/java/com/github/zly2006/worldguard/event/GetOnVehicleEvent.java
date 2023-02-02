package com.github.zly2006.worldguard.event;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.Entity;

public class GetOnVehicleEvent extends Event {
    public Entity vehicle;
    public GetOnVehicleEvent(@Nullable ServerPlayerEntity player, @Nullable BlockPos pos, Entity vehicle) {
        super(player, pos);
        this.vehicle = vehicle;
    }
}
