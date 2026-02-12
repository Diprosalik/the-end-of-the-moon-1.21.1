package net.diprosalik.the_end_of_the_moon.mixin.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "computeFallDamage", at = @At("HEAD"), cancellable = true)
    private void adjustFallDamageInEnd(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity.getWorld().getRegistryKey() == World.END) {
            float adjustedDistance = fallDistance * 0.5F;

            int finalDamage = (int) Math.max(0, Math.ceil((adjustedDistance - 3.0F) * damageMultiplier));
            cir.setReturnValue(finalDamage);
        }
    }

    @Inject(method = "getNextAirOnLand", at = @At("HEAD"), cancellable = true)
    private void stopAirRegenInEnd(int air, CallbackInfoReturnable<Integer> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.getWorld().getRegistryKey() == World.END) {
            cir.setReturnValue(air);
        }
    }
}
