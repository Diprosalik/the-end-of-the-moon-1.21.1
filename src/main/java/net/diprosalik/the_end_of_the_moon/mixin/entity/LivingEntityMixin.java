package net.diprosalik.the_end_of_the_moon.mixin.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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

    @Inject(method = "getNextAirUnderwater", at = @At("HEAD"), cancellable = true)
    private void stopAirLossInEnd(int air, CallbackInfoReturnable<Integer> cir) {
        if (isProtectedByDragonFight((LivingEntity) (Object) this)) {
            cir.setReturnValue(air);
        }
    }

    // 2. ZUM FIXEN DER REGENERATION (Damit die Luft nicht "allgemein" stehen bleibt)
    @Inject(method = "getNextAirOnLand", at = @At("HEAD"), cancellable = true)
    private void stopAirRegenInEnd(int air, CallbackInfoReturnable<Integer> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity.getWorld().getRegistryKey() == World.END && !isProtectedByDragonFight(entity)) {
            // Nur abbrechen, wenn der aktuelle Luftwert kleiner oder gleich dem alten ist.
            // Das erlaubt es Items (wie deiner Gas Bottle), den Wert nach oben zu setzen.
            if (entity.getAir() <= air) {
                cir.setReturnValue(air);
            }
        }
    }

    // 3. DIE GEMEINSAME LOGIK (Hilfsmethode)
    @Unique
    private boolean isProtectedByDragonFight(LivingEntity entity) {
        if (entity.getWorld().getRegistryKey() != World.END) return false;

        double distanceSq = entity.squaredDistanceTo(0, entity.getY(), 0);
        boolean nearSpawn = distanceSq <= 10000.0;

        if (entity.getWorld().isClient) return nearSpawn;

        if (entity.getWorld() instanceof ServerWorld serverWorld) {
            var fight = serverWorld.getEnderDragonFight();
            return nearSpawn && fight != null && !fight.hasPreviouslyKilled(); // hasPreviouslyKilled
        }
        return false;
    }
}
