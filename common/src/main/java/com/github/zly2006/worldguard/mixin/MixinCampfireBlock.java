package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.ExtinguishCampfireEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlock.class)
public class MixinCampfireBlock {
    @Inject(at = @At("HEAD"), method = "extinguish", cancellable = true)
    private static void onExtinguish(Entity entity, WorldAccess world, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (world instanceof ServerWorld serverWorld) {
            if (entity instanceof ServerPlayerEntity player) {
                if (WorldGuardDispatcher.shouldPrevent(new ExtinguishCampfireEvent(player, pos, serverWorld))) {
                    ci.cancel();
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "tryFillWithFluid", cancellable = true)
    private void onFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {
        if (world instanceof ServerWorld serverWorld) {
            if (WorldGuardDispatcher.shouldPrevent(new ExtinguishCampfireEvent(null, pos, serverWorld))) {
                cir.setReturnValue(false);
            }
        }
    }
}
