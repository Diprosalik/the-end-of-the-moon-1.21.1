package net.diprosalik.the_end_of_the_moon.block;

import net.diprosalik.the_end_of_the_moon.ChorusRootlingBlock;
import net.diprosalik.the_end_of_the_moon.ChorusShroomBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlock {

    public static final Block CHORUS_ROOTLING = new ChorusRootlingBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.PALE_PURPLE)
                    .ticksRandomly() // Wichtig für die Verwandlung zur Blume!
                    .strength(0.4F)
                    .sounds(BlockSoundGroup.WART_BLOCK)
                    .nonOpaque()
    );

    public static final Block CHORUS_SHROOM = new ChorusShroomBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.PALE_PURPLE)
                    .strength(0.1F) // Sehr leicht zu zerbrechen
                    .sounds(BlockSoundGroup.GRASS)
                    .nonOpaque()
                    .breakInstantly()
    );

    // Hilfsmethode zur Registrierung
    public static void registerModBlocks() {
        Registry.register(Registries.BLOCK, Identifier.of("the-end-of-the-moon", "chorus_rootling"), CHORUS_ROOTLING);
        Registry.register(Registries.BLOCK, Identifier.of("the-end-of-the-moon", "chorus_shroom"), CHORUS_SHROOM);
    }
}
