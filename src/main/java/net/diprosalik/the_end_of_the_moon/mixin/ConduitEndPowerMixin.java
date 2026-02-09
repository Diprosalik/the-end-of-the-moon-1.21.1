package net.diprosalik.the_end_of_the_moon.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ConduitBlockEntity.class)
public abstract class ConduitEndPowerMixin {

    /* ------------------------------------------------------------
     * 1. Aktivierungs-Logik im END komplett ersetzen
     * ------------------------------------------------------------ */
    @Inject(
            method = "updateActivatingBlocks",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void updateActivatingBlocksInEnd(
            World world,
            BlockPos pos,
            List<BlockPos> activatingBlocks,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (world.getRegistryKey() != World.END) return;

        activatingBlocks.clear();

        // 3x3x3-Check OHNE Wasser
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (world.isOutOfHeightLimit(pos.add(x, y, z))) {
                        cir.setReturnValue(false);
                        return;
                    }
                }
            }
        }

        // Rahmen prüfen (5x5 Kreuzform)
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    int ax = Math.abs(x);
                    int ay = Math.abs(y);
                    int az = Math.abs(z);

                    if ((ax > 1 || ay > 1 || az > 1) &&
                            (x == 0 && (ay == 2 || az == 2)
                                    || y == 0 && (ax == 2 || az == 2)
                                    || z == 0 && (ax == 2 || ay == 2))) {

                        BlockPos checkPos = pos.add(x, y, z);
                        BlockState state = world.getBlockState(checkPos);

                        if (isValidEndFrameBlock(state.getBlock())) {
                            activatingBlocks.add(checkPos);
                        }
                    }
                }
            }
        }

        cir.setReturnValue(activatingBlocks.size() >= 16);
    }

    @Unique
    private static boolean isValidEndFrameBlock(Block block) {
        return block == Blocks.PRISMARINE
                || block == Blocks.PRISMARINE_BRICKS
                || block == Blocks.DARK_PRISMARINE
                || block == Blocks.SEA_LANTERN
                || block == Blocks.END_STONE_BRICKS;
    }

    /* ------------------------------------------------------------
     * 2. Spieler-Effekt auch an Land im END
     * ------------------------------------------------------------ */
    @Inject(
            method = "givePlayersEffects",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void giveEffectsInEnd(
            World world, BlockPos pos, List<BlockPos> activatingBlocks, CallbackInfo cir
    ) {
        if (world.getRegistryKey() != World.END) return;

        int range = activatingBlocks.size() / 7 * 16;
        Box box = new Box(pos).expand(range).stretch(0, world.getHeight(), 0);

        for (PlayerEntity player : world.getNonSpectatingEntities(PlayerEntity.class, box)) {
            if (pos.isWithinDistance(player.getBlockPos(), range)) {
                player.addStatusEffect(
                        new net.minecraft.entity.effect.StatusEffectInstance(
                                net.minecraft.entity.effect.StatusEffects.CONDUIT_POWER,
                                260,
                                0,
                                true,
                                true
                        )
                );
            }
        }

        cir.cancel();
    }

    /* ------------------------------------------------------------
     * 3. Nautilus-Partikel → Reverse Portal
     * ------------------------------------------------------------ */
    // 3. Nautilus-Partikel → Reverse Portal
    @Inject(
            method = "spawnNautilusParticles",
            at = @At("HEAD"), // Wir nutzen HEAD, um die gesamte Methode zu kontrollieren
            cancellable = true
    )
    private static void replaceParticles(
            World world,
            BlockPos pos,
            List<BlockPos> activatingBlocks,
            net.minecraft.entity.Entity entity,
            int ticks,
            org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci
    ) {
        if (world.getRegistryKey() != World.END) return;

        net.minecraft.util.math.random.Random random = world.random;

        if (world.random.nextFloat() < 0.3f) {
            // Partikel direkt im Zentrum des Conduits (0.5 ist die vertikale Mitte des Blocks)
            for (int i = 0; i < 3; i++) {
                double offsetX = (random.nextDouble() - 0.5) * 1; // Enge Aura
                double offsetY = (random.nextDouble() - 0.5) * 1;
                double offsetZ = (random.nextDouble() - 0.5) * 1;

                world.addParticle(
                        ParticleTypes.REVERSE_PORTAL,
                        pos.getX() + 0.5 + offsetX,
                        pos.getY() + 0.5 + offsetY, // Hier auf 0.5 geändert, um es nach unten zu setzen
                        pos.getZ() + 0.5 + offsetZ,
                        0.01 * (random.nextDouble() - 0.5),
                        0.01 * (random.nextDouble() - 0.5),
                        0.01 * (random.nextDouble() - 0.5)
                );
            }
        }

        ci.cancel();

    }
}
