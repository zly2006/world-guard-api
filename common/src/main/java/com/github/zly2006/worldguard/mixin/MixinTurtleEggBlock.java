package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.BreakTurtleEggEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TurtleEggBlock.class)
public class MixinTurtleEggBlock {
    @Inject(method = "tryBreakEgg", at = @At("HEAD"), cancellable = true)
    private void onBreakEgg(World world, BlockState state, BlockPos pos, Entity entity, int inverseChance, CallbackInfo ci) {
        if (!world.isClient) {
            if (WorldGuardDispatcher.shouldPrevent(new BreakTurtleEggEvent(pos))) {
                ci.cancel();
            }
        }
    }
}
