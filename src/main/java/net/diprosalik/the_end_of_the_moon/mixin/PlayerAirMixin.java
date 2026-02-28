package net.diprosalik.the_end_of_the_moon.mixin;

import net.diprosalik.the_end_of_the_moon.block.ModBlock;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
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

                boolean nearShroom = false;
                Iterable<BlockPos> checkArea = BlockPos.iterate(
                        player.getBlockPos().add(-1, -1, -1),
                        player.getBlockPos().add(1, 1, 1)
                );

                for (BlockPos checkPos : checkArea) {
                    if (world.getBlockState(checkPos).isOf(ModBlock.CHORUS_SHROOM)) {
                        nearShroom = true;
                        break;
                    }
                }

                if (!nearShroom) {
                    if (player.age % 2 == 0 && !player.hasStatusEffect(StatusEffects.CONDUIT_POWER)) {
                        player.setAir(currentAir - 1);
                    }
                } else {
                    if (currentAir < 300 && player.age % 5 == 0) {
                        player.setAir(currentAir + 1);
                    }
                }

                if (player instanceof ServerPlayerEntity serverPlayer) {
                    // 1. Hole den Loader
                    var advancementLoader = serverPlayer.getServer().getAdvancementLoader();

                    // 2. Erstelle den Identifier (muss exakt zum Pfad der JSON passen)
                    Identifier advId = Identifier.of("the_end_of_the_moon", "umbrasynthesis");

                    // 3. Suche das AdvancementEntry
                    var advancementEntry = advancementLoader.get(advId);

                    if (advancementEntry != null) {
                        // 4. Hole den Fortschritt des Spielers
                        var tracker = serverPlayer.getAdvancementTracker();
                        var progress = tracker.getProgress(advancementEntry);

                        // 5. Nur vergeben, wenn noch nicht erreicht
                        if (!progress.isDone()) {
                            // Hier vergeben wir das Kriterium "shroom_protection"
                            // (Muss exakt so in der JSON stehen!)

                        }
                    }
                }

                if (currentAir <= -20) {
                    player.setAir(-20);
                    if (player.age % 20 == 0) {
                        player.damage(world.getDamageSources().generic(), 2.0F);
                    }
                }
            }
        }
    }

}