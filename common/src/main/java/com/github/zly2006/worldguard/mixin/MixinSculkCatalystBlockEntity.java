package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.SculkSpreadEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SculkCatalystBlockEntity;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SculkCatalystBlockEntity.class)
public class MixinSculkCatalystBlockEntity extends BlockEntity {
    public MixinSculkCatalystBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Redirect(method = "listen", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/SculkSpreadManager;spread(Lnet/minecraft/util/math/BlockPos;I)V"))
    private void spread(SculkSpreadManager instance, BlockPos pos, int charge) {
        if (getWorld() == null || getWorld().isClient) {
            // 爱咋咋地，不改变行为
            instance.spread(pos, charge);
        }
        if (WorldGuardDispatcher.shouldPrevent(new SculkSpreadEvent(pos))) {
            return;
        }
        instance.spread(pos, charge);
    }
}
