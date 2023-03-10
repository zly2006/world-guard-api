package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.ShootEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BowItem.class)
public class MixinBowItem {
    @Inject(at = @At("HEAD"), method = "onStoppedUsing", cancellable = true)
    public void checkBowPermission(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
        if (user instanceof ServerPlayerEntity player) {
            if (WorldGuardDispatcher.shouldPrevent(new ShootEvent(player, player.getBlockPos(), stack))) {
                ci.cancel();
            }
        }
    }
}
