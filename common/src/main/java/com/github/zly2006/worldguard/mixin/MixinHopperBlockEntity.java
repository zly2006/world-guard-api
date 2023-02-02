package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.OpenContainerEvent;
import com.github.zly2006.worldguard.event.PickupItemEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(HopperBlockEntity.class)
public class MixinHopperBlockEntity extends BlockEntity {
    public MixinHopperBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "extract(Lnet/minecraft/world/World;Lnet/minecraft/block/entity/Hopper;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;getAvailableSlots(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/util/math/Direction;)Ljava/util/stream/IntStream;"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private static void onExtract(World world, Hopper hopper, CallbackInfoReturnable<Boolean> cir, Inventory inventory, Direction direction) {
        if (world.isClient) return;
        BlockPos pos = new BlockPos(new Vec3d(hopper.getHopperX(), hopper.getHopperY(), hopper.getHopperZ()));
        if (WorldGuardDispatcher.shouldPrevent(new OpenContainerEvent(null, pos, (ServerWorld) world, inventory, null))) {
            cir.setReturnValue(false);
        }
    }
    @Inject(method = "extract(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/entity/ItemEntity;)Z", at = @At("HEAD"), cancellable = true)
    private static void onExtract(Inventory inventory, ItemEntity itemEntity, CallbackInfoReturnable<Boolean> cir) {
        if (itemEntity.world.isClient) return;
        if (WorldGuardDispatcher.shouldPrevent(new PickupItemEvent(null, itemEntity.getBlockPos(), itemEntity))) {
            cir.setReturnValue(false);
        }
    }
}
