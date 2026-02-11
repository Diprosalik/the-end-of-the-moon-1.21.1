package net.diprosalik.the_end_of_the_moon.mixin.visuals;

import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.entity.Entity.class)
public abstract class MoonDustMixin {

    @Inject(method = "spawnSprintingParticles", at = @At("HEAD"), cancellable = true)
    private void spawnMoonDust(CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;
        World world = entity.getWorld();

        if (world.getRegistryKey() == World.END) {
            Random random = world.getRandom();
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();

            for (int i = 0; i < 5; i++) {
                world.addParticle(
                        ParticleTypes.WHITE_ASH,
                        x + (random.nextDouble() - 0.5) * 0.5,
                        y + 0.1,
                        z + (random.nextDouble() - 0.5) * 0.5,
                        0, 0.05, 0
                );
            }
            ci.cancel(); // Verhindert die normalen Vanilla-Partikel
        }
    }
}
