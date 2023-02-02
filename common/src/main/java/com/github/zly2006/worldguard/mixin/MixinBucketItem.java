package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.PlaceBlockEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BucketItem.class)
public class MixinBucketItem {
    @Shadow @Final private Fluid fluid;

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;canPlayerModifyAt(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;)Z"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void onUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir, ItemStack itemStack, BlockHitResult blockHitResult, BlockPos blockPos, Direction direction, BlockPos blockPos2) {
        if (user instanceof ServerPlayerEntity player) {
            if (WorldGuardDispatcher.shouldPrevent(new PlaceBlockEvent(player, player.getBlockPos(), itemStack))) {
                player.currentScreenHandler.syncState();
                cir.setReturnValue(TypedActionResult.fail(itemStack));
            }
        }
    }
}
