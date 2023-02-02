package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.FireSpreadEvent;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.fabricmc.api.EnvType.SERVER;

@Environment(SERVER)
@Mixin(FireBlock.class)
public class MixinFireBlock {
    private void set(World instance, BlockPos pos, BlockState blockState, int i) {
        if (instance.isClient) return;
        if (WorldGuardDispatcher.shouldPrevent(new FireSpreadEvent(pos))) return;
        instance.setBlockState(pos, blockState, i);
    }

    private void remove(World instance, BlockPos pos, boolean move) {
        if (instance.isClient) return;

        if (WorldGuardDispatcher.shouldPrevent(new FireSpreadEvent(pos))) {
            if (instance.getBlockState(pos).isOf(Blocks.FIRE)) {
                instance.removeBlock(pos, move);
            }
        } else {
            instance.removeBlock(pos, move);
        }
    }

    @Redirect(method = "scheduledTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private boolean redirectSetter(ServerWorld instance, BlockPos pos, BlockState blockState, int i) {
        set(instance, pos, blockState, i);
        return true;
    }

    @Redirect(method = "scheduledTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"))
    private boolean redirectSetter(ServerWorld instance, BlockPos pos, boolean b) {
        remove(instance, pos, b);
        return true;
    }

    @Redirect(method = "trySpreadingFire", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private boolean redirectSetter(World instance, BlockPos pos, BlockState state, int flags) {
        set(instance, pos, state, flags);
        return true;
    }

    @Redirect(method = "trySpreadingFire", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"))
    private boolean redirectSetter(World instance, BlockPos pos, boolean move) {
        remove(instance, pos, move);
        return true;
    }
}
