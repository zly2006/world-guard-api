package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.BreakBlockEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerPlayerInteractionManager.class)
public class MixinServerPlayerInteractionManager {
    @Shadow @Final protected ServerPlayerEntity player;

    @Shadow protected ServerWorld world;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onBreak(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)V"), method = "tryBreakBlock", locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void breakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir, BlockState state, BlockEntity entity, Block block) {
        if (entity != null) {
            if (WorldGuardDispatcher.shouldPrevent(new BreakBlockEvent(player, world, pos))) {
                player.playerScreenHandler.syncState();
                player.networkHandler.sendPacket(new BlockUpdateS2CPacket(world, pos));
                for (Direction dir : Direction.values())
                    player.networkHandler.sendPacket(new BlockUpdateS2CPacket(world, pos.offset(dir)));
                player.networkHandler.sendPacket(BlockEntityUpdateS2CPacket.create(entity, BlockEntity::createNbt));
                cir.setReturnValue(true);
            }
        }
    }
}
