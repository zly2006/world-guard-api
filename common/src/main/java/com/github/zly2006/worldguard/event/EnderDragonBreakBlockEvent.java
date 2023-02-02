package com.github.zly2006.worldguard.event;

import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.math.BlockPos;

public class EnderDragonBreakBlockEvent extends Event {
    public EnderDragonEntity enderDragon;

    public EnderDragonBreakBlockEvent(BlockPos pos, EnderDragonEntity enderDragon) {
        super(null, pos);
        this.enderDragon = enderDragon;
    }
}
