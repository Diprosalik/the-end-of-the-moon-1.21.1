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
        ConduitAttackMixin accessor = (ConduitAttackMixin)(Object)blockEntity;

        if (world instanceof ServerWorld serverWorld && world.getRegistryKey() == World.END) {
            if (blockEntity.isEyeOpen()) {

                if (accessor.targetEntity == null || !accessor.targetEntity.isAlive()) {
                    List<LivingEntity> targets = world.getEntitiesByClass(LivingEntity.class, new Box(pos).expand(20.0),
                            e -> e instanceof Monster && e.isAlive());
                    if (!targets.isEmpty()) {
                        accessor.targetEntity = targets.get(world.random.nextInt(targets.size()));
                    }
                }

                if (accessor.targetEntity != null) {
                    spawnAttackParticles(serverWorld, pos, accessor.targetEntity);

                    if (accessor.endAttackCooldown > 0) {
                        accessor.endAttackCooldown--;
                    } else {
                        world.playSound(
                                null,
                                pos.getX(), pos.getY(), pos.getZ(),
                                net.minecraft.sound.SoundEvents.BLOCK_CONDUIT_ATTACK_TARGET,
                                net.minecraft.sound.SoundCategory.BLOCKS
                        );

                        accessor.targetEntity.damage(world.getDamageSources().magic(), 4.0f);
                        accessor.endAttackCooldown = 40;
                    }
                }
            }
        }
    }

    @Unique
    private static void spawnAttackParticles(ServerWorld world, BlockPos pos, LivingEntity target) {
        Vec3d end = target.getEyePos();
        world.spawnParticles(ParticleTypes.REVERSE_PORTAL, end.x, end.y, end.z, 2, 0.2, 0.2, 0.2, 0.02);
        world.spawnParticles(ParticleTypes.WITCH, end.x, end.y, end.z, 1, 0.2, 0.2, 0.2, 0.02);
    }
}