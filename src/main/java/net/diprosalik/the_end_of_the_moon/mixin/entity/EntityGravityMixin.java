package net.diprosalik.the_end_of_the_moon.mixin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityGravityMixin {

    @Shadow public abstract World getWorld();

    @Inject(method = "getFinalGravity", at = @At("RETURN"), cancellable = true)
    private void modifyGravityInEnd(CallbackInfoReturnable<Double> cir) {
        Entity entity = (Entity) (Object) this;

        // Prüfen, ob wir im End sind
        if (this.getWorld().getRegistryKey() == World.END) {

            // WICHTIG: Prüfen, ob das Entity eine LivingEntity ist und gerade mit der Elytra fliegt
            if (entity instanceof LivingEntity living && living.isFallFlying()) {
                // Wenn man fliegt, geben wir den originalen Wert (normale Gravitation) zurück
                return;
            }

            // Ansonsten: Verringerte Gravitation für den "Moon-Effekt"
            cir.setReturnValue(cir.getReturnValue() * 0.25);
        }
    }
}