package com.github.zly2006.worldguard.event;

import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;

public class FallingBlockLandEvent extends Event {
    public FallingBlockEntity entity;

    public FallingBlockLandEvent(BlockPos pos, FallingBlockEntity entity) {
        super(null, pos);
        this.entity = entity;
    }
}

