package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.BreakVehicleEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecartEntity.class)
public abstract class MixinAbstractMinecartEntity extends Entity {
    protected MixinAbstractMinecartEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ServerPlayerEntity player;
        if (source.getAttacker() instanceof ServerPlayerEntity) {
            player = (ServerPlayerEntity) source.getAttacker();
        } else {
            player = null;
        }
        if (this.world.isClient) {
            return;
        }
        if (WorldGuardDispatcher.shouldPrevent(new BreakVehicleEvent(player, this.getBlockPos(), this))) {
            cir.setReturnValue(false);
        }
    }
}
