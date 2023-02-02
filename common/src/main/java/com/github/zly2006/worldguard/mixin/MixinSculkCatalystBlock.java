package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.SculkSpreadEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkCatalystBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkCatalystBlock.class)
public class MixinSculkCatalystBlock {
    @Inject(method = "bloom", at = @At("HEAD"), cancellable = true)
    private static void bloom(ServerWorld world, BlockPos pos, BlockState state, Random random, CallbackInfo ci) {
        if (WorldGuardDispatcher.shouldPrevent(new SculkSpreadEvent(pos))) {
            ci.cancel();
        }
    }

    @Inject(method = "scheduledTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"), cancellable = true)
    private void setBlock(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (WorldGuardDispatcher.shouldPrevent(new SculkSpreadEvent(pos))) {
            ci.cancel();
        }
    }
}
