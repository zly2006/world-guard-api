package com.github.zly2006.worldguard.event;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class FluidFlowEvent extends Event {
    public BlockPos to;
    public ServerWorld world;

    public FluidFlowEvent(ServerWorld world, BlockPos from, BlockPos to) {
        super(null, from);
        this.world = world;
        this.to = to;
    }
}
