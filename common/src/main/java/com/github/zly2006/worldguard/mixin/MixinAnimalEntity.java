package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.FeedAnimalEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalEntity.class)
public abstract class MixinAnimalEntity extends Entity {
    public MixinAnimalEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "eat", cancellable = true)
    private void onEating(PlayerEntity player, Hand hand, ItemStack stack, CallbackInfo ci) {
        if (getWorld().isClient) {
            return;
        }
        if (WorldGuardDispatcher.shouldPrevent(new FeedAnimalEvent((ServerPlayerEntity) player, getBlockPos(), (AnimalEntity) (Object) this, stack))) {
            ci.cancel();
        }
    }
}
