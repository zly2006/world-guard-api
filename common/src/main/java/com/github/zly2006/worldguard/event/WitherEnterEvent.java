package com.github.zly2006.worldguard.event;

import net.minecraft.util.math.BlockPos;

public class WitherEnterEvent extends Event {
    public WitherEnterEvent(BlockPos pos) {
        super(null, pos);
    }
}
