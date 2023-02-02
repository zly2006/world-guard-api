package com.github.zly2006.worldguard.event;

import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class BreakArmourStandEvent extends Event {
    public ArmorStandEntity armorStand;

    public BreakArmourStandEvent(@Nullable ServerPlayerEntity player, @Nullable BlockPos pos, ArmorStandEntity armorStand) {
        super(player, pos);
        this.armorStand = armorStand;
    }
}
