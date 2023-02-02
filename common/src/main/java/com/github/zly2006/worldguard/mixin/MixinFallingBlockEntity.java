package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.FallingBlockLandEvent;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.fabricmc.api.EnvType.SERVER;

@Environment(SERVER)
@Mixin(value = FallingBlockEntity.class)
public abstract class MixinFallingBlockEntity extends Entity {
    @Shadow
    @Final
    protected static TrackedData<BlockPos> BLOCK_POS;

    public MixinFallingBlockEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract BlockPos getFallingBlockPos();

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"), method = "tick", cancellable = true)
    private void protectFallingBlocks(CallbackInfo ci) {
        if (world.isClient) {
            return;
        }
        if (WorldGuardDispatcher.shouldPrevent(new FallingBlockLandEvent(getFallingBlockPos(), (FallingBlockEntity) (Object) this))) {
            discard();
            ci.cancel();
        }
    }
}
