package com.github.zly2006.worldguard.event;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class OpenContainerEvent extends Event {
    public ServerWorld world;
    public Inventory inventory;
    @Nullable
    public BlockEntity blockEntity;

    public OpenContainerEvent(@Nullable ServerPlayerEntity player, @Nullable BlockPos pos, ServerWorld world, Inventory inventory, @Nullable BlockEntity blockEntity) {
        super(player, pos);
        this.world = world;
        this.inventory = inventory;
        this.blockEntity = blockEntity;
    }
}
