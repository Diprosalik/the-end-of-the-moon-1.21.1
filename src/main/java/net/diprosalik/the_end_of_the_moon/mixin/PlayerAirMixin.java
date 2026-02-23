package net.diprosalik.the_end_of_the_moon.mixin;

import net.diprosalik.the_end_of_the_moon.block.ModBlock;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerAirMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void applyEndSuffocation(CallbackInfo info) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        World world = player.getWorld();
        int currentAir = player.getAir();

        if (!world.isClient() && world.getRegistryKey() == World.END) {
            if (!player.isCreative() && !player.isSpectator()) {

                boolean nearShroom = false;
                Iterable<BlockPos> checkArea = BlockPos.iterate(
                        player.getBlockPos().add(-1, -1, -1),
                        player.getBlockPos().add(1, 1, 1)
                );

                for (BlockPos checkPos : checkArea) {
                    if (world.getBlockState(checkPos).isOf(ModBlock.CHORUS_SHROOM)) {
                        nearShroom = true;
                        break;
                    }
                }

                if (!nearShroom) {
                    if (player.age % 2 == 0 && !player.hasStatusEffect(StatusEffects.CONDUIT_POWER)) {
                        player.setAir(currentAir - 1);
                    }
                } else {
                    if (currentAir < 300 && player.age % 5 == 0) {
                        player.setAir(currentAir + 1);
                    }
                }

                if (currentAir <= -20) {
                    player.setAir(-20);
                    if (player.age % 20 == 0) {
                        player.damage(world.getDamageSources().generic(), 2.0F);
                    }
                }
            }
        }
    }

}