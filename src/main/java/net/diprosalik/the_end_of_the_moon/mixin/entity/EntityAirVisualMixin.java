package net.diprosalik.the_end_of_the_moon.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.entity.Entity.class)
public abstract class EntityAirVisualMixin {
    @Inject(method = "isSubmergedInWater", at = @At("HEAD"), cancellable = true)
    private void showBubblesInEnd(CallbackInfoReturnable<Boolean> cir) {
        net.minecraft.entity.Entity entity = (net.minecraft.entity.Entity) (Object) this;
        if (entity.getWorld() != null && entity.getWorld().getRegistryKey() == net.minecraft.world.World.END) {
            cir.setReturnValue(true);
        }
    }
}
