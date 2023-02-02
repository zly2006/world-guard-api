package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.PlaceBlockEvent;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class MixinBlockItem {
    @Inject(method = "canPlace", at = @At("HEAD"), cancellable = true)
    private void canPlace(ItemPlacementContext context, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (context.getPlayer() instanceof ServerPlayerEntity serverPlayer) {
            if (WorldGuardDispatcher.shouldPrevent(new PlaceBlockEvent(serverPlayer, context.getBlockPos(), context.getStack()))) {
                cir.setReturnValue(false);
            }
        }
    }
}
