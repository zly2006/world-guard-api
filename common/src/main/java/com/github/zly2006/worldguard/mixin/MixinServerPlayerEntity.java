package com.github.zly2006.worldguard.mixin;

import com.github.zly2006.enclosure.EnclosureArea;
import com.github.zly2006.enclosure.ServerMain;
import com.github.zly2006.enclosure.access.PlayerAccess;
import com.github.zly2006.enclosure.utils.Permission;
import com.github.zly2006.enclosure.utils.Utils;
import com.mojang.authlib.GameProfile;
import net.fabricmc.api.Environment;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.zly2006.enclosure.ServerMain.Instance;
import static com.github.zly2006.enclosure.ServerMain.commonConfig;
import static com.github.zly2006.enclosure.commands.EnclosureCommand.CONSOLE;
import static com.github.zly2006.enclosure.utils.Permission.*;
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
