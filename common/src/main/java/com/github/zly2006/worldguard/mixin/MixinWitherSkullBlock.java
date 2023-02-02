package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.SpawnWitherEvent;
import net.minecraft.block.WitherSkullBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WitherSkullBlock.class)
public class MixinWitherSkullBlock {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/block/pattern/BlockPattern;searchAround(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/pattern/BlockPattern$Result;"), method = "onPlaced(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/SkullBlockEntity;)V", cancellable = true)
    private static BlockPattern.Result guard$searchAround(BlockPattern instance, WorldView world, BlockPos pos) {
        if (WorldGuardDispatcher.shouldPrevent(new SpawnWitherEvent(pos, (ServerWorld) world))) {
            return null;
        }
        return instance.searchAround(world, pos);
    }
}
