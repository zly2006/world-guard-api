package com.github.zly2006.worldguard.event;

import net.minecraft.util.math.BlockPos;

public class WitherDestroyEvent extends Event {
    public WitherDestroyEvent(BlockPos pos) {
        super(null, pos);
    }
}
