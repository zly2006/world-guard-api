package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.ExtinguishCampfireEvent;
import net.minecraft.block.Block;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.block.CampfireBlock.LIT;
import static net.minecraft.block.CampfireBlock.WATERLOGGED;

@Mixin(PotionEntity.class)
public abstract class MixinPotionEntity extends ThrownItemEntity {
    public MixinPotionEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "extinguishFire", cancellable = true)
    private void onExtinguishFire(BlockPos pos, CallbackInfo ci) {
        if (world.isClient) {
            return;
        }
        Block block = world.getBlockState(pos).getBlock();
        ServerPlayerEntity player;
        if (getOwner() instanceof ServerPlayerEntity) {
            player = (ServerPlayerEntity) getOwner();
        } else {
            player = null;
        }
        if (block instanceof CampfireBlock) {
            if (WorldGuardDispatcher.shouldPrevent(new ExtinguishCampfireEvent(player, pos, (ServerWorld) world))) {
                world.setBlockState(pos, world.getBlockState(pos).with(WATERLOGGED, false).with(LIT, true), 252);
                ci.cancel();
            }
        }
    }
}
