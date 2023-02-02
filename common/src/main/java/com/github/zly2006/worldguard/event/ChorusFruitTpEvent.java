package com.github.zly2006.worldguard.event;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class ChorusFruitTpEvent extends Event {
    public Vec3d fromPos;
    public Vec3d toPos;

    public ChorusFruitTpEvent(@Nullable ServerPlayerEntity player, Vec3d fromPos, Vec3d toPos) {
        super(player, new BlockPos(toPos));
        this.fromPos = fromPos;
        this.toPos = toPos;
    }
}
