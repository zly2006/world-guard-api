package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.BreakTurtleEggEvent;
import net.minecraft.entity.ai.goal.StepAndDestroyBlockGoal;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StepAndDestroyBlockGoal.class)
public class MixinStepAndDestroyBlockGoal {
    @Inject(method = "tweakToProperPos", at = @At("RETURN"), cancellable = true)
    private void onRemoveBlock(BlockPos blockPos, BlockView world, CallbackInfoReturnable<BlockPos> cir) {
        if (cir.getReturnValue() != null && world instanceof ServerWorld serverWorld) {
            BlockPos pos = cir.getReturnValue();
            if (WorldGuardDispatcher.shouldPrevent(new BreakTurtleEggEvent(pos))) {
                cir.setReturnValue(null);
            }
        }
    }
}
