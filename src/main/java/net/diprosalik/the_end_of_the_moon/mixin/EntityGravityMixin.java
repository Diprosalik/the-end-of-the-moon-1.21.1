package net.diprosalik.the_end_of_the_moon.mixin;

import net.minecraft.entity.Entity;
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

        if (this.getWorld().getRegistryKey() == World.END) {
            cir.setReturnValue(cir.getReturnValue() * 0.25);
        }
    }
}