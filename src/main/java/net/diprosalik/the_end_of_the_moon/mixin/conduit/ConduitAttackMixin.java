package net.diprosalik.the_end_of_the_moon.mixin.conduit;

import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ConduitBlockEntity.class)
public abstract class ConduitAttackMixin {

    @Shadow private LivingEntity targetEntity;

    @Unique
    private int endAttackCooldown = 0;

    @Inject(method = "serverTick", at = @At("HEAD"))
    private static void customEndAttackLogic(World world, BlockPos pos, net.minecraft.block.BlockState state, ConduitBlockEntity blockEntity, CallbackInfo ci) {
        // Zugriff auf die Instanz-Variablen über den Accessor-Cast
        ConduitAttackMixin accessor = (ConduitAttackMixin)(Object)blockEntity;

        // 1. Nur im Ende und auf dem Server
        if (world instanceof ServerWorld serverWorld && world.getRegistryKey() == World.END) {

            // 2. Prüfen, ob der Conduit die höchste Stufe (42 Blöcke) hat
            // Wir nutzen hier eine Hilfsmethode oder schauen in die Liste der aktivierenden Blöcke
            // In 1.21.1 gibt es oft eine Methode wie 'isActive()' oder man prüft die Liste
            // Hier simulieren wir die Bedingung 'Stufe 42':
            if (blockEntity.isEyeOpen()) { // 'isActive' ist wahr, wenn der Rahmen steht

                // Wir suchen ein Ziel, wenn wir keines haben
                if (accessor.targetEntity == null || !accessor.targetEntity.isAlive()) {
                    List<LivingEntity> targets = world.getEntitiesByClass(LivingEntity.class, new Box(pos).expand(20.0),
                            e -> e instanceof Monster && e.isAlive());
                    if (!targets.isEmpty()) {
                        accessor.targetEntity = targets.get(world.random.nextInt(targets.size()));
                    }
                }

                if (accessor.targetEntity != null) {
                    // 3. Partikel fliegen lassen
                    spawnAttackParticles(serverWorld, pos, accessor.targetEntity);

                    // 4. 2-Sekunden Cooldown (40 Ticks)
                    if (accessor.endAttackCooldown > 0) {
                        accessor.endAttackCooldown--;
                    } else {
                        world.playSound(
                                null,                      // Der auslösende Spieler (null = für alle hörbar)
                                pos.getX(), pos.getY(), pos.getZ(),
                                net.minecraft.sound.SoundEvents.BLOCK_CONDUIT_ATTACK_TARGET,
                                net.minecraft.sound.SoundCategory.BLOCKS
                        );
                        // Schaden zufügen (4.0f = 2 Herzen)
                        accessor.targetEntity.damage(world.getDamageSources().magic(), 4.0f);
                        accessor.endAttackCooldown = 40;
                    }
                }
            }
        }
    }

    @Unique
    private static void spawnAttackParticles(ServerWorld world, BlockPos pos, LivingEntity target) {
        Vec3d start = pos.toCenterPos();
        Vec3d end = target.getEyePos();
        // Erzeugt Partikel, die zum Ziel wandern
        world.spawnParticles(ParticleTypes.REVERSE_PORTAL, end.x, end.y, end.z, 2, 0.2, 0.2, 0.2, 0.02);
        world.spawnParticles(ParticleTypes.WITCH, end.x, end.y, end.z, 1, 0.2, 0.2, 0.2, 0.02);
    }
}