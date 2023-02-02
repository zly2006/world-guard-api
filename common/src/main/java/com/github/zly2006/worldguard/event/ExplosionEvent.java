package com.github.zly2006.worldguard.event;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.explosion.Explosion;

public class ExplosionEvent extends Event {
    public Explosion explosion;

    public ExplosionEvent(BlockPos pos, Explosion explosion) {
        super(null, pos);
        this.explosion = explosion;
    }
}
