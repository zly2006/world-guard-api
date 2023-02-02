package com.github.zly2006.worldguard.event;

import net.minecraft.util.math.BlockPos;

public class PistonMoveEvent extends Event {
    public BlockPos to;

    public PistonMoveEvent(BlockPos from, BlockPos to) {
        super(null, from);
        this.to = to;
    }
}
