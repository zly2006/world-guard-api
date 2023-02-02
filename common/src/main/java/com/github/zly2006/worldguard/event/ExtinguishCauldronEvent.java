package com.github.zly2006.worldguard.event;

import net.minecraft.util.math.BlockPos;

public class ExtinguishCauldronEvent extends Event {
    public ExtinguishCauldronEvent(BlockPos pos) {
        super(null, pos);
    }
}
