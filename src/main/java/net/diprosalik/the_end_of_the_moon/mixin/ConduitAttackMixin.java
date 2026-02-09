package net.diprosalik.the_end_of_the_moon.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ConduitBlockEntity.class)
public abstract class ConduitAttackMixin {

    // Hiermit machen wir die private Methode für das Mixin sichtbar
    @Shadow
    private static Box getAttackZone(BlockPos pos) {
        return null; // Wird vom Mixin-Prozessor ignoriert
    }

    // Hiermit machen wir das private Feld sichtbar
    @Shadow
    private LivingEntity targetEntity;

    @Inject(
            method = "attackHostileEntity",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getEntitiesByClass(Ljava/lang/Class;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Ljava/util/List;")
    )
    private static void customTargetSearch(World world, BlockPos pos, BlockState state, List<BlockPos> activatingBlocks, ConduitBlockEntity blockEntity, CallbackInfo ci) {
        if (world.getRegistryKey() == World.END) {
            // Jetzt findet er getAttackZone, da wir es oben als @Shadow deklariert haben
            List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, getAttackZone(pos),
                    (entity) -> entity instanceof Monster);

            if (!list.isEmpty()) {
                // Zugriff auf das Feld über das blockEntity-Objekt
                // Da targetEntity privat ist, müssen wir es im Mixin via Shadow einbinden (siehe unten)
                ((ConduitAttackMixin)(Object)blockEntity).targetEntity = list.get(world.random.nextInt(list.size()));
            }
        }
    }
}