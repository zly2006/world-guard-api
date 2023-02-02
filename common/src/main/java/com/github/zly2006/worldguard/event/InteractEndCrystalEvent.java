package com.github.zly2006.worldguard.event;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class InteractEndCrystalEvent extends Event {
    protected InteractEndCrystalEvent(@Nullable ServerPlayerEntity player, @Nullable BlockPos pos) {
        super(player, pos);
    }
}
