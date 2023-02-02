package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.OpenContainerEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LockableContainerBlockEntity.class)
public abstract class MixinLockableContainerBlockEntity extends BlockEntity implements Inventory  {
    public MixinLockableContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(at = @At("HEAD"), method = "checkUnlocked(Lnet/minecraft/entity/player/PlayerEntity;)Z", cancellable = true)
    private void checkUnlocked(PlayerEntity p, CallbackInfoReturnable<Boolean> cir) {
        if (p instanceof ServerPlayerEntity player && world instanceof ServerWorld serverWorld) {
            if (player.interactionManager.getGameMode() == GameMode.SPECTATOR) {
                return;
            }

            if (WorldGuardDispatcher.shouldPrevent(new OpenContainerEvent(player, pos, serverWorld, this, this))) {
                cir.setReturnValue(false);
            }
        }
    }
}
