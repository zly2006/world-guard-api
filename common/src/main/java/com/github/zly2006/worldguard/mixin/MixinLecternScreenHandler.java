package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.LecternTakeBookEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.LecternScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LecternScreenHandler.class)
public abstract class MixinLecternScreenHandler extends ScreenHandler {
    @Shadow public abstract ItemStack getBookItem();

    protected MixinLecternScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Inject(method = "onButtonClick", at = @At("HEAD"), cancellable = true)
    private void onButtonClick(PlayerEntity player, int id, CallbackInfoReturnable<Boolean> cir) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            if (id == 3) {
                if (WorldGuardDispatcher.shouldPrevent(new LecternTakeBookEvent(serverPlayer, getBookItem(), player.getBlockPos()))) {
                    cir.setReturnValue(false);
                    serverPlayer.closeHandledScreen();
                }
            }
        }
    }
}
