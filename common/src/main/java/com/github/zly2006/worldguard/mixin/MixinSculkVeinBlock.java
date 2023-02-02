package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.SculkSpreadEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkVeinBlock;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(SculkVeinBlock.class)
public class MixinSculkVeinBlock {
    private static boolean disallow(WorldAccess world, BlockPos pos) {
        if (world instanceof ServerWorld serverWorld) {
            if (WorldGuardDispatcher.shouldPrevent(new SculkSpreadEvent(pos))) {
                return true;
            }
        }
        return false;
    }
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"), method = "place", cancellable = true)
    private static void place(WorldAccess world, BlockPos pos, BlockState state, Collection<Direction> directions, CallbackInfoReturnable<Boolean> cir) {
        if (disallow(world, pos)) {
            cir.setReturnValue(false);
        }
    }
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"), method = "spreadAtSamePosition", cancellable = true)
    private void spreadAtSamePosition(WorldAccess world, BlockState state, BlockPos pos, Random random, CallbackInfo ci) {
        if (disallow(world, pos)) {
            ci.cancel();
        }
    }
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"), method = "convertToBlock", cancellable = true)
    private void convertToBlock(SculkSpreadManager spreadManager, WorldAccess world, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if (disallow(world, pos)) {
            cir.setReturnValue(false);
        }
    }
}
