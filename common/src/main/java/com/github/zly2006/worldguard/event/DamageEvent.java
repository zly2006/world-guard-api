package com.github.zly2006.worldguard.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;

public class DamageEvent extends Event {
    public DamageSource source;
    public Entity entity;

    public DamageEvent(DamageSource source, Entity entity, BlockPos pos) {
        super(null, pos);
        this.source = source;
        this.entity = entity;
    }
}
