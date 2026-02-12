package net.diprosalik.the_end_of_the_moon.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkGenerator.class)
public class EndIslandExtenderMixin {

    @Inject(method = "generateFeatures", at = @At("TAIL"))
    private void extendIslandsDown(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo ci) {
        if (world.getDimension().hasFixedTime() && !world.getDimension().hasSkyLight()) {

            BlockPos.Mutable mutablePos = new BlockPos.Mutable();

            for (int x = 0; x < 16; x += 8) {
                for (int z = 0; z < 16; z += 8) {
                    for (int y = 120; y > 40; y--) {
                        mutablePos.set(chunk.getPos().getStartX() + x, y, chunk.getPos().getStartZ() + z);

                        if (world.getBlockState(mutablePos).isOf(Blocks.END_STONE)) {
                            fillDown(world, mutablePos.toImmutable());
                            break;
                        }
                    }
                }
            }
        }
    }

    @Unique
    private void fillDown(StructureWorldAccess world, BlockPos centerPos) {
        Random random = world.getRandom();
        int baseMaxHeight = 15 + random.nextInt(20);
        int startRadius = 3 + random.nextInt(2);

        for (int dx = -startRadius; dx <= startRadius; dx++) {
            for (int dz = -startRadius; dz <= startRadius; dz++) {
                double distanceSq = dx * dx + dz * dz;

                if (distanceSq <= startRadius * startRadius) {
                    double radiusFactor = 1.0 - (Math.sqrt(distanceSq) / (startRadius + 0.5));
                    int columnHeight = (int) (baseMaxHeight * radiusFactor);

                    if (columnHeight > 2) {
                        columnHeight -= random.nextInt(4);
                    }

                    for (int yOffset = 0; yOffset < columnHeight; yOffset++) {
                        int currentY = centerPos.getY() - yOffset;
                        if (currentY <= world.getBottomY()) break;

                        BlockPos targetPos = new BlockPos(centerPos.getX() + dx, currentY, centerPos.getZ() + dz);

                        if (world.getBlockState(targetPos).isAir()) {
                            world.setBlockState(targetPos, Blocks.END_STONE.getDefaultState(), 2);
                        }
                    }
                }
            }
        }
    }
}
