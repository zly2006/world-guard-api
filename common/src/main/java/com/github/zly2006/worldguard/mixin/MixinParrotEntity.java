package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.FeedParrotCookieEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParrotEntity.class)
public abstract class MixinParrotEntity extends AnimalEntity {
    @Shadow
    @Final
    private static Item COOKIE;

    protected MixinParrotEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
    private void interactMob(PlayerEntity p, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (p instanceof ServerPlayerEntity player) {
            if (player.getStackInHand(hand).isOf(COOKIE)) {
                if (WorldGuardDispatcher.shouldPrevent(new FeedParrotCookieEvent(player, getBlockPos(), this, player.getStackInHand(hand)))) {
                    cir.setReturnValue(ActionResult.FAIL);
                }
            }
        }
    }
}
