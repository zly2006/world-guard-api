package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.LeadAttachEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.LeadItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LeadItem.class)
public class MixinLeadItem {
    @Inject(method = "attachHeldMobsToBlock", at = @At("HEAD"), cancellable = true)
    private static void onAttachHeldMobsToBlock(PlayerEntity player, World world, BlockPos pos, CallbackInfoReturnable<ActionResult> cir) {
        if (player instanceof ServerPlayerEntity serverPlayerEntity) {
            if (WorldGuardDispatcher.shouldPrevent(new LeadAttachEvent(serverPlayerEntity, pos))) {
                cir.setReturnValue(ActionResult.PASS);
            }
        }
    }
}
