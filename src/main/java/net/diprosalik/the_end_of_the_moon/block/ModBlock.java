package net.diprosalik.the_end_of_the_moon.block;

import net.diprosalik.the_end_of_the_moon.HarvestedChorusFlower;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlock {

    // Hier definierst du deinen neuen Block
    public static final Block CHORUS_FLOWER_HARVESTED = new HarvestedChorusFlower(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.PALE_PURPLE)
                    .ticksRandomly() // Wichtig für das Nachwachsen!
                    .strength(0.4F)
                    .sounds(BlockSoundGroup.WART_BLOCK)
                    .nonOpaque() // Da der Block kleiner ist (12x12)
    );

    // Hilfsmethode zur Registrierung
    public static void registerModBlocks() {
        Registry.register(Registries.BLOCK, Identifier.of("the_end_of_the_moon", "chorus_flower_harvested"), CHORUS_FLOWER_HARVESTED);
    }
}
