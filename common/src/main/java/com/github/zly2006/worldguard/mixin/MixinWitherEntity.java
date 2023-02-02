package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.WitherDestroyEvent;
import com.github.zly2006.worldguard.event.WitherEnterEvent;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(WitherEntity.class)
public abstract class MixinWitherEntity extends Entity {
    public MixinWitherEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "mobTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;breakBlock(Lnet/minecraft/util/math/BlockPos;ZLnet/minecraft/entity/Entity;)Z"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void tick(CallbackInfo ci, int i, int j, int k, boolean bl, int l, int m, int n, int o, int p, int q, BlockPos blockPos, BlockState blockState) {
        if (world.isClient) {
            return;
        }
        if (WorldGuardDispatcher.shouldPrevent(new WitherDestroyEvent(blockPos))) {
            ci.cancel();
        }
        if (WorldGuardDispatcher.shouldPrevent(new WitherEnterEvent(blockPos))) {
            discard();
            ci.cancel();
        }
    }
}
