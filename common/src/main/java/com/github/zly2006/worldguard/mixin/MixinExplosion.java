package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.ExplosionEvent;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(Explosion.class)
public abstract class MixinExplosion {
    @Shadow
    @Final
    private ObjectArrayList<BlockPos> affectedBlocks;

    @Shadow
    @Final
    private World world;

    @Shadow @Nullable public Entity entity;

    @ModifyVariable(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;<init>(DDD)V", ordinal = 1), method = "collectBlocksAndDamageEntities")
    private List<Entity> protectEntities(List<Entity> list) {
        if (!world.isClient) {
            BlockPos pos;
            if (this.entity != null) {
                pos = this.entity.getBlockPos();
            }
            else {
                pos = this.world.getSpawnPos();
            }
            if (WorldGuardDispatcher.shouldPrevent(new ExplosionEvent(pos, (Explosion) (Object) this))) {
                affectedBlocks.clear();
                return List.of();
            }
        }
        return list;
    }
}
