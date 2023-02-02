package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.GetOnVehicleEvent;
import com.github.zly2006.worldguard.event.OpenContainerEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.RideableInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.entity.vehicle.VehicleInventory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBoatEntity.class)
public abstract class MixinChestBoatEntity extends BoatEntity implements RideableInventory, VehicleInventory {
    public MixinChestBoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "canPlayerUse", at = @At("HEAD"), cancellable = true)
    private void canPlayerUse(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            if (WorldGuardDispatcher.shouldPrevent(new OpenContainerEvent(serverPlayer, this.getBlockPos(), serverPlayer.getWorld(), this, null))) {
                cir.setReturnValue(false);
            }
            if (WorldGuardDispatcher.shouldPrevent(new GetOnVehicleEvent(serverPlayer, this.getBlockPos(), this))) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void onInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            if (WorldGuardDispatcher.shouldPrevent(new OpenContainerEvent(serverPlayer, this.getBlockPos(), serverPlayer.getWorld(), this, null))) {
                cir.setReturnValue(ActionResult.FAIL);
            }
            if (WorldGuardDispatcher.shouldPrevent(new GetOnVehicleEvent(serverPlayer, this.getBlockPos(), this))) {
                cir.setReturnValue(ActionResult.FAIL);
            }
        }
    }
}
