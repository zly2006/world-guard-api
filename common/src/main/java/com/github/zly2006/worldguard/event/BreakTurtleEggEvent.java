package com.github.zly2006.worldguard.event;

import net.minecraft.util.math.BlockPos;

public class BreakTurtleEggEvent extends Event {
    public BreakTurtleEggEvent(BlockPos pos) {
        super(null, pos);
    }
}
