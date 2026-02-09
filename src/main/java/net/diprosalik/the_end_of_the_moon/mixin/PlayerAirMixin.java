package net.diprosalik.the_end_of_the_moon.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
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

                if (player.age % 2 == 0 && !player.hasStatusEffect(StatusEffects.CONDUIT_POWER)) {
                    player.setAir(currentAir - 1);
                }

                // Wenn die Luft fast leer ist (unter 100), bekommt er schon Nausea
//                if (currentAir < 50) {
//                    // Stärke 0 ist Level 1, 200 Ticks = 10 Sekunden
//                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 100, 0, true, false));
//                }

                if (currentAir <= -20) {
                    player.setAir(-20);
                    if (player.age % 20 == 0) {
                        // Wir nutzen generic() statt drown(), um den Gurgel-Sound zu vermeiden
                        player.damage(world.getDamageSources().generic(), 2.0F);
                    }
                }
            }

            if (player.isCreative() || player.isSpectator()) {
                currentAir = 300;
                player.setAir(currentAir);
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void spawnSpaceParticles(CallbackInfo info) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        World world = player.getWorld();

        // Nur auf dem Client Partikel spawnen (Performance!)
        if (world.isClient && world.getRegistryKey() == World.END) {
            double dist = Math.sqrt(player.getX() * player.getX() + player.getZ() * player.getZ());

            // Partikel in der Schwerelosigkeits-Zone (150 - 900 Blöcke)
            if (dist > 900 && world.random.nextFloat() < 0.5f) {
                world.addParticle(ParticleTypes.WHITE_ASH,
                        player.getX() + (world.random.nextDouble() - 0.5) * 20,
                        player.getY() + (world.random.nextDouble() - 0.5) * 20,
                        player.getZ() + (world.random.nextDouble() - 0.5) * 20,
                        0, 0, 0
                );
            }

            if (dist > 900 && world.random.nextFloat() < 0.2f) {
                world.addParticle(ParticleTypes.WITCH,
                        player.getX() + (world.random.nextDouble() - 0.5) * 20,
                        player.getY() + (world.random.nextDouble() - 0.5) * 20,
                        player.getZ() + (world.random.nextDouble() - 0.5) * 20,
                        (world.random.nextDouble() - 0.5) * 0.2,
                        (world.random.nextDouble() - 0.5) * 0.2,
                        (world.random.nextDouble() - 0.5) * 0.2
                );
            }


            double minRadius = 5.0;
            double maxRadius = 15.0;


            double theta = world.random.nextDouble() * 2.0 * Math.PI;
            double phi = Math.acos(2.0 * world.random.nextDouble() - 1.0);
            double r = minRadius + (maxRadius - minRadius) * world.random.nextDouble();


            double offsetX = r * Math.sin(phi) * Math.cos(theta);
            double offsetY = r * Math.sin(phi) * Math.sin(theta);
            double offsetZ = r * Math.cos(phi);

            if (dist > 100 && dist < 900 && world.random.nextFloat() < 0.3f) {
                world.addParticle(ParticleTypes.ELECTRIC_SPARK,
                        player.getX() + offsetX,
                        player.getY() + offsetY + 1.0,
                        player.getZ() + offsetZ,
                        (world.random.nextDouble() - 0.5) * 0.2,
                        (world.random.nextDouble() - 0.5) * 0.2,
                        (world.random.nextDouble() - 0.5) * 0.2
                );
            }


        }
    }
}