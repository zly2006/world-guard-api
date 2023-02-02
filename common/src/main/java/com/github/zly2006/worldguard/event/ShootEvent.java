package com.github.zly2006.worldguard.event;

import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShootEvent extends Event {
    public ItemStack stack;

    public ShootEvent(ServerPlayerEntity player, @Nullable BlockPos pos, ItemStack stack) {
        super(player, pos);
        this.stack = stack;
    }

    @Override
    public @NotNull ServerPlayerEntity getPlayer() {
        assert player != null;
        return player;
    }
}
