package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.FarmlandRevertEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmlandBlock.class)
public class MixinFarmlandBlock {
    @Inject(method = "onLandedUpon", at = @At("HEAD"), cancellable = true)
    private void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci) {
        ServerPlayerEntity player;
        if (world instanceof ServerWorld serverWorld) {
            if (entity instanceof ServerPlayerEntity) {
                player = (ServerPlayerEntity) entity;
            } else player = null;
            if (WorldGuardDispatcher.shouldPrevent(new FarmlandRevertEvent(player, serverWorld, pos))) {
                ci.cancel();
            }
        }
    }
}
