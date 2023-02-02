package com.github.zly2006.worldguard.event;

import net.minecraft.util.math.BlockPos;

public class FireSpreadEvent extends Event {
    public FireSpreadEvent(BlockPos pos) {
        super(null, pos);
    }
}
