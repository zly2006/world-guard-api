package com.github.zly2006.worldguard.event;

import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class PlaceBlockEvent extends Event {
    public ItemStack itemStack;

    public PlaceBlockEvent(@Nullable ServerPlayerEntity player, @Nullable BlockPos pos, ItemStack itemStack) {
        super(player, pos);
        this.itemStack = itemStack;
    }
}

