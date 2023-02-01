package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.BreakBlockEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractDecorationEntity.class)
public abstract class MixinAbstractDecorationEntity extends Entity {
    public MixinAbstractDecorationEntity(EntityType<?> type, World world) {
        super(type, world);
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
        if (WorldGuardDispatcher.shouldPrevent(new BreakBlockEvent(player, (ServerWorld) this.world, this.getBlockPos()))) {
            cir.setReturnValue(false);
        }
    }
}
