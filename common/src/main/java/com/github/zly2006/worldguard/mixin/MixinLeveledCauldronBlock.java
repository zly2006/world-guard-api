package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.ExtinguishCauldronEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeveledCauldronBlock.class)
public class MixinLeveledCauldronBlock {
    @Inject(method = "onFireCollision", at = @At("HEAD"), cancellable = true)
    private void onFireCollision(BlockState state, World world, BlockPos pos, CallbackInfo ci){
        if (!world.isClient) {
            if (WorldGuardDispatcher.shouldPrevent(new ExtinguishCauldronEvent(pos))) {
                ci.cancel();
            }
        }
    }
}
