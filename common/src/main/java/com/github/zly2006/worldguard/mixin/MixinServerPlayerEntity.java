package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.worldguard.WorldGuardDispatcher;
import com.github.zly2006.worldguard.event.PvpEvent;
import com.mojang.authlib.GameProfile;
import net.fabricmc.api.Environment;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.fabricmc.api.EnvType.SERVER;

@Environment(SERVER)
@Mixin(ServerPlayerEntity.class)
public abstract class MixinServerPlayerEntity extends PlayerEntity {

    @Shadow public abstract void sendMessage(Text message);

    @Shadow public abstract ServerWorld getWorld();

    @Shadow public abstract void sendMessage(Text message, boolean overlay);

    @Shadow @Final public MinecraftServer server;

    public MixinServerPlayerEntity(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void protectPVP(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!(source instanceof EntityDamageSource)) {
            return;
        }
        if (source.getAttacker() instanceof ServerPlayerEntity attacker) {
            if (WorldGuardDispatcher.shouldPrevent(new PvpEvent(attacker, (ServerPlayerEntity) (Object) this))) {
                cir.setReturnValue(false);
            }
        }
    }
}
