package com.github.zly2006.worldguard.event;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class FeedAnimalEvent extends Event {
    public AnimalEntity animal;
    public ItemStack itemStack;

    public FeedAnimalEvent(ServerPlayerEntity player, BlockPos pos, AnimalEntity animal, ItemStack itemStack) {
        super(player, pos);
        this.animal = animal;
        this.itemStack = itemStack;
    }
}
