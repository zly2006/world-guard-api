package com.github.zly2006.worldguard.event;

import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class LecternTakeBookEvent extends Event {
    public ItemStack book;

    public LecternTakeBookEvent(ServerPlayerEntity player, ItemStack book, BlockPos pos) {
        super(player, pos);
        this.book = book;
    }
}
